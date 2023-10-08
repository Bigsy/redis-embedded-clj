(ns redis-embedded-clj.test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is testing use-fixtures]]
            [redis-embedded-clj.core :as sut]
            [taoensso.carmine :as car]))

(def opts {:pool (car/connection-pool {}) :spec (car/make-conn-spec)})

(use-fixtures :once sut/with-rd-fn)

(defn around-all
  [f]
  (sut/with-rd-fn {:port 6379} f))

(use-fixtures :once around-all)

(def db-spec {})
(deftest can-wrap-around
  (testing "using default port"
    (is (= "OK" (car/wcar opts (car/set "wibble" "wobble"))))))
