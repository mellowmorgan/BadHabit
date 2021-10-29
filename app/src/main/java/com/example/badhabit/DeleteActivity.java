package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {
    String id;
    DatabaseHelper db;
    UserModel um;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
       Intent i = getIntent();
        id = i.getStringExtra("ID");
        db = new DatabaseHelper(this);
        int intId = Integer.parseInt(id);
      //  Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        um = db.fetchUserByID(intId);




    }
    public void authenticatePasswordOnSubmit(View v){
        String actualPassword = um.getPassword();
        TextView t = findViewById(R.id.password2delete);
        String enteredPassword = t.getText().toString();
        if (enteredPassword.equals(actualPassword)){
            int intId = Integer.parseInt(id);
            //delete user from user_table, fetch all dates by user, for each date forloop delete date
            db.deleteUser(intId);
            db.deleteAllDatesMarked(intId);
            launchLoginOnDelete();
        }
        else{
            Toast.makeText(this, "Password did not match.", Toast.LENGTH_SHORT).show();
        }

    }
    public void launchLoginOnDelete(){
        Intent i = new Intent(this, LoginActivity.class);
        String strDelete = "Your account was successfully deleted.";
        i.putExtra("SUCCESSDELETE", strDelete);
        startActivity(i);

    }
}
