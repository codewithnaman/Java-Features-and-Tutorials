package com.example.util;

public class Util {
    public void getClassInfo() {
        System.out.println("Util Class Class Info Started");
        System.out.println("Class Name  : "+Util.class);
        System.out.println("Module Name : "+Util.class.getModule());
        System.out.println("Util Class Class Info Finished");
    }
}
