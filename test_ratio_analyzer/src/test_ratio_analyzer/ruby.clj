(ns test-ratio-analyzer.ruby
  (:require [test-ratio-analyzer.io-helper :as io-helper]
            [clojure.java.io :as io]))

(defn is-test?
  [file]
  (or (io-helper/file-ends-with? file "_test.rb")
      (io-helper/file-ends-with? file "_spec.rb")
      (io-helper/file-contains-line? file #"^(Rspec\.)?describe")))

(defn- ignored-line?
  [line]
  (or (clojure.string/blank? line)
      (clojure.string/starts-with? line "#")
      (clojure.string/starts-with? line "require")))

(defn count-lines
  [file]
  (->> (io-helper/all-lines file)
       (mapv clojure.string/trim)
       (remove ignored-line?)
       count))
