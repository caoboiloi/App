package com.example.bookinghotel.entity;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String id;
    private String love;
    private String phone;
    private String sex;
    private String address;
    private String job;
    private String workplace;
    private ArrayList<Ticket> ticket = new ArrayList<>();
    ArrayList<String> favorite = new ArrayList<String>();

    public User() {

    }

    public User(String name, String email, String id, String love, String phone, String sex, String address, String job, String workplace) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.love = love;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.job = job;
        this.workplace = workplace;
    }

    public User(String name, String email, String love, String phone, String sex, String address, String job, String workplace) {
        this.name = name;
        this.email = email;
        this.love = love;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.job = job;
        this.workplace = workplace;
    }

    public ArrayList<String> getFavorite() {
        return favorite;
    }

    public void setFavorite(ArrayList<String> favorite) {
        this.favorite = favorite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public ArrayList<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(ArrayList<Ticket> tickets) {
        this.ticket = tickets;
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
