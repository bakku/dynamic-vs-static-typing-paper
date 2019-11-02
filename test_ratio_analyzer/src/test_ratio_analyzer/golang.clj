(ns test-ratio-analyzer.golang
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]
            [test-ratio-analyzer.io-helper :as io-helper]))

(defn is-test?
  [file]
  (or (io-helper/file-ends-with? file "_test.go")))

(defn- ignored-line?
  [line]
  (or (string/blank? line)
      (string/starts-with? line "//")))

(defn- filter-block-comments
  [lines]
  (loop [remaining        lines
         filtered         []
         in-block-comment false]
    (match [remaining in-block-comment]
      [([] :seq) _]          filtered
      [([h & t] :seq) false] (if (string/starts-with? h "/*")
                               (recur t filtered true)
                               (recur t (conj filtered h) false))
      [([h & t] :seq) true]  (if (string/ends-with? h "*/")
                               (recur t filtered false)
                               (recur t filtered true)))))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (filter-block-comments)
       (remove ignored-line?)
       count))
