(ns redis-embedded-clj.redis
  (:require [integrant.core :as ig]
            [clojure.java.io :as io])
  (:import
    (redis.embedded  RedisServer)))

(defn ->rd [port]
  (let [rd (-> (RedisServer. port))]
    (.start rd)
    (prn (.isActive rd))
    rd))

(defn halt! [rd]
  (when rd
    (.stop rd)))

(defmethod ig/init-key ::redis [_ {:keys [port]}]
  (->rd port))

(defmethod ig/halt-key! ::redis [_ rd]
  (halt! rd))