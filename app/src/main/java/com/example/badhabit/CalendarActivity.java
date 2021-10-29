package com.example.badhabit;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;

import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarActivity extends AppCompatActivity implements Serializable{
//invoke database tomorrow, will solve everything
    //user,calendars,arraylist of trackeddays(good/bad, date)
    //find streak from yesterday -1 current on counter starts at one if yesterday is good,
    // loop back dates --1 until none, display counter above calendar with message
    //user has calendar, arraylist of trackedays, if changed to nothing delete date from array
    //should i use datedata?

    private static final String TAG = "CalendarActivity";
    private MCalendarView mCalendarView;
    private UserModel userModel;
    private DatabaseHelper db;
    private boolean eval;
    private List<DateMarkedModel> dmList;
    private boolean firstLoad =true;

    private String sID;
    int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       dmList = new ArrayList<>();

       Intent i = getIntent();
     //  userEmail = i.getStringExtra("EMAIL");
        setContentView(R.layout.calendar_layout);
        //DateMarkedModel dm = new DateMarkedModel();
        //userModel = new UserModel();
        db = new DatabaseHelper(this);
        sID= i.getStringExtra("ID");
        boolean loadEval = i.getExtras().getBoolean("EVAL");
        firstLoad = loadEval;
        id = Integer.parseInt(sID);
        dmList = db.getAllDatesForUser(id);
        datesMarkedLoader(dmList);
        trackerSet();

        mCalendarView = (MCalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                Toast.makeText(CalendarActivity.this, String.format("%d-%d", year, month), Toast.LENGTH_SHORT).show();
            }
        });


        MCalendarView date = mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

                String da = date.getMonthString() + date.getDayString() + date.getYear();
//                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: " + da);
                Intent intent = new Intent(CalendarActivity.this, CalendarEntryPopUpActivity.class);
                intent.putExtra("date", da);
                intent.putExtra("id", sID);
                startActivity(intent);

            }


        });
        //if intent is there trigger hasSavedDay, make calendar show saved days? might need to put at to


    }
    public void datesMarkedLoader(List<DateMarkedModel> list){
        if (list.size()>0){if(firstLoad){
            for(int i=0;i<list.size();i++){
                DateMarkedModel day = list.get(i);
                hasSavedDay(day.getHowDay(), day.getDate());
            }}
            else{
                DateMarkedModel day = list.get((list.size()-1));
                hasSavedDay(day.getHowDay(), day.getDate());
            }
        }

    }

    public void hasSavedDay(String howDay, String dateSelected) {
        int dateMonth = Integer.parseInt(dateSelected.substring(0, 2));
        int dateDay = Integer.parseInt(dateSelected.substring(2, 4));
        int dateYear = Integer.parseInt(dateSelected.substring(4, 8));
        mCalendarView = (MCalendarView) findViewById(R.id.calendarView);

        //database time sArr.add(dateSelected);

//        else{
//           //database sArr.add(dateSelected);
//        }
          if ("good".equals(howDay)) {
                DateData d = new DateData(dateYear, dateMonth, dateDay);
              mCalendarView.unMarkDate(d);
                mCalendarView.markDate(d.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.GREEN))
                );

            }

            if ("bad".equals(howDay)) {
                DateData d = new DateData(dateYear, dateMonth,dateDay);
                mCalendarView.unMarkDate(d);
                mCalendarView.markDate(d.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.RED))
                );

            }
            if ("nothing".equals(howDay)) {
                DateData d = new DateData(dateYear, dateMonth,dateDay);
                mCalendarView.unMarkDate(d);

            }




        }







    public void clearCalendar(View v) {

        List<DateMarkedModel> listDatesMarked = db.getAllDatesForUser(id);
        for (int i=0;i<listDatesMarked.size();i++){
            DateMarkedModel currentRow = listDatesMarked.get(i);
            String sD = currentRow.getDate();
            int dateMonth = Integer.parseInt(sD.substring(0, 2));
            int dateDay = Integer.parseInt(sD.substring(2, 4));
            int dateYear = Integer.parseInt(sD.substring(4, 8));
            mCalendarView.unMarkDate(dateYear, dateMonth, dateDay);}
        db.deleteAllDatesMarked(id);
        trackerSet();

}

public void trackerSet(){
    TextView tracker = findViewById(R.id.textViewTracker);
    int intTracker = db.goodDayTracker(id);
    String strTracker = Integer.toString(intTracker);
    if (intTracker==1){
        tracker.setText("Tracker: " + strTracker + " good day so far.");}
    else if (intTracker>1){
        tracker.setText("Tracker: " + strTracker + " good days!");

    }
    else {
        tracker.setText("A clean slate; you got this!");
    }

}





}

