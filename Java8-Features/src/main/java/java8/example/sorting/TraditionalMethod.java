package java8.example.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TraditionalMethod {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Test", 75),
                new Person("Test", 12),
                new Person("Test", 24),
                new Person("Test", 2),
                new Person("Test", 10),
                new Person("Test", 85));

        persons.forEach(System.out::println);

        System.out.println("Sorted By Age: ");
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                return person1.getAge() - person2.getAge();
            }
        });

        persons.forEach(System.out::println);
    }
}
