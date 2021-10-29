package com.example.badhabit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    //setting up second table for dates marked
    public static final String DATE_TABLE = "DATE_TABLE";
    public static final String COLUMN_DATE_USER_ID = "DATE_USER_ID";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_DATE_HOW_DAY = "HOW_DAY";
    //private static final String DATABASE_CREATE_DATE_TABLE = "CREATE TABLE IF NOT EXISTS DATE_TABLE()";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";
        String createTable2Statement = "CREATE TABLE " + DATE_TABLE + " (" + COLUMN_DATE_USER_ID + " INTEGER, " + COLUMN_DATE + " TEXT, " + COLUMN_DATE_HOW_DAY + " TEXT)";

        db.execSQL(createTableStatement);
        db.execSQL(createTable2Statement);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public boolean addOne(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, userModel.getUsername());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());
        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }


    }
    public boolean addDateMarked(DateMarkedModel dm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE_USER_ID, dm.getId());
        cv.put(COLUMN_DATE, dm.getDate());
        cv.put(COLUMN_DATE_HOW_DAY, dm.getHowDay());
        long insert = db.insert(DATE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }
    public void deleteAllUsers(){
        String queryString = "DELETE FROM USER_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();

    }
    public void deleteUser(int id){
        String queryString = "DELETE FROM USER_TABLE WHERE ID=\"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();
    }
    public void deleteAllDatesMarked(int id){
        String queryString = "DELETE FROM DATE_TABLE WHERE DATE_USER_ID=\"" + id + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();

    }
    public void deleteDateMarked(int id, String date){
        String queryString = "DELETE FROM DATE_TABLE WHERE DATE_USER_ID=\"" + id + "\"" + " AND " + "DATE=\"" + date + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();

    }
    public boolean doesDateMarkedExist(int id, String date){
        boolean b = false;
        String queryString = "SELECT * FROM DATE_TABLE WHERE DATE_USER_ID=\"" + id + "\"" + " AND " + "DATE=\"" + date + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            b=true;
        }

        else {
           b=false;
        }
        cursor.close();
        db.close();
        return b;
    }
    public DateMarkedModel fetchDate(int id, String date){
        DateMarkedModel dm;
        String queryString = "SELECT * FROM DATE_TABLE WHERE DATE_USER_ID=\"" + id + "\"" + " AND " + "DATE=\"" + date + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()) {
                int columnId = cursor.getInt(0);
                String columnDate = cursor.getString(1);
                String columnHow = cursor.getString(2);
                dm = new DateMarkedModel(columnId, columnDate, columnHow);

        }
        else {
                dm = new DateMarkedModel(id, date, "nothing");
            }
        cursor.close();
        db.close();
        return dm;
    }

    public UserModel fetchUserByID(int id){
        UserModel uM;
        String queryString = "SELECT * FROM USER_TABLE WHERE ID=\"" + id + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            int userID = cursor.getInt(0);
            String userName = cursor.getString(1);
            String userEmail = cursor.getString(2);
            String userPassword = cursor.getString(3);
            uM = new UserModel(userID, userName, userEmail, userPassword);

        }

        else {
            uM = new UserModel(-1, "null", "null", "null");

        }
        cursor.close();
        db.close();
        return uM;
    }


    public UserModel fetchUser(String email){
        UserModel uM;
        String queryString = "SELECT * FROM USER_TABLE WHERE USER_EMAIL=\"" + email + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
          if (cursor.moveToFirst()) {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userPassword = cursor.getString(3);
                uM = new UserModel(userID, userName, userEmail, userPassword);

            }

         else {
             uM = new UserModel(-1, "null", "null", "null");

        }
        cursor.close();
        db.close();
        return uM;
    }

    public int goodDayTracker(int id){

        //find yesterday in database see if marked
        int trackerCounter=0;
        //get yesterday date string
        DateMarkedModel yesterdayModel;
        String dateYesterday;
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        int i=-1;
        boolean check;
        do {
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, i);
                dateYesterday = formatter.format(cal.getTime());
                yesterdayModel = this.fetchDate(id, dateYesterday);
                check = this.doesDateMarkedExist(id, dateYesterday);
                if (yesterdayModel.getHowDay().equals("good")) {
                    trackerCounter++;
                    i--;
                }

        }while((check==true) && (yesterdayModel.getHowDay().equals("good")));
        return trackerCounter;



    }

    public List<UserModel> getAll() {

        List<UserModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM USER_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userPassword = cursor.getString(3);
                UserModel uM = new UserModel(userID, userName, userEmail, userPassword);
                returnList.add(uM);
            } while (cursor.moveToNext());

        } else {
        }
        cursor.close();
        db.close();
        return returnList;

    }
    public List<DateMarkedModel> getAllDates() {

        List<DateMarkedModel> returnDateList = new ArrayList<>();
        String queryString = "SELECT * FROM DATE_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            do {
                int dateUserID = cursor.getInt(0);
                String date = cursor.getString(1);
                String howDay = cursor.getString(2);
                DateMarkedModel dM = new DateMarkedModel(dateUserID, date, howDay);
                returnDateList.add(dM);
            } while (cursor.moveToNext());

        } else {
        }
        cursor.close();
        db.close();
        return returnDateList;

    }
    public UserModel updatePassword(int id, String newPassword) {
        String queryString = "UPDATE USER_TABLE SET USER_PASSWORD=\"" + newPassword + "\"" + " WHERE ID=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();
        UserModel um = fetchUserByID(id);
        return um;
    }

    public UserModel updateUserName(int id, String newName) {
        String queryString = "UPDATE USER_TABLE SET USER_NAME=\"" + newName + "\"" + " WHERE ID=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        UserModel um = fetchUserByID(id);
        db.close();

        return um;
    }


    public List<DateMarkedModel> getAllDatesForUser(int dateUserID) {

        List<DateMarkedModel> returnUserDateList = new ArrayList<>();
        String queryString = "SELECT * FROM DATE_TABLE WHERE DATE_USER_ID=\"" + dateUserID + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            do {
                int dateID = cursor.getInt(0);
                String date = cursor.getString(1);
                String howDay = cursor.getString(2);
                DateMarkedModel dM = new DateMarkedModel(dateID, date, howDay);
                returnUserDateList.add(dM);
            } while (cursor.moveToNext());

        } else {
        }
        cursor.close();
        db.close();
        return returnUserDateList;

    }
    public boolean userAuthenticate(String email, String password){
        List<UserModel> allUsers = getAll();
        boolean auth=false;
        for(int i=0; i<allUsers.size();i++){
            UserModel userCurrent = allUsers.get(i);
            String authEmail = userCurrent.getEmail();
            String authPassword = userCurrent.getPassword();
            if ((authEmail.equals(email)) & (authPassword.equals(password))){
                auth = true;
            }
            else
            {auth = false;}
        }
        return auth;


    }

}