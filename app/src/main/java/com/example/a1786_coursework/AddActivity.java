package com.example.a1786_coursework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1786_coursework.database.AppDatabase;
import com.example.a1786_coursework.models.HikeLog;

import java.time.LocalDate;

public class AddActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    TextView datePicker;
    Spinner level_hike;
    EditText name_hike, location_hike, length_hike, description_hike;
    RadioGroup parking_check;
    RadioButton parking_true;

    String parking_hike = "Parking Available";
    Button add_hike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hikeLog_db")
                .allowMainThreadQueries()
                .build();

        //Date Picker for Date Hike
        datePicker = findViewById(R.id.date_picker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        add_hike = findViewById(R.id.add_button);

        add_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllInputs();
            }
        });
    }

    private void getAllInputs() {
        name_hike = findViewById(R.id.name_hike_input);
        location_hike = findViewById(R.id.location_hike_input);
        length_hike = findViewById(R.id.length_hike_input);
        level_hike = findViewById(R.id.level_hike_input);
        description_hike = findViewById(R.id.description_hike_input);
        parking_check = findViewById(R.id.parking_hike_input);
        parking_true = findViewById(R.id.parking_hike_true);

        parking_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup parking_check, int parking_true) {
                if (parking_true == R.id.parking_hike_true) {
                    parking_hike = "Parking Available";
                } else {
                    parking_hike = "No Parking";
                }
            }
        });

        String name = name_hike.getText().toString().trim();
        String location = location_hike.getText().toString();
        String date = datePicker.getText().toString().trim();
        parking_hike.trim();
        int length_int = Integer.parseInt(length_hike.getText().toString().trim());
        String length = length_hike.getText().toString().trim();
        String level = level_hike.getSelectedItem().toString().trim();
        String description = description_hike.getText().toString().trim();

        // Use function displayNextAlert to display an AlertDialog
        displayNextAlert(name, location, date, parking_hike, length, level, description, length_int );
    }

    // display Alert to show information after add to DB
    public void displayNextAlert(String name, String location, String date, String parking_hike, String length, String level, String description, int length_int){
        if (name == "null" || name.isEmpty() || location == "null" || location.isEmpty() ||date == "null" || date.isEmpty() || date.equalsIgnoreCase("Click here to select the date") ||
                parking_hike == "null" || parking_hike.isEmpty() ||length == "null" || length.isEmpty() ||level == "null" || level.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please fill in all the fields")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Information")
                    .setMessage(
                            "Your Hike Information: \n" +
                                    "Name: " + name + "\n" +
                                    "Location: " + location + "\n" +
                                    "Date: " + date + "\n" +
                                    "Parking: " + parking_hike + "\n" +
                                    "Length: " + length + "\n" +
                                    "Level: " + level + "\n" +
                                    "Description: " + description
                    )
                    .setCancelable(true)
                    .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addHikeLog(name, location, date, parking_hike, length_int, level, description );
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    private void addHikeLog(String name, String location, String date, String parking_hike, int length, String level, String description) {

        HikeLog hikeLog = new HikeLog();
        hikeLog.name_hike = name;
        hikeLog.location_hike = location;
        hikeLog.date_hike = date;
        hikeLog.parking_hike = parking_hike;
        hikeLog.length_hike = length;
        hikeLog.level_hike = level;
        hikeLog.description_hike = description;

        // Calls the insertDetails method we created
        long hike_id = appDatabase.hikeDao().insertHikeLog(hikeLog);

        // Shows a toast with the automatically generated id
        Toast.makeText(this, "Hike Log has been created with id: " + hike_id,
                Toast.LENGTH_LONG
        ).show();

        // Launch Details Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // DatePicker Fragment
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);}

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate dateHike = LocalDate.of(year, ++month, day);
            ((AddActivity)getActivity()).updateDate(dateHike);
        }
    }
    public void updateDate(LocalDate dateHike){
        TextView datePicker = findViewById(R.id.date_picker);
        datePicker.setText(dateHike.toString());
    }
}