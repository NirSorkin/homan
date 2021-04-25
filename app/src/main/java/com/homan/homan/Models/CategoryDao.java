package com.homan.homan.Models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("select * from Category")
    LiveData<List<Category>> getAllCategories();

   /* @Query("select * from Category where houseID == :houseID")
    LiveData<List<Category>> getByHouseID(int houseID);*/

    @Query("select * from Category where categoryType == :categoryType")
    LiveData<List<Category>> getByCategoryType(String categoryType);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category...categories);

    @Delete
    void deleteCategory(Category category);

}

