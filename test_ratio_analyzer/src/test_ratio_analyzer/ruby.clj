(ns test-ratio-analyzer.ruby
  (:require [test-ratio-analyzer.io-helper :as io-helper]))

(defn is-test?
  [file]
  (or (io-helper/file-ends-with? file "_test.rb")
      (io-helper/file-ends-with? file "_spec.rb")
      (io-helper/file-contains-line? file #"^(Rspec\.)?describe")
      (io-helper/file-contains-line? file #"class.+<.+TestCase")
      (io-helper/file-contains-line? file #"class.+<.+Minitest::Test")))

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
