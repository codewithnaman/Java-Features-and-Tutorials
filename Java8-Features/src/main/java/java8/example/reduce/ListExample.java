package java8.example.reduce;

import java8.example.sorting.Person;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class ListExample {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Bahubali", 12),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Ananya", 24),
                new Person("Zeus", 85),
                new Person("Zeus", 85));


        // Don't ever do this, this is not right approach
        List<Person> personsSortedByAge = new ArrayList<>();
        persons
                .stream()
                .sorted(Comparator.comparing(Person::getAge))
                .forEach(person -> personsSortedByAge.add(person));

        // Method to do it
        List<Person> sortedByAge = persons
                .stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(toList());

        sortedByAge.forEach(System.out::println);

        System.out.println("==========Set Collection===========");
        Set<Person> setOfPerson = persons
                .stream()
                .collect(toSet());
        setOfPerson.forEach(System.out::println);

        System.out.println("==========Age group Collection===========");
        Map<Integer, List<Person>> ageGroupOfPerson =
                persons
                        .stream()
                        .collect(groupingBy(Person::getAge));
        System.out.println(ageGroupOfPerson);

        System.out.println("==========Char At  group Collection===========");
        Map<Character, List<Person>> firstCharacterGroupOfPerson =
                persons
                        .stream()
                        .collect(groupingBy(person -> person.getName().charAt(0)));
        System.out.println(firstCharacterGroupOfPerson);

        System.out.println("==========Char Age  group Collection===========");
        Map<Character, List<Integer>> charAge =
                persons
                        .stream()
                        .collect(groupingBy(person -> person.getName().charAt(0),
                                mapping(person -> person.getAge(), toList())));
        System.out.println(charAge);


        System.out.println("==========Char eldest collection===========");
        Function<Person, Character> firstCharacterOfPersonName = person -> person.getName().charAt(0);
        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> charEldest =
                persons
                        .stream()
                        .collect(groupingBy(firstCharacterOfPersonName, maxBy(byAge)));
        System.out.println(charEldest);


        System.out.println("==========Char with eldest person age collection===========");
        Map<Character, Optional<Integer>> charEldestAge =
                persons
                        .stream()
                        .collect(groupingBy(firstCharacterOfPersonName,
                                mapping(Person::getAge, maxBy(Integer::compare))));
        System.out.println(charEldestAge);

        System.out.println("==========Name length and then eldest collection===========");
        Function<Person, Integer> nameLength = person -> person.getName().length();
        BinaryOperator<Person> criteria = (person1, person2) -> {
            if (person1.getName().length() == person2.getName().length()) {
                return person1.getAge() > person2.getAge() ? person1 : person2;
            } else {
                return person1.getName().length() > person2.getName().length() ? person1 : person2;
            }
        };
        Map<Integer, Optional<Person>> nameLengthThenEldest =
                persons
                        .stream()
                        .collect(groupingBy(nameLength, reducing(criteria)));
        System.out.println(nameLengthThenEldest);

    }
}
