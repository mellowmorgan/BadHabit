package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupHyperlink();
    }

    public void setupHyperlink(){
        TextView linkTextView = findViewById(R.id.linkRegister);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setTextColor(Color.BLUE);
    }

    //authenticate User and launch dashboard
    public void loginHandler(View v){
        TextView textEmail = findViewById(R.id.loginEmail);
        String inputEmail = textEmail.getText().toString();
        TextView textPassword = findViewById(R.id.loginPassword);
        String inputPassword = textPassword.getText().toString();
        UserModel userModel;
        DatabaseHelper db;
        db = new DatabaseHelper(this);
        boolean doesUserExist = db.userAuthenticate(inputEmail, inputPassword);
        if (doesUserExist){
           userModel = db.fetchUser(inputEmail);
           int id = userModel.getId();


          launchMainActivity(id, inputEmail, inputPassword, userModel);

        }

        else{
            Toast.makeText(this, "Please provide a valid login.", Toast.LENGTH_SHORT).show();
        }

    }
    public void launchMainActivity(int id, String email, String password, UserModel user){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("EMAIL", email);
        String inputID = Integer.toString(id);
        i.putExtra("ID", inputID);
        i.putExtra("PASSWORD", password);
        String username = user.getUsername();
        i.putExtra("USERNAME", username);

        startActivity(i);

    }
    public void launchRegister(View v){

        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

}