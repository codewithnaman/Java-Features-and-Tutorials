package java9.feature11;

import java.util.stream.Collectors;

public class FactorialStakeWalkerStreamExample {
    public static void main(String[] args) {
        FindFactorialStream factorial = new FindFactorialStream();
        factorial.factorial(500);
    }
}

class FindFactorialStream {

    public int factorial(int number){
        if(number==1){
            StackWalker walker = StackWalker.getInstance();
            walker.walk(stackFrameStream ->
                    stackFrameStream.filter(frame-> frame.getLineNumber()!=25)
                    .map(frame-> frame.getClassName()+" "+frame.getMethodName()+" "+frame.getLineNumber()).
                    collect(Collectors.toList()))
            .forEach(System.out::println);

            return number;
        }
        return number * factorial(number-1);
    }

}
