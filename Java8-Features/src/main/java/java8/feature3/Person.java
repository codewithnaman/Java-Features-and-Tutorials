package java8.feature3;

public class Person {
    private String name;
    private int age;

    public Person(String name,Integer age){
        this.name = name;
        this.age = age;
    }

    public void printPersonInformation(){
        System.out.println(this);
    }

    public static void printPerson(Person person){
        System.out.println(person);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
