(ns flames.core
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as str]
            [org.httpkit.server :as httpd]
            [cheshire.core :as json]
            [ring.middleware.params :as params]
            [riemann.jvm-profiler :as profiler]))

(defn- put!
  [state-atom {:keys [body]}]
  (let [lines (doall (line-seq (io/reader body)))
        events (for [line lines
                     :when (seq line)]
                 (json/parse-string line keyword))]
    (swap! state-atom assoc :events events)))

(defn- remove-line-numbers
  [frame]
  (str/replace frame #" \((.*):[-0-9]+\)$" " [$1]"))

(defn- events->traces
  [predicate events]
  (for [{:keys [description metric]} events
        :when (and description
                   (predicate description))
        :let [frames (->> (str/split description #"\n")
                          (map remove-line-numbers)
                          reverse)]]
    (str (str/join ";" frames) " " metric)))

(defn- create-script-file!
  []
  (let [tmpdir (System/getProperty "java.io.tmpdir")
        file (io/file tmpdir "flames-flamegraph.pl")]
    (when-not (.canRead file)
      (with-open [in (io/reader (io/resource "flames/flamegraph.pl"))
                  out (io/writer file)]
        (io/copy in out)))
    file))

(defn- traces->svg
  [traces]
  (let [script-file (create-script-file!)
        result (shell/sh "perl" (str script-file)
                         :in (str/join "\n" traces))
        {:keys [out exit]} result]
    (if (zero? exit)
      out
      (throw (ex-info "Failed to generate image" result)))))

(defn- params->trace-filter
  [{:strs [remove filter] :or {remove [], filter [".*"]}}]
  (let [param->patterns #(map re-pattern (if (vector? %) % [%]))
        remove-patterns (param->patterns remove)
        filter-patterns (param->patterns filter)]
    (fn [description]
      (let [match? #(re-find % description)]
        (and (some match? filter-patterns)
             (not-any? match? remove-patterns))))))

(defn- svg
  [state {:keys [params]}]
  (if-let [events (seq (:events state))]
    (let [filter (params->trace-filter params)
          traces (events->traces filter events)]
      (if (seq traces)
        {:body (traces->svg traces)
         :headers {"content-type" "image/svg+xml"}}
        {:status 500
         :body "No data match given filters"}))
    {:status 503
     :body "No data available yet"}))

(defn- handler
  [state {:keys [uri request-method] :as req}]
  (case [request-method uri]
    [:put "/events"] (put! state req)
    [:get "/flames.svg"] (svg @state req)
    {:body "Not found", :status 404}))

(defn start!
  [opts]
  (let [state (atom nil)
        handler (-> #(handler state %)
                    params/wrap-params)
        defaults {:port 54332, :host "localhost"}
        opts (merge defaults opts)
        server (httpd/run-server handler (select-keys opts [:host :port]))
        profiler (profiler/start (select-keys opts [:host :port :dt :load]))]
    {:profiler profiler, :server server}))

(defn stop!
  [{:keys [profiler server]}]
  (profiler/stop! profiler)
  (server))
