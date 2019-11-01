(ns test-ratio-analyzer.io-helper-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [test-ratio-analyzer.io-helper :refer :all]))

(deftest test-delete-directory
  (testing "Should remove empty directory"
    (let [path (temp-dir)]
      (delete-directory path)
      (is (not (.exists (io/as-file path))))))
  (testing "Should remove non empty directory"
    (let [path (temp-dir)]
      (.mkdir (io/as-file (str path "/hello")))
      (.mkdir (io/as-file (str path "/hello/bye")))
      (spit (str path "/test.txt") "content")
      (spit (str path "/hello/test.txt") "content")
      (delete-directory path)
      (is (not (.exists (io/as-file path)))))))

(deftest test-remove-after
  (testing "should remove path after evaluation"
    (let [path  (temp-dir)
          path2 (temp-dir)]
      (remove-after [dir  path
                     dir2 path2]
        (+ 1 1))
      (is (not (.exists (io/as-file path)))))))
