package java9.feature7;

import java.util.Arrays;

public class Person {
    private String name;
    private int age;
    private String[] emails;

    public Person(String name, int age, String... emails) {
        this.name = name;
        this.age = age;
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emails=" + Arrays.toString(emails) +
                '}';
    }
}
