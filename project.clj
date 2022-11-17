(defproject flames "0.4.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.9.6"
                  :exclusions [commons-fileupload
                               commons-io
                               crypto-equality
                               crypto-random
                               org.clojure/tools.reader]]
                 [riemann-jvm-profiler "0.1.1"
                  :exclusions [org.clojure/clojure]]
                 [http-kit "2.6.0"]])
