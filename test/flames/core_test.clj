(ns flames.core-test
  (:require [flames.core :refer [start! stop!]]
            [clojure.test :refer [deftest is]]))

(deftest test-basics
  (let [spin-until #(if (< (System/nanoTime) %)
                      (recur %))
        p (start! {:port 54312, :host "localhost", :load 1, :dt 0.05})]
    (try
      (spin-until (+ 4e8 (System/nanoTime)))
      (is (re-find #"flames\.core_test[^ ]+spin_until[^ ]+ invoke"
                   (slurp "http://0:54312/flames.svg")))
      (finally
        (stop! p)))))

(deftest test-closing
  (with-open [p (start! {:port 54312, :host "localhost"})]
    (is (thrown-with-msg? java.io.FileNotFoundException
                          #"http://0:54312/"
                          (slurp "http://0:54312/")))))
