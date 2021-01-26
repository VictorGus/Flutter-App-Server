(ns app.dbcore
  (:require [honeysql.core       :as hsql]
            [hikari-cp.core      :as hc]
            [clojure.java.jdbc   :as jdbc]
            [cheshire.core       :as json]
            [dsql.pg             :as dsql]
            [clj-postgresql.core :as pg]
            [app.manifest        :as m]))

(def pool-config (delay (pg/pool :host     (get-in m/app-config [:db :host])
                                 :port     (get-in m/app-config [:db :port])
                                 :user     (get-in m/app-config [:db :user])
                                 :password (get-in m/app-config [:db :password])
                                 :dbname   (get-in m/app-config [:db :dbname])
                                 :hikari {:read-only true})))

(def test-config (delay (pg/pool :host     (get-in m/app-config [:db :host])
                                 :port     (get-in m/app-config [:db :port])
                                 :user     (get-in m/app-config [:db :user])
                                 :password (get-in m/app-config [:db :password])
                                 :dbname   "fortest"
                                 :hikari {:read-only true})))

(defn query [query ctx & [format]]
  (let [format (or format :hsql)]
    (case format
      :hsql
      (->> query hsql/format
           (jdbc/query @ctx))

      :dsql
      (->> query dsql/format
           (jdbc/query @ctx))

      (throw (Exception. (str "Unknown format " format " supported formats :hsql :dsql"))))))

(defn query-first [query ctx & [format]]
  (let [format (or format :hsql)]
    (case format
      :hsql
      (->> query hsql/format
           (jdbc/query @ctx)
           first)

      :dsql
      (->> query dsql/format
           (jdbc/query @ctx)
           first)

      (throw (Exception. (str "Unknown format " format " supported formats :hsql :dsql"))))))

(defn execute [query ctx & [format]]
  (let [format (or format :hsql)]
    (case format
      :hsql
      (->> query hsql/format
           (jdbc/execute! @ctx)
           first)

      :dsql
      (->> query dsql/format
           (jdbc/execute! @ctx)
           first)

      (throw (Exception. (str "Unknown format " format " supported formats :hsql :dsql"))))))

(comment

  (query-first {:select [:*]
                :from [[:public_user :p]]
                :left-join [[:settings :s] [:= :s.id :p.id]]} pool-config)

  (query {:select [:resource]
          :from [:public_user]} pool-config)

  (query (hsql/raw "select * from public_user p left join settings s on p.settings_id = s.id") pool-config)

 {:name {:family "Test"
         :given "Test"}
  :email "test@gmail.com"
  :username "test"
  :age "45"
  :active true
  :password "encrypted"}
  )


