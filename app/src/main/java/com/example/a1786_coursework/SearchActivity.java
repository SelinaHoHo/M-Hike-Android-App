package com.example.a1786_coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a1786_coursework.adapter.HikeLogAdapter;
import com.example.a1786_coursework.database.AppDatabase;
import com.example.a1786_coursework.models.HikeLog;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    RecyclerView recyclerView;
    List<HikeLog> hikeLogs;

    HikeLogAdapter adapter;

    EditText searchText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.button_search);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hikeLog_db")
                .allowMainThreadQueries()
                .build();

        hikeLogs = appDatabase.hikeDao().getAllHikes();

        adapter = new HikeLogAdapter(hikeLogs);

        adapter.setOnItemClickListener(new HikeLogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchText.getText().toString();
                if (!search.isEmpty()) {
                    searchHike(search);
                }
                else{
                    adapter.setHikeLogs(appDatabase.hikeDao().getAllHikes());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        recyclerView.setAdapter(adapter);

    }
    private void searchHike(String search) {
        hikeLogs = appDatabase.hikeDao().searchHikeLog(search);
        adapter.setHikeLogs(hikeLogs);
        recyclerView.setAdapter(adapter);
    }
}