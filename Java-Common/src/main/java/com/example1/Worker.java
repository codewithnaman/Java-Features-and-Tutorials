package com.example1;

public class Worker {
    private Window window = new Window();

    public Worker() {
        System.out.println("Worker Created....");
        work1();
    }

    public Worker(String test){
        System.out.println("Worker Created....");
        work2();
    }

    public void work() {
        System.out.println("Worker Working...");
    }

    public void work1() {
        System.out.println("Worker Working on other task...");
    }

    public void work2(){
        System.out.println("Final task of worker");
    }
}
