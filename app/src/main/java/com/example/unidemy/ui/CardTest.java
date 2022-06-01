package com.example.unidemy.ui;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CardTest implements Parcelable{


    private final String test_title;
    private final String test_views;


    public CardTest(String test_title, String test_views) {
        this.test_title = test_title;
        this.test_views = test_views;
    }

    protected CardTest(Parcel in) {
        test_title = in.readString();
        test_views = in.readString();

    }

    public static final Parcelable.Creator<VideoCard> CREATOR = new Parcelable.Creator<VideoCard>() {
        @Override
        public VideoCard createFromParcel(Parcel in) {
            return new VideoCard(in);
        }

        @Override
        public VideoCard[] newArray(int size) {
            return new VideoCard[size];
        }
    };

    public String getTest_title() {
        return test_title;
    }

    public String getTest_views() {
        return test_views;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(test_title);
        parcel.writeString(test_views);
    }
}
