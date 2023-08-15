;; https://buttondown.email/cassidoo/archive/normal-is-not-something-to-aspire-to-its-4437/
;; https://stackoverflow.com/questions/23849231/map-with-an-accumulator-in-clojure

(defn vertical-slashes
  [slashes]
  (->> (cons nil slashes)  ;; #util #pattern - look behind
       (map vector slashes)
       (reductions (fn [[_ spaces] [s s-prev]]
                     (case [s-prev s]
                       [\\ \\] [s (inc spaces)]
                       [\\ \/] [s spaces]
                       [\/ \\] [s spaces]
                       [\/ \/] [s (dec spaces)]
                       [s 0]))
                   nil)
       (rest)
       (map (fn [[slash pad]] (str (->> " " (repeat pad) (clojure.string/join)) slash)))
       (clojure.string/join "\n")))

(println (vertical-slashes "\\\\\\//\\/\\\\"))
(println (vertical-slashes "\\\\\\\\"))
