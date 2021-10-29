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
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupHyperlink();
        try{
            Intent i = getIntent();
            String deleteMessage = i.getStringExtra("SUCCESSDELETE");
            Toast.makeText(this,deleteMessage, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){

        }
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
           id = userModel.getId();
          launchMainActivity(id);

        }

        else{
            Toast.makeText(this, "Please provide a valid login.", Toast.LENGTH_SHORT).show();
        }

    }
    public void launchMainActivity(int id){
        Intent i = new Intent(this, MainActivity.class);
        String inputID = Integer.toString(id);
        i.putExtra("ID", inputID);
        Boolean loggedIn = true;
        i.putExtra("LOGGEDIN", loggedIn);
        startActivity(i);

    }
    public void launchRegister(View v){

        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

}