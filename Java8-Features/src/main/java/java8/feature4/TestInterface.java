package java8.feature4;

public class TestInterface {

    public static void main(String[] args) {
        SeaPlane seaPlane = new SeaPlane();
        seaPlane.takeOff(); //Rule Number 4
        seaPlane.turn(); //Rule Number 2
        seaPlane.cruise(); // Rule Number 3
    }
}
