# Java 8 Features and Examples
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

## Feature 2 - Interface default methods
In Java 8 we can write method implementation in interfaces; for do that we need to use default keyword. Please find
example in com.example.java8.feature2. Let's understand few things about interface default methods.
1. Default methods can be overridden in subclasses.
2. Default method follow hierarchy when calling the method i.e. extending class has higher priority then the extending
interface method.
3. If two interfaces have same method, and they don't have any class hierarchy then we need to resolve this problem
in implementing class. Either we need to write our own implementation or can call one or both of super implementation in
a sequence. In simple words I can say whenever diamond problem comes from interfaces; it should be solved by implementing
class.

## Feature 3 - Passing Lambada to function
For this we took an example in traditional method till java 7; where we want to add the numbers in a list, then we have
some condition before adding. In Java 7 we implemented using 3 methods, and almost we are duplicating the code.
(com.example.java8.feature3.TraditionalMethod) and 
Then we took the Java8 transformation and removed the duplicate using taking lambada expression as argument in function.
We took the Predicate as function argument to check what to sum or not. As this example we can use so many places
and can be reduce duplicate code and, also provide extensibility of operation.