package java8.feature2;

public class RefactoredModernImplementation {

    public static void main(String[] args) {
        TimeIt.measureMethodExecutionTime(sleepProgramFor(1000));
        TimeIt.measureMethodExecutionTime(sleepProgramFor(2000));
    }

    public static MethodImplementation sleepProgramFor(int value) {
        return () -> {
            try {
                Thread.sleep(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
