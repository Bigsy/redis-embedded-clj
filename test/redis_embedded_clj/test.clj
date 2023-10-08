(ns redis-embedded-clj.test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is testing use-fixtures]]
            [redis-embedded-clj.core :as sut]))

(use-fixtures :once sut/with-rd-fn)

(defn around-all
  [f]
  (sut/with-rd-fn {:port 54321
                   :log-redirect "wibble.log"} f))

(use-fixtures :once around-all)

(def db-spec {})
(deftest can-wrap-around
  (testing "using custom port"
    )

  (testing "using custom log redirect"
    ))
