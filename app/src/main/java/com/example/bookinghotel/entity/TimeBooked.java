package com.example.bookinghotel.entity;

import java.util.ArrayList;

public class TimeBooked {
    ArrayList<Long> begin = new ArrayList<>();
    ArrayList<Long> end = new ArrayList<>();

    public TimeBooked() {
        this.begin = new ArrayList<>();
        this.begin.add(1L);
        this.end = new ArrayList<>();
        this.end.add(1L);
    }

    public TimeBooked(ArrayList<Long> begin, ArrayList<Long> end) {
        this.begin = begin;
        this.end = end;
    }

    public ArrayList<Long> getBegin() {
        return begin;
    }

    public void setBegin(ArrayList<Long> begin) {
        this.begin = begin;
    }

    public ArrayList<Long> getEnd() {
        return end;
    }

    public void setEnd(ArrayList<Long> end) {
        this.end = end;
    }
    public void appendBegin(Long item) {
        this.begin.add(item);
    }
    public void appendEnd(Long item) {
        this.end.add(item);
    }
    @Override
    public String toString() {
        return "TimeBooked{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
