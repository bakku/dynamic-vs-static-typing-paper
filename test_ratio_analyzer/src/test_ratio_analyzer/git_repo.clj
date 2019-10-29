(ns test-ratio-analyzer.git-repo
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [test-ratio-analyzer.io-helper :as io-helper]))

(defn- remove-trailing-slash
  [string]
  (if (= \/ (last string))
    (string/join "" (drop-last string))
    string))

(defn- repo-zip-archive
  [url]
  (str (remove-trailing-slash url) "/archive/master.zip"))

(defn- download-file
  [src dest]
  (with-open [in  (io/input-stream (repo-zip-archive src))
              out (io/output-stream "temp.zip")]
    (io/copy in out))
  (io-helper/unzip-file "temp.zip" dest)
  (.delete (io/as-file "temp.zip")))

(defn download-repo
  [src]
  (let [dest (io-helper/temp-dir)]
    (download-file src dest)
    (.toString dest)))

(defn- all-files-rec
  [file]
  (if (.isDirectory file)
    (map all-files-rec (.listFiles file))
    [file]))

(defn- all-files
  [file]
  (-> (io/as-file file)
      all-files-rec
      flatten
      vec))

(defn filter-files
  [path suffix]
  (->> (all-files path)
       (mapv #(.getAbsolutePath %))
       (filterv #(clojure.string/ends-with? % suffix))))
