# Objects as functions in Clojure

We often talk about objects as concrete features of a language. For example, a JavaScript object. But objects are also an abstract idea, that can be implemented without using language features specific to objects. In fact, objects can be implemented using function closures. Every functional programming language can implement them, even if they're not officially supported at the language level.

To demonstrate, let's compare stack objects in Python and Clojure.

Here's the Python implementation of a stack object using the `class` keyword:
```Python
class Stack:
  def __init__(self):
    self.elements = []

  def push(self, data):
    self.elements = [data] + self.elements

  def pop(self):
    if len(self.elements):
      result = self.elements[0]
      self.elements = self.elements[1:]
      return result
    else:
      return None

stack = Stack()
stack.push(1)
stack.push(2)
print(stack.elements)
```
And here's how we use it:
```
stack = Stack()
stack.push(1)
stack.push(2)
stack.push(3)
stack.pop()
=> 3
stack.elements 
=> [2, 1]
```
Straight-forward stuff. Now, how about Clojure?

```Clojure
(defn new-stack []
  (let [elements (atom [])]
    (fn [method]
      (case method
        :push     (fn [x]
                    (swap! elements #(cons x %))
                    nil)
        :pop      (fn []
                    (let [result (first @elements)]
                      (swap! elements rest)
                      result))
        :elements (fn [] @elements)))))
```
It's a bit noisier than the Python version, but it works in a similar way. Here's how you use it:
```Clojure
(def stack (new-stack))
((stack :push) 1)
((stack :push) 2)
((stack :push) 3)
((stack :pop))
=> 3
((stack :elements))
=> (2 1)
```
Notice the similarity?

The Clojure "class" is just a function that returns a function. That returned function is an object! It has it's own internal state, and has a set of methods that operate on that state. You can get a method by calling the object function with the method name. Once you've got the method from the object, you can call it like a normal function. The difference between a method and a typical function is that the method has access to the object's internal state.

```Clojure
object  method-name
   |       |
   |       |
   .       .
((stack :push) 3)
  ...........  .
       |       |
       |       |
       |       |
       |       |
    method   method-argument
```

There's no built-in `class` symbol in Clojure that can create objects for us. But that doesn't mean it doesn't support OOP! You can think of Python's `class` symbol as syntactic sugar. Semantically, it creates a function. Python classes can do more than just define objects, like enable inheritance and subtyping. But the basics 
