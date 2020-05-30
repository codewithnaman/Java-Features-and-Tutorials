package java8.feature2;

public class RefactoredTraditionalImplementation {

    public static void main(String[] args) {
        TimeIt.measureMethodExecutionTime(
                new MethodImplementation() {
                    @Override
                    public void codeBlock() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
