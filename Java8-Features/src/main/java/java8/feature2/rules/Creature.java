package java8.feature2.rules;

public interface Creature {
    default void eat() {
        System.out.println("Eat");
    }

    default void walk(){
        System.out.println("Walk");
    }

    default void breathe(){
        System.out.println("Breathe");
    }
}
