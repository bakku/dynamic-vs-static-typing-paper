(ns test-ratio-analyzer.comment-detection
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]))

(defn- block-comment-in-line?
  [start end line]
  (and (string/includes? line start)
       (not (string/includes? line end))))

(defn filter-block-comments
  [start end lines]
  (loop [remaining        lines
         filtered         []
         in-block-comment false]
    (match [remaining in-block-comment]
      [([] :seq) _]          filtered
      [([h & t] :seq) false] (cond
                               (string/starts-with? h start)        (recur t filtered true)
                               (block-comment-in-line? start end h) (recur t (conj filtered h) true)
                               :else                                (recur t (conj filtered h) false))
      [([h & t] :seq) true]  (if (string/ends-with? h end)
                               (recur t filtered false)
                               (recur t filtered true)))))
