(ns test-ratio-analyzer.golang-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.golang :refer :all]))

(deftest test-is-test?
  (testing "should return true for files that end with test"
    (is (is-test? (test-file "golang" "math_test.go"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "golang" "math.go"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 5 (count-lines (test-file "golang" "math.go"))))))
