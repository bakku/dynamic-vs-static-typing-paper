(ns test-ratio-analyzer.io-helper)

(defn unzip-file
  [src dest]
  (clojure.java.shell/sh "sh"
                         "-c"
                         (str "unzip " src " -d " dest)))

;; Creates a temporary directory. FileAttributes need
;; to be passed to Java method so just pass empty array
(defn temp-dir
  []
  (->> (into-array java.nio.file.attribute.FileAttribute [])
       (java.nio.file.Files/createTempDirectory nil)))
