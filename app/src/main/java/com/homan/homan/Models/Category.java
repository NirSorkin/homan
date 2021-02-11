package com.homan.homan.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Date;

@Entity
public class Category {
    private String type;
    private String name;
    private Date date;
    private double amount;
    private House houseId;
    private  String note;
    private String image;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @NonNull
    public House getHouseId() {
        return houseId;
    }

    public void setHouseId(@NonNull House houseId) {
        this.houseId = houseId;
    }
}
