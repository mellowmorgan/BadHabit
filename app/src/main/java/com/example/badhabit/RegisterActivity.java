package com.example.badhabit;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    List<UserModel> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(RegisterActivity.this);
        userList = db.getAll();


        Toast.makeText(this, userList.toString(),Toast.LENGTH_LONG).show();

    }
    //xml should show enter first name, email, password twice, confirm, go to login page
    public void onSubmitAuthenticate(View v){
        //if authenticated, onSubmitRegister(); passwords valid (must be at least six characters, match, email valid, not used
        //if not, go back to same page, show errors in if by else if

    }
    public void onSubmitRegister(View v) {
        TextView name = findViewById(R.id.registerName);
        TextView email = findViewById(R.id.emailRegister);
        TextView password = findViewById(R.id.password1Register);
        TextView password2 = findViewById(R.id.password2Register);
        String inputName = name.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPassword = password.getText().toString();
        String inputPassword2 = password2.getText().toString();
        UserModel userModel;
        DatabaseHelper db;
        db = new DatabaseHelper(RegisterActivity.this);
        List<UserModel> userList = db.getAll();
        boolean isDuplicate=false;
        //check to see if email duplicate
        for (int i=0;i<userList.size();i++){
            UserModel user = userList.get(i);
            if(user.getEmail().equals(inputEmail)){
                isDuplicate=true;
            }
        }


        if (inputPassword.equals(inputPassword2) & !isDuplicate){
            userModel = new UserModel(1, inputName, inputEmail, inputPassword);

            db.addOne(userModel);

            //strings, store in database, create user database somewhere else???
            //need to create separate database for trackers/calendars
            //launch login after success, message account created! please log in.
            Toast.makeText(this, "SUCCESS!!!!",Toast.LENGTH_SHORT).show();


        }
        else{
            Toast.makeText(this, "ERROR",Toast.LENGTH_SHORT).show();
        }
    }
    public void launchLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
