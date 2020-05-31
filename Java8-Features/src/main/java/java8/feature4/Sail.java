package java8.feature4;

public interface Sail {
    default void cruise(){
        System.out.println("Sail::cruise");
    }
}
