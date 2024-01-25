;; https://buttondown.email/cassidoo/archive/in-our-leisure-we-reveal-what-kind-of-people-we/
;; https://twitter.com/cassidoo/status/1749310664886071643

(defrecord Node [value left right])
(defrecord DepthNode [node depth])

(defn- height [root]
  (if (nil? root)
    0
    (inc (max (height (:left root))
              (height (:right root))))))

(defn- pad [n]
  (apply str (repeat n " ")))

(defn print-tree [root]
  (let [num-pad (atom (-> (height root) (dec) (* 2)))
        b (StringBuilder.)
        init-depth 1]
    (doto b
      (.append (pad @num-pad))
      (.append (:value root)))
    (loop [last-depth init-depth
           queue [(DepthNode. root last-depth)]]
      (let [depth-node (first queue)
            depth (:depth depth-node)
            new-row? (> depth last-depth)]
        (when-let [node (:node depth-node)]
          (when (> depth init-depth)
            (if new-row?
              (do
                (swap! num-pad (comp dec dec))
                (.append b "\n")
                (.append b (pad (inc @num-pad)))
                (.append b "/ \\\n")
                (.append b (pad @num-pad))
                (.append b (:value node)))
              (doto b
                (.append "   ")
                (.append (:value node)))))
          (recur depth
                 (concat (rest queue)
                         [(DepthNode. (:left node) (inc depth))
                          (DepthNode. (:right node) (inc depth))])))))
    (print (str b))))

(def tree (-> (Node. 1)
              (assoc :left (Node. 2))
              (assoc :right (Node. 3))
              (assoc-in [:left :left] (Node. 4))
              (assoc-in [:left :right] (Node. 5))))

(print-tree tree)
; (out)     1
; (out)    / \
; (out)   2   3
; (out)  / \
; (out) 4   5
