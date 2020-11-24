package com.example.bookinghotel.entity;

public class Rating{
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
