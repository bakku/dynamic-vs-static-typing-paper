(ns test-ratio-analyzer.test-helper)

(defn test-file
  [lang file]
  (str (System/getProperty "user.dir")
       "/test/files/"
       lang
       "/"
       file))
