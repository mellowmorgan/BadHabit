package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

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
    public void loginHandler(View v){
        TextView textEmail = findViewById(R.id.loginEmail);
        String inputEmail = textEmail.getText().toString();
        TextView textPassword = findViewById(R.id.loginPassword);
        String inputPassword = textPassword.getText().toString();
        launchMainActivity(inputEmail, inputPassword);

    }
    public void launchMainActivity(String email, String password){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("EMAIL", email);
        i.putExtra("PASSWORD", password);
        startActivity(i);

    }
    public void launchRegister(View v){

        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

    }

}