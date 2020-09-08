package java9.feature11;

public class FactorialStakeWalkerExample {
    public static void main(String[] args) {
        FindFactorial factorial = new FindFactorial();
        factorial.factorial(5);
    }
}

class FindFactorial {

    public int factorial(int number){
        if(number==1){
            StackWalker walker = StackWalker.getInstance();
            walker.forEach(stackFrame -> System.out.println(stackFrame.getClassName()+" "+stackFrame.getMethodName()+" "+stackFrame.getLineNumber()));
            return number;
        }
        return number * factorial(number-1);
    }

}
