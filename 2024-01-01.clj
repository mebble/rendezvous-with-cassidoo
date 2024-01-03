;; https://buttondown.email/cassidoo/archive/learn-from-yesterday-live-for-today-hope-for/
;; https://twitter.com/cassidoo/status/1741732644134506696

(require '[clojure.math :as m])

(defn- encode [s]
  (->> s
       (map (comp str int))
       (apply str)
       (BigInteger.)))

(defn- take-while+
  "https://stackoverflow.com/a/30928487/5811761"
  [pred coll]
  (lazy-seq
   (when-let [[f & r] (seq coll)]
     (if (pred f)
       (cons f (take-while+ pred r))
       [f]))))

(defn- consume
  "consumes the x least significant digits of n"
  [x n]
  (let [div (int (m/pow 10 x))]
    (vector (quot n div) (mod n div))))

(defn- consume-ascii [[n _]]
  (let [[_ least-sig] (consume 2 n)]
    (if (< least-sig 32)
      (consume 3 n)
      (consume 2 n))))

(defn- decode [n]
  (->> (iterate consume-ascii [n 0])
       (take-while+ (fn [[n _]] (> n 0)))
       (rest)
       (map (comp char second))
       (reverse)
       (apply str)))

(defn greet [numerical-msg]
  (println (decode numerical-msg)))

(greet 729711211212132110101119321211019711433) ; => Happy new year!
