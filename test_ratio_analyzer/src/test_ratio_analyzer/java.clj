(ns test-ratio-analyzer.java
  (:require [clojure.string :as string]
            [test-ratio-analyzer.io-helper :as io-helper]
            [test-ratio-analyzer.comment-detection :as comments]))

(defn is-test?
  [file]
  (io-helper/file-ends-with? file "Test.java"))

(defn- ignored-line?
  [line]
  (or (string/blank? line)
      (string/starts-with? line "//")
      (and (string/starts-with? line "/*") (string/ends-with? line "*/"))))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (remove ignored-line?)
       (comments/filter-block-comments "/*" "*/")
       count))
