(ns test-ratio-analyzer.haskell-test
  (:require [clojure.test :refer :all]
            [test-ratio-analyzer.test-helper :refer :all]
            [test-ratio-analyzer.haskell :refer :all]))

(deftest test-is-test?
  (testing "should return true for HUnit tests"
    (is (is-test? (test-file "haskell" "HUnitTest.hs"))))
  (testing "should return true for files that end with Spec"
    (is (is-test? (test-file "haskell" "SomeSpec.hs"))))
  (testing "should return true for tasty tests"
    (is (is-test? (test-file "haskell" "TastyTest.hs"))))
  (testing "should return false otherwise"
    (is (not (is-test? (test-file "haskell" "Standard.hs"))))))

(deftest test-count-lines
  (testing "should return correct amount of lines"
    (is (= 4 (count-lines (test-file "haskell" "Standard.hs"))))))
