package com.example.a1786_coursework.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a1786_coursework.models.HikeLog;

import java.util.List;
@Dao
public interface HikeDao {
    @Insert
    long insertHikeLog(HikeLog hikeLog);

    @Query("SELECT * FROM hikeLogs ORDER BY name_hike")
    List<HikeLog> getAllHikes();

    @Query("DELETE FROM hikeLogs WHERE hike_id=:id")
    void deleteHikeLog(long id);

    @Update
    void updateHikeLog(HikeLog hikeLog);

    @Query("DELETE FROM hikeLogs")
    void deleteAllHikes();

    @Query("SELECT * FROM hikeLogs WHERE name_hike LIKE '%' || :search  || '%' OR location_hike LIKE '%' || :search || '%'")
    List<HikeLog> searchHikeLog(String search);



}
