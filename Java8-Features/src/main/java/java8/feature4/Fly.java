package java8.feature4;

public interface Fly {
    default void takeOff() {
        System.out.println("Fly::takeoff");
    }

    default void turn(){
        System.out.println("Fly::turn");
    }

    default void cruise(){
        System.out.println("Fly::cruise");
    }

    default void land(){
        System.out.println("Fly::land");
    }
}
