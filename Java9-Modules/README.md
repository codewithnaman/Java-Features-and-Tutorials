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
```text
ngupta@NAMAN-DELL:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib$ ls -lrth rt.jar
-rw-r--r-- 1 root root 63M Aug  3 06:46 rt.jar
```

