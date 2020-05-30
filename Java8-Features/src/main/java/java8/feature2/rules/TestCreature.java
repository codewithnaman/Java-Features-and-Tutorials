package java8.feature2.rules;

public class TestCreature {

    public void test(){
        TestHuman human = new TestHuman();
        human.breathe();
        human.eat();
        human.walk();
    }

    public static void main(String[] args) {
        new TestCreature().test();
    }
}
