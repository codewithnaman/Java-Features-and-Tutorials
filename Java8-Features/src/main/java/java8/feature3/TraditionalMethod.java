package java8.feature3;

import java.util.Arrays;
import java.util.List;

public class TraditionalMethod {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(totalOfNumbers(numbers));
        System.out.println(totalOfEvenNumbers(numbers));
        System.out.println(totalOfOddNumbers(numbers));
    }

    private static int totalOfNumbers(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    private static int totalOfEvenNumbers(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            if (number % 2 == 0)
                total += number;
        }
        return total;
    }

    private static int totalOfOddNumbers(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            if (number % 2 != 0)
                total += number;
        }
        return total;
    }
}
