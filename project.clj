(defproject kimono-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.18"]
                 [org.clojure/data.json "0.2.6"]
                 [environ "1.0.0"]
                 [midje "1.7.0"]]

  :plugins [[lein-environ "1.0.0"]]

  :injections [(use 'midje.repl)])
