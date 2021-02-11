package com.homan.homan.Models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.homan.homan.*;

@Database(entities = {Category.class , House.class , User.class }, version = 1)
abstract class AppLocalDBRepository extends RoomDatabase {
    public abstract HouseDao houseDao();
    public abstract CategoryDao categoryDao();
    public abstract UserDao userDao();
}

public class AppLocalDB {
    public static AppLocalDBRepository db = Room
            .databaseBuilder(MyApplication.context, AppLocalDBRepository.class, "homanDB.db")
            .fallbackToDestructiveMigration()
            .build();
}
