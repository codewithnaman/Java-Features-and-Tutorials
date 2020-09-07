package java9.feature6;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrExample {
    public static List<Integer> getListOfNumbers() {
        return Arrays.asList(10, 51, 57, 25, 82, 91);
    }

    public static void main(String[] args) {
        java9OrMethod(80);
        java9OrMethod(800);
    }

    private static void java9OrMethod(int findFirstValueGreaterThan) {
        Optional<Integer> optional = getListOfNumbers()
                .stream()
                .filter(e -> e > findFirstValueGreaterThan)
                .findFirst()
                .or(() -> Optional.of(0));
        System.out.println(optional.get());
    }
}
