package com.example.bookinghotel.entity;

public class Comment {
    private String name;
    private String comment;
    private int rating;
    public Comment() {
    }

    public Comment(String name, String comment, int rating) {
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
}
