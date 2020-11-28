package com.example.bookinghotel.entity;

import java.util.ArrayList;

public class BookedRoom {
    ArrayList<TimeBooked> Large = new ArrayList<>();
    ArrayList<TimeBooked> Medium = new ArrayList<>();

    public BookedRoom(ArrayList<TimeBooked> large, ArrayList<TimeBooked> medium) {
        Large = large;
        Medium = medium;
    }

    public BookedRoom() {
        ArrayList<TimeBooked> Large = new ArrayList<>();
        ArrayList<TimeBooked> Medium = new ArrayList<>();
    }

    public ArrayList<TimeBooked> getLarge() {
        return Large;
    }

    public void setLarge(ArrayList<TimeBooked> large) {
        Large = large;
    }

    public ArrayList<TimeBooked> getMedium() {
        return Medium;
    }

    public void setMedium(ArrayList<TimeBooked> medium) {
        Medium = medium;
    }

    @Override
    public String toString() {
        return "BookedRoom{" +
                "Large=" + Large +
                ", Medium=" + Medium +
                '}';
    }
}
