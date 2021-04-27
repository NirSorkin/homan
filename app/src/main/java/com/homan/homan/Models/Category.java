package com.homan.homan.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int houseID;
    private String userID;
    private String categoryType;
    private String date;
    private String amount;
    private String desc;
    private String image;
    private Boolean isRemoved;
    private Long lastUpdated;

    public Category(String userID, String categoryType , String amount , String desc) {
        this.userID = userID;
        this.categoryType = categoryType;
        this.amount = amount;
        this.desc = desc;
    }

    public Category() { }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("userID", userID);
            put("categoryType", categoryType);
            put("amount", amount);
            put("desc", desc);
            put("image", image);
            put("removed", isRemoved);
            put("lastUpdated", FieldValue.serverTimestamp());
        }};
    }

    public void fromMap(Map<String, Object> map) {
        userID = (String) map.get("userID");
        categoryType = (String) map.get("categoryType");
        amount = (String) map.get("amount");
        desc = (String) map.get("desc");
        image = (String) map.get("image");
        isRemoved = (Boolean) map.get("removed");
        lastUpdated = ((Timestamp) map.get("lastUpdated")).toDate().getTime();
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
    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
