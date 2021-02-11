package com.homan.homan.Models;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;


@Dao
public interface UserDao {

    @Query("select * from User where HouseId == HouseId")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User...users);

    @Delete
    void deleteUser(User user);
}
