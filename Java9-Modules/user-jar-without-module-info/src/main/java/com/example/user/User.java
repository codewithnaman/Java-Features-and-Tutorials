package com.example.user;

import com.example.util.Util;

public class User {
    public static void main(String[] args) {
        System.out.println("User Class Class Info Started");
        System.out.println("Class Name  : "+ User.class);
        System.out.println("Module Name : "+User.class.getModule());
        System.out.println("User Class Class Info Finished");
        System.out.println("-------------------------------------------");
        System.out.println("User Calling Util Info Started");
        Util util = new Util();
        util.getClassInfo();
        System.out.println("User Calling Util Info Finished");
        System.out.println("-------------------------------------------");
        System.out.println("Printing Util Class Info From User class started");
        System.out.println("Class Name  : "+Util.class);
        System.out.println("Module Name : "+Util.class.getModule());
        System.out.println("Printing Util Class Info From User class started");
    }
}
