(ns test-ratio-analyzer.git-repo
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- remove-trailing-slash
  [string]
  (if (= \/ (last string))
    (string/join "" (drop-last string))
    string))

(defn- repo-zip-archive
  [url]
  (str (remove-trailing-slash url) "/archive/master.zip"))

(defn- zip-stream
  [path]
  (-> path
      io/input-stream
      (java.util.zip.ZipInputStream.)))

(defn- unzip-file
  [src dest]
  (clojure.java.shell/sh "sh"
                         "-c"
                         (str "unzip " src " -d " dest)))

(defn- download-file
  [src dest]
  (with-open [in  (io/input-stream (repo-zip-archive src))
              out (io/output-stream "temp.zip")]
    (io/copy in out))
  (unzip-file "temp.zip" dest)
  (.delete (io/as-file "temp.zip")))

;; Creates a temporary directory. FileAttributes need
;; to be passed to Java method so just pass empty array
(defn- temp-dir
  []
  (->> (into-array java.nio.file.attribute.FileAttribute [])
       (java.nio.file.Files/createTempDirectory nil)))

(defn download-repo
  [src]
  (let [dest (temp-dir)]
    (download-file src dest)
    (.toString dest)))
