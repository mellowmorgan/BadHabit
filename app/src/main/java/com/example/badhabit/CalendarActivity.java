package com.example.badhabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity {
    CustomCalendar customCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        customCalendar = findViewById(R.id.calendar);
        HashMap<Object, Property> descHashMap = new HashMap<>();
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default", defaultProperty);
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("current", currentProperty);
        Property goodProperty = new Property();
        goodProperty.layoutResource = R.layout.good_view;
        goodProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("good", goodProperty);
        Property badProperty = new Property();
        badProperty.layoutResource = R.layout.bad_view;
        badProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("bad", badProperty);

        customCalendar.setMapDescToProp(descHashMap);
        HashMap<Integer, Object> dateHashMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        dateHashMap.put(cal.get(Calendar.DAY_OF_MONTH), "current");
//        dateHashMap.put(1, "good");
//        dateHashMap.put(2, "bad");
//        dateHashMap.put(3, "good");
//        dateHashMap.put(4, "bad");
//        dateHashMap.put(20, "good");
//        dateHashMap.put(30, "bad");
        customCalendar.setDate(cal, dateHashMap);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc){
                String sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        + "/" +  (selectedDate.get(Calendar.MONTH)+1)
                        + "/" + selectedDate.get(Calendar.YEAR);
                Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void launchCalendarPopUp(View view){
        Intent i = new Intent(this, CalendarEntryActivity.class);
        i.putExtra()
        startActivity(i);
    }
}