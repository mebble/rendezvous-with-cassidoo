;; https://buttondown.email/cassidoo/archive/since-we-cannot-change-reality-let-us-change-the/

(require '[clojure.string :as s])

(defn- line-partitions [max-width lines next-word]
  (let [last-line (last lines)
        last-line-count (apply + (map count (interpose " " last-line)))
        next-word-count (count next-word)]
    (if (<= (+ last-line-count next-word-count 1) max-width)
      (vec (concat (butlast lines) (vector (conj (vec last-line) next-word))))
      (conj lines (vector next-word)))))

(defn- distribute-spaces
  "returns [size-of-space-between-adjacent-words, number-of-extra-spaces-to-distribute]"
  [num-space num-slot]
  (if (zero? num-slot) 
    [0 0]
    [(quot num-space num-slot) (mod num-space num-slot)]))

(defn num-spaces [max-width line]
  (->> line
       (s/join)
       (count)
       (- max-width)))
(def num-slots (comp dec count))

(defn- justify-line [max-width line]
  ;; https://stackoverflow.com/questions/18940629/using-map-with-different-sized-collections-in-clojure
  (let [[slot-size num-extra-spaces] (distribute-spaces (num-spaces max-width line) (num-slots line))
        spaces       (concat (repeat (num-slots line) (s/join (repeat slot-size " "))) (repeat ""))
        extra-spaces (concat (repeat num-extra-spaces " ")                             (repeat ""))]
    (->> [spaces extra-spaces]
         (apply map str)
         (interleave line)
         (s/join))))

(defn- pad-end
  "Pad a string s with the character c till the output becomes n characters long"
  ;; https://stackoverflow.com/questions/27262268/idiom-for-padding-sequences
  [c n s]
  (s/join (take n (concat s (repeat c)))))

(defn justify-text [words max-width]
  (->> words
       (reduce (partial line-partitions max-width) [])
       (map (partial justify-line max-width))
       (map (partial pad-end " " max-width))))

(assert (= (justify-text ["This" "is" "an" "example" "of" "text" "justification."] 16)
           ["This    is    an"
            "example  of text"
            "justification.  "]))
