package com.homan.homan.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    @Query("SELECT * FROM Category WHERE categoryType == :categoryType AND userID == :userID")
    LiveData<List<Category>> getByCategoryType(String categoryType, String userID);

    @Query("SELECT * FROM Category WHERE ownerId == :ownerId AND categoryType == :categoryType" )
    LiveData<List<Category>> getByUserCategoryType(String ownerId, String categoryType);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category...categories);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM Category " +
            "WHERE ownerId LIKE :ownerId")
    LiveData<List<Category>> getAllByUserId(String ownerId);
}

