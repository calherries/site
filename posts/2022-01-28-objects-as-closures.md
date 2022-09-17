# Objects as closures

Objects are an abstract idea that can be implemented without using specific language features. Every programming language with first class functions and lexical scoping can implement objects, even if objects are not officially supported at the language level.

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

The new-stack "class" in Clojure is just a function that returns another function with access to the mutable state. The returned function is called a closure because the function's open variables (in this case `elements`) have been _closed_ by the surrounding environment. 

That function closure is an object. It has it's own state, and has a set of methods that operate on that state. You can get a method from the object by calling the object function with the method name. Once you've got the method, you can call it like a normal function. The difference between a method and a typical function is that the method has privileged access to the object's hidden internal state.

```Clojure
object  method-name
   |       |
   .       .
((stack :push) 3)
               .
               |
        method-argument
```

There's no built-in `class` symbol in Clojure that can create objects for us. But that doesn't mean it doesn't support OOP! At the level of the object abstraction, the Python class and the Clojure function achieve the same goal. Python classes can do more than just define objects, like enable inheritance. Inheritance is an added feature to objects, and wasn't included in Alan Kay's original vision for OOP.
