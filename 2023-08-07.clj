;; https://buttondown.email/cassidoo/archive/it-isnt-the-mountains-ahead-to-climb-that-wear/
;; https://twitter.com/cassidoo/status/1688379699083288576

;; https://stackoverflow.com/q/72768/5811761

(defn digit-seq [n]
  ;; #util
  (->> n
       (str)
       (map #(Integer/parseInt (str %)))))

(defn generate [xs]
  ;; #util !infinite
  (apply concat (repeat xs)))

(defn sum-digits
  "Adds the digits of int n as long as n <= 19 https://math.stackexchange.com/q/1221698/318706"
  [n]
  (if (and (>= n 10) (<= n 19))
    (- n 9)
    n))

(defn get-check-digit [payload]
  (->> (map (fn [x y] (* x y))
            (reverse (digit-seq payload))
            (generate [2 1]))
       (map sum-digits)
       (apply +)
       ((fn [s] (- 10 (mod s 10))))))

(defn luhn-check [n]
  (let [given-check-digit (mod n 10)
        payload (quot n 10)]
    (= given-check-digit (get-check-digit payload))))

(assert (= false (luhn-check 123456789)))
(assert (= true (luhn-check 5555555555554444)))
