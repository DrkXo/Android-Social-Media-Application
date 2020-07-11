package com.example.ebsma.basic.models.main;

public class Posts {
    public String uid, time, date, postimage, description, profileimage, fullname, genre;
    public double latitude, longitude;
    public int Event_Year, Event_Month, Event_Date;

    public Posts() {
    }

    public Posts(String uid, String time, String date, String postimage, String description, String profileimage, String fullname, String genre,
                 double latitude, double longitude, int Event_Year, int Event_Month, int Event_Date) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.genre = genre;
        this.postimage = postimage;
        this.description = description;
        this.profileimage = profileimage;
        this.fullname = fullname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.Event_Date = Event_Date;
        this.Event_Month = Event_Month;
        this.Event_Year = Event_Year;
    }

    public int get_Event_Year() {
        return Event_Year;
    }

    public void set_Event_Year(int Event_Year) {
        this.Event_Year = Event_Year;
    }

    public int get_Event_Month() {
        return Event_Month;
    }

    public void set_Event_Month(int Event_Month) {
        this.Event_Month = Event_Month;
    }

    public int get_Event_Date() {
        return Event_Date;
    }

    public void set_Event_Date(int Event_Date) {
        this.Event_Date = Event_Date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitute) {
        this.longitude = longitute;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
