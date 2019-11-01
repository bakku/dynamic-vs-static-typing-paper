(ns test-ratio-analyzer.git-repo-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [test-ratio-analyzer.io-helper :as io-helper]
            [test-ratio-analyzer.git-repo :refer :all]))

(deftest test-download-repo
  (testing "should download directory"
    (let [path (io/as-file (io-helper/temp-dir))
          repo (download-repo "https://github.com/bakku/dotfiles" path)]
      (is (.exists path))
      (is (.isDirectory path))
      (io-helper/delete-directory path))))

(deftest test-filter-files
  (testing "should return vector of files that match suffix"
    (let [path (io-helper/temp-dir)]
      (.createNewFile (io/as-file (str path "/" "one.go")))
      (.createNewFile (io/as-file (str path "/" "two.rb")))
      (.mkdir (io/as-file (str path "/" "hello_world")))
      (.createNewFile (io/as-file (str path "/" "hello_world" "/" "three.go")))
      (.createNewFile (io/as-file (str path "/" "hello_world" "/" "four.rb")))
      (is (= 2 (count (filter-files path "rb")))))))
