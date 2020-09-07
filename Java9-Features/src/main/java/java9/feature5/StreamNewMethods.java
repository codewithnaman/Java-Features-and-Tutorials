package java9.feature5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamNewMethods {
    public static List<Person> createPersonsList() {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Ananya", 24),
                new Person("Bahubali", 12));
        return persons;
    }

    public static void main(String[] args) {
        //testDropWhile();
        //testTakeWhile();
        //testDropWhileWithTakeWhile();
        //java8IntStreamMethods();
        //testIntStreamIterationStepUp();
        //testIntStreamIterationStepDown();
        testIntStreamIterationUnbounded();
    }

    private static void java8IntStreamMethods() {
        Stream.iterate(10, i -> i + 1)
                .limit(5)
                .forEach(System.out::println);
        System.out.println("------------------");
        IntStream.range(10, 15)
                .forEach(System.out::println);
        System.out.println("------------------");
        IntStream.rangeClosed(10, 15)
                .forEach(System.out::println);
    }

    private static void testIntStreamIterationUnbounded() {
        IntStream.iterate(1, i -> i + 3)
                .dropWhile(number -> Math.sqrt(number) < 3)
                .takeWhile(number -> Math.sqrt(number) < 5)
                .forEach(System.out::println);
    }

    private static void testIntStreamIterationStepDown() {
        IntStream.iterate(18, i -> i > 0, i -> i - 3)
                .forEach(System.out::println);
    }

    private static void testIntStreamIterationStepUp() {
        IntStream.iterate(1, i -> i < 18, i -> i + 3)
                .forEach(System.out::println);
    }

    private static void testDropWhileWithTakeWhile() {
        createPersonsList().stream()
                .dropWhile(person -> person.getAge() > 20)
                .takeWhile(person -> person.getAge() < 80)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    private static void testTakeWhile() {
        createPersonsList().stream()
                .takeWhile(person -> person.getAge() < 80)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    private static void testDropWhile() {
        createPersonsList().stream()
                .dropWhile(person -> person.getAge() > 20)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
