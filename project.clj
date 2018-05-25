(defproject flames "0.3.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.6.3"
                  :exclusions [commons-fileupload
                               commons-io
                               crypto-equality
                               crypto-random
                               org.clojure/tools.reader]]
                 [riemann-jvm-profiler "0.1.0"
                  :exclusions [clj-time]]
                 [http-kit "2.3.0"]])
