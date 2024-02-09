;; https://buttondown.email/cassidoo/archive/people-who-are-truly-strong-lift-others-up-people/
;; https://twitter.com/cassidoo/status/1754422540519326070

(require '[clojure.string :as s])

(defn- repeat-s [n s]
  ;; #util
  (apply str (repeat n s)))

(defn- draw-space [n]
  (repeat-s n " "))

(defn- draw-line [n]
  (repeat-s n "_"))

(def inc2 (comp inc inc))
(def dec2 (comp dec dec))

(defn- slope-depth [scale]
  (if (even? scale)
    (/ scale 2)
    (/ (dec scale) 2)))

(defn- base-pad-size [scale]
  (-> (slope-depth scale)
      (dec)
      (* 2)
      (+ scale)))

(defn- left-pad-size [scale]
  (let [scale-double (* 2 scale)]
    (if (even? scale) scale-double (dec scale-double))))

(defn- render-rows [rows]
  (->> rows
       (filter seq)
       (s/join "\n")))

(defn- render-slope-row [parts]
  (apply str (mapcat (fn [[pad-size c]] (str (draw-space pad-size) c)) parts)))

(defn- render-slopes [scale pad-inits pad-changes slope-chars]
  (let [num-slope-rows (dec (slope-depth scale))]
    (->> pad-inits
         (iterate pad-changes)
         (take num-slope-rows)
         (map (fn [row] (map vector row slope-chars)))
         (map render-slope-row)
         (s/join "\n"))))

(defn- render-top-base-1 [scale]
  (let [pad-size (left-pad-size scale)]
    (str (draw-space pad-size) (draw-line scale))))

(defn- render-top-slopes [scale]
  (let [pad1 (dec (left-pad-size scale))
        pad2 scale]
    (render-slopes scale
                   [pad1 pad2]
                   (fn [[p1 p2]] [(dec p1) (inc2 p2)])
                   ["/" "\\"])))

(defn- render-top-base-2 [scale]
  (let [depth (slope-depth scale)
        pad-size (base-pad-size scale)]
    (str (draw-space depth)
         (str (draw-line scale) "/")
         (draw-space pad-size)
         (str "\\" (draw-line scale)))))

(defn- render-mid-slopes-1 [scale]
  (let [pad1 (dec (slope-depth scale))
        pad2 scale
        pad3 (base-pad-size scale)
        pad4 scale]
    (render-slopes scale
                   [pad1 pad2 pad3 pad4]
                   (fn [[p1 p2 p3 p4]] [(dec p1) (inc2 p2) (dec2 p3) (inc2 p4)])
                   ["/" "\\" "/" "\\"])))

(defn- render-mid-base-1 [scale]
  (let [pad-size (base-pad-size scale)]
    (str "/"
         (draw-space pad-size)
         "\\"
         (draw-line scale)
         "/"
         (draw-space pad-size)
         "\\")))

(defn- render-mid-slopes-2 [scale]
  (let [base-size (base-pad-size scale)
        pad1 0
        pad2 base-size
        pad3 scale
        pad4 base-size]
    (render-slopes scale
                   [pad1 pad2 pad3 pad4]
                   (fn [[p1 p2 p3 p4]] [(inc p1) (dec2 p2) (inc2 p3) (dec2 p4)])
                   ["\\" "/" "\\" "/"])))

(defn- render-mid-base-2 [scale]
  (let [depth (slope-depth scale)
        middle-pad-size (base-pad-size scale)]
    (str (draw-space (dec depth))
         "\\"
         (draw-line scale)
         "/"
         (draw-space middle-pad-size)
         "\\"
         (draw-line scale)
         "/")))

(defn- render-mid [scale]
  (render-rows [(render-mid-slopes-1 scale)
                (render-mid-base-1 scale)
                (render-mid-slopes-2 scale)
                (render-mid-base-2 scale)]))

(defn- render-btm-slopes [scale]
  (let [pad1 (+ scale (slope-depth scale))
        pad2 (base-pad-size scale)]
    (render-slopes scale
                   [pad1 pad2]
                   (fn [[p1 p2]] [(inc p1) (dec2 p2)])
                   ["\\" "/"])))

(defn- render-btm-base [scale]
  (str (draw-space (dec (left-pad-size scale)))
       "\\"
       (draw-line scale)
       "/"))

(defn honeycomb [size]
  (let [scale (if (< size 2) 2 size)]
    (render-rows [(render-top-base-1 scale)
                  (render-top-slopes scale)
                  (render-top-base-2 scale)
                  (render-mid scale)
                  (render-mid scale)
                  (render-btm-slopes scale)
                  (render-btm-base scale)])))

(print (honeycomb 3))
; (out)      ___
; (out)  ___/   \___
; (out) /   \___/   \
; (out) \___/   \___/
; (out) /   \___/   \
; (out) \___/   \___/
; (out)     \___/
(print (honeycomb 4))
; (out)         ____
; (out)        /    \
; (out)   ____/      \____
; (out)  /    \      /    \
; (out) /      \____/      \
; (out) \      /    \      /
; (out)  \____/      \____/
; (out)  /    \      /    \
; (out) /      \____/      \
; (out) \      /    \      /
; (out)  \____/      \____/
; (out)       \      /
; (out)        \____/

; -----------------------
; the diagram is the same no matter the size; so this is only a scaling problem i.e. we can start from a base diagram and scale it
; render-slopes does not render the part of the slope that touches a base. hence render-slopes might return an empty string

;     __
;  __/  \__
; /  \__/  \
; \__/  \__/
; /  \__/  \
; \__/  \__/
;    \__/

;         ____
;        /    \
;   ____/      \____
;  /    \      /    \
; /      \____/      \
; \      /    \      /
;  \____/      \____/
;  /    \      /    \
; /      \____/      \
; \      /    \      /
;  \____/      \____/
;       \      /
;        \____/

;          _____
;         /     \
;   _____/       \_____
;  /     \       /     \
; /       \_____/       \
; \       /     \       /
;  \_____/       \_____/
;  /     \       /     \
; /       \_____/       \
; \       /     \       /
;  \_____/       \_____/
;        \       /
;         \_____/

;             ______
;            /      \
;           /        \
;    ______/          \______
;   /      \          /      \
;  /        \        /        \
; /          \______/          \
; \          /      \          /
;  \        /        \        /
;   \______/          \______/
;   /      \          /      \
;  /        \        /        \
; /          \______/          \
; \          /      \          /
;  \        /        \        /
;   \______/          \______/
;          \          /
;           \        /
;            \______/

; -----------------------

;     __
;  __/  \__

; /  \__/  \
; \__/  \__/

;    \__/

;      ___
;  ___/   \___
;
; /   \___/   \
; \___/   \___/

;     \___/

;section       ;left padding ;diagram

;top-base-1    ;left         ;            ______

;top-slopes    ;             ;           /      \
;              ;             ;          /        \

;top-base-2    ;depth        ;   ______/ base-pad \______

;mid-slopes-1  ;             ;  /      \ base-pad /      \
;              ;             ; /        \        /        \

;mid-base-1    ;0            ;/ base-pad \______/ base-pad \

;mid-slopes-2  ;             ;\ base-pad /      \ base-pad /
;              ;             ; \        /        \        /

;mid-base-2    ;depth-1      ;  \______/ base-pad \______/

;btm-slopes    ;             ;         \ base-pad /
;              ;             ;          \        /

;btm-base      ;left-1       ;           \______/

