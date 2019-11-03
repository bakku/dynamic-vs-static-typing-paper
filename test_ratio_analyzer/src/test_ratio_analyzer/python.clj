(ns test-ratio-analyzer.python
  (:require [clojure.string :as string]
            [test-ratio-analyzer.io-helper :as io-helper]
            [test-ratio-analyzer.comment-detection :as comments]))

(defn is-test?
  [file]
  (or (io-helper/file-starts-with? file "test_")
      (io-helper/file-ends-with? file "_test.py")))

(defn- ignored-line?
  [line]
  (or (clojure.string/blank? line)
      (clojure.string/starts-with? line "#")))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (remove ignored-line?)
       count))
