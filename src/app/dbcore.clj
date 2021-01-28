(ns app.dbcore
  (:require [clojure.java.jdbc   :as jdbc]
            [cheshire.core       :as json]
            [monger.core         :as mg]
            [monger.credentials  :as mg-cred]
            [monger.collection   :as mg-col]
            [monger.conversion   :as mg-conv]
            [app.manifest        :as m]))

(def db-connection (delay
                    (let [connection (mg/connect-with-credentials "localhost" 27017 (mg-cred/create "root" "chat-db" (.toCharArray "root")))
                          db         (mg/get-db connection "chat-db")]
                      db)))

(mg-col/find-maps @db-connection "Documents")
