# Abstraction

Abstraction is a widely used, but deeply overloaded term. Programming is full of tropes like "program at the right level of abstraction", "avoid premature abstraction", and "every abstraction is leaky". All these imagine a program as a hierarchy of levels, and these levels as abstractions. But what defines these levels, really? You can definitely _feel_ them in your programs, but can you explain what they are?

We could continue to be imprecise about what we mean by abstraction. Abstraction is a pretty successful idea in the software engineering world, even if most people use it without saying exactly what it means. But I find it hard to talk about abstraction in real-world conversations. We need to share a common language to make progress. Hopefully reading this post can help future discussions on the subject.

Let's start with the definition from Rich Hickey: "Abstraction is about drawing from a set of exemplars some essential thing."

(Fun side note: this definition is recursive. Abstraction is _itself_ an abstraction.)

I like this definition because it's both correct, precise, and I can explain it to a 5 year old. But it needs unpacking. 

If abstraction is drawing from a set of exemplars, let's look at an example Rich Hickey created himself: the collection abstraction in Clojure. 

A collection supports three operations:
- `count` - for getting the number of items in the collection
- `conj` - for 'adding' to the collection
- `seq` - to get a sequence that can walk the entire collection.

The operations maintain these invariants:
- the sequence of items in the collection contains all the items that have been added to the collection
- the size of the collection is equal to number of items that have been added to the collection

These properties adhere to Rich's definition of an abstraction. If something has implementations for these operations, and the operations satisfy these invariants, that thing is a collection.

This idea of a collection is an abstract one because it leaves out details of how it works. There is not enough information in the definition to create a working collection. There is only enough information to say what a collection _is_. The collection abstraction does not specify anything about how its invariants are maintained, how its operations are implemented, or even how to create one. That is the job of the abstraction's concretions. 

A value is said to be a concretion of an abstraction if it has all the properties of the abstraction. But it can have other properties too. For example, the collection could be ordered, or not. It could have 0(1) access, or not. But these properties are not considered, in the abstract domain. If all you know about something is that it is a collection, you cannot know any of these things.

It is sometimes helpful to imagine an abstraction as a function of concrete collections to abstract collections, called an abstraction function. The function is surjective: For every abstract value, there are one or more corresponding concrete values.

```text
the sequence '(1 2 3)
the vector    [1 2 3]    =>   these all map to the same abstract collection
the set      #{1 2 3}
```

This reveals a fundamental property of abstraction: if an abstraction is about finding common things between values, an abstract value must contain the same or less information than its corresponding concrete values. You always have enough information to get the abstract value from a concrete one, but not the other way around.

Now that we know what an abstraction is, let's acknowledge some things that are not abstractions.

**Interfaces, protocols or typeclasses**

These enumerate operations on a data type, but they are not abstractions since they omit all other types of information. For example, the IPersistentCollection is the interface for the collection in Clojure, and says that all collections must implement `count`, `conj`, and `seq` operations. But it says nothing about the invariants of these operations.

An interface/protocol/typeclass is not even necessary to create an abstraction. Consider the abstraction of a point in 2D space. Here are three examples of points in Clojure:

```clojure
(def my-map-point {:x 10 :y 20})
(def my-vector-point [10 20])
(def my-string-point "Point(10, 20)")
```

We could unite these instances under one interface and define a set of valid operations on points. That might be good programming practice, but it's unnecessary. In fact, we already know what a point is without writing _any_ code. We can simply imagine it. To prove my point, consider how the above examples map to a specific abstract value, namely the point where x=10 and y=20. We didn't need a protocol, interface, or typeclass to know that.

**Functions**

Functions are black boxes that take inputs, might do something, and return a value. Functions can be used to transform or remove information from a complex value into a simpler representation. This can sometimes looks equivalent to an abstraction, but it isn't the same.

For example, would you say Java's `Integer.parseInt` function is an abstraction? Not quite. It converts a value of type `String[]` to the `int` type, but it doesn't say anything else. An integer is actually an abstraction where the operations adhere to certain mathematical axioms like addition and substraction. `Integer.parseInt` just converts between two representations of integers: `String[]` and `int`. A value `"1010" : String[]` represents the same integer as the value `10 : int`, just as `"1010" + "0001" = "1011"` represents the same operation as `10 + 1 = 11`. The conversion between representations of an abstraction is not what defines an abstraction.

**Classes**

Classes combine an interface (the list of the class's methods) with implementations of the interface. If an interface isn't required for an abstraction, a class is even more overkill. All previous arguments apply.

**Types**

A type is a set of values that a compiler can use to constrain the operations made on each of its members. An abstraction is not a type, because there is nothing in its definition that allows the compiler to constrain operations in any way, as a type does. Two examples: the collection abstraction isn't a type, and neither is the point abstraction above. But is every type an abstraction? Well, here is a definition of a tree type in ML:

```ML
datatype point = Point of (double * double)
```

Here a `point` is a product type of doubles that is created using the type constructor `Point`. But by defining it this way we have already made more assumptions than necessary for a point abstraction. 

Here are four things defined above that aren't necessary for a point abstraction:
- Points need to be created with the `Point` type constructor
- X and Y are doubles
- X and Y can be accessed by pattern matching on a value of type `Point`
- Points can contain no other information other than X and Y. You can't add a 3rd dimension to a point, or an ID, or give the point a name.

These smell like implementation details. From my perspective, these are the only properties you need to know about a 2D point: 

1. A point has at least two values, which represent the x and y axes in vector space.
2. Operations on points should be defined to adhere to the axioms in mathematics. For example, addition of two points should do pointwise addition.

In the examples of points in Clojure shown previously, few assumptions were made. The common thread between them all is that they have X and Y points defined (somehow), and you could _imagine_ defining operations on them that match mathematical axioms.

The important insight to make is that abstraction doesn't need to be written down anywhere to be present. It can exist in documentation, in comments, or only in the mind of the programmer. As long as it can be named and defined, it exists.