package com.example1;

public class TestConstructor2 {
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.work();
        System.out.println();
        Manager manager = new Manager();
        manager.work();
    }
}
