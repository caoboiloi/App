package com.example.bookinghotel;

import com.example.bookinghotel.entity.Review;

import java.util.ArrayList;

public class Singleton {
    // static variable single_instance of type Singleton
    private static Singleton single_instance = null;

    // variable of type String
    private static String baseImg = "";

    // private constructor restricted to this class itself
    public Singleton() {}

    // static method to create instance of Singleton class
    public static Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }

    public String getBaseImg() {
        return baseImg;
    }

    public void setBaseImg(String baseImg) {
        Singleton.baseImg = baseImg;
    }

}
