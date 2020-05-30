package java8.feature2;

public class InitialImplementation {

    public static void main(String[] args) {
        long start = System.nanoTime();
        timeConsumingMethod();
        long end = System.nanoTime();
        System.out.println("timeConsumingMethod Method took : "+ (end-start)/1.0e9);

        long start1 = System.nanoTime();
        timeConsumingMethod2();
        long end1 = System.nanoTime();
        System.out.println("timeConsumingMethod2 Method took : "+ (end1-start1)/1.0e9);
    }

    public static void timeConsumingMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void timeConsumingMethod2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
