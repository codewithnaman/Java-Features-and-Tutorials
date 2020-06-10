package com.example1;

public class Manager extends Worker {
     private Desk desk = new Desk();

    public Manager(){
        super();
        System.out.println("Manager Created....");
    }

    public Manager(String test){
        super(test);
        System.out.println("Manager Created....");
    }

    @Override
    public void work() {
        super.work();
        System.out.println("Manager Working...");
    }

    @Override
    public void work1() {
        System.out.println("Manager Working on other task...");
    }

    @Override
    public void work2() {
        desk.use();
        System.out.println("Final task of manager");
    }
}
