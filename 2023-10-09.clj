;; https://buttondown.email/cassidoo/archive/all-great-achievements-require-time-maya-angelou/
;; https://twitter.com/cassidoo/status/1711285283906322781

(require '[clojure.string :as s])

;; ------------- Parse words -------------

(def places ["Million" "Thousand" "Hundred" "Base"])

(defn- add-to-map [[places-map word-seq] place]
  (cond
    (= place "Base")               [(assoc places-map place word-seq) []]
    (some #{place} word-seq)       (let [[place-words [_ & remaining-words]] (split-with (complement #{place}) word-seq)]
                                     [(assoc places-map place place-words) remaining-words])
    :else                          [(assoc places-map place nil) word-seq]))

(defn- place-words-map [words]
  (->> places
       (reduce add-to-map [{} (s/split words #" ")])
       (first)))

;; ------------- Convert words to numeral -------------

;; Each key of the places-words-map could have one or two words.
;; If two words, then the first word is a "ty" i.e. twenty, thirty, etc. The second word is a unit i.e. two, three, etc
;; If one word, then it could be a "unit" or it could be a "teen" i.e. "ten", "eleven", "twelve", "thirtee" etc
;; units and teens are mapped to numbers
;; tys are mapped to numbers multiplied by 10
(def ty->num {"Twenty" 20 "Thirty" 30 "Forty" 40 "Fifty" 50 "Sixty" 60 "Seventy" 70 "Eighty" 80 "Ninety" 90})
(def unit->num {"One" 1 "Two" 2 "Three" 3 "Four" 4 "Five" 5 "Six" 6 "Seven" 7 "Eight" 8 "Nine" 9})
(def teen->num {"Ten" 10 "Eleven" 11 "Twelve" 12 "Thirteen" 13 "Fourteen" 14 "Fifteen" 15 "Sixteen" 16 "Seventeen" 17 "Eighteen" 18 "Nineteen" 19})

;; ------------- Main -------------

(defn parse-number [words]
  (place-words-map words))

(assert (= 45045 (parse-number "Forty Five Thousand Forty Five")))
(assert (= 12345 (parse-number "Twelve Thousand Three Hundred Forty Five")))
(assert (= 16503 (parse-number "Twelve Thousand Forty Five Hundred Three")))
(assert (= 16503 (parse-number "Sixteen Thousand Five Hundred Three")))
(assert (= 123 (parse-number "One Hundred Twenty Three")))
