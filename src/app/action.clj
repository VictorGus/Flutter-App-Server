(ns app.action
  (:require [clojure.java.io :as io]
            [cheshire.core   :as json]
            [org.httpkit.server :as server]
            [clojure.string     :as str]))

(def channels (atom {}))

(defn chat-handler [request]
  (server/with-channel request channel
    (swap! channels assoc channel request)
    (server/on-close channel (fn [_]
                               (swap! channels dissoc channel))))
  {:body {:message "test"}})

(defn create-message [ctx]
  (fn [request]
    (doseq [channel (keys @channels)]
      (server/send! channel request))))
