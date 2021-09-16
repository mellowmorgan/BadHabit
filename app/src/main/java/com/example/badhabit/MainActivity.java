//Dashboard for logged in user
//displaying Welcome, User Name, date and list of trackers clickable, settings button in corner
//trackers clickable to TrackerPageActivity
//settings clickable to SettingsActivity

package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}