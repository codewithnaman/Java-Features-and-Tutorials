package java9.feature6;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IfPresentOrElseExample {

    public static List<Integer> getListOfNumbers() {
        return Arrays.asList(10, 51, 57, 25, 82, 91);
    }

    public static void main(String[] args) {
        //traditionalJava8Example();
        //traditionalJava8ExampleGetMethod(80);
        //traditionalJava8ExampleGetMethod(800);
        //java8OrElseMethod(80);
        //java8OrElseMethod(800);
        //java8IsPresentMethod(80);
        //java8IsPresentMethod(800);
        //java8IfPresent(80);
        //java8IfPresent(800);
        //java9IfPresentOrElse(80);
        //java9IfPresentOrElse(800);
        java8IfPresentOrElse(80);
        java8IfPresentOrElse(800);
    }

    private static void java8IfPresentOrElse(int findFirstValueGreaterThan) {
        System.out.println(getListOfNumbers()
                .stream()
                .filter(number -> number > findFirstValueGreaterThan)
                .findFirst()
                .map(element -> element.toString())
                .orElse("Value Not found"));
    }

    private static void java9IfPresentOrElse(int findFirstValueGreaterThan) {
        getListOfNumbers()
                .stream()
                .filter(number -> number > findFirstValueGreaterThan)
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Value Not found"));
    }

    private static void java8IfPresent(int findFirstValueGreaterThan) {
        getListOfNumbers()
                .stream()
                .filter(number -> number > findFirstValueGreaterThan)
                .findFirst()
                .ifPresent(System.out::println);
    }

    private static void java8IsPresentMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        if (optionalInteger.isPresent()) {
            System.out.println(optionalInteger.get());
        } else {
            System.out.println("Value Not found");
        }

    }

    private static void java8OrElseMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        System.out.println(optionalInteger.orElse(0));
    }

    private static void traditionalJava8ExampleGetMethod(Integer findFirstValueGreaterThan) {
        Optional<Integer> optionalInteger = getListOfNumbers()
                .stream()
                .filter(integer -> integer > findFirstValueGreaterThan)
                .findFirst();

        System.out.println(optionalInteger.get());
    }

    private static void traditionalJava8Example() {
        System.out.println(
                getListOfNumbers()
                        .stream()
                        .filter(integer -> integer > 80)
                        .findFirst()
        );
        System.out.println(
                getListOfNumbers()
                        .stream()
                        .filter(integer -> integer > 800)
                        .findFirst()
        );
    }
}
