package com.example.a1786_coursework.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "hikeLogs")
public class HikeLog {
    @PrimaryKey(autoGenerate = true)
    public long hike_id;
    public String name_hike;
    public String location_hike;
    public String date_hike;
    public String parking_hike;

    public int length_hike;
    public String level_hike;
    public String description_hike;


}
