package com.example.bookinghotel.entity;

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

    public void setMedium(Type medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Room{" +
                "large:" + large.toString() +
                ", medium:" + large.toString() +
                '}';
    }
}
class Type {
    private Integer available;
    private Integer total;
    private Integer price;
    public Type(Integer available, Integer total,Integer price) {
        this.available = available;
        this.total = total;
        this.price = price;
    }

    public Type() {

    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        return "available=" + available +", total=" + total+", price=" + this.price  ;
    }
}
