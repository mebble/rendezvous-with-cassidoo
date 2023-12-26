;; https://buttondown.email/cassidoo/archive/in-seed-time-learn-in-harvest-teach-in-winter/
;; https://twitter.com/cassidoo/status/1739195022703239465

(import [java.time LocalDate Month]
        [java.time.format DateTimeFormatter])

(def formatter (DateTimeFormatter/ofPattern "MMM d, u"))

(defn- is-december [localdate]
  (= Month/DECEMBER (.getMonth localdate)))

(defn return-gift [datestring]
  (let [d           (LocalDate/parse datestring formatter)
        window-days (if (is-december d) 90 30)]
    (-> d
        (.plusDays (dec window-days))
        (.format formatter))))

(assert (= "Mar 23, 2024" (return-gift "Dec 25, 2023")))
(assert (= "Dec 24, 2023" (return-gift "Nov 25, 2023")))
