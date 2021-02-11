package com.homan.homan.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private String id;
    private String UserName;
    private String password;
    private String email;
    @NonNull
    private House houseId;

    public String getId() {
        return id;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public House getHouseId() {
        return houseId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHouseId(House houseId) {
        this.houseId = houseId;
    }
}
