package com.example.bookinghotel.entity;

import java.util.ArrayList;

public class Room {
    private Type large;
    private Type medium;


    public Room(Type large, Type medium) {
        this.large = large;
        this.medium = medium;

    }
    public Room() {

    }

    public Type getLarge() {
        return large;
    }

    public void setLarge(Type large) {
        this.large = large;
    }

    public Type getMedium() {
        return medium;
    }
    public Type getTypeByName(String name) {
        if(name.equals("Medium")){
            return medium;
        }else{
            return large;
        }

    }
    public void setMedium(Type medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Room{" +
                "large:" + large.toString() +
                ", medium:" + medium.toString() +
                '}';
    }
}

