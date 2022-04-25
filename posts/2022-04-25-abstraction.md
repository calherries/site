# Abstraction

Abstraction is a widely used, but deeply overloaded term. Programming is full of tropes like "program at the right level of abstraction", and "avoid premature abstraction", or even "every abstraction is leaky". All these uses of the term hint at a program as a hierarchy of levels, comprised of abstractions. But what are these levels, really? You can definitely _feel_ them, but can you define them?

We could continue to be imprecise about what we mean by abstraction. Abstraction is a pretty successful idea, even if most use the term without clarifying exactly what it means. As a software engineer, I'm interested in reducing the cost of higher quality software. And I find it hard to talk about abstraction because the term can be loose and meaningless. We need to share a common language to make progress. Hopefully reading this post can help future discussions on the subject.

Let's start with the definition from Rich Hickey: "Abstraction is about drawing from a set of exemplars some essential thing."

(Fun side note: this definition is recursive: abstraction is _itself_ an abstraction.)

If abstraction is understood as drawing from a set of exemplars, let's look at an example Rich created himself: the collection abstraction in Clojure. 

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

It's worth taking a step back and acknowledging some things that are clearly not abstractions by themselves:

**Interfaces and protocols**

An interface or protocol is not sufficient to create an abstraction. Things that describe the operations on a data type are not abstractions, since they omit invariants. The IPersistentCollection is the interface for the collection in Clojure, but it is clearly not the abstraction in itself. 

Interfaces or protocols are not even necessary to create an abstraction. Consider the abstraction of a point in 2D space. Here are three 

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

A type is a set of values that a compiler can use to constrain the operations made on each of its members. An abstraction is not a type, because there is nothing in its definition that allows the compiler to constrain operations in any way, as a type does. Two examples: the collection abstraction isn't a type, and neither is the point abstraction above. But is every type an abstraction? Well, here is a definition of a tree type in ML:

```
datatype point = Point of (double * double)
```

Here, a `point` is defined as a product type of doubles, that is created using the type constructor `Point`. But by doing so we have already made more assumptions about a point than are necessary for a point abstraction. Here we are saying three things that aren't necessary for the point abstraction:
- Points need to be created with the `Point` type constructor
- X and Y are doubles
- X and Y can be accessed by pattern matching on the type
- Points can contain no other information other than X and Y. You can't add a 3rd dimension to a point, or an ID, or include a name.

These smell like implementation details. Here is all that is needed for a 2D point abstraction: 

- A point has at least two values, which represent the x and y axes in vector space.
- Operations on points should be defined to adhere to the axioms in mathematics. For example, addition of two points should do pointwise addition.

In the Clojure examples shown above, there are very few assumptions. The common thread between them all is that they have X and Y points defined somehow, and you can imagine defining operations on them that match mathematical axioms.

The important insight to make is that abstraction doesn't need to be written down anywhere to be present. It can exist in documentation, in comments, or even only in the mind of the programmer. As long as it can be named and defined, it exists.