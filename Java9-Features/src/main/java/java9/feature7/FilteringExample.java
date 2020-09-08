package java9.feature7;

import java9.feature5.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilteringExample {

    public static List<Person> createPersonsList() {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75),
                new Person("Tina", 24),
                new Person("Sara", 2),
                new Person("Aarohi", 10),
                new Person("Chhaya", 85),
                new Person("Aakash", 25),
                new Person("Ananya", 24),
                new Person("Sara", 20),
                new Person("Tina", 35),
                new Person("Bahubali", 12));
        return persons;
    }

    public static void main(String[] args) {
       // java8GroupingByName();
        //java8GroupingByNameAndMappingOnlyAge();
        //java9FilterPersonWithAge();
        java9FilterPersonWithAgeAndGetAgeOnly();
    }

    private static void java9FilterPersonWithAgeAndGetAgeOnly() {
        Map<String,List<Integer>> personsGroupedByNameAndMappedByAge =
                createPersonsList().stream().
                        collect(Collectors.groupingBy(Person::getName,
                                Collectors.filtering(person -> person.getAge()>20,
                                        Collectors.mapping(Person::getAge,Collectors.toList()))));
        System.out.println(personsGroupedByNameAndMappedByAge);
    }

    private static void java9FilterPersonWithAge() {
        Map<String,List<Person>> personsGroupedByNameAndMappedByAge =
                createPersonsList().stream().
                        collect(Collectors.groupingBy(Person::getName,
                                Collectors.filtering(person -> person.getAge()>20,Collectors.toList())));
        System.out.println(personsGroupedByNameAndMappedByAge);
    }

    private static void java8GroupingByNameAndMappingOnlyAge() {
        Map<String,List<Integer>> personsGroupedByNameAndMappedByAge =
                createPersonsList().stream().
                        collect(Collectors.groupingBy(Person::getName, Collectors.mapping(Person::getAge,Collectors.toList())));
        System.out.println(personsGroupedByNameAndMappedByAge);
    }

    private static void java8GroupingByName(){
        Map<String,List<Person>> personsGroupedByName =
                createPersonsList().stream().collect(Collectors.groupingBy(Person::getName));
        System.out.println(personsGroupedByName);
    }
}
