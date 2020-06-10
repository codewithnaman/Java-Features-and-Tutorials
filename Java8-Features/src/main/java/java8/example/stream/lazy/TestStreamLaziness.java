package java8.example.stream.lazy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TestStreamLaziness {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Predicate<Integer> evenNumber = (value) -> {
            System.out.println("Even number called on " + value);
            return value % 2 == 0;
        };

        Predicate<Integer> gt3 = (value) -> {
            System.out.println("Greater than called on " + value);
            return value > 3;
        };
        Function<Integer, Integer> doubleIt = (value) -> {
            System.out.println("Double It called on " + value);
            return value * 2;
        };

        Stream<Integer> resultStream = numbers
                .stream()
                .filter(gt3)
                .filter(evenNumber)
                .map(doubleIt);

        System.out.println("Before calling the any terminal operation operation " + resultStream);
        System.out.println("Called findFirst() on the stream " + resultStream.findFirst());
    }
}
