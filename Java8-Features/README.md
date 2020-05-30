# Java 8 Features and Examples
Java 8 introduces declarative style or functional style of programming over the imperative programming. Let's first
understand what is declarative programming and imperative programming.

### Imperative programming
In this style of programming we tell each and every thing to compiler like what will be control flow and what operation
need to perform on the data. Let's take an example:
```java
public class ImperativeProgramming {
    public static void main(String[] args){
      List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
      for (int i = 0; i < numbers.size(); i++) {
         System.out.println(numbers.get(i));
      }
    }
}
```
In above program we have data numbers; over which we want to iterate and perform some operation. So in above program
we are telling we want from where we want to start, then at what index we want to iterate, and then we are performing
actual operation.

### Declarative programming
In declarative programming we leave the control to compiler, and we specify what operation we want to perform. Let's 
transform above example to declarative programming style.
```java
public class DeclarativeProgramming {
    public static void main(String[] args){
      List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
      numbers.forEach((Integer value) -> System.out.println(value));
    }
}
```
In above example we left the iteration, or I can say control flow logic on the controller, and I created anonymous 
function in which I am just giving the what operation is to perform. This anonymous function is kind of implementation
for Consumer interface comes in java.util.stream package in Java 8.

In 1 liner If I want define the declarative programming and imperative programming. I will say below line:
```text
“Imperative programming is like how you do something, and declarative programming is more like what you do.”
```

## Functional Interfaces
A functional interface is useful to create and assign a Lambada function to them. We generally implement interface, 
or we create anonymous class of it to use. From Java 8 we can create lambada expression which is anonymous function
and can assign to interface. To create a functional interface the interface should qualify below properties:
* It should contain only one abstract method
* Interface can have default methods
* Interface can have static methods
* Optionally we can mark the @FunctionalInterface annotation

In Java 8 we can write method implementation in interfaces; for do that we need to use default keyword. Please find
example in com.example.java8.feature2.rules. Let's understand few things about interface default methods.
1. Default methods can be overridden in subclasses.
2. Default method follow hierarchy when calling the method i.e. extending class has higher priority then the extending
interface method.
3. If two interfaces have same method, and they don't have any class hierarchy then we need to resolve this problem
in implementing class. Either we need to write our own implementation or can call one or both of super implementation in
a sequence. In simple words I can say whenever diamond problem comes from interfaces; it should be solved by implementing
class.

Below are already existing Functional Interface till Java 7:
* Runnable
* Callable
* Comparable

There are new Functional Interface with Java 8:
* Consumer\<U>
* Supplier\<U>
* Function\<U,R>
* Predicate\<T>

## Feature 1 - Lambada expression
Below code is typical example of how we do the iteration in Java7.
```java
public class TraditionalMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));
        }
    }
}
```

or


```java
public class TraditionalMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        for (int number : numbers) {
            System.out.println(number);
        }
    }
}
```

Now with Java 8; we get few other method to iterate our objects in collections. Let's see it.

**Method 1:**
```java
public class ModernMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer value) {
                System.out.println(value);
            }
        });
    }
}
```

The above one is use anonymous class and implement the logic. Here we implemented the consumer interface. But with java
8 lambadas expression we can convert into more short.

**Method 2:**
```java
public class ModernMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.forEach(value -> System.out.println(value));
    }
}
```
We converted our functional interface to lambada expression and did same thing. We can also do it by method reference
like below:
```java
public class ModernMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.forEach(System.out::println);
    }
}
```

Now what is difference between implementing anonymous class and lambada expression. Just remember basic from java when
we implement any anonymous class it will create class file like Classname$1.class and so on. So just consider if we 
iterate those number 20 times with implementation it will generate those many classes. While using lambada expression 
there is no extra classes is generated, it is compile within main class itself.

## Feature 2 - Creating the Functional Interface
For this we will take an example. We will create an interface; use it in class, refactor as lambada. Let's take an 
example class which is used to measure time taken by a method. If we wanna measure multiple method the code block
is much duplicated for take record of start and end time; which is not good. We have wrote such code in 
[InitialImplmentation class](src/main/java/java8/feature2/InitialImplementation.java).

There is code duplication problem with initial code. Let's fix it by refactoring the code and can say we will take a class, 
and we call its method to measure the time took by method to run. We have created implementation for this in class
and interface; we kept only one abstract method in the interface and marked that as the @FunctionalInterface. As 
discussed in functional interface section that for an interface to be a functional interface, it should have only
one abstract method. Our class [TimeIt](src/main/java/java8/feature2/TimeIt.java) have one method and take 
[MethodImplementation](src/main/java/java8/feature2/MethodImplementation.java) as argument and call the method codeBlock,
It will measure the time to execute whatever instruction provided in the codeBlock. As MethodImplementation is an 
interface with single abstract method, and we declared the interface as @FunctionalInterface. Now Let's see how
we can use in Refactored Traditional method to pass and measure time till java 7 
[RefactoredTraditionalImplementation](src/main/java/java8/feature2/RefactoredTraditionalImplementation.java).

```java
  TimeIt.measureMethodExecutionTime(
                new MethodImplementation() {
                    @Override
                    public void codeBlock() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
```

In above code I can see a lot of ceremony and not much readable. Since this is a functional interface we can convert it
to lambada function. Let's see refactoring in 
[RefactorModernImplementation](src/main/java/java8/feature2/RefactoredModernImplementation.java).

There are a below interface which JDK already provides for simple operation.
1. Consumer
2. Supplier
3. Predicate
4. Function

Let's see each interface what it does.

**1. Consumer :-** Consumer interface takes the value and work with it. Consumer interface has one abstract method
which is accept; which takes the value and perform the operation on the value. The Consumer interface also
has default method which is andThen, Which takes another consumer as argument and return a consumer. andThen is for
chaining of consumers.

**2. Supplier :-** Supplier interface is for provide the value. This interface has only one method in it which is get.
There is no default method declared interface. It is used for lazy creation of object, and optional's orElseGet or
elseThrow methods.

**3. Predicate :-** It is for evaluating expression and return response as true or false. It is most for stream filter
kind of operation, also used if we wanna pass that to function for dynamic conditions inside method (Strategy Pattern). 
Predicate has abstract method test. Predicate has few default methods like negate() to invert the response, or & and
method to combine multiple predicates in a statement.

**4. Function :-** It takes an input and returns an output. This is for implement any function. This we can see use 
in map of streams. The function has one abstract method apply. There are 2 default methods which are andThen and compose.
andThen chain function in sequence, while compose will reverse chain the function i.e. argument function will executed
first and then the caller function. 

## Feature 3 - Method References
Till now we have seen that we can pass lambada expression to function. In Java 8 we can also pass the method reference 
instead of the lamabda function. If we write a function which is doing nothing calling other function, we can replace it
with the method reference. We can pass the method reference by 'Class or Object::method_name' or "target::method_name".

Static method reference is allow with class only; we can't use instance for static method reference.

Method reference routed in two ways; in one way parameter is passed as argument and in other way parameter is used as target
to call the method. Let's see this as example.

[MethodReferenceAsArgument](src/main/java/java8/feature3/MethodReferenceAsArgument.java) we are calling static method of
Person class which is printPerson(Person person), this takes person object as argument so in our stream foreach the
parameter is used as argument and passed it to printPerson.

[MethodReferenceAsTarget](src/main/java/java8/feature3/MethodReferenceAsTarget.java) we are calling instance method of 
Person class printPersonInformation() which does not take any argument but prints instance information from
whichever instance it is called. So in this the parameter is used as target on which the method is invoked.