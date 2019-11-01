(ns test-ratio-analyzer.core
  (:require [test-ratio-analyzer.io-helper :as io-helper]
            [test-ratio-analyzer.git-repo :as git-repo]
            [test-ratio-analyzer.ruby :as ruby])
  (:gen-class))

(def supported-languages
  {"ruby"   {:suffix        ".rb"
             :fn-test-pred  ruby/is-test?
             :fn-line-count ruby/count-lines}
   "golang" {:suffix ".go"}})

(defn- get-repo-code-files
  [repo-url save-at code-file-suffix]
  (-> (git-repo/download-repo repo-url save-at)
      (git-repo/filter-files code-file-suffix)))

(defn- analyze-tests
  [files language]
  (let [test-files (filter (:fn-test-pred language) files)]
    {:test-files (count test-files)
     :test-loc   (reduce + (map (:fn-line-count language) test-files))}))

(defn- analyze-implementation
  [files language]
  (let [implementation-files (remove (:fn-test-pred language) files)]
    {:implementation-files (count implementation-files)
     :implementation-loc   (reduce + (map (:fn-line-count language) implementation-files))}))

(defn- analyze-repo
  [repo-url language]
  (io-helper/remove-after [tmp (io-helper/temp-dir)]
    (let [code-files              (get-repo-code-files repo-url tmp (:suffix language))
          test-analysis           (analyze-tests code-files language)
          implementation-analysis (analyze-implementation code-files language)]
      (merge test-analysis implementation-analysis))))

(defn- print-analysis
  [analysis]
  (println "Test files:"           (:test-files analysis))
  (println "Test LOC:"             (:test-loc analysis))
  (println "Implementation files:" (:implementation-files analysis))
  (println "Implementation LOC:"   (:implementation-loc analysis))
  (println "Ratio:"                (/ (:test-loc analysis) (float (:implementation-loc analysis)))))

(defn -main
  [& args]
  (let [repo-url (first args)
        language (second args)]
    (if (contains? supported-languages language)
      (do
        (-> (analyze-repo repo-url (get supported-languages language))
            print-analysis)
        (System/exit 0)) ;; because we use clojure.java.shell which uses futures
      (println "Unsupported language given"))))
