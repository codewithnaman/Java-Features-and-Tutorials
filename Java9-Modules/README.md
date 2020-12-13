# Java 9 Modules 

# Lesson 1 : Environment Setup and Why Modules
## Environment Setup
Let's first setup environment with Java 8 and Java 9 or 9+; for this example we are setting up the OpenJDK 8 and OpenJDK
14. We are doing setup for both to see brief difference and then 9+ we will use in our most examples. To install the
OpenJDK 8 and 14 we are using below commands on Ubuntu.
* Java 8:
```text
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ sudo apt-get install openjdk-8-jdk-headless
...
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ java -version
openjdk version "1.8.0_265"
OpenJDK Runtime Environment (build 1.8.0_265-8u265-b01-0ubuntu2~20.04-b01)
OpenJDK 64-Bit Server VM (build 25.265-b01, mixed mode)
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ javac -version
javac 1.8.0_265
```
* Java 14:
```text
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ sudo apt install openjdk-14-jdk-headless
...
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ java -version
openjdk version "14.0.1" 2020-04-14
OpenJDK Runtime Environment (build 14.0.1+7-Ubuntu-1ubuntu1)
OpenJDK 64-Bit Server VM (build 14.0.1+7-Ubuntu-1ubuntu1, mixed mode, sharing)
ngupta@NAMAN-DELL:/mnt/c/Users/naman$ javac -version
javac 14.0.1
```
So we have installed both the JDK; I set the Java 14 as my default JDK.

## Why Modules?
Let's understand what problem JDK team is solving using Modules. Modules are part of 
[Jigsaw Project](https://openjdk.java.net/projects/jigsaw/) of JDK.

### Size of rt.jar
If we give a look on the size of library specially rt.jar to previous versions of the java; it's huge and contains a lot 
of functionality; which you might be using or might be not in your application but provided to you with this jar. Let's
see look the size of rt.jar in Java 8.
```text
ngupta@NAMAN-DELL:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib$ ls -lrth rt.jar
-rw-r--r-- 1 root root 63M Aug  3 06:46 rt.jar
```
So it is huge and contains many functionality like CORBA, Swing, AWT etc. even you use these features in your application
or not that will bundle in library and provided in this jar. If you use these features in your application in the future,
it is readily available, and you can write code for those features without including any library.

With Java 9 or 9+ these functionalities aren't provided by default to you; When you use those you include its modules 
and then it will available to you.

### Lack of clarify on dependencies
We generally use the Maven or Gradle for dependency management in our project; which manage our compile time dependency.
Java 9 provides the dependency graph which will be same as compile time and carried at runtime which provides more 
security and reliability for the code.

### Security
Till Java 8 if in our application anybody drops a library with the same package name then java sees both package loaded
and keeps running application. So anybody can drop their jar with the same package and use your classes for their code.

While in Java 9; that does not allow modules with same name; and takes only module with the one name. So if anybody drops
the jar with same module Java 9 load one module and kicks out the other.
    
### Public is too open
public classes is too open; If anything is public does not mean you should use it. Moving forward with Java 9 public is
no more public; for making a class public needs to be exported for become public. We will see it further how this can be
done further in this tutorial.

### Late runtime failures
Fail-fast is a good model; as we get quick feedback and can handle failures effectively. In Java 8; we copy the library
and include the dependencies in classpath; But just consider that we forgot or dropped one library for the application
to include in the classpath and started the application. Application will start fine and complain about the library that
ClassNotFound when we hit the flow. It will not tell immediately that jar or classes missing which may require at runtime.

With Java 9 it complains at start of the application if anything is missing for application before the application start.
It is possible because of dependency graph we built at compile time and same used at runtime to verify this.

Let's see how many modules are present for our Java version and how to list it:
```text
ngupta@NAMAN-DELL:/$ java --list-modules
java.base@14.0.2
java.compiler@14.0.2
java.datatransfer@14.0.2
java.desktop@14.0.2
java.instrument@14.0.2
java.logging@14.0.2
java.management@14.0.2
java.management.rmi@14.0.2
java.naming@14.0.2
java.net.http@14.0.2
java.prefs@14.0.2
...
```
We can see the java.base which is needed for all other modules and other modules which we can list by `java --list-modules`
command. Let's see how many modules we have with Java 14:
```text
ngupta@NAMAN-DELL:/$ java --list-modules|wc -l
72
```
#Lesson 2 : Module creation and Using it in other module 
## Creating modules
### Creating Classes without modules
We will create a class and compile it with Java Version 9 or 9+ and then we will see the output of that.
```text
ngupta@NAMAN-DELL:~$ mkdir -p compute/com/example/compute
ngupta@NAMAN-DELL:~$ ls -lrt
total 4
drwxr-xr-x 3 ngupta ngupta 4096 Nov 16 11:05 compute
ngupta@NAMAN-DELL:~$ cd compute/com/example/compute/
ngupta@NAMAN-DELL:~/compute/com/example/compute$ ls -lrt
total 0
ngupta@NAMAN-DELL:~/compute/com/example/compute$ vi Calculator.java
ngupta@NAMAN-DELL:~/compute/com/example/compute$ cat Calculator.java
package com.example.compute;

public class Calculator{
        public int add(int arg1,int arg2){ return arg1+arg2;}
}
```
We created a directory and then created class Calculator.java. Let's compile it and see what it generates. For this
we crated a script to save repetitive work which contains the below content:
```text
ngupta@NAMAN-DELL:~$ cat build.sh
rm -rf output
mkdir -p output/mlib
mkdir -p output/classes

javac -d output/classes `find compute -name *java`
jar -c -f output/mlib/compute.jar -C output/classes .
```
Now we will run the script; and see the files. Also we will see a look inside the jar. 
```text
ngupta@NAMAN-DELL:~/output/mlib$ ls -lrt
total 4
-rw-r--r-- 1 ngupta ngupta 1009 Nov 16 11:16 compute.jar
ngupta@NAMAN-DELL:~/output/mlib$ jar xf compute.jar
ngupta@NAMAN-DELL:~/output/mlib$ ls -lrt
total 12
drwxr-xr-x 3 ngupta ngupta 4096 Nov 16 11:16 com
drwxr-xr-x 2 ngupta ngupta 4096 Nov 16 11:16 META-INF
-rw-r--r-- 1 ngupta ngupta 1009 Nov 16 11:16 compute.jar
ngupta@NAMAN-DELL:~/output/mlib$ cat META-INF/MANIFEST.MF
Manifest-Version: 1.0
Created-By: 14.0.2 (Private Build)

ngupta@NAMAN-DELL:~/output/mlib$ ls -lrt com/example/compute/Calculator.class
-rw-r--r-- 1 ngupta ngupta 270 Nov 16 11:16 com/example/compute/Calculator.class
```

We can see that jar contains the META-INF file and class files. Let's also check module information of the jar.
```text
ngupta@NAMAN-DELL:~/output/mlib$ ls -rlt
total 4
-rw-r--r-- 1 ngupta ngupta 1009 Nov 16 11:16 compute.jar
ngupta@NAMAN-DELL:~/output/mlib$ jar -f compute.jar -d
No module descriptor found. Derived automatic module.

compute automatic
requires java.base mandated
contains com.example.compute
```
In above, we can see that there is a `No module descriptor found. Derived automatic module.`. We will learn about 
'Derived automatic module' more in upcoming sections. Let's understand what java did for us in above output. 
Since No module descriptor found Java created an automatic module and name it on the jar name which is `compute automatic`. 
Then it is specifying that it requires java.base package from the JDK, and it includes the package 
com.example.compute.

### Create our own module
For creating module we are using workspace present here [compute-module](compute-module).

To create a module we create module-info.java and provide the specification like what is required and what we are 
exporting as part of the module. Let's see how module-info.java looks like and then fill the further information 
for this.
```java
module com.example.computation {
    
}
```
module is not keyword in java; but to declare the module we declare like above snippet. Let's create the jar and see the 
information of jar. 
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
requires java.base mandated
```

As we can see that automatic module is not used for above jar and module name becomes the `com.example.computation` here.
This is coming from the module-info.java; We didn't declare any require, but it added require java.base.

Let's add it manually and see the output.
```java
module com.example.computation {
    requires java.base;
}
``` 
Let's now see the information of jar:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
requires java.base
```
If we compare the output when we didn't include the requires; it added by itself with `requires java.base manadated` ; 
But when we explicitly provide this it will give whatever we provided `requires java.base`.

Right now we are not exporting any package from the module; so no information about packaged has been displayed. Let's 
export our package and then again observe the output.
```java
module com.exmaple.computation {
    requires java.base;
    exports com.example.compute;
}
``` 
Output of Jar module description:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
exports com.example.compute
requires java.base
```
* The module always requires other modules and exports its own packages.

We have seen how to create a module; Let's now see how to use it.

### Use modules in our module
For this we have created an example of [user application](lesson-2/user-application-module). Where we are taking the compute
module as required module and using its package and methods. Let's see module-info.java of this module.
```java
module com.example.user.application {
    requires java.base;
    requires com.example.computation;
}
```
Let's see the information of this module jar.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\user-application-module>jar -f target\user-application-module-1.0-SNAPSHOT.jar -d
com.example.user.application@1.0-SNAPSHOT jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/user-application-module/target/user-application-module-1.0-SNAPSHOT.jar/!module-info.class
requires com.example.computation
requires java.base mandated
contains com.example.user
main-class com.example.user.Application
```
This requires the computation module; Which is exporting compute package, and we are using the Calculator class in our
main class of User application. Let's have a look on the main class then output of jar.
```java
package com.example.user;

import com.example.compute.Calculator;

public class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(5,3));
    }
}
```
Let's look output of jar:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\user-application-module>java -cp ..\compute-module\target\compute-module-1.0-SNAPSHOT.jar;target\user-application-module-1.0-SNAPSHOT.jar com.example.user.Application
8
```
or
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\user-application-module>java -p ..\compute-module\target\compute-module-1.0-SNAPSHOT.jar;target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application
8
```
We can see it is providing proper output; and we are using other module in our module. 

Creating and using module is two-way process; we need to create the module and export package and user module need to 
declare the module in module-info.java requires. Let's see if any of them is missing then what error we get:
1. When we don't export package
```text
[ERROR] /D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/user-application-module/src/main/java/com/example/user/Application.java:[3,19] package com.example.compute is not visible
  (package com.example.compute is declared in module com.example.computation, which does not export it)
```
2. When We don't include in requires
```text
[ERROR] /D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/user-application-module/src/main/java/com/example/user/Application.java:[3,19] package com.example.compute is not visible
  (package com.example.compute is declared in the unnamed module, but module com.example.user.application does not read it)
```
* The above is saying unnamed module because the jar has been included in the classpath; if it will be included in module 
path; it will give proper module name. 

In above section we have seen that creating and using module is like hand-shake where one module need to export package 
while other have to import it for proper use. Let's see how module exports works and what it exposes in next section.

### Un-Exported Packages in module
For this we changed computation module, and we have added `com.example.random` and `com.example.random.impl` package.
In `com.example.random`; we have kept an Interface, and its implementation inside `com.example.random.impl` package.

We have exported only interface package as of now as part of the module-info.java not the implementation class. Also, we
have added one method in Calculator class which provides the instance of the RandomProvider. The Calculator class method
looks like below:
```text
 public RandomProvider getRandomProvider() {
        return new RandomProviderImpl();
    }
``` 

Now we use this in our user application class like below:
```text
  public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(5, 3));
        RandomProvider randomProvider = calculator.getRandomProvider();
        System.out.println("Random Number is " + randomProvider.getRandomNumber());
        System.out.println("RandomProvider Implementation got is " + randomProvider.getClass());
    }
```
The output of above is like below:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application

8
Random Number is 1237702826
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
```
We can see even we don't exported the impl package we are getting implementation indirectly from the Calculator class.

Let's see if we can access it directly and can use it.
```text
public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(5, 3));
        RandomProvider randomProvider = calculator.getRandomProvider();
        System.out.println("Random Number is " + randomProvider.getRandomNumber());
        System.out.println("RandomProvider Implementation got is " + randomProvider.getClass());
        RandomProviderImpl randomProvider1 = null;
    }
```
When we compile above code we get below error:
```text
[ERROR] /D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/user-application-module/src/main/java/com/example/user/Application.java:[5,26] package com.example.random.impl is not visible
  (package com.example.random.impl is declared in module com.example.computation, which does not export it)
```
So we are getting it indirectly as part of implementation of RandomProvider from the Calculator class , but we can't 
directly initialize it here because we don't export it from the module.

So we even can't mention the name of un-exported package or classes. Otherwise, we will get above error. Also, As 
discussed in above section the public class is no more public we can see here.

### Module Export and Reflection API
Let's try to create a method inside the RandomProvider implementation and try to invoke using the Reflection API of java.
We have created below method in RandomProviderImpl:
```text
    public double getRandomDecimalNumber() {
        return random.nextDouble();
    }
```
This is in the implementation class but not in the interface; Now Let's try to invoke this method using Reflection API
and see if we get access to this method using Reflection API.
```text
  try {
            Method decimalMethod = randomProvider.getClass().getMethod("getRandomDecimalNumber");
            System.out.println(decimalMethod);
        } catch (Exception e) {
            System.out.println(e);
        }
```

This will give if the method is present in the class. Let's see output of above:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application

8
Random Number is -177937742
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
```
So, we get the method name, and it exists in class. Let's invoke it:
```text
try {
            Method decimalMethod = randomProvider.getClass().getMethod("getRandomDecimalNumber");
            System.out.println(decimalMethod);
            System.out.println(decimalMethod.invoke(randomProvider));
        } catch (Exception e) {
            System.out.println(e);
        }
```
When we run above program; The output would be like below:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application

8
Random Number is 1133294128
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
java.lang.IllegalAccessException: class com.example.user.Application (in module com.example.user.application) cannot access class com.example.random.impl.RandomProviderImpl (in module com.example.computation) because module com.example.computation does not export com.example.random.impl to module com.example.user.application
```

As we can see in above output We can't access the method using Reflection API at runtime. Let's export the package and 
run the same program.
```java
module com.example.computation {
    exports com.example.compute;
    exports com.example.random;
    exports com.example.random.impl;
}
```
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application

8
Random Number is -1961149267
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
0.32558885048553976
```

### Opening Module or Package for runtime access
For giving runtime access to module we need to modify the module-info.java package like below:
```text
open module com.example.computation {
    exports com.example.compute;
    exports com.example.random;
}
```
Here `open` keyword gives access to module at runtime. So all package classes are available at runtime; and compile
time access of those classes which are exports are present in module-info.java. If we want to provide runtime access f
or specific package then we can provide like below:
```text
module com.example.computation {
    exports com.example.compute;
    exports com.example.random;
    opens com.example.random.impl;
}
``` 
Here `opens` keyword provide runtime access for the package; but still we don't get compile time access for the package.

Let's see output of our program after this change:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application

8
Random Number is 1435840007
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
0.29222097055068363
```

### Opening Module or Package to particular module
In this section we will open module or package to limited number or specified modules. For creating example of this
we are creating another [user-application-module](lesson-2/user-application-module2) and then restrict the runtime access of 
computation module to only one application module. This module is exactly same as the user-application-module apart 
from the module name. Let's see the module-info.java of this:
```java
module com.example.user.application2 {
    requires com.example.computation;
}
```

We will give the runtime access to application module 1, and it will not have access to application module 2. Let's see 
the computation module module-info.java to restrict access.
```java
module com.example.computation {
    exports com.example.compute;
    exports com.example.random;
    opens com.example.random.impl to com.example.user.application;
}
```

Now Let's run the both the application and see the output.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module\target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application
8
Random Number is -244269939
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
0.8789429751279432

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;user-application-module2\target\user-application-module2-1.0-SNAPSHOT.jar -m com.example.user.application2
8
Random Number is 513411651
RandomProvider Implementation got is class com.example.random.impl.RandomProviderImpl
public double com.example.random.impl.RandomProviderImpl.getRandomDecimalNumber()
java.lang.IllegalAccessException: class com.example.user2.Application (in module com.example.user.application2) cannot access class com.example.random.impl.RandomProviderImpl (in module com.example.computation) because 
module com.example.computation does not export com.example.random.impl to module com.example.user.application2
```

As we can see user-application-module has runtime access for the impl package while user-application-module2
throws error as it does not access. We can provide the comma separated values in module-info.java for providing the
access to that module. 

Now question comes why anybody wants to restrict packages to open to particular module not to rest of the world. Below
are two good reasons for that:
1. You want to provide the access to test module which should be limited to testing not open for use in development
purpose.
2. You want to restrict a module to use within your organization module then you cna build this.

### Module versioning
Right now we don't get much from the versioning; But in future with development of Java we may get much out of it.
Right now we need to understand how it works and how to create versions of module. Let's get started:
* Two versions of same module can't be exist in module path
* To create the module version we can provide the `--module-version=1.0.0` while creating the jar; In our example
maven is passing the artifact version as argument, and we can see below output:
```text
com.example.computation@1.0-SNAPSHOT jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
exports com.example.compute
exports com.example.random
requires java.base mandated
qualified opens com.example.random.impl to com.example.user.application
```
`1.0-SNAPSHOT` is coming from POM artifact version for our example.

### Transitive Module Visibility
We have seen in last sections that we need to export package or open them to provide compile time access or runtime
access of module packages or module. Also, we have seen how to provide access of module to particular another module.

Now we will see what happen when we use Module A in Module B and then this module B is used by Module C. Do Module 
C have by default access to Module A? or if not then how we can provide it.

For create an example of this we have compute module from our last sections; Which exports compute and random packages
and open reflective access for implementation package to application module.

We are creating a new module named [advance-compute-module](lesson-2/advance-compute-module); Which uses the computation module
and this advance-compute-module is used by [user-application-module3](lesson-2/user-application-module3). Now we will see if 
user-application-module3 get the access for the compute module or not. 

We have written below module-info.java in advance-compute-module:
```java
module com.example.advance.compute {
    requires com.example.computation;
    exports com.exmaple.advance;
}
```
and, below one in user-application-module3:
```java
module user.application.module3 {
    requires com.example.advance.compute;
}
```

We created one class in the advance-compute-module which provide the square root of given number and the instance of 
Calculator from the computation module. Looks like below:
```java
package com.exmaple.advance;

import com.example.compute.Calculator;

public class AdvanceCalculator {

    public Calculator getCalculator(){
        return new Calculator();
    }

    public double getSquareRoot(double number){
        return Math.sqrt(number);
    }
}
```

And, In user module we will use this class and see if we can use the calculator there.
```java
package com.example.user3;

import com.exmaple.advance.AdvanceCalculator;

public class Application {

    public static void main(String[] args) {
        AdvanceCalculator advanceCalculator = new AdvanceCalculator();
        System.out.println("Square root of 4 is " + advanceCalculator.getSquareRoot(4));
        System.out.println("Sum of 5 and 4 is "+ advanceCalculator.getCalculator().add(5,4));
    }
}
```
When we compile with above code; we get below error:
```text
/D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-2/user-application-module3/src/main/java/com/example/user3/Application.java:[10,83] com.example.compute.Calculator.add(int,int) in package com.example.compute is not accessible
  (package com.example.compute is declared in module com.example.computation, but module user.application.module3 does not read it)
```
Which mention that the `user.application.module3` does not have access to module `com.example.computation`. Now If we 
want to provide the access we can provide like below:
```java
module user.application.module3 {
    requires com.example.advance.compute;
    requires com.example.computation;
}
``` 

What if we want this pass from the advance compute module to other module i.e. if any module who requires the advance 
compute module will also get the computation module to perform the computation package operation. For doing this we
will change the advance-compute-module module-info.java and add transitive for computation requires.
```java
module com.example.advance.compute {
    requires transitive com.example.computation;
    exports com.exmaple.advance;
}
```

Now Let's compile and run the code for the same.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>java -p compute-module\target\compute-module-1.0-SNAPSHOT.jar;advance-compute-module\target\advance-compute-module-1.0-SNAPSHOT.jar;user-application-module3\target\u
ser-application-module3-1.0-SNAPSHOT.jar -m user.application.module3
Square root of 4 is 2.0
Sum of 5 and 4 is 9
```

**When to use Transitive requires**
* When we are developing utility, and we expose any class or method of requires module then we should use the transitive
import.
* When refactoring existing code and break them into multiple modules. This will help to forward the new module to client's
module, so their code will be not impacted due to this refactoring.

## Targeted Linking
This is related to bundling your apps and carrying the modules you really need to run your application. For example
of this we are going to use [user-application-module3](lesson-2/user-application-module3) and bundle it separately. Just to 
recap this module requires `com.example.advance.compute` module and transitively it requires `com.example.computation`
which requires the `java.base` module.

Now Let's bundle them in a package to deploy in environments and see what Java provides us for doing this so. We have 
seen Java has number of modules itself; But that all not required to user-application-module3. Let's create a small 
targeted module that just keep our application and module required to it. For creating this Java provides us jlink.
For this we need to provide the path to jmods present in <Java installation root directory>\jmods; Which will bring the
Java library modules required by our application and create bundle from them. For creating the our targetted module
we will use below command.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>set JAVA_MODS="C:\CodeTools\Java\OpenJDK 15\jmods"

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>echo %JAVA_MODS%
"C:\CodeTools\Java\OpenJDK 15\jmods"

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2>jlink --module-path %JAVA_MODS%;compute-module\target\compute-module-1.0-SNAPSHOT.jar;advance-compute-module
\target\advance-compute-module-1.0-SNAPSHOT.jar;user-application-module3\target\user-application-module3-1.0-SNAPSHOT.jar --output deployable --add-modules user.ap
plication.module3 --launcher=testdeployable=user.application.module3
```

In above command we included the modules in module path; then in output we have provided the directory in which we want
to create our targeted module; add modules will create optimize deployable for our module, and the launcher is the name
of file which will start the application which should assign to a module or main class to start the application. 

Let's have a look on the directory deployable we have created using above command.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\deployable>dir
 Volume in drive D has no label.
 Volume Serial Number is 0EA9-10A1

 Directory of D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\deployable

30-11-2020  10:19    <DIR>          .
30-11-2020  10:19    <DIR>          ..
30-11-2020  10:19    <DIR>          bin
30-11-2020  10:19    <DIR>          conf
30-11-2020  10:19    <DIR>          include
30-11-2020  10:19    <DIR>          legal
30-11-2020  10:19    <DIR>          lib
30-11-2020  10:19               121 release
               1 File(s)            121 bytes
               7 Dir(s)  83,712,598,016 bytes free
```
In this folder a small deployable is created with java and library related with app. Lets list-modules of this java:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\deployable>bin\java.exe --list-modules
com.example.advance.compute@1.0-SNAPSHOT
com.example.computation@1.0-SNAPSHOT
java.base@15.0.1
user.application.module3@1.0-SNAPSHOT
```
Also, it contains a file which will launch our application.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-2\deployable>bin\testdeployable
Square root of 4 is 2.0
Sum of 5 and 4 is 9
```

So in above example we have seen that using jlink command we can create our application specific deployment; which
contains its own java with the only module required by our application; We are not carrying all java modules and jars
which is not needed by application.

* jlink will not work with automatic modules.
* If we think we need the module in path and need to add then we can provide the list of modules in --add-modules.

# Lesson 3 : Automatic Module, Unnamed Module and Explicit Named module and Legacy jar to module migration
## Legacy jars, Automatic modules in classpath and modulepath
In above sections we have seen if we pass any jar in the module path then it act like automatic module, and jar name 
will be the name of automatic module.

In old java we had classpath; From Java 9 onwards we have classpath and modulepath both. Traditionally we put the 
libraries in classpath and, we are able to run the program if compile time dependencies are present. Also, If we are
using any runtime dependencies and that is not present; we got to know when it blows up at runtime which is not secure.

With the modulepath; It takes dependencies graph at runtime and if it didn't start-up application; which is fail-fast
and more secure as compare to blow up in runtime. 

* jar go into classpath.
* modules go into modulepath. Modules are jars with the module descriptor.

But not all jars right now have the module description information. Let's first see what happen when we put these
legacy jars in the classpath and modulepath or mix and match of them. A

Let's create two jars without module descriptors. 
1. [Util jar](lesson-3/util-jar-without-module-info) which will be used by User jar.
2. [User jar](lesson-3/user-jar-without-module-info) which will use util jar and call it methods.

Let's see both the jars information.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>jar -f util-jar-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar -d
No module descriptor found. Derived automatic module.

util.jar.without.module.info@1.0-SNAPSHOT automatic
requires java.base mandated
contains com.example.util


D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>jar -f user-jar-without-module-info\target\user-jar-without-module-info-1.0-SNAPSHOT.jar -d
No module descriptor found. Derived automatic module.

user.jar.without.module.info@1.0-SNAPSHOT automatic
requires java.base mandated
contains com.example.user
```

**Scenario 1:**

Let's add Both the jar in classpath and run the program.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -classpath util-jar-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar;user-jar-w
ithout-module-info\target\user-jar-without-module-info-1.0-SNAPSHOT.jar com.example.user.User
User Class Class Info Started
Class Name  : class com.example.user.User
Module Name : unnamed module @6b884d57
User Class Class Info Finished
-------------------------------------------
User Calling Util Info Started
Util Class Class Info Started
Class Name  : class com.example.util.Util
Module Name : unnamed module @6b884d57
Util Class Class Info Finished
User Calling Util Info Finished
-------------------------------------------
Printing Util Class Info From User class started
Class Name  : class com.example.util.Util
Module Name : unnamed module @6b884d57
Printing Util Class Info From User class started
```

We can see that module information is coming as unnamed module as we put the jars in classpath.

**Scenario 2:**

Let's add Both the jar in modulepath and run the program.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p util-jar-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar;user-jar-without-m
odule-info\target\user-jar-without-module-info-1.0-SNAPSHOT.jar -m user.jar.without.module.info/com.example.user.User
User Class Class Info Started
Class Name  : class com.example.user.User
Module Name : module user.jar.without.module.info
User Class Class Info Finished
-------------------------------------------
User Calling Util Info Started
Util Class Class Info Started
Class Name  : class com.example.util.Util
Module Name : module util.jar.without.module.info
Util Class Class Info Finished
User Calling Util Info Finished
-------------------------------------------
Printing Util Class Info From User class started
Class Name  : class com.example.util.Util
Module Name : module util.jar.without.module.info
Printing Util Class Info From User class started
```
We can see in above output the module info which we get as automatic module.

**Scenario 3:**

Now Let's put the util jar in classpath and user jar in module path.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -classpath util-jar-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar -p user-ja
r-without-module-info\target\user-jar-without-module-info-1.0-SNAPSHOT.jar -m user.jar.without.module.info/com.example.user.User
User Class Class Info Started
Class Name  : class com.example.user.User
Module Name : module user.jar.without.module.info
User Class Class Info Finished
-------------------------------------------
User Calling Util Info Started
Util Class Class Info Started
Class Name  : class com.example.util.Util
Module Name : unnamed module @133314b
Util Class Class Info Finished
User Calling Util Info Finished
-------------------------------------------
Printing Util Class Info From User class started
Class Name  : class com.example.util.Util
Module Name : unnamed module @133314b
Printing Util Class Info From User class started
```

As we can see the util is included in classpath and considered as unnamed module; But user class is modulepath and it
prints the automatic module information.

**Scenario 4:**

Now Let's put the util jar in module path and user jar in class path.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -classpath user-jar-without-module-info\target\user-jar-without-module-info-1.0-SNAPSHOT.jar -p util-ja
r-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar com.example.user.User
User Class Class Info Started
Class Name  : class com.example.user.User
Module Name : unnamed module @1ddc4ec2
User Class Class Info Finished
-------------------------------------------
User Calling Util Info Started
Exception in thread "main" java.lang.NoClassDefFoundError: com/example/util/Util
        at com.example.user.User.main(User.java:13)
Caused by: java.lang.ClassNotFoundException: com.example.util.Util
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:606)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:168)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
        ... 1 more
```
When we include the util jar in modulepath then it is not able to find class and fails.

### Conclusion from above scenario
* We can put all jars in classpath to run the program.
* We can put all jars in modulepath and can use automatic module name.
* We can put the user jar in modulepath and dependent jars in classpath, but vice-versa is not true.


* Class in the classpath (Unnamed Module) can reach other classes in the classpath (Unnamed Module).
* Class in the modulepath (Automatic Module) can reach other classes in the modulepath (Automatic Module).
* Class in the modulepath (Automatic Module) can reach other classes in the classpath (Unnamed Module).
* Class in the classpath (Unnamed Module) can't reach other class in the modulepath (Automatic Module).

## Explicit named modules, Automatic modules and Unnamed Modules
Let's first create an Explicit named module by providing module-info.java and see the module information. For this example
we created [user-jar-with-module-info](lesson-3/user-jar-with-module-info). Now Let's see the information of it's jar.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>jar -f user-jar-with-module-info\target\user-jar-with-module-info-1.0-SNAPSHOT.jar -d
userApp@1.0-SNAPSHOT jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/lesson-3/user-jar-with-module-info/target/user-jar-with-module-info-1.0-SNAPSHOT.jar
/!module-info.class
requires java.base mandated
```
Let's try to run the program and see if we are getting module information.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p user-jar-with-module-info\target\user-jar-with-module-info-1.0-SNAPSHOT.jar -m userApp/com.example.u
ser.UserApp
UserApp Class Info Started
Class Name  : class com.example.user.UserApp
Module Name : module userApp
UserApp Class Info Finished
```

Generally Explicit named modules sits in the modulepath. Let's see what happen when we use them in classpath.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p user-jar-with-module-info\target\user-jar-with-module-info-1.0-SNAPSHOT.jar -m userApp/com.example.u
ser.UserApp
UserApp Class Info Started
Class Name  : class com.example.user.UserApp
Module Name : module userApp
UserApp Class Info Finished
```
As we can see in above code that when we provide the Explicit named module in classpath it behave like unnamed module.

### Explicit named module using other Explicit named module
Let's see if an Explicit named module can talk to other Explicit named module.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p user-jar-with-module-info\target\user-jar-with-module-info-1.0-SNAPSHOT.jar;util-jar-with-module-inf
o\target\util-jar-with-module-info-1.0-SNAPSHOT.jar -m userApp/com.example.user.UserApp
UserApp Class Info Started
Class Name  : class com.example.user.UserApp
Module Name : module userApp
UserApp Class Info Finished
-----------------------------------------------
UserApp Calling InfoUtil Started
InfoUtil Class Info Started
Class Name  : class com.example.util.InfoUtil
Module Name : module util.with.module.info
InfoUtil Class Info Finished
UserApp Calling InfoUtil Finished
```
* Explicit named module can call other Explicit named module by making an entry in module require.

### Explicit named module using automatic module
Before we start using an automatic module in an Explicit named module; We need to know below point:
* Automatic modules exports everything it has.

Let's try using an automatic module in our module.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p user-jar-with-module-info\target\user-jar-with-module-info-1.0-SNAPSHOT.jar;util-jar-with-module-inf
o\target\util-jar-with-module-info-1.0-SNAPSHOT.jar;util-jar-without-module-info\target\util-jar-without-module-info-1.0-SNAPSHOT.jar -m userApp/com.example.user.U
serApp
UserApp Class Info Started
Class Name  : class com.example.user.UserApp
Module Name : module userApp
UserApp Class Info Finished
-----------------------------------------------
UserApp Calling InfoUtil Started
InfoUtil Class Info Started
Class Name  : class com.example.utils.InfoUtil
Module Name : module util.with.module.info
InfoUtil Class Info Finished
UserApp Calling InfoUtil Finished
-----------------------------------------------
UserApp Calling Util Started
Util Class Class Info Started
Class Name  : class com.example.util.Util
Module Name : module util.jar.without.module.info
Util Class Class Info Finished
UserApp Calling Util Finished
```

From above example we can see an Explicit named module can call an automatic module.

### Explicit named module using unnamed module
Explicit named module cannot access unnamed module. As anything to use in the explicit named module you need to provide that
in requires of module-info.java.

Since we can't provide the unnamed module in module descriptor and no handshake can be done between the unnamed module
and Explicit named module; so Explicit named module can't access the unnamed module.

### Revise Module access
Let's quickly revise via diagram:

![Module Access Image](images/Modules%20Access%20Chart.jpg)

* Unnamed module sit in the classpath
* Automatic modules sit in the modulepath
* Explicit named modules has module descriptor (module-info.java)
* Explicit named modules sit in the modulepath
* Explicit named modules when run from the classpath behave as unnamed

* Unnamed modules can access other unnamed modules
* Unnamed modules can't access automatic modules
* Automatic modules can access other automatic modules
* Automatic modules can access unnamed modules
* Explicit named modules can access other Explicit named modules
* Explicit named modules can access automatic modules
* Explicit named modules can't access unnamed modules 


# Transitioning from Legacy jars to modules
When, we start migrating from our legacy jars to new modules. We should follow from Left to right dependency i.e.
we need to create the module of user jar which are using other jars. Let's understand this by taking an example

Let's consider we have A.jar which is used by B.jar which is further used by C.jar. Then Dependency will look like
`C---using--->B---using--->A`. So We need to change first the C.jar and in module info we can require automatic
module B. This is according to rule `Explicit named modules can access automatic modules`. 

But think from perspective, if we start from right to left then we transform the A.jar and try to access from B; Which
will Automatic module then `Automatic modules can't access  Explicit named modules`. And that will break migration.

Let's again continue with our original migration `C---using--->B---using--->A`. Now let's consider C is now a module
which requires the automatic module B. Now after C has been migrated B as the Explicit named modules. Now we have problem
let's say you given the B.jar module name foo. Then you need to change the C and provide `requires foo`; which is not
ideal; We will deal with this problem later in this section subsections. Let's consider we have done with C and B and,
now B requires automatic module A, and then we can convert A into the Explicit named module and can provide it to B. 
With all of this the transition is completed. 

Let's understand some problems we may face during migration; One we already seen in the above paragraph of renaming
require automatic module to explicit named module. This problem also called split package.

## Issues we might face while migrating from leagcy jars to modules.
### Access violation
Converting a legacy jar to module; we need to provide what package we are providing to access others; Which package
we are opening for reflection. So we need to take care on this part what access we are providing to users of library
and what are restricted.

### Automatic package naming and split package
Let's first take an jar and we will see how it behaves on providing different names to jar and their automatic module
names.
* Simple jar name
```text
ngupta@NAMAN-DELL:~$ jar -f util.jar -d
No module descriptor found. Derived automatic module.

util automatic
requires java.base mandated
contains com.example.util
```

* Jar name with dots
```text
ngupta@NAMAN-DELL:~$ jar -f util.example.jar -d
No module descriptor found. Derived automatic module.

util.example automatic
requires java.base mandated
contains com.example.util
```

* Jar name with dash
```text
ngupta@NAMAN-DELL:~$ jar -f util-example.jar -d
No module descriptor found. Derived automatic module.

util.example automatic
requires java.base mandated
contains com.example.util
```  
We can see the dash is converted to dot here in automatic module name.

* Jar name with number
```text
ngupta@NAMAN-DELL:~$ jar -f util1.jar -d
No module descriptor found. Derived automatic module.

util1 automatic
requires java.base mandated
contains com.example.util
```  

* Jar name with dots and number
```text
ngupta@NAMAN-DELL:~$ jar -f util.1.jar -d
Unable to derive module descriptor for: util.1.jar
util.1: Invalid module name: '1' is not a Java identifier
```  
We can see that after . if there is number it is not able to create module out of it.

* Jar name with dash and number
```text
ngupta@NAMAN-DELL:~$ jar -f util-1.jar -d
No module descriptor found. Derived automatic module.

util@1 automatic
requires java.base mandated
contains com.example.util
```
We can see after the dash number becomes the version of module.

* Jar name with dash, number and dots
```text
ngupta@NAMAN-DELL:~$ jar -f util-1.jar -d
No module descriptor found. Derived automatic module.

util@1 automatic
requires java.base mandated
contains com.example.util
```  

* Jar name with dots, number, dashes and letter
```text
ngupta@NAMAN-DELL:~$ jar -f util-1.2.3adsfd.jar -d
No module descriptor found. Derived automatic module.

util@1.2.3adsfd automatic
requires java.base mandated
contains com.example.util
```

As we can see in above example the automatic module change the name with jar name. When, we migrate user jar to 
explicit named module and provide the automatic module in require and then automatic module is migrated to explicit
named module; then we need to update the user jar just to update requires in the jar and recompile and deploy.

To overcome this problem we can create a file with any name and provide the automatic module name like below in the file.
```text
Automatic-Module-Name: com.example.B

```
And we can re-create the automatic module jar with providing an extra option `-m <file_path_containing_automatic_module_name>`
and this will create the jar which automatic module name is fixed and will not change with jar name.

In the file line break is important; If you don't provide the line break while creating jar it will not consider the 
property we provided.
 
### Module name collision in modulepath and package collision in different modules
#### Module name collision in modulepath
Let's create an example with two jars both have main class in different packages, but we will keep the module name same.
Let's see these jars code
* [Main class in com.example.first package](lesson-3/same-module-name-first)
* [Main class in com.example.second package](lesson-3/same-module-name-second)

```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>scp same-module-name-first\target\same-module-name-first-1.0-SNAPSHOT.jar temp\
        1 file(s) copied.

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>scp same-module-name-second\target\same-module-name-second-1.0-SNAPSHOT.jar temp\
        1 file(s) copied.
        
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp -m same-module-name-first\com.example.first.Application
Error occurred during initialization of boot layer
java.lang.module.FindException: Two versions of module com.example.same.name found in temp (same-module-name-second-1.0-SNAPSHOT.jar and same-module-name-first-1.0-SNAPSHOT.jar)

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-first-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.first.Application
Module Name :module com.example.same.name First Package Application

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-second-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.second.Application
Module Name :module com.example.same.name Second Package Application
```

When we pass individual jars in module path and pass the module name and class name the program works fine. When we
put both jars in single module path then it gives error that two versions of same module is present in the path.

If we provide the jars separated by ; or : then it will work fine because they are present in different module path.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-first-1.0-SNAPSHOT.jar;temp\same-module-name-second-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.first.Application
Module Name :module com.example.same.name First Package Application

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-second-1.0-SNAPSHOT.jar;temp\same-module-name-first-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.first.Application
Error: Could not find or load main class com.example.first.Application in module com.example.same.name

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-first-1.0-SNAPSHOT.jar;temp\same-module-name-second-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.second.Application
Error: Could not find or load main class com.example.second.Application in module com.example.same.name

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-second-1.0-SNAPSHOT.jar;temp\same-module-name-first-1.0-SNAPSHOT.jar -m com.example.same.name/com.example.second.Application
Module Name :module com.example.same.name Second Package Application
```
We can see in two of examples it worked perfectly fine; because two versions of same jar in different module path
not in single module path.

Let's see why we are getting error in rest two of examples. If we observe the output it says it didn't find the Class. 
It is because in modulepath precendence is given to first module in modulepath; if same name module is present
in different modulepaths. So, when we have given the first package module in first modulepath then it take precedence over the
second package module and it is able to execute the program. But when we have given second pacakge module first in the 
modulepath first then it ignores first package module and it is not able to find the class.

Let's see one more example and conclude the same module name module collision:
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp\same-module-name-second-1.0-SNAPSHOT.jar;temp\same-module-name-first-1.0-SNAPSHOT.jar;temp -m com.example.same.name/com.example.second.Applicat
ion
Error occurred during initialization of boot layer
java.lang.module.FindException: Two versions of module com.example.same.name found in temp (same-module-name-second-1.0-SNAPSHOT.jar and same-module-name-first-1.0-SNAPSHOT.jar)
```
In above example even we have given the second package module precedence but since in last modulepath; we are giving the
two modules with same name in single modulepath; that's why we are getting above error.

**Conclusion:**
* In a modulepath we can't have multiple modules with same name.
* When multiple modulepaths are provided, the order in which they are specified dictates the order of precedence.

#### Package collision in different modules
Let's create two application which has same package.
* [same-package-first](lesson-3/same-package-first)
* [same-package-second](lesson-3/same-package-second)
* [same-package-user](lesson-3/same-package-user)


We have two different classes in same package in different modules; and our user application is not using the classes,
but we just added them in module-info.java requires.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>scp same-package-first\target\same-package-first-1.0-SNAPSHOT.jar temp
        1 file(s) copied.

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>scp same-package-second\target\same-package-second-1.0-SNAPSHOT.jar temp
        1 file(s) copied.

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>scp same-package-user\target\same-package-user-1.0-SNAPSHOT.jar temp
        1 file(s) copied.

D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\lesson-3>java -p temp -m same.util.user/com.example.same.user.UserApplication
Error occurred during initialization of boot layer
java.lang.LayerInstantiationException: Package com.example.same in both module same.util.first and module same.util.second
```
As we can see in above example; We are not even exporting the package from modules. And in our user application we 
are not even provided them in require. Still, since both the modules have same package then we are getting the error.
If we export the modules we get the error in compile time of the user application.

So In modules if any user application import two different module which has same package; we will get the error. This 
just include the same package. Subpackages are considered as different package i.e. com.example.same and com.example.same.first
will we considered as different package, and we will not get any exception for this.

# Lesson 4 : Multiple module same package solution
There are some situation in which we want that same package exist in multiple modules. One of such case is the test cases. 
