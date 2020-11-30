package com.example.bookinghotel.entity;

public class Review {
    public String img;
    public String name;
    public String comment;
    public int rating;

    public Review() {
        this.img = "";
        this.name = "";
        this.comment = "";
        this.rating = 0;
    }

    public Review(String name, String img, String comment, int rating) {
        this.img = img;
        this.name = name;
        this.comment = comment;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Review{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
