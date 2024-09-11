(defproject org.clojars.bigsy/redis-embedded-clj "0.0.3"
  :description "Embedded redis for clojure"
  :url "https://github.com/Bigsy/redis-embedded-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [integrant "0.11.0"]
                 [org.signal/embedded-redis "0.9.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [org.clojure/tools.namespace "1.5.0"]]

  :profiles {:dev {:dependencies [[com.taoensso/carmine "3.4.1"]]}})
