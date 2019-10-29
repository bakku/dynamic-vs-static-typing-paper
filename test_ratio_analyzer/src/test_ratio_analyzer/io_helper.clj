(ns test-ratio-analyzer.io-helper
  (:require [clojure.java.shell :as shell]))

(defn unzip-file
  [src dest]
  (shell/sh "sh"
            "-c"
            (str "unzip " src " -d " dest)))

;; Creates a temporary directory. FileAttributes need
;; to be passed to Java method so just pass empty array
(defn temp-dir
  []
  (->> (into-array java.nio.file.attribute.FileAttribute [])
       (java.nio.file.Files/createTempDirectory nil)))

(defn file-ends-with?
  [file suffix]
  (-> file
      (.toString)
      (clojure.string/ends-with? suffix)))

(defn all-lines
  [file]
  (clojure.string/split (slurp file) #"\n"))

(defn file-contains-line?
  [file regex]
  (->> (all-lines file)
       (filter #(some? (re-find regex %)))
       empty?
       not))
