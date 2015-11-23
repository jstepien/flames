(defproject flames "0.2.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.2"
                  :exclusions [clj-time
                               commons-fileupload
                               commons-io
                               crypto-equality
                               crypto-random
                               org.clojure/tools.reader]]
                 [riemann-jvm-profiler "0.1.0"]])
