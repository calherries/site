# Predicate-set duality theory

The wave-particle duality theory of light states that light waves can show particle-like properties while particles can show wave-like properties. Light is neither just a wave nor a particle. It can be both, depending on how you view it.

In set-theory as in programming, sets aren't literally the same thing as predicates. But there is what I call the predicate-set duality theory: sets show predicate-like properties and predicates show set-like properties. Realising this can bring tremendous clarity to the programs you write.

To demonstrate, imagine we're classifying animals by the number of legs they have. One way to do it is with sets:

```clojure
(def four-legged-animals #{"sheep" "cow" "pig"})
(def three-legged-animals #{}
(def two-legged-animals #{"human" "bird"}
```

To see whether an animal is four-legged, we can check whether it's in the four-legged-animal set:
```clojure
(contains? four-legged-animals "sheep")
=> true
```

But another way to achieve the same goal is to define a predicate for each set:
```
(defn four-legged? [animal] (contains? #{"sheep" "cow" "pig"} animal)
(defn three-legged? [animal] false)
(defn two-legged? [animal] (contains? #{"human" "bird"} animal)
```
To see whether an animal is four-legged, we can just call the predicate:
```clojure
(four-legged? "sheep")
=> true
```

This is even more true in the Clojure programming language, where sets can be called as functions. We could have defined the predicates AS sets.

```clojure
(def four-legged? #{"sheep" "cow" "pig"})
(def three-legged? #{})
(def two-legged? #{"human" "bird"})
```
And simply called them as functions:
```clojure
(four-legged? "sheep")
=> true
```
