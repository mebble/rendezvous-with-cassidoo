;; https://buttondown.email/cassidoo/archive/i-love-mistakes-because-its-the-only-way-you/
;; https://twitter.com/cassidoo/status/1706145472019046903

(require '[cheshire.core :as c]
         '[clojure.string :as s])

(defn find-first [pred coll]
  ;; #util
  ;; https://stackoverflow.com/a/10192733
  (first (filter pred coll)))

(defn get-all-types []
  (:results (c/parse-string (slurp "https://pokeapi.co/api/v2/type/") true)))

(defn get-damage-relations [url]
  (let [{:keys [double_damage_from double_damage_to]} (:damage_relations (c/parse-string (slurp url) true))]
    [double_damage_from double_damage_to]))

(defn concat-names [pokemon]
  (case (count pokemon)
    0 "nobody"
    1 (-> pokemon first :name)
    2 (s/join " and " (map :name pokemon))
    (let [but-last (pop pokemon)
          last-poke (update (last pokemon) :name #(str "and " %))]
      (->> (conj but-last last-poke)
           (map :name)
           (s/join ", ")))))

(defn type-matchup [type-name]
  (let [poke-type (find-first (fn [{:keys [name]}] (= name type-name))
                              (get-all-types))]
    (if-not poke-type
      "This is not a valid Pokémon type"
      (let [[damage-from damage-to] (get-damage-relations (:url poke-type))]
        (str "Weak against "   (concat-names damage-from) ". "
             "Strong against " (concat-names damage-to)   ".")))))

(assert (= "Weak against flying, psychic, and fairy. Strong against normal, rock, steel, ice, and dark."
           (type-matchup "fighting")))
(assert (= "Weak against ghost and dark. Strong against ghost and psychic."
           (type-matchup "ghost")))
(assert (= "Weak against fighting. Strong against nobody."
           (type-matchup "normal")))
(assert (= "This is not a valid Pokémon type"
           (type-matchup "cassidy")))
