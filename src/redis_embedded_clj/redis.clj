(ns redis-embedded-clj.redis
  (:require [integrant.core :as ig]
            [clojure.java.io :as io])
  (:import
    (redis.embedded  RedisServer)))

(defn ->rd [port rd-log]
  (let [rd (-> (RedisServer. 6379)
             )]
    (.start rd)
    (prn (.isActive rd))
    rd


    ))

(defn halt! [rd]
  (when rd
    (.stop rd)))

(defmethod ig/init-key ::redis [_ {:keys [port log-redirect]}]
  (->rd port log-redirect))

(defmethod ig/halt-key! ::redis [_ rd]
  (halt! rd))