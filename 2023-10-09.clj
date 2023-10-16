;; https://buttondown.email/cassidoo/archive/all-great-achievements-require-time-maya-angelou/
;; https://twitter.com/cassidoo/status/1711285283906322781

(require '[clojure.string :as s])

;; ------------- Parse words -------------

;; key order matters for the algorithm to work
(def places (array-map "Million" 1000000 "Thousand" 1000 "Hundred" 100 "Base" 1))

(declare add-to-map)
(defn- place-words-map [words-seq]
  (->> (keys places)
       (reduce add-to-map [{} words-seq])
       (first)))

(defn- add-to-map [[places-map word-seq] place]
  (cond
    (= place "Base")               [(assoc places-map place word-seq) []]
    (some #{place} word-seq)       (let [[place-words [_ & remaining-words]] (split-with (complement #{place}) word-seq)]
                                     [(assoc places-map place (place-words-map place-words)) remaining-words])
    :else                          [(assoc places-map place nil) word-seq]))

;; ------------- Convert words to numeral -------------

;; Each key of the places-words-map is a sub-map of the same structure, i.e. it is recursive, except for the "Base" key
;; The base value has one or two words.
;; If two words, then the first word is a "ty" i.e. twenty, thirty, etc. The second word is a unit i.e. two, three, etc
;; If one word, then it could be a "unit" or it could be a "teen" i.e. "ten", "eleven", "twelve", "thirtee" etc
;; tys are mapped to numbers multiplied by 10
;; units and teens are mapped to numbers
(def ty->num {"Twenty" 20 "Thirty" 30 "Forty" 40 "Fifty" 50 "Sixty" 60 "Seventy" 70 "Eighty" 80 "Ninety" 90})
(def teen->num {"Ten" 10 "Eleven" 11 "Twelve" 12 "Thirteen" 13 "Fourteen" 14 "Fifteen" 15 "Sixteen" 16 "Seventeen" 17 "Eighteen" 18 "Nineteen" 19})
(def unit->num {"One" 1 "Two" 2 "Three" 3 "Four" 4 "Five" 5 "Six" 6 "Seven" 7 "Eight" 8 "Nine" 9})
(defn base->num [[f s]]
  (if (nil? s)
    (or (ty->num f)
        (teen->num f)
        (unit->num f))
    (+ (ty->num f)
       (unit->num s))))

(defn places-to-number [places-map]
  (if-not places-map
    0
    (->> (dissoc places "Base")
         (map (fn [[place mul]] (* mul (places-to-number (get places-map place)))))
         (cons (base->num (get places-map "Base")))
         (reduce +))))

;; ------------- Main -------------

(defn parse-number [words]
  (places-to-number (place-words-map (s/split words #" "))))

(assert (= 123 (parse-number "One Hundred Twenty Three")))  ;; simple
(assert (= 12345 (parse-number "Twelve Thousand Three Hundred Forty Five")))  ;; ends in unit
(assert (= 12340 (parse-number "Twelve Thousand Three Hundred Forty")))  ;; ends in ty
(assert (= 12316 (parse-number "Twelve Thousand Three Hundred Sixteen")))  ;; ends in teen
(assert (= 45045 (parse-number "Forty Five Thousand Forty Five")))  ;; multiple parts in thousands place
(assert (= 345035 (parse-number "Three Hundred Forty Five Thousand Thirty Five")))  ;; hundreds within thousands place
(assert (= 345000035 (parse-number "Three Hundred Forty Five Million Thirty Five")))  ;; hundreds within millions place
(assert (= 16503 (parse-number "Twelve Thousand Forty Five Hundred Three")))  ;; unconventional wording
