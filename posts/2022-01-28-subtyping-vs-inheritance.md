# Subtyping vs inheritance

Inheritance allows objects to re-use a subset of it's superclasses methods.

Subtyping allows objects to be substituted for one another. A type is a subtype if it implements all the operations of the supertype.

Deque supports 4 operations:
- insert-front
- drop-front
- insert-rear
- drop-rear

Stack can inherit from Deque:
- insert-front -> can be renamed as push
- drop-front -> can be renamed as pop

Queue can inherit from Deque:
- insert-front -> can be renamed as insert
- drop-rear -> can be renamed as delete

Stack and Queue inherit behaviour from Deque (albeit modifying it).

If the methods in Stack and Queue are not renamed, then Deque is a subtype of Stack and Queue.
