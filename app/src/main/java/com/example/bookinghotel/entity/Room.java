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
class Type {
    private Integer available;
    private Integer total;
    private Integer price;
    private ArrayList<String> image;
    public Type(Integer available, Integer total,Integer price,ArrayList<String> image) {
        this.available = available;
        this.total = total;
        this.price = price;
        this.image = image;
    }

    public Type() {

    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
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
        return "available=" + available +", total=" + total+", price=" + this.price+" imge:"+image  ;
    }

}
