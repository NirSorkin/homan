package com.homan.homan.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("select * from Category")
    List<Category> getAllCategories();

    @Query("select * from Category where houseID == :houseID")
    List<Category> getByHouseID(int houseID);

    @Query("select * from Category where houseID == :houseID and categoryType == :categoryType")
    List<Category> getByCategoryType(int houseID , String categoryType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category...categories);

    @Delete
    void deleteCategory(Category category);

}

