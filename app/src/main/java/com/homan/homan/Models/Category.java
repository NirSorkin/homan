package com.homan.homan.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int houseID;
    private String userID;  //Name of the expenses for example Food -> Rami Levi
    private String categoryType;
    private String date;
    private double amount;
    private String desc;
    private String image;

    public Category(int houseID, String userID, String categoryType) {
        this.houseID = houseID;
        this.userID = userID;
        this.categoryType = categoryType;
    }
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("id", userID);
            put("ownerId", categoryType);
            put("name", amount);
            put("desc", desc);
            put("image", image);
        }};
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
