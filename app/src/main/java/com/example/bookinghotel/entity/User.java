package com.example.bookinghotel.entity;

public class User {
    private String name;
    private String email;
    private String id;
    private String love;

    public User() {

    }

    public User(String name, String email, String id, String love) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.love = love;
    }

    public User(String name, String email, String love) {
        this.name = name;
        this.email = email;
        this.love = love;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", love='" + love + '\'' +
                '}';
    }
}
