package java8.feature2;

public class TimeIt {
    public static void measureMethodExecutionTime(MethodImplementation methodImplementation) {
        long start = System.nanoTime();
        methodImplementation.codeBlock();
        long end = System.nanoTime();
        System.out.println("Method took : "+ (end-start)/1.0e9);
    }
}
