package com.example.user;

import com.example.util.Util;
import com.example.utils.InfoUtil;

public class UserApp {
    public static void main(String[] args) {
        System.out.println("UserApp Class Info Started");
        System.out.println("Class Name  : "+UserApp.class);
        System.out.println("Module Name : "+UserApp.class.getModule());
        System.out.println("UserApp Class Info Finished");
        System.out.println("-----------------------------------------------");
        /* Added Later as part of explicit can use other explicit module*/
        System.out.println("UserApp Calling InfoUtil Started");
        InfoUtil infoUtil = new InfoUtil();
        infoUtil.getInfo();
        System.out.println("UserApp Calling InfoUtil Finished");
        System.out.println("-----------------------------------------------");
        /* Added Later as part of explicit can use other Automatic module*/
        System.out.println("UserApp Calling Util Started");
        Util util = new Util();
        util.getClassInfo();
        System.out.println("UserApp Calling Util Finished");
    }
}
