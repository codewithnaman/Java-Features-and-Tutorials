## Constructor Sequence
When we create an object in Java, first variable is initialized for that class, then it's constructor is called. We
can see this in [TestConstructor](src/main/java/com/example1/TestConstructor.java). Also if class has subclass
then it try to initialize the base class first, then it initialize it's own variable and then constructor will be
completed. We can see this in [TestConstructor1](src/main/java/com/example1/TestConstructor1.java). In this class
first the base class member of base is initialized and then the constructor of base class is called, then it comes
to extending class and initialize the extending class member and then constructor got complete.

Now Let's create a polymorphic method in our class. So as we can see in  
[TestConstructor2](src/main/java/com/example1/TestConstructor2.java), it is calling method depending on the instance.
The complexity of problem is increases when we call the polymorphic method from constructor. 

In [TestConstructor3](src/main/java/com/example1/TestConstructor3.java) we can see if we call polymorphic method from
the constructor it call the method depending on the object. 

Now the problems occur if polymorphic method is using
a class variable of extending class then it got failed like in our 
[TestConstructor4](src/main/java/com/example1/TestConstructor4.java). So in this example extending class variable is not
initialized but we are calling method using base class constructor due to which it fails with NPE.

* Recommended practice is that don't call polymorphic function from a constructor.
* Also, if you still want to do some process on this we can use static factory method. Also, we can use the supplier to
de-duplicate the code for factory.

