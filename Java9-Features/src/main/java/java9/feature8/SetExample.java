package java9.feature8;

import java.util.List;
import java.util.Set;

public class SetExample {

    public static void main(String[] args) {
        Set<String> zeroSet = Set.of();
        System.out.println(zeroSet.getClass());
        Set<String> oneSet = Set.of("Test");
        System.out.println(oneSet.getClass());
        Set<String> twoSet = Set.of("Test","Test2");
        System.out.println(twoSet.getClass());
        Set<String> nSet = Set.of("Test","Test2","Test3");
        System.out.println(nSet.getClass());

        Set<String> duplicateValueSet = Set.of("Test","Test","Test3");
    }
}
