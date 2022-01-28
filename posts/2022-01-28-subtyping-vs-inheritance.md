# Subtyping vs inheritance

Inheritance allows objects to re-use a subset of it's superclasses methods.

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

So, Stack and Queue inherit behaviour from Deque.

If the methods in Stack and Queue are named the same as Deque, then Deque is a subtype of Stack and Queue.
