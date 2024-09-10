(ns redis-embedded-clj.redis
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig])
  (:import
    (redis.embedded  RedisServer)))

(defn ->rd [port]
  (try
    (let [rd (RedisServer. port)]
      (.start rd)
      (if (.isActive rd)
        (do
          (log/info "Redis server started on port:" port)
          rd)
        (do
          (log/error "Failed to start Redis server on port:" port)
          nil)))
    (catch Exception e
      (log/error e "Exception occurred while starting Redis server"))))

(defn halt! [rd]
  (when rd
    (try
      (.stop rd)
      (log/info "Redis server stopped")
      (catch Exception e
        (log/error e "Exception occurred while stopping Redis server")))))

(defmethod ig/init-key ::redis [_ {:keys [port]}]
  (->rd port))

(defmethod ig/halt-key! ::redis [_ rd]
  (halt! rd))
