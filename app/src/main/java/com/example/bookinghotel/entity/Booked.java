package com.example.bookinghotel.entity;

public class Booked {
    private int id;
    private String begin;
    private String end;
    private String typeRoom;
    public Booked() {
    }

    public Booked(int id, String begin, String end, String typeRoom) {
        this.begin = begin;
        this.end = end;
        this.typeRoom = typeRoom;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
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
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", typeRoom='" + typeRoom + '\'' +
                ", id  =" + id +
                '}';
    }
}
