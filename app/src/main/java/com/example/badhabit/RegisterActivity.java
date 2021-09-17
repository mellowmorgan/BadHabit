package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    //xml should show enter first name, email, password twice, confirm, go to login page
    public void onSubmitAuthenticate(View v){
        //if authenticated, onSubmitRegister(); passwords valid (must be at least six characters, match, email valid, not used
        //if not, go back to same page, show errors in if by else if

    }
    public void onSubmitRegister(View v){
        TextView name = findViewById(R.id.registerName);
        TextView email = findViewById(R.id.emailRegister);
        TextView password = findViewById(R.id.password1Register);
        //strings, store in database, create user database somewhere else???
        //need to create separate database for trackers/calendars
        //launch login after success, message account created! please log in.
    }
    public void launchLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
