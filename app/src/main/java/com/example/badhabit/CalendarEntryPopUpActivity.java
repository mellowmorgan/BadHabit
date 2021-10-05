package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import sun.bob.mcalendarview.vo.DateData;

public class CalendarEntryPopUpActivity extends AppCompatActivity implements Serializable,CompoundButton.OnCheckedChangeListener {
    private Switch aSwitch;
    private Switch bSwitch;
    private String id;
    private String howDaySelector = "nothing";
    private ArrayList<String> storedArr;
    private boolean bool;
    DatabaseHelper db;
    String sDate;
    DateMarkedModel dmModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bool=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_entry_pop_up);
        Intent i = getIntent();
        db = new DatabaseHelper(this);
        sDate = i.getStringExtra("date");
        id = i.getStringExtra("id");
        TextView t = findViewById(R.id.textView2);
        t.setText(sDate);

        aSwitch = findViewById(R.id.switch_a);
        bSwitch = findViewById(R.id.switch_b);

        DateMarkedModel dm = db.fetchDate((Integer.parseInt(id)), sDate);

        String howClickedDay = dm.getHowDay();
        if (howClickedDay.equals("bad")) {
            bSwitch.setChecked(true);
            aSwitch.setClickable(false);
        }
        else if (howClickedDay.equals("good")) {
            aSwitch.setChecked(true);
            bSwitch.setClickable(false);
        }
        aSwitch.setOnCheckedChangeListener(this);
        bSwitch.setOnCheckedChangeListener(this);



    }
    @Override
    public void onCheckedChanged(CompoundButton btn, boolean isChecked){


        if (btn==aSwitch){
            if (isChecked){
                showMessage("Switch Good Day");
                bSwitch.setClickable(false);
                howDaySelector = "good";
            }
        }
        if (btn==bSwitch){
            if (isChecked){
                showMessage("Switch Bad Day");
                aSwitch.setClickable(false);
                howDaySelector = "bad";

            }}
        if (aSwitch.isChecked()==false & bSwitch.isChecked()==false){
            bSwitch.setClickable(true);
            aSwitch.setClickable(true);
            howDaySelector="nothing";
        }


    }

    public void showMessage(String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    public void onSave(View v){
        Intent i = new Intent(this, CalendarActivity.class);

        int idInt = Integer.parseInt(id);
        dmModel = new DateMarkedModel(idInt, sDate, howDaySelector);
        //if date exists in row
        //delete date, add date
        if(db.doesDateMarkedExist(idInt, sDate)){

            db.deleteDateMarked(idInt, sDate);
        }
        db.addDateMarked(dmModel);

        i.putExtra("ID", id);
        startActivity(i);

    }
    public void onCancel(View v){

       Intent i = new Intent(this, CalendarActivity.class);
        i.putExtra("ID", id);
        //no longer first time loading, don't remark all the dates

        i.putExtra("EVAL",false);
        startActivity(i);

    }
}





