(ns test-ratio-analyzer.python-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.python :refer :all]))

(deftest test-is-test?
  (testing "should return true for files starting with test"
    (is (is-test? (test-file "python" "test_bla.py"))))
  (testing "should return true for files ending with test"
    (is (is-test? (test-file "python" "bla_test.py"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "python" "bla.py"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 4 (count-lines (test-file "python" "bla.py"))))))
