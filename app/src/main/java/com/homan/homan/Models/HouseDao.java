package com.homan.homan.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface HouseDao {

    @Query("select * from House")
    List<House> getAllHouses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouse(House...houses);

    @Delete
    void deleteHouse ( House house);
}
