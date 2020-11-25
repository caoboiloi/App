package com.example.bookinghotel.entity;

import java.util.ArrayList;

public class Booked {
    private int id;
    private ArrayList<Long> begin;
    private ArrayList<Long> end;
    private String typeRoom;
    public Booked() {
    }

    public Booked(int id, ArrayList<Long> begin, ArrayList<Long> end, String typeRoom) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.typeRoom = typeRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Long> getBegin() {
        return begin;
    }

    public void setBegin(ArrayList<Long> begin) {
        this.begin = begin;
    }

    public ArrayList<Long> getEnd() {
        return end;
    }

    public void setEnd(ArrayList<Long> end) {
        this.end = end;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    @Override
    public String toString() {
        return "Booked{" +
                "id=" + id +
                ", begin=" + begin +
                ", end=" + end +
                ", typeRoom='" + typeRoom + '\'' +
                '}';
    }
}
