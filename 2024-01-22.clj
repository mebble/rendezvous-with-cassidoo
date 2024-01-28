;; https://buttondown.email/cassidoo/archive/in-our-leisure-we-reveal-what-kind-of-people-we/
;; https://twitter.com/cassidoo/status/1749310664886071643

#_(defrecord DepthNode [node depth])
#_(defn print-tree [root]
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

(require '[clojure.string :as s])

(defrecord Node [value left right])
(defrecord Part [x y value node])

(defn- height [root]
  (if (nil? root)
    0
    (inc (max (height (:left root))
              (height (:right root))))))

(defn- pad [n]
  (apply str (repeat n " ")))

(defn generate-parts
  "This does BFS on the tree, except it traverses both nodes and edges. Hence each output part is either a node or an edge."
  [root]
  (let [parts (atom [])
        root-x (-> (height root) (dec) (* 2))]
    (loop [queue [(Part. root-x 1 (:value root) root)]]
      (when-let [part (first queue)]
        (let [node (:node part)]
          (swap! parts conj part)
          (recur
           (cond-> (vec (rest queue))
             (:left node) (conj (Part. (dec (:x part))
                                       (inc (:y part))
                                       "/"))
             (:right node) (conj (Part. (inc (:x part))
                                        (inc (:y part))
                                        "\\"))
             (:left node) (conj (Part. (dec (dec (:x part)))
                                       (inc (inc (:y part)))
                                       (:value (:left node))
                                       (:left node)))
             (:right node) (conj (Part. (inc (inc (:x part)))
                                        (inc (inc (:y part)))
                                        (:value (:right node))
                                        (:right node))))))))
    @parts))

(defn- with-x-deltas [parts]
  (let [first-x (:x (first parts))]
    (->> (rest parts)
         (map vector parts)
         (map (fn [[p1 p2]]
                (dec (- (:x p2)
                        (:x p1)))))
         (cons first-x)
         (map (fn [part x-delta] (assoc part :x-delta x-delta)) parts))))

(defn render-row [row-parts]
  (->> row-parts
       (with-x-deltas)
       (map (fn [part]
              (str (pad (:x-delta part))
                   (:value part))))
       (s/join)))

(defn render-parts [parts]
  (->> parts
       (partition-by :y)
       (map render-row)
       (s/join "\n")))

(defn print-tree [root]
  (->> (generate-parts root)
       (render-parts)
       (println)))

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

(def tree2 (-> (Node. 1)
                (assoc :left (Node. 2))
                (assoc :right (Node. 5))
                (assoc-in [:left :left] (Node. 3))
                (assoc-in [:left :left] (Node. 4))
                (assoc-in [:left :left :left] (Node. 7))))

(print-tree tree2)
; (out)       1
; (out)      / \
; (out)     2   5
; (out)    /
; (out)   4
; (out)  /
; (out) 7
