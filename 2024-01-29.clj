;; https://buttondown.email/cassidoo/archive/maybe-your-weird-is-my-normal-whos-to-say-nicki/
;; https://twitter.com/cassidoo/status/1751882123919634782

(import [java.time LocalDate]
        [java.time.format DateTimeFormatter]
        [java.time.temporal ChronoUnit])

(defmacro try-with-default [expr default-value]
  ;; #util
  `(try ~expr (catch Exception e# ~default-value)))

(def formatter (DateTimeFormatter/ofPattern "MMM d, y"))

(defn days-between [d1-str d2-str]
  (try-with-default
   (let [d1 (LocalDate/parse d1-str formatter)
         d2 (LocalDate/parse d2-str formatter)]
     (.between ChronoUnit/DAYS d1 d2))
   nil))

(assert (= 28 (days-between "Jan 1, 2024" "Jan 29, 2024")))
(assert (= -28 (days-between "Jan 29, 2024" "Jan 1, 2024")))
(assert (= 1340 (days-between "Feb 29, 2020" "Oct 31, 2023")))
(assert (= nil (days-between "foo" "bar")))
