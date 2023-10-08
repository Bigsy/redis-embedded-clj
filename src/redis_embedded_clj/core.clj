(ns redis-embedded-clj.core
  (:require [clojure.pprint :as pprint]
            [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [redis-embedded-clj.state :as state]))

(def default-config
  {:port         5432
   :log-redirect nil})

(defn ->ig-config [config]
  {:redis-embedded-clj.redis/redis {:port (:port config)
                                       :log-redirect (:log-redirect config)}})

(defn halt-rd! []
  (when @state/state
    (swap! state/state
           (fn [s]
             (ig/halt! (:system s))
             nil))))

(defn init-rd
  ([] (init-rd default-config))
  ([config]
   (let [ig-config (->ig-config config)
         config-pp (with-out-str (pprint/pprint config))]
     (log/info "starting embedded-redis with config:" config-pp)
     (try
       (halt-rd!)
       (ig/load-namespaces ig-config)
       (reset! state/state
               {:system (ig/init ig-config)
                :config ig-config})
       (catch clojure.lang.ExceptionInfo ex
         (ig/halt! (:system (ex-data ex)))
         (throw (.getCause ex)))))))

(defn with-rd-fn
  "Startup with the specified configuration; executes the function then shuts down."
  ([config f]
   {:pre [(map? config) (fn? f)]}
   (try
     (init-rd config)
     (f)
     (finally
       (halt-rd!))))
  ([f]
   (with-rd-fn default-config f)))

(defmacro with-rd
  "Startup with the specified configuration; executes the body then shuts down."
  [config & body]
  `(with-rd-fn ~config (fn [] ~@body)))