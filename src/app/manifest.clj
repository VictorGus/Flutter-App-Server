(ns app.manifest)

(def app-config
  {:db {:host     (or (System/getenv "PGHOST")     "localhost")
        :port     (or (System/getenv "PGPORT")     5443)
        :user     (or (System/getenv "PGUSER")     "postgres")
        :password (or (System/getenv "PGPASSWORD") "postgres")
        :dbname   (or (System/getenv "PGDATABASE") "mobiledb")}
   :app {:port    (or (System/getenv "PORT") 9090)}})
