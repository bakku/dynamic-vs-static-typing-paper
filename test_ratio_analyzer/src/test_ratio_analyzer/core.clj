(ns test-ratio-analyzer.core
  (:require [test-ratio-analyzer.git-repo :as git-repo]
            [test-ratio-analyzer.ruby :as ruby])
  (:gen-class))

(defn repo-code-files
  [repo-url code-file-suffix]
  (-> (git-repo/download-repo repo-url)
      (git-repo/filter-files code-file-suffix)))

(defn -main
  [& args]
  (let [code-files (repo-code-files (first args) ".rb")
        test-files (filter ruby/is-test? code-files)
        test-loc (reduce + (map ruby/count-lines test-files))
        implementation-files (filter #(not (ruby/is-test? %)) code-files)
        implementation-loc (reduce + (map ruby/count-lines implementation-files))]
    (println "Test files:" (count test-files))
    (println "Test LOC:" test-loc)
    (println "Implementation files:" (count implementation-files))
    (println "Implementation LOC:" implementation-loc)
    (println "Ratio:" (/ test-loc (float implementation-loc)))
    (System/exit 0))) ;; because we use clojure.java.shell which uses futures
