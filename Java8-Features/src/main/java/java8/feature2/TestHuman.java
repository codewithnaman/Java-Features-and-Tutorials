package java8.feature2;

public class TestHuman extends Human implements LivingCreature, Soul {
    @Override
    public void walk() {
        System.out.println("Test Human walks");
        LivingCreature.super.walk();
    }
}
