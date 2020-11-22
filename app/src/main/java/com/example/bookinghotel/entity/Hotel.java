package com.example.bookinghotel.entity;

import android.util.Log;

import java.util.ArrayList;

public class Hotel {
    Integer id;
    double lat;
    double longitude;
    String name;
    Room room;
    ArrayList<Rating> rating;
    public Hotel() {
    }


    public Hotel(Integer id,double lat, double longitude, String name, Room room, ArrayList<Rating> rating) {
        this.id =id;
        this.lat = lat;
        this.longitude = longitude;
        this.name = name;
        this.room = room;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Rating> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Rating> rating) {
        this.rating = rating;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    public float getAveRating(){
        Integer total = 0 ;
        Integer numberRating=0;
        if(this.rating != null) {
            for (int i = 1; i < this.rating.size() ; i++) {
                numberRating += 1;
                total += this.rating.get(i).getStar();
            }
            return (float) total / (numberRating);
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Hotel{" +
                " id=" + id +
                " lat=" + lat +
                ", longitude=" + longitude +
                ", averating=" + getAveRating() +
                ", name='" + name + '\'' +
                ", romm='" + room.toString() + '\'' +
                '}';
    }
    public boolean isEmpty(String string) {
        return (string != null && string.isEmpty());
    }

}
class Rating{
    private String comment;
    private String name;
    private int star;
    private String userId;
    public Rating() {

    }
    public Rating(String comment, String name, int star,String userId) {
        this.comment = comment;
        this.name = name;
        this.star = star;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "comment='" + comment + '\'' +
                ", name='" + name + '\'' +
                ", star=" + star +
                ", userId='" + userId + '\'' +
                '}';
    }
}