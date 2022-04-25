# Abstraction

Abstraction is a widely used, but deeply overloaded term. Programming is full of tropes like "program at the right level of abstraction", and "avoid premature abstraction", or even "every abstraction is leaky". All these uses of the term hint at a program as a hierarchy of levels, and these levels are abstractions. But what defines these levels, really? You can definitely _feel_ them, but can you explain?

We could continue to be imprecise about what we mean by abstraction. Abstraction is a pretty successful idea in the software engineering world, even if most people use it without saying exactly what it means. But I still find it hard to talk about abstraction. We need to share a common language to make progress. Hopefully reading this post can help future discussions on the subject.

Let's start with the definition from Rich Hickey: "Abstraction is about drawing from a set of exemplars some essential thing."

(Fun side note: this definition is recursive: abstraction is _itself_ an abstraction.)

If abstraction is understood as drawing from a set of exemplars, let's look at an example Rich created himself: the collection abstraction in Clojure. 

A collection supports three operations:
- `count` - for getting the size of the collection
- `conj` - for 'adding' to the collection
- `seq` - to get a sequence that can walk the entire collection

The collection abstraction is defined by these operations, combined with these invariants:
- the sequence of items in the collection contains all the items that have been added to the collection
- the size of the collection is equal to number of items that have been added to the collection

These properties adhere to Rich's definition of an abstraction. If something has an implementation for the above operations, which satisfy these invariants, it is a collection.

This idea of a collection is an abstract one, precisely because it omits details of how it works. There is not enough information in the definition to create a working collection. There is only enough information to say what a collection _is_. The abstraction does not specify anything about how its invariants are maintained, or how its operations are implemented. That is the job of the abstraction's concretions. This reveals a fundamental property of abstraction: if an abstraction is about finding common things

The set of concretions for the collection abstraction include anything that contains more information and still has the properties of the abstraction. The mapping of concretions to the abstract type is called an abstraction mapping, or an abstraction function.

It's worth taking a step back and acknowledging some things that are clearly not abstractions by themselves:

**Interfaces, protocols or typeclasses**

These enumerate operations on a data type, but they are not abstractions since they omit all other types of information. For example, the IPersistentCollection is the interface for the collection in Clojure, and says that all collections must implement `count`, `conj`, and `seq` operations. But it says nothing about the invariants of these operations.

An interface/protocol/typeclass is not even necessary to create an abstraction. Consider the abstraction of a point in 2D space. Here are three examples of points:

```
(def my-map-point {:x 10 :y 20})
(def my-vector-point [10 20])
(def my-string-point "Point(10, 20)")
```

We could unite all these instances under one interface that defines a set of valid operations on points. That might be good programming practice, but it's unnecessary. In fact, we already know what a point is without writing _any_ code. We can simply imagine it. To prove my point, consider how the above examples map to a specific abstract value, namely the point where x=10 and y=20. We didn't need a protocol, interface, or typeclass to know that.

**Functions**

Functions are black boxes that take inputs, might do something, and return a value. Functions can be used to transform or remove information from a complex value into a simpler representation. This can sometimes looks equivalent to an abstraction, but it isn't the same. For example, would you say Java's `Integer.parseInt` function is an abstraction? It converts `String[]` to the `int` type. Not quite. An integer is a value of the abstract type, whose operations adhere to certain mathematical axioms like addition and substraction. Both the input and output types of `Integer.parseInt`, `String[]` and `int`, are two representations of integers. `"0101" :: String[]` maps to the same integer as `10 :: int`, but they are equally both concretions. Both need to operate the same way under integer addition.

**Classes**

Classes combine an interface (the list of the class's methods) with implementations of the interface. If an interface isn't required for an abstraction, a class is certainly even more overkill. All previous arguments apply.

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