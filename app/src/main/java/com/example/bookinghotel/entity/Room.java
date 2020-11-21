package com.example.bookinghotel.entity;

public class Room {
    private Type large;
    private Type medium;

    public Room(Type large, Type medium) {
        this.large = large;
        this.medium = medium;
    }
    public Room() {
        this.large = large;
        this.medium = medium;
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

    public void setMedium(Type medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Room{" +
                "large=" + large.toString() +
                ", medium=" + large.toString() +
                '}';
    }
}
class Type {
    private Integer available;
    private Integer total;

    public Type(Integer available, Integer total) {
        this.available = available;
        this.total = total;
    }

    public Type() {

    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "available=" + available +", total=" + total ;
    }
}
