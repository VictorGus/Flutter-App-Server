(ns build
  (:require [cambada.uberjar :as uberjar]
            [clojure.java.shell :as shell]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(defn shell [cmd]
  (println "$ " cmd)
  (let [res (apply shell/sh (str/split cmd #"\s+"))]
    (if (= 0 (:exit res))
      (str/replace (:out res) #"\s*\n$" "")
      (throw (Exception. (pr-str res))))))

(defn build []
  (println "Building uberjar")
  (uberjar/-main
   "-a" "all"
   "--app-group-id" "app"
   "--app-artifact-id" "app"
   "--app-version" "0.0.1"
   "-m" "clojure.main"))

(comment
  (-main)
  )

