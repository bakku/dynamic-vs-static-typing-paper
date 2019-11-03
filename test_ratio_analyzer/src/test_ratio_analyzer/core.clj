(ns test-ratio-analyzer.core
  (:require [test-ratio-analyzer.io-helper :as io-helper]
            [test-ratio-analyzer.git-repo  :as git-repo]
            [test-ratio-analyzer.ruby      :as ruby]
            [test-ratio-analyzer.golang    :as golang]
            [test-ratio-analyzer.python    :as python]
            [test-ratio-analyzer.clojure   :as clojure]
            [test-ratio-analyzer.java      :as java])
  (:gen-class))

(def supported-languages
  {"ruby"    {:suffix        ".rb"
              :fn-test-pred  ruby/is-test?
              :fn-line-count ruby/count-lines}
   "golang"  {:suffix ".go"
              :fn-test-pred  golang/is-test?
              :fn-line-count golang/count-lines}
   "python"  {:suffix ".py"
              :fn-test-pred  python/is-test?
              :fn-line-count python/count-lines}
   "clojure" {:suffix ".clj"
              :fn-test-pred clojure/is-test?
              :fn-line-count clojure/count-lines}
   "java"    {:suffix ".java"
              :fn-test-pred java/is-test?
              :fn-line-count java/count-lines}})

(defn- get-repo-code-files
  [repo-url save-at code-file-suffix]
  (-> (git-repo/download-repo repo-url save-at)
      (git-repo/filter-files code-file-suffix)))

(defn- analyze-tests
  [files {:keys [fn-test-pred fn-line-count] :as language}]
  (let [test-files (filter (:fn-test-pred language) files)]
    {:test-files (count test-files)
     :test-loc   (reduce + (map (:fn-line-count language) test-files))}))

(defn- analyze-implementation
  [files {:keys [fn-test-pred fn-line-count] :as language}]
  (let [implementation-files (remove fn-test-pred files)]
    {:implementation-files (count implementation-files)
     :implementation-loc   (reduce + (map fn-line-count implementation-files))}))

(defn- analyze-repo
  [repo-url {:keys [suffix] :as language}]
  (io-helper/remove-after [tmp (io-helper/temp-dir)]
    (let [code-files              (get-repo-code-files repo-url tmp suffix)
          test-analysis           (analyze-tests code-files language)
          implementation-analysis (analyze-implementation code-files language)]
      (merge test-analysis implementation-analysis))))

(defn- print-analysis
  [{:keys [test-files test-loc implementation-files implementation-loc]}]
  (println "Test files:"           test-files)
  (println "Test LOC:"             test-loc)
  (println "Implementation files:" implementation-files)
  (println "Implementation LOC:"   implementation-loc)
  (println "Ratio:"                (/ test-loc (float implementation-loc))))

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
