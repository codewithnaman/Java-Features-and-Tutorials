package java9.feature6;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamExample {

    public static List<Integer> getListOfNumbers() {
        return Arrays.asList(10, 51, 57, 25, 82, 91);
    }

    public static void main(String[] args) {
        //java8OptionalToPipeLineExample();
        java9OptionalToPipeLineExample();
    }

    private static void java9OptionalToPipeLineExample() {
        processInput(getListOfNumbers()
                .stream()
                .filter(number -> number > 80)
                .findFirst().stream());
        processInput(getListOfNumbers()
                .stream()
                .filter(number -> number > 800)
                .findFirst().stream());
    }

    private static void java8OptionalToPipeLineExample() {
        Optional<Integer> value = getListOfNumbers()
                .stream()
                .filter(number -> number > 80)
                .findFirst();
        if (value.isPresent()) {
            processInput(Stream.of(value.get()));
        }
    }

    private static void processInput(Stream<Integer> stream) {
        stream.map(number -> number * 2)
                .forEach(System.out::println);
    }
}
