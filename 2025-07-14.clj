;; https://buttondown.com/cassidoo/archive/first-we-build-the-tools-then-they-build-us/
;; https://x.com/cassidoo/status/1944671832059130282

(require '[clojure.string :as s])

(defn- check [pos cmd]
  (let [[x y] pos]
    (case cmd
      \h [x (dec y)]
      \j [(inc x) y]
      \k [(dec x) y]
      \l [x (inc y)]
      pos)))

(defn- move [grid pos cmd]
  (let [new-pos (check pos cmd)]
    (if (get-in grid new-pos)
      new-pos
      pos)))

(defn get-char-after-vim-cmds [s cmds]
  (let [grid (s/split-lines s)
        final-pos (reduce (partial move grid) [0 0] cmds)
        final-char (get-in grid final-pos)]
    final-char))

; Test cases:
; horizontal only
(def s1 "cat")
; lines have same length
(def s2 "hello
world")
; lines vary in length
(def s3 "Hello, world!
how are ya?")

(do
  (assert (= \c (get-char-after-vim-cmds s1 "")))
  (assert (= \t (get-char-after-vim-cmds s1 "ll")))
  (assert (= \e (get-char-after-vim-cmds s2 "jllkh")))
  (assert (= \c (get-char-after-vim-cmds s1 "h")))
  (assert (= \w (get-char-after-vim-cmds s2 "jj")))
  (assert (= \d (get-char-after-vim-cmds s2 "jlllllllllll")))
  (assert (= \? (get-char-after-vim-cmds s3 "jlllllllllll")))
  (assert (= \w (get-char-after-vim-cmds s3 "jlhll"))))

