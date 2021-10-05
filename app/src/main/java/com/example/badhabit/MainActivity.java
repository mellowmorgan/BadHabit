//Dashboard for logged in user
//displaying Welcome, User Name, date and list of trackers clickable, settings button in corner
//trackers clickable to TrackerPageActivity
//settings clickable to SettingsActivity

package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    String email;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDashboard();
    }



    public void loadDashboard() {

        Intent i = getIntent();
        email = i.getStringExtra("EMAIL");
        id = email = i.getStringExtra("ID");
        String password = i.getStringExtra("PASSWORD");
        String username = i.getStringExtra("USERNAME");
        id = i.getStringExtra("ID");
        TextView t = findViewById(R.id.welcomeMessage);
        t.setText("Welcome, " + username);
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        String stringDate2 = sdf2.format(new Date());
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        TextView t2 = findViewById(R.id.dateField);
        String monthString;
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        t2.setText(stringDate2 + ", " + monthString + " " + day);


    }

    public void launchSettingsActivity(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void launchCalendarActivity(View v) {
        Intent i = new Intent(this, CalendarActivity.class);
        i.putExtra("EMAIL", email);
        i.putExtra("ID", id);
      //tell the calendaractivity it's first time loading w true
        i.putExtra("EVAL",true);
        startActivity(i);

    }
}