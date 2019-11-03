(ns test-ratio-analyzer.clojure-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.clojure :refer :all]))

(deftest test-is-test?
  (testing "should return true for files ending with test"
    (is (is-test? (test-file "clojure" "bla_test.clj"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "clojure" "bla.clj"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 4 (count-lines (test-file "clojure" "bla.clj"))))))
