package com.example.a1786_coursework.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.a1786_coursework.dao.HikeDao;
import com.example.a1786_coursework.models.HikeLog;

@Database(entities = {HikeLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
