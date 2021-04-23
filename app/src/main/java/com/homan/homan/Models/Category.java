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
    private String amount;
    private String desc;
    private String image;

    public Category(String userID, String categoryType , String amount , String desc) {
        this.userID = userID;
        this.categoryType = categoryType;
        this.amount = amount;
        this.desc = desc;
    }

    public Category() { }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("UserID", userID);
            put("CategoryType", categoryType);
            put("Amount", amount);
            put("Description", desc);
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
