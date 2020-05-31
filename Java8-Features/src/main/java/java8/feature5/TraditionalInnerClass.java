package java8.feature5;

public class TraditionalInnerClass {

    public interface Multiplier{
        int multiplier(int value);
    }


    public static void main(String[] args) {
        Multiplier  multiplier = createMultiplier(4);
        System.out.println(multiplier.multiplier(3));
    }

    private static Multiplier createMultiplier(int multiplier) {
        final int instMultiplier = multiplier;
        return new Multiplier() {
            @Override
            public int multiplier(int value) {
                return value * instMultiplier;
            }
        };
    }
}
