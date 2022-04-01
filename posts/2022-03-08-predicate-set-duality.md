# Predicate-Set Duality

The wave-particle duality theory of light states that light waves show particle-like properties while particles show wave-like properties. Light is neither just a wave nor a particle. It is both, depending on how you view it.

There is an analogous phenemenon in set-theory. Sets show predicate-like properties and predicates show set-like properties. I call this predicate-set duality. Recognizing this can bring clarity to the programs you write, and open up possibilities for refactoring.

To demonstrate, imagine we're classifying animals by the number of legs they have. One way to do this is with sets:

```clojure
(def four-legged-animals #{"sheep" "cow" "pig"})
(def three-legged-animals #{}
(def two-legged-animals #{"human" "bird"}
```

To see whether an animal is four-legged, you can check whether it's in the four-legged-animal set:
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

This is even more true in the Clojure programming language, where sets can be called as if they were functions. We could have defined the predicates as sets.

```clojure
(def four-legged? #{"sheep" "cow" "pig"})
(def three-legged? #{})
(def two-legged? #{"human" "bird"})
```
You can call the sets as functions:
```clojure
(four-legged? "sheep")
=> true
```

This reveals a fundamental duality: sets *are* predicates. Predicates *are* sets. Just not in the program, but on a more abstract, [logical level](https://www.pathsensitive.com/2018/01/the-three-levels-of-software-why-code.html). They both describe a property of a data point, that can be queried separate from the data.

We can develop this idea further by taking the property designated by the predicate, and adding to the data the predicate describes.

For example, instead of having a predicate function like this:

```clojure
;; The data:
(def animal {:type "sheep"
             :name "Milly"})

;; The predicate function:
(defn four-legged? [animal]
  (contains? #{"sheep" "cow" "pig"} (:type animal))

(four-legged? animal)
=> true
```

Or a predicate set like this:
```clojure
;; The data:
(def animal {:type "sheep"
             :name "Milly"})

;; The predicate set:
(def four-legged? #{"sheep" "cow" "pig"})

(four-legged? (:type animal))
=> true
```

We can package the `four-legged?` predicate with the data:

```clojure
;; The data:
(def animal {:type "sheep"
             :name "Milly"
             :legs :four-legged}) ; The predicate is packaged with the data

(= (:legs animal) :four-legged)
=> true
```

Either way, the effect is the same. We can determine that the animal is four-legged or not. This demonstrates a predicate function is a way to describe a property of data that is separate from the data itself.

We now have three methods to define the "four-leggedness" property of an animal: a set, a predicate function, or a key on the data itself. Each method has its benefits and downsides, but each are equivalent in outcome. In general, if you have a property you want to designate about an object or piece of data, you have these three ways to do it.

Through this lens, whether you define a property as a set, predicate, or a key on a map is now an implementation detail. If you want to, you can hide the choice of method from other parts of the code, so they don't depend on *how* it works. Only the abstraction of a property matters when you reason about *why* the program works.