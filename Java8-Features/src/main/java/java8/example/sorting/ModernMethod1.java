package java8.example.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ModernMethod1 {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Bahubali", 12),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Ananya", 24));

        persons.forEach(System.out::println);
        System.out.println("Sorted by Increasing age:");
        Comparator<Person> increasingAgeSort = (person1, person2) -> person1.getAge() - person2.getAge();
        persons.stream().sorted(increasingAgeSort).
                forEach(System.out::println);
        System.out.println("Original Collection : ");
        persons.forEach(System.out::println);
        System.out.println("Reverse the collection");
        persons.stream().sorted(increasingAgeSort.reversed()).forEach(System.out::println);


        persons.stream().sorted(Comparator.comparing(Person::getAge)).forEach(System.out::println);

        persons.
                stream().
                sorted(Comparator.
                        comparing(Person::getAge).
                        thenComparing(Person::getName))
                .forEach(System.out::println);
    }
}
