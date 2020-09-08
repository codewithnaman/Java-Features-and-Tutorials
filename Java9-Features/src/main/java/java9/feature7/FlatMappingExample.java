package java9.feature7;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMappingExample {

    public static List<Person> createPersonsList() {
        List<Person> persons = Arrays.asList(
                new Person("Aakash", 75, "Aakash@gmail.com","Aakash@gmail.com"),
                new Person("Tina", 24,"Tina@hotmail.com"),
                new Person("Sara", 2,"Sara@gmail.com","Sara@xyz.com"),
                new Person("Aakash", 24, "Aakash24@gmail.com","Aakash29@gmail.com"));
        return persons;
    }

    public static void main(String[] args) {
        //traditionalGroupingForName();
        flatMappingNameAndEmails();
    }

    private static void flatMappingNameAndEmails() {
        System.out.println(
                createPersonsList().stream()
                        .collect(Collectors.groupingBy(
                                Person::getName,
                                Collectors.flatMapping(person-> Stream.of(person.getEmails()),Collectors.toList())))
        );
    }

    private static void traditionalGroupingForName() {
        System.out.println(
                createPersonsList().stream()
                .collect(Collectors.groupingBy(Person::getName,Collectors.mapping(Person::getEmails,Collectors.toList())))
        );
    }
}
