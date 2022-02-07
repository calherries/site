# Subtyping and inheritance

Inheritance allows objects to re-use a subset of its superclasses' methods.

Subtyping allows objects to be substituted for one another. A type is a subtype if it implements all the operations of the supertype.

Let's see an example.

Deque supports 4 operations:
- insert-front
- drop-front
- insert-rear
- drop-rear

Stack can inherit from Deque:
- Deque.insert-front -> push
- Deque.drop-front -> pop

Queue can inherit from Deque:
- Deque.insert-front -> insert
- Deque.drop-rear -> delete

Stack and Queue inherit behaviour from Deque.

But Stack and Queue are not subtypes, because they do not reimplement everything from Deque. Only a subset.

Deque would actually be a subtype of Stack and Queue if the methods were not renamed.
