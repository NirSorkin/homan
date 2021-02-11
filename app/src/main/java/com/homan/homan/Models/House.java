package com.homan.homan.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class House {
    @PrimaryKey(autoGenerate = true)
    private String id;
    @NonNull
    private String adminUserId;

    public void setAdminUserId(@NonNull String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
