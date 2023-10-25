package com.example.a1786_coursework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.a1786_coursework.adapter.HikeLogAdapter;
import com.example.a1786_coursework.database.AppDatabase;
import com.example.a1786_coursework.models.HikeLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_hike_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hikeLog_db")
                .allowMainThreadQueries()
                .build();

        List<HikeLog> hikeLogs = appDatabase.hikeDao().getAllHikes();

        HikeLogAdapter adapter = new HikeLogAdapter(hikeLogs);
        adapter.setOnItemClickListener(new HikeLogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                HikeLog hikeLog = hikeLogs.get(position);
                intent.putExtra("hike_id", Long.toString(hikeLog.hike_id));
                intent.putExtra("hike_name", hikeLog.name_hike);
                intent.putExtra("hike_location", hikeLog.location_hike);
                intent.putExtra("hike_date", hikeLog.date_hike);
                intent.putExtra("hike_parking", hikeLog.parking_hike);
                intent.putExtra("hike_length", Integer.toString(hikeLog.length_hike));
                intent.putExtra("hike_level", hikeLog.level_hike);
                intent.putExtra("hike_description", hikeLog.description_hike);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


        add_hike_button = findViewById(R.id.add_hike_button);
        add_hike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.search_app){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}