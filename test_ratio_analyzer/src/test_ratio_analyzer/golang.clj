(ns test-ratio-analyzer.golang
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]
            [test-ratio-analyzer.io-helper :as io-helper]))

(defn is-test?
  [file]
  (io-helper/file-ends-with? file "_test.go"))

(defn- ignored-line?
  [line]
  (or (string/blank? line)
      (string/starts-with? line "//")
      (and (string/starts-with? line "/*") (string/ends-with? line "*/"))))

(defn- block-comment-in-line?
  [line]
  (and (string/includes? line "/*")
       (not (string/includes? line "*/"))))

(defn- filter-block-comments
  [lines]
  (loop [remaining        lines
         filtered         []
         in-block-comment false]
    (match [remaining in-block-comment]
      [([] :seq) _]          filtered
      [([h & t] :seq) false] (cond
                               (string/starts-with? h "/*") (recur t filtered true)
                               (block-comment-in-line? h)   (recur t (conj filtered h) true)
                               :else                        (recur t (conj filtered h) false))
      [([h & t] :seq) true]  (if (string/ends-with? h "*/")
                               (recur t filtered false)
                               (recur t filtered true)))))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (remove ignored-line?)
       (filter-block-comments)
       count))
