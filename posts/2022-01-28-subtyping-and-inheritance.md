# Subtyping and inheritance

Two oft-confused concepts.

Inheritance: 
- allows objects to reuse a subset of its superclasses' methods
- allows reuse of method implementations, which helps you extend existing classes with new operations

Subtyping:
- allows objects to be substituted for one another. A type is a subtype if it implements all the operations of the supertype, and shares its invariants
- implementations of operations can be completely different between subtypes
- helps you write generic, reusable code that can operate across a range of types
