package com.homan.homan.Models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.homan.homan.*;

@Database(entities = {Category.class}, version = 2)
abstract class AppLocalDBRepository extends RoomDatabase {

    public abstract CategoryDao categoryDao();

}

public class AppLocalDB {
    static public AppLocalDBRepository db = Room
            .databaseBuilder(MyApplication.context, AppLocalDBRepository.class, "homanDB.db")
            .fallbackToDestructiveMigration()
            .build();
}
