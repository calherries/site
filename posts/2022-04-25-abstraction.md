# Abstraction

Abstraction is a widely used, but deeply overloaded term. Programming is full of tropes like "program at the right level of abstraction", and "avoid premature abstraction".

Like many, I consider it an indispensable term for reasoning in programming discussions. So it's worth nailing down its definition.

Let's start with the definition from Rich Hickey: "Abstraction is about drawing from a set of exemplars some essential thing."

(Fun side note: this definition is recursive: abstraction is _itself_ an abstraction.)

If abstraction is understood as drawing from a set of exemplars, let's look at an example: the collection abstraction in Clojure. 

A collection supports three operations:
- count - for getting the size of the collection
- conj - for 'adding' to the collection
- seq - to get a sequence that can walk the entire collection

The collection abstraction is defined by these operations, combined with these invariants:
- the sequence of items in the collection contains all the items that have been added to the collection
- the size of the collection is equal to number of items that have been added to the collection

These properties adhere to Rich's definition of an abstraction. If something has an implementation for the above operations, which satisfy these invariants, it is a collection.

This idea of a collection is an abstract one, precisely because it omits details of how it works. There is not enough information in the definition to create a working collection. There is only enough information to say what a collection _is_. The abstraction does not specify anything about how its invariants are maintained, or how its operations are implemented. That is the job of the abstraction's concretions. This reveals a fundamental property of abstraction: if an abstraction is about finding common things

The set of concretions for the collection abstraction include anything that contains more information and still has the properties of the abstraction. The mapping of concretions to the abstract type is called an abstraction mapping, or an abstraction function.

### Not abstractions

It's worth taking a step back and acknowledging some things that are clearly not abstractions by themselves:

**Interfaces and protocols**

An interface or protocol is not sufficient to create an abstraction. Things that describe the operations on a data type are not abstractions, since they omit invariants. The IPersistentCollection is the interface for the collection in Clojure, but it is clearly not the abstraction in itself. 

Interfaces or protocols are not even necessary to create an abstraction. Consider the abstraction of a point in 2D space.

```
(def my-map-point {:x 10 :y 20})
(def my-vector-point [10 20])
(def my-string-point "Point(10, 20)")
```

We could create a protocol that define a set of valid operations on points. That might be good programming practice, but it's unnecessary to imagine the point abstraction. We already know what a point is without writing _any_ code. To prove my point, consider how the above examples clearly map to the same value in the abstract space.

**Functions**

Functions are black boxes that take inputs, might do something, and return a value. They're helpful for making concretions, but they aren't an abstraction in themselves.

**Classes**

Classes combine interfaces (the classes' methods) with implementations of the interface. If an interface isn't required for an abstraction, a class certainly isn't.

**Types**

A type is a set of values, together with the ability to constrain the available operations you can make on each value of the type. An abstraction is not a type, because it doesn't allow you to constrain operations in any way, as a type does. Two examples: the collection abstraction isn't a type, and neither is the point abstraction above. The confusion here is that creating a type often comes with a new abstraction. For example, creating a Point type in Haskell:

```
data Point = MakePoint Double Double
```

### Why should we care?

As a software engineer, I'm interested in reducing the cost of higher quality software. But to make progress we need to share a common language. I find it hard to talk about abstraction with other programmers because the term is so loose and meaningless. Hopefully this post can help change that.
