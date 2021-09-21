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

public class CalendarEntryPopUpActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch aSwitch;
    private Switch bSwitch;
    private String howDaySelector = "nothing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_entry_pop_up);
        Intent i = getIntent();
        String s = i.getStringExtra("date");
        TextView t = findViewById(R.id.textView2);
        t.setText(s);
        aSwitch = findViewById(R.id.switch_a);
        bSwitch = findViewById(R.id.switch_b);
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
        Intent intent = getIntent();
        String s = intent.getStringExtra("date");
        Intent i = new Intent(this, CalendarActivity.class);
        i.putExtra("howDay", howDaySelector);
        i.putExtra("date", s);
        startActivity(i);

    }
    public void onCancel(View v){
        Intent i = new Intent(this, CalendarActivity.class);
        startActivity(i);

    }
}





