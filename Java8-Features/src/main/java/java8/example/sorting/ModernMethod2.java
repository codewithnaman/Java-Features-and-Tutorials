package java8.example.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ModernMethod2 {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Bahubali", 12),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Ananya", 24),
                new Person("Zeus", 85));

        System.out.println("Eldedst Person : "+
                persons.stream().max(Comparator.comparing(Person::getAge)));
    }
}
