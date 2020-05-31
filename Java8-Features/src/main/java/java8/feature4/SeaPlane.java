package java8.feature4;

public class SeaPlane implements FastFly, Sail {

    @Override
    public void takeOff() {
        System.out.println("SeaPlane::takeoff");
    }

    //Rule number 5
    @Override
    public void cruise() {
        System.out.println("SeaPlane::cruise");
        Sail.super.cruise();
        FastFly.super.cruise();
        //Fly.super.cruise();
    }
}
