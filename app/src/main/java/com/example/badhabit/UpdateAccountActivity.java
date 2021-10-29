package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateAccountActivity extends AppCompatActivity {
    DatabaseHelper db;
    int id;
    String sID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        db = new DatabaseHelper(this);
        sID = getIntent().getStringExtra("ID");
        id = Integer.parseInt(sID);
    }
    public void onCancelUpdateAccount(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("ID", sID);
        startActivity(i);

    }
    public void submitUpdateAccount(View v){
        TextView confirmPassword = findViewById(R.id.editTextPassword);
        TextView newUserName = findViewById(R.id.editTextName);
        String sNewName = newUserName.getText().toString();
        String passwordEntered = confirmPassword.getText().toString();
        UserModel currentUser = db.fetchUserByID(id);
        String truePassword = currentUser.getPassword();
        if (passwordEntered.equals(truePassword)){
            db.updateUserName(id, sNewName);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("ID", sID);
            i.putExtra("NEWNAMEMESSAGE","Success. Name changed.");
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
        }

    }
}