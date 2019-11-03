(ns test-ratio-analyzer.java-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.java :refer :all]))

(deftest test-is-test?
  (testing "should return true for files that end with test"
    (is (is-test? (test-file "java" "BlaTest.java"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "java" "Bla.java"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 6 (count-lines (test-file "java" "Bla.java"))))))
