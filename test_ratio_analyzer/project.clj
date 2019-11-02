(defproject test_ratio_analyzer "0.1.0-SNAPSHOT"
  :description "Analyzes the code to test ratio of a given github repository"
  :url "https://github.com/bakku/dynamic-vs-static-typing-paper"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.match "0.3.0"]]
  :main ^:skip-aot test-ratio-analyzer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
