package java9.feature1;

public class Feature1InterfaceDefaultMethod implements Feature1Interface {

    public static void main(String[] args) {
        Feature1Implementation implementation = new Feature1Implementation();
        System.out.println("Threads : "+implementation.numbersOfThreadsAvailable());
        System.out.println("Total Memory :"+implementation.totalMemory());
    }
}
