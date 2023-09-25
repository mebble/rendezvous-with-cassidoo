;; https://buttondown.email/cassidoo/archive/i-love-mistakes-because-its-the-only-way-you/
;; https://twitter.com/cassidoo/status/1706145472019046903

(require '[cheshire.core :as c]
         '[clojure.string :as s])

(defn get-all-types []
  (:results (c/parse-string (slurp "https://pokeapi.co/api/v2/type/") true)))

(defn get-damage-relations [url]
  (let [{:keys [double_damage_from double_damage_to]} (:damage_relations (c/parse-string (slurp url) true))]
    [double_damage_from double_damage_to]))

(defn concat-names [pokemon]
  (if (empty? pokemon)
    "nobody"
    (let [but-last (pop pokemon)
          last-poke (update (last pokemon) :name #(str "and " %))]
      (->> (conj but-last last-poke)
           (map :name)
           (s/join ", ")))))

(defn matching-type [all-types type-name]
  (first (drop-while (fn [{:keys [name]}] (not= name type-name))
                     all-types)))

(defn type-matchup [type-name]
  (let [poke-type (matching-type (get-all-types) type-name)]
    (if-not poke-type
      "This is not a valid Pokémon type"
      (let [[damage-from damage-to] (get-damage-relations (:url poke-type))]
        (str "Weak against "   (concat-names damage-from) ". "
             "Strong against " (concat-names damage-to)   ".")))))

(assert (= "Weak against flying, psychic, and fairy. Strong against normal, rock, steel, ice, and dark."
           (type-matchup "fighting")))
(assert (= "This is not a valid Pokémon type"
           (type-matchup "cassidy")))
