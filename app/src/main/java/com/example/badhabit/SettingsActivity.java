package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//not in use, add functionality later if i have time
public class SettingsActivity extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent i = getIntent();
        id = i.getStringExtra("ID");

    }

    public void logOut(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("LOGGEDIN", false);
        startActivity(intent);
    }
    public void deleteAccount(View view){
        Intent i = new Intent(this, DeleteActivity.class);
        i.putExtra("ID", id);
        startActivity(i);
    }
    public void changeUserInfo(View v) {
        Intent i = new Intent(this, UpdateAccountActivity.class);
        i.putExtra("ID", id);
        startActivity(i);

    }

}