(ns darong.kimono.core-test
  (:use midje.sweet)
  (:require [darong.kimono.core :refer :all]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]
            [environ.core :refer [env]]))

(defn http-get
  ([url params] (http-get url params (fn [b] (json/read-str b :key-fn keyword))))

  ([url params ok-fn]
   (let [result (http/get url {:query-params params})
         {:keys [status headers body error opts]} @result]
     (cond
       (= 200 status) (ok-fn body)
       :else [:no status error opts headers body]))))

(defn list-api [api-key]
  (http-get "https://www.kimonolabs.com/kimonoapis" {:apikey api-key}))

(defn read-api [api-id params]
  (let [url "https://www.kimonolabs.com/api"
        json-url (fn [id] (str url "/" id))
        csv-url (fn [id] (str url "/csv/" id))
        rss-url (fn [id] (str url "/rss/" id))
        kimono-params (fn [p] (dissoc p :format))]
    (cond
      (= :csv (:format params)) (http-get (csv-url api-id) (kimono-params params) identity)
      (= :rss (:format params)) (http-get (rss-url api-id) (kimono-params params) identity)
      :else (http-get (json-url api-id) (kimono-params params)))))

#_(list-api (env :kimono-apikey))

#_(read-api (env :kimono-apiid) {:apikey (env :kimono-apikey)})
#_(read-api (env :kimono-apiid) {:apikey (env :kimono-apikey) :format :csv})
#_(read-api (env :kimono-apiid) {:apikey (env :kimono-apikey) :format :rss})

;; TODO: implement the following

#_(read-api-ondemand "xxx")
#_(read-api-version "xxx" 1)

#_(read-api "xxx" {:apikey "xxx" :format :csv/no-headers})
#_(read-api "xxx" {:apikey "xxx" :format :csv/no-collection-headers})


#_(fact "apikey must set" (list-api "") => [:status/unauthorized nil])

#_(def api [:api-key :api-id])

#_(update-api api {:targeturl
                 :frequency
                 :crawllimit
                 :urls
                 })

#_(start-crawl api)

#_(export-data api [:api-key :api-id :format/csv :kimnoheaders {:ondemand
                                             :version
                                             :kimlimit
                                             :kimoffset
                                             :kimbypage
                                             :kimwithurl
                                             :kimindex
                                             :kimhash
                                             :kimseries
                                             :kimstats}]
                 [:format/rss {}])
