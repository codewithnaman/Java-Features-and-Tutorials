package java8.feature3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ModernMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(totalOfNumbers(numbers, e -> true));
        System.out.println(totalOfNumbers(numbers, e -> e % 2 == 0));
        System.out.println(totalOfNumbers(numbers, e -> e % 2 != 0));
        System.out.println(totalOfNumbersByStreams(numbers, e -> true));
        System.out.println(totalOfNumbersByStreams(numbers, e -> e % 2 == 0));
        System.out.println(totalOfNumbersByStreams(numbers, e -> e % 2 != 0));
    }

    private static int totalOfNumbers(List<Integer> numbers, Predicate<Integer> predicate) {
        int total = 0;
        for (int number : numbers) {
            if (predicate.test(number))
                total += number;
        }
        return total;
    }

    private static int totalOfNumbersByStreams(List<Integer> numbers, Predicate<Integer> predicate) {
        return numbers.stream()
                .filter(predicate)
                .reduce(0, (c,e) -> c+e);
    }

}
