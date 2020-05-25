package java8.feature2;

public interface LivingCreature extends Creature {
    @Override
    default void breathe() {
        System.out.println("Living Creature breathe");
    }

    @Override
    default void eat() {
        System.out.println("Living Creature Eats");
    }
}
