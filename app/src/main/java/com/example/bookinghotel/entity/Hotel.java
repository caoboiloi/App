package com.example.bookinghotel.entity;

import android.util.Log;

import com.example.bookinghotel.BookRoom;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel {
    Integer id;
    String image;
    double lat;
    double longitude;
    String name;
    Room room;
    ArrayList<Rating> rating = new  ArrayList<Rating>();
    BookedRoom bookedRoom = new BookedRoom();
    String path;
    public Hotel() {
    }



    public Hotel(Integer id, double lat, double longitude, String name, Room room, ArrayList<Rating> rating, String image) {
        this.id =id;
        this.lat = lat;
        this.longitude = longitude;
        this.name = name;
        this.room = room;
        this.rating = rating;
        this.image = image;
    }
    public BookedRoom getBookedRoom() {
        return bookedRoom;
    }

    public void setBookedRoom(BookedRoom bookedRoom) {
        this.bookedRoom = bookedRoom;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
            for (int i = 0; i < this.rating.size() ; i++) {
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
                ", booked='" + bookedRoom.toString() + '\'' +
                '}';
    }
    public boolean isEmpty(String string) {
        return (string != null && string.isEmpty());
    }

}
