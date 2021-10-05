package com.example.badhabit;

public class DateMarkedModel {
    private int id;
    private String date;
    private String howDay;
    public DateMarkedModel(int id, String date, String howDay){
        this.id = id;
        this.date = date;
        this.howDay = howDay;
    }
    @Override
    public String toString() {
        return "DateMarkedModel{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", howDay='" + howDay + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHowDay() {
        return howDay;
    }

    public void setHowDay(String howDay) {
        this.howDay = howDay;
    }
}
