(ns test-ratio-analyzer.git-repo-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [test-ratio-analyzer.git-repo :refer :all]))

(deftest test-download-repo
  (testing "should download directory"
    (let [path (-> (download-repo "https://github.com/bakku/dotfiles")
                   io/as-file)]
      (is (.exists path))
      (is (.isDirectory path)))))
