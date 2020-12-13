package com.example.utils;

public class InfoUtil {
    public void getInfo(){
        System.out.println("InfoUtil Class Info Started");
        System.out.println("Class Name  : "+InfoUtil.class);
        System.out.println("Module Name : "+InfoUtil.class.getModule());
        System.out.println("InfoUtil Class Info Finished");
    }
}
