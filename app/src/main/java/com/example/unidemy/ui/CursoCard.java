package com.example.unidemy.ui;




import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

// From https://developer.android.com/training/data-storage/room/

public class CursoCard implements Parcelable {


    private final String course_title;
    private final String course_views;
    private final String course_rating;
    private final String course_description;
    private final String course_owner;
    private final String course_id;
    private ArrayList<String> course_videos;


    //private final AppDatabase db;


    public CursoCard(String course_title, String course_description,
                     String course_owner, String course_views, String course_rating,
                     String course_id, ArrayList<String> course_videos) {

        this.course_title = course_title;
        this.course_views = course_views;
        this.course_rating = course_rating;
        this.course_description = course_description;
        this.course_owner = course_owner;
        this.course_id = course_id;
        this.course_videos = course_videos;
    }

    protected CursoCard(@NonNull Parcel in) {
        course_title = in.readString();
        course_views = in.readString();
        course_rating = in.readString();
        course_description = in.readString();
        course_owner = in.readString();
        course_id = in.readString();
        course_videos = in.createStringArrayList();
    }

    public static final Creator<CursoCard> CREATOR = new Creator<CursoCard>() {
        @Override
        public CursoCard createFromParcel(Parcel in) {
            return new CursoCard(in);
        }

        @Override
        public CursoCard[] newArray(int size) {
            return new CursoCard[size];
        }
    };

    public String getCourse_title() {
        return course_title;
    }

    public String getCourse_views() {
        return course_views;
    }

    public String getCourse_rating() {
        return course_rating;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getOwner() { return course_owner; }

    public String getCourse_id() {return course_id; }

    public ArrayList<String> getCourse_videos(){ return course_videos; }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(course_title);
        parcel.writeString(course_views);
        parcel.writeString(course_rating);
        parcel.writeString(course_description);
        parcel.writeString(course_owner);
        parcel.writeString(course_id);
        parcel.writeList(course_videos);
    }
}
