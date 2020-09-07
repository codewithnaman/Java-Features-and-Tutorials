# Java 9 Features
With Java 9 release there are number of features added. Let's have a look on the features; With Java 9 Modules feature
is also added; which we look in detail after all other major features covered.

## Interfaces and private method in interfaces
With Java 8 we can provide the implementation details in the interface with public default method or static method; But
in Java 8 we can't provide private method or private static method implementation in the interface. With Java 9 they
have removed these limitations, and you can provide write private method implementation as well as private static 
methods.

Now questions comes why we may require private method is interface. So consider a case where you want to execute a piece 
of code more than one time for your public default methods, and you don't want to expose the code or method which is 
commonly used by both of the default methods. Then in that case we would like to create a common method and like to call 
from within the default methods and make it private to that interface. Then with Java 9 this is possible where we can 
create private methods as well private static methods.For Example in 
[Feature1Interface](src/main/java/java9/feature1/Feature1Interface.java) we have got the RunTime using private static 
method while we get number of threads using the default method to avoid duplication of the code in the Interface.

## _ in java 9
From Java 9 _ is a keyword now, and you can't use any further to naming variable like below:
```text
int _=8;
```
If your code is using such variable name then proper naming should be provided; or with Java 9 you get below compilation
error.
```
java: as of release 9, '_' is a keyword, and may not be used as an identifier
```
But still you can name variable like below:
```text
int __=50;
```
The above is not recommended practice.

## try-with-resources 
With Java 7 we had Automatic Resource Management using AutoClosable interface and creating the resource in try block 
and using within it only. We can't pass the reference of resource to function where its reference can be used within
the try block, so it can be autoclose within that function itself till Java8. In Java 9 we can pass the resource to 
function where it does its work and close the resource. Let's see an example:
```text
public class Feature3ARMFunctionPassing {

    public static void main(String[] args) throws IOException {
        File file = new File(Feature3ARMFunctionPassing.class.getClassLoader().getResource("testFile.txt").getFile());
        if(file.exists()){
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            readLines(fileReader);
           // fileReader.readLine();
        }
    }

    private static void readLines(BufferedReader reader) {
        try (reader){
            String line = null;
            while(!Objects.isNull(line = reader.readLine())){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
``` 

In above code we have created the resource in main method and use it inside the method readLines where we have given
the reader inside try block which means after consuming the resource functionalities; it will be closed. 

With this restriction revoked there is a caveat created which has raised and that need to handled by the developer. After
closing resource inside the function; the resource should not be used in caller function or can't be used by any other 
function. Just like in our example I have commented out the line fileReader.readLine() in main method; Because resource
has already closed, so I can't use it further if I uncomment the line then I will get "java.io.IOException: Stream closed".
Because the stream has already closed in readLines method.

## finalize deprecation
When we start developing a program, or an application there are two concern which developer need to handle. One is memory
and other is resources. When we program in C++ we need to handle both. But with introduction to Java; memory problem 
had handled by Garbage Collector but resource handling it provided initially using finalize method. And with development
of Language itself it comes to try-with-resources and other solutions to handle the resources.

So From Java 9, Object method finalize is marked as deprecated and may be remove in future versions of Java. So, try to 
avoid using finalize for cleaning of the resource or any other work. Even with finalize method, we don't have control
over the call of finalize method because this method calls whenever the garbage collection happens. So, initially it  
was good place to clean up resource but when we get Try-with-resources and AutoClosable; I think try-with-resources
is good place to clean up resources. 

## finalize, try-with-resources and cleaner
Let's see this with an example where we have a resource class and resource is hold by another class, and we perform our 
task in main and want to clean up resource. We will see with all three methods; and when to use to which.

* **finalize method** : We will see example with this; But never ever use this method for resource cleaning because first
we don't have control over finalize method call it can happen anytime when object is eligible for garbage collection
and garbage collector runs and second it is got deprecated from Java 9 and may be remove in Future version of Java.
```text
class Resource {
    public static int calledFinalized;
    private int value;

    public Resource(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void finalize() throws Throwable {
        calledFinalized++;
        System.out.println("Cleaned Resource");
    }
}



class Holder {

    Resource resource;

    public Holder(int value){
        resource = new Resource(value);
    }

    public int performOperation(){
        return resource.getValue();
    }

}
```

In above code we have created Resource class with finalize method and Holder class which hold resource. Now if we have
code like below:
```text
public class Feature4Demo {

    public static void main(String[] args) {
        Holder holder = new Holder(9);
        holder = null;
        System.out.println("Finalized Called : " + Resource.calledFinalized);
    }
}
``` 
"Cleaned Resource" will not print a single time itself, and we can see output like below:
```text
Finalized Called : 0
```
Garbage collection doesn't happen for above program due to which finalize is not called a single. Let's try to create
a 1M objects and then discarding them and then see if finalize calls.
```text
public class Feature4Demo {

    public static void main(String[] args) {
       for(int i=0;i<1000000;i++) {
                   Holder holder = new Holder(9);
                   holder = null;
               }
       System.out.println("Finalized Called : " + Resource.calledFinalized);
    }
}
```
So, with above call we can see in output that "Cleaned Resource" prints multiple time, also we can see below line in 
output too.
```text
Finalized Called : 9587
```
Which means that only 9k out of 1M resources has cleaned with garbage collection; while other didn't close. So finalize
it not best place to perform cleaning up the resources. Apart from that if we wanna perform some other operation before
removing object from memory then we can use it; But this method has been deprecated, we will what alternative we have
to perform an operation before removing an object from memory.

* **try-with-resources** : It is most preferred way to clean up resources. We will also have control over when to call
the method to close resource. For this resource need to implement AutoCloseable interface and implement close method.
This close method we can call manually; or if we create resource in try block or put the reference in try block then
it will call the close method automatically when try block completes or exception thrown.

Let's see example for this:
```text
class Resource implements AutoCloseable {
    public static int calledFinalized;
    public static int closedResource;
    private int value;

    public Resource(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void finalize() throws Throwable {
        calledFinalized++;
        System.out.println("Cleaned Resource");
    }

    @Override
    public void close() throws Exception {
        closedResource++;
        System.out.println("Resource Closed");
    }
}


class Holder implements AutoCloseable{

    private Resource resource;

    public Holder(int value) {
        resource = new Resource(value);
    }

    public int performOperation() {
        return resource.getValue();
    }

    @Override
    public void close() throws Exception {
        resource.close();
    }
}
```

Both Resource and Holder class is need to implement AutoCloseable; and in close method we perform the logic to close
resource. In holder class whatever resource we want to close; we have to call close on them one by one, if multiple 
resource it is holding.

Let's see some program output for this:
```text
    public static void main(String[] args) {
        try(Holder holder = new Holder(9)) {
            System.out.println(holder.performOperation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Close method called : "+ Resource.closedResource);
    }
```
 Thre output of above code is:
 ```text
9
Resource Closed
Close method called : 1
```
We created the resource in the try block; and as try blocks complete we can see close method called. Also, we can see
the called count has increased. We have seen in last feature [section](README.md#try-with-resources) that close is also 
called if we pass the reference in try block. Let's see if we do it 1M times will it close it all the times:
```text
    public static void main(String[] args) {
        for(int i=0;i<1000000;i++) {
            try (Holder holder = new Holder(i)) {
                System.out.println(holder.performOperation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Close method called : "+ Resource.closedResource);
    }
```
For above program we can also se the last line is "Close method called : 1000000". It means whenever our resource use
is finish we can close it.

* **Cleaner**: This is alternative to finalize method. Cleaner works with a PhantomReference.

* PhantomReference: A PhantomReference is the substitute to finalize; When an object becomes otherwise unreachable i.e.
not referenced apart from PhantomReference it becomes a phantom object. Phantom objects typical in a queue, and we can 
get a reference from the queue and remove it from other places where we are holding on to PhantomReference. This will
useful when we have an object which is referenced from multiple places and once the references goes away all the places
and object become eligible for the garbage collection then with PhantomReference we will get notify that object can be 
garbage collected, and we can perform the cleanup operation or any operation we want to perform before removing object
from memory.

Let's understand how Cleaner works.
* First we need to implement Runnable interface on the Resource and in run method we provide the cleanup logic for the
resource.
* Then in Holder class we will register the resource with Cleaner and if you want to call manual cleanup on some 
condition, we need to take reference after registering with Cleaner which provides us reference of Type Cleanable.
* If we want to manually call the resource cleanup; then we need to call the clean method on the Cleanable reference
we got in last step.

Let's have a look on below code where we are registering the resource while creating the resource and calling the clean 
method in close method of autoclose i.e. if we provide the holder in try block then once the try block is over clean 
method will be called and run method of resource will run.
```text
class Resource implements AutoCloseable, Runnable {
    public static int calledFinalized;
    public static int calledClose;
    public static int calledCleanerRun;
    private int value;

    public Resource(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void close() throws Exception {
        calledClose++;
        System.out.println("Resource Closed");
    }

    @Override
    public void run() {
        calledCleanerRun++;
        System.out.println("Cleaner Run Called ");
    }
}

class Holder implements AutoCloseable{

    private Resource resource;
    private Cleaner.Cleanable cleanable;
    private Cleaner cleaner = Cleaner.create();

    public Holder(int value) {
        resource = new Resource(value);
        cleanable = cleaner.register(this,resource);
    }

    public int performOperation() {
        return resource.getValue();
    }

    @Override
    public void close() throws Exception {
        //resource.close();
        cleanable.clean();
    }
}
```

Let's try above code in main and see the output:
```text
    public static void main(String[] args) {
      try(Holder holder = new Holder(9)) {
            System.out.println("Value from Holder :"+holder.performOperation());
       } catch (Exception e) {
             e.printStackTrace();
       }
    }
```
The output of above program will be like below:
```text
Value from Holder :9
Cleaner Run Called 
```

This is happening by calling clean method and AutoClosable we implemented on holder class. But What happen when we don't
provide the holder in try block and  call the clean method when removing object from the memory. For this we need to 
register a PhantomReference to holder object which will call the clean method on resources registered with Cleaner. 
Let's try below code:
```text
    public static void main(String[] args) {
        Holder holder = new Holder(9);
        new PhantomReference<Holder>(holder, null);
        System.out.println("Cleaner Called : " + Resource.calledCleanerRun);
    }
``` 
The output of above program is below:
```text
Cleaner Called : 0
```
But why not run method called? Since the JVM had sufficient space for the program and garbage collection never happened,
so cleaner never called. Cleaner clean will be called on the object when there is no reference present to object and 
garbage collection happened. So It is more likely the finalize method; we can't predict when the clean method will be
called; but will help if we want to perform some operation before removing an object from memory.

Let's run this in a loop and try creating 20k objects and see the output.
```text
    public static void main(String[] args) {
        for(int i=0;i<20000;i++) {
            Holder holder = new Holder(9);
            new PhantomReference<Holder>(holder, null);
        }
        System.out.println("Cleaner Called : " + Resource.calledCleanerRun);
    }
``` 
The output for above program prints "Cleaner Run Called" many times and below line:
```text
Cleaner Called : 2673
```

Just Cautious About cleaner calls since they run in different threads and if you are expecting more than 10k or more
resource will be garbage collected then you should have high PID limits on your system. otherwise, your program may stuck
or throw error for process IDs.
    
## Stream new methods
### dropWhile and takeWhile
In Java 8 we have limit and skip method to discard some elements over collection stream, But just consider we want
to allow elements after a condition has met or discard the elements till a condition doesn't meet. To develop this in 
functional programming a lot of effort required, and most of the cases we go in our traditional way to achieve this which
is for loop with break or continue statement in if block.

With Java 9, we have functions to achieve this in functional programming which are dropWhile and takeWhile. We have 
created a list using below function and, we will try new stream function on this list stream.
```text
    public static List<Person> createPersonsList(){
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Ananya", 24),
                new Person("Bahubali", 12));
        return persons;
    }
```

dropWhile method takes the predicate and discards the element till the condition is true, or We can say it will allow 
the elements once the condition become false. Let's see this by example:
```text
  private static void testDropWhile() {
        createPersonsList().stream()
                .dropWhile(person -> person.getAge()>20)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
```
The output of above code is like below:
```text
SARA
AAROHI
CHHAYA
ANANYA
BAHUBALI
```

So, dropWhile discarded the elements or persons till it get a person whose as is less than 20 as provided in the predicate,
and after that all the values gone through the stream and perform the map operation and print above output.

takeWhile method takes the predicate and takes the element till the condition is true, or We can say it will discard 
the elements once the condition become false. Let's see this by example:
```text
    private static void testTakeWhile() {
        createPersonsList().stream()
                .takeWhile(person -> person.getAge()<80)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
```

The output of above code is like below:
```text
AAKASH
TINA
SARA
AAROHI
```

So, takeWhile allowed the elements or persons till it get a person whose as is less than 80 as provided in the predicate,
and allowed values gone through the stream and perform the map operation and print above output.

Let's see one more example, and its output:
```text
    private static void testDropWhileWithTakeWhile() {
        createPersonsList().stream()
                .dropWhile(person -> person.getAge() > 20)
                .takeWhile(person -> person.getAge() < 80)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
```
In above example we are dropping till the person's age is greater than 20 and after that we will take only persons whose
age is less than 80. Let's see output:
```text
SARA
AAROHI
```
So it dropped the element till TINA because till TINA every person's age is greater than 20 and then take until takeWhile
has the person whose age is less than 80. Once takeWhile get a person age more than 80 which is Chhaya in our case; it 
discarded values after that person in list.

### Iteration in Stream
In Java 8 for iteration we have limited method; which generates the number in range. Let's see the Java 8 method example:
```text
 private static void java8IntStreamMethods() {
        Stream.iterate(10, i -> i + 1)
                .limit(5)
                .forEach(System.out::println);
        System.out.println("------------------");
        IntStream.range(10,15)
                .forEach(System.out::println);
        System.out.println("------------------");
        IntStream.rangeClosed(10,15)
                .forEach(System.out::println);
    }
```

So in first we are generating the unbounded stream of numbers and limiting the values using limit function; But What
if we want to limit it via a condition. 

In second function we are generating the bounded stream of numbers starting from the 10 to 15-1; But if we want to 
increment number by 3 or more values for next; then it is not possible with range function.

Same as second function rangeClosed provide the bounded stream of numbers including the last number provided in function.

Java 9 resolve this problem by providing iterate method in IntStream Class; which takes the 3 arguments or 2 arguments 
depending on the use. Let's see the example for this:
```text
    private static void testIntStreamIterationStepUp() {
        IntStream.iterate(1, i -> i < 18, i -> i + 3)
                .forEach(System.out::println);
    }
```
The above code looks like the traditional for loop which takes three arguments first one is the from where to start,
second one is condition till iteration need to perform and, third one is after each iteration value should be increment 
with.

The output of above code be like below:
```text
1
4
7
10
13
16
```

So with above code we have more control over the iterate method for the function programming. Let's see an example
where is seed is higher, and we want to decrement the value and stop at 0.
```text
        IntStream.iterate(18, i -> i > 0, i -> i -3)
                .forEach(System.out::println);
```
The output for above code is below:
```text
18
15
12
9
6
3
```

So we can increment or decrement the value with a particular number. What If we want to generate the unbounded stream and 
want to limit it via a condition; Let's see how to do that using iterate method.
```text
    private static void testIntStreamIterationUnbounded() {
        IntStream.iterate(1, i -> i + 3)
                .dropWhile(number -> Math.sqrt(number) < 3)
                .takeWhile(number -> Math.sqrt(number) < 5)
                .forEach(System.out::println);
    }
```
In above example Intstream.iterate will generate an unbounded stream of numbers with increment of 3 numbers; so stream
will be like 1,4,7,10,13.... and so on, but we bounded them using condition by using dropWhile function and takeWhile 
function. In our example numbers whose squreroot is less than 3 are discarded and whose will take only the numbers
whose squreroot root is greater than 3, then in takeWhile we have defined that we will take only those numbers whose
squreroot is less than 5. So, When numbers squareroot is found greater than 5 then the stream will terminate 
and the numbers which pass from both the condition will be printed. Output of our program is like below:
```text
10
13
16
19
22
```

## Optional New Methods
### ifPresentOrElse method
Let's first take a look how we get the value in Java 8 and then what's Java 9 introduce. Let's take below example:
```text
 public static List<Integer> getListOfNumbers() {
        return Arrays.asList(10, 51, 57, 25, 82, 91);
    }
```
We created a function as above to demonstrate the Optional function.
```text
   private static void traditionalJava8Example() {
        System.out.println(
                getListOfNumbers()
                        .stream()
                        .filter(integer -> integer > 80)
                        .findFirst()
        );
        System.out.println(
                getListOfNumbers()
                        .stream()
                        .filter(integer -> integer > 800)
                        .findFirst()
        );
    }
```
If we run above program we will get below output:
```text
Optional[82]
Optional.empty
```
Since in first run the value is present which is greater than 80, so it returns optional of it; While no value greater
than 800 is present,so it returns Optional of Empty. Now we want value out of option then I we generally write it.
```text
    private static void traditionalJava8ExampleGetMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        System.out.println(optionalInteger.get());
    }
```
If we run above code with passing value 80 then we get output 82; But if we run it with passing value 800 then it blow
up with NoSuchElementException.

To avoid the Exception on get method we have done below in Java 8:
```text
   private static void java8OrElseMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        System.out.println(optionalInteger.orElse(0));
    }
```
So in above example if value is present we return the value; and if it is Optional of Empty then we return a default 
value which is 0 in above example. Let's say we don't want to return 0 as default value if we get Optional of Empty
We want to print some other string message; Then we will do below in Java 8:
```text
    private static void java8IfPresentMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        if (optionalInteger.isPresent()) {
            System.out.println(optionalInteger.get());
        } else {
            System.out.println("Value Not found");
        }

    }
```
In above code we have check if the value is present it will call get method and print it using System.out; otherwise
it will print the message "Value Not Found". But in above code we have done a lot of ceremony, and it is not the 
functional way to perform the operation. 

If we want to achieve above in Java 8; That will be like below:
```text
    private static void java8IfPresent(int findFirstValueGreaterThan) {
        getListOfNumbers()
                .stream()
                .filter(number -> number>findFirstValueGreaterThan)
                .findFirst()
                .ifPresent(System.out::println);
    }
```

But above method perform only if part; We don't have functional way to perform the if and else both part in Java 8 as 
we seen in example previous to above.

Java 9 Come up with a method to perform operation of if and else both with method of ifPresentOrElse. Let's see by below
example:
```text
    private static void java9IfPresentOrElse(int findFirstValueGreaterThan) {
        getListOfNumbers()
                .stream()
                .filter(number -> number>findFirstValueGreaterThan)
                .findFirst()
                .ifPresentOrElse(System.out::println,()-> System.out.println("Value Not found"));
    }
```
In the function IfPresentOrElse take two arguments first is the Consumer function which takes the value if present and
perform the operation; and Second argument is Runnable, which will executed when the value is not present, or we get
Optional of Empty.

In Java 8 we can still do what ifPresentOrElse do without getting any exception like below:
```text
    private static void java8IfPresentOrElse(int findFirstValueGreaterThan) {
        System.out.println(getListOfNumbers()
                .stream()
                .filter(number -> number > findFirstValueGreaterThan)
                .findFirst()
                .map(element -> element.toString())
                .orElse("Value Not found"));
    }
```
Here we are transforming the function with map element and if not present then returning other value.

### or method
Till now, we have seen the method to perform the operation to get the value and if absent then perform other operation.
Let's say we don't want to perform an operation if value is not present and, also don't want to return a default value;
instead we want to return an Optional of Default value for further process then how we can do it. Then, Java 9 
introduces Or method to return Optional of default value if we get the Optional of Empty. Let's see below example:

```text
 private static void java9OrMethod(int findFirstValueGreaterThan) {
        Optional<Integer> optional = getListOfNumbers()
                .stream()
                .filter(e -> e > findFirstValueGreaterThan)
                .findFirst()
                .or(() -> Optional.of(0));
        System.out.println(optional.get());
  }
```
In above function we are using Or method so it will return the Optional of 0; so get calls never fails because
if value is present we get the Optional of value, and if not present then we get Optional of 0. 

Let's see a quick comparison of orElse, map and or method comparison below:

|  Method                   |  Optional<T>(n)   | Optional<T>.Empty     |
|---------------------------|------------------ |-----------------------|
|  orElse(value)            |  T n              |   value               |
|  map(Function<T,R> f)     |  Optional<R>(f(n))|   Optional<R>(empty)  |
|  or(()->Optional<T>(k)    |  Optional<T>(n)   |   Optional<T>(k)      |

In above table T,R are the type which Optional type we get or Map Function returns; f(n) is the value which we get
by computing the function on value n. And k is value for which we return default optional.

### stream method
As we know stream works with 0,1 and n number of values. But till Java 8 there was no method present which can convert the 
Optional of value or Empty to stream. But with Java 9, they have introduced the stream method to convert an Optional 
into stream which contains no value or 1 value.

Now question is why this required; Many times we see that a function which takes the Stream as argument perform series
of operation as pipeline. Now consider if we have Optional value, and we want to process that value with same pipeline
then to perform such operation in Java 8 we need to write below code:
```text
private static void java8OptionalToPipeLineExample() {
        Optional<Integer> value = getListOfNumbers()
                .stream()
                .filter(number -> number > 80)
                .findFirst();
        if(value.isPresent()){
            processInput(Stream.of(value.get())));
        }
    }
```
Where we are getting the Optional in the different block and then make stream of value in a different block and pass it
for processing. With Java 9 We can directly call stream method on optional which will generate the stream of 1 value or
no value. Let's see in below code:
```text
private static void java9OptionalToPipeLineExample() {
        processInput(getListOfNumbers()
                .stream()
                .filter(number -> number > 80)
                .findFirst().stream());
        processInput(getListOfNumbers()
                .stream()
                .filter(number -> number > 800)
                .findFirst().stream());
    }
```
So in above example we are converting the Optional to stream and passing into the function which process them. In above
example first will have value Optional of 82 which converts to stream and processed by function while second one have
Optional of Empty and Stream with no value has passed to function.

## Collectors New Methods
