(ns test-ratio-analyzer.clojure
  (:require [test-ratio-analyzer.io-helper :as io-helper]))

(defn is-test?
  [file]
  (io-helper/file-ends-with? file "_test.clj"))

(defn- ignored-line?
  [line]
  (or (clojure.string/blank? line)
      (clojure.string/starts-with? line ";")))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (remove ignored-line?)
       count))
