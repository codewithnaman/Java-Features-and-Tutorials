# Table of Contents
* Why

# Java 9 Modules 
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
Since No module descriptor found Java created an automatic module and name it on the jar name which is `
compute automatic`. Then it is specifying that it requires java.base package from the JDK, and it includes the package 
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
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
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
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
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
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\compute-module>jar -f target\compute-module-1.0-SNAPSHOT.jar -d
com.example.computation jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/compute-module/target/compute-module-1.0-SNAPSHOT.jar/!module-info.class
exports com.example.compute
requires java.base
```
* The module always requires other modules and exports its own packages.

We have seen how to create a module; Let's now see how to use it.

### Use modules in our module
For this we have created an example of [user application](user-application-module). Where we are taking the compute
module as required module and using its package and methods. Let's see module-info.java of this module.
```java
module com.example.user.application {
    requires java.base;
    requires com.example.computation;
}
```
Let's see the information of this module jar.
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\user-application-module>jar -f target\user-application-module-1.0-SNAPSHOT.jar -d
com.example.user.application@1.0-SNAPSHOT jar:file:///D:/Workspace/Java-Features-and-Tutorials/Java9-Modules/user-application-module/target/user-application-module-1.0-SNAPSHOT.jar/!module-info.class
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
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\user-application-module>java -cp ..\compute-module\target\compute-module-1.0-SNAPSHOT.jar;target\user-application-module-1.0-SNAPSHOT.jar com.example.user.Application
8
```
or
```text
D:\Workspace\Java-Features-and-Tutorials\Java9-Modules\user-application-module>java -p ..\compute-module\target\compute-module-1.0-SNAPSHOT.jar;target\user-application-module-1.0-SNAPSHOT.jar -m com.example.user.application
8
```

We can see it is providing proper output; and we are using other module in our module. 

Let's see how module exports works and what it exposes in next section.
