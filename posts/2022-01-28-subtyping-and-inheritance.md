# Subtyping and inheritance

Subtyping and inheritance are two similar, but subtly different concepts.

Inheritance allows objects to re-use a subset of its superclasses' methods.

Subtyping allows objects to be substituted for one another. A type is a subtype if it implements all the operations of the supertype.

Let's see an example.

Deque supports 4 operations:
- `Deque.insert_front`
- `Deque.drop_front`
- `Deque.insert_rear`
- `Deque.drop_rear`

An implementation of a stack can re-use (inherit) methods from Deque:
- `Deque.insert_front` is like a push operation
- `Deque.drop_front` is like a pop operation

An implementation of a queue can re-use (inherit) methods from Deque:
- `Deque.insert_front` is like an insert operation
- `Deque.drop_rear` is like a delete operation

As long as the methods are not renamed, Stack and Queue *inherit* behaviour from Deque.

But Stack and Queue are not subtypes of Deque, because they do not reimplement everything from Deque. Only a subset.

Deque is actually a subtype of Stack and Queue, as long as it's methods are not renamed.

The key difference:
- Subtyping allows substitution of types, which helps you write generic code that is reusable for a range of types.
- Inheritance allows reuse of a type's methods, which helps you extend types with new operations.
