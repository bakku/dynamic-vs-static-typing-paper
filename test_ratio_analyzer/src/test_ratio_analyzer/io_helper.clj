(ns test-ratio-analyzer.io-helper
  (:require [clojure.java.shell :as shell]
            [clojure.java.io :as io]))

(defn delete-directory
  [path]
  (let [dir (io/as-file path)]
    (doseq [file (file-seq dir)
            :when (.isFile file)]
      (io/delete-file file))
    (doseq [subdir (reverse (rest (file-seq dir)))]
      (io/delete-file subdir))
    (io/delete-file dir)))

(defmacro remove-after
  [bindings & body]
  (cond
    (= (count bindings) 0) `(do ~@body)
    (symbol? (bindings 0)) `(let ~(subvec bindings 0 2)
                              (try
                                (remove-after ~(subvec bindings 2) ~@body)
                                (finally (delete-directory ~(bindings 0)))))))

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
       (java.nio.file.Files/createTempDirectory nil)
       (.toString)))

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
