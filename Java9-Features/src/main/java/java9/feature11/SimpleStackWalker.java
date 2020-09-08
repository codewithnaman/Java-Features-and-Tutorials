package java9.feature11;

public class SimpleStackWalker {

    public static void main(String[] args) {
        TestClass1 testClass1 = new TestClass1();
        testClass1.testMethod1();
    }
}

class TestClass1 {
    public void testMethod1() {
        TestClass2 testClass2 = new TestClass2();
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        System.out.println(walker.getCallerClass());
        testClass2.testMethod2();
    }
}

class TestClass2 {
    public void testMethod2(){
        StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        System.out.println(walker.getCallerClass());
        System.out.println("Done");
    }
}
