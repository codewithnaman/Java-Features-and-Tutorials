package java8.feature4;

public interface FastFly extends Fly {

    //Rule Number 1
    @Override
    default void takeOff() {
        System.out.println("FastFly::takeoff");
    }

    @Override
    default void cruise() {
        System.out.println("FastFly::cruise");
    }
}
