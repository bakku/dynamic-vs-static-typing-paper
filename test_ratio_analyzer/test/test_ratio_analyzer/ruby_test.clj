(ns test-ratio-analyzer.ruby-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.ruby :refer :all]))

(deftest test-is-test?
  (testing "should return true for files that end with test"
    (is (is-test? (test-file "ruby" "ruby_test.rb"))))
  (testing "should return true for files that end with spec"
    (is (is-test? (test-file "ruby" "ruby_spec.rb"))))
  (testing "should return true for files that contain describe ... do"
    (is (is-test? (test-file "ruby" "random_test_file.rb"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "ruby" "standard_file.rb"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 5 (count-lines (test-file "ruby" "random_test_file.rb"))))))
