package com.example.unidemy.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class CardTest implements Parcelable {


    private final String test_title;
    private final String test_views;
    private final String testID;


    public CardTest(String test_title, String test_views, String testID) {
        this.test_title = test_title;
        this.test_views = test_views;
        this.testID = testID;

    }

    protected CardTest(Parcel in) {
        test_title = in.readString();
        test_views = in.readString();
        testID = in.readString();
    }


    public static final Creator<CardTest> CREATOR = new Creator<CardTest>() {
        @Override
        public CardTest createFromParcel(Parcel in) {
            return new CardTest(in);
        }

        @Override
        public CardTest[] newArray(int size) {
            return new CardTest[size];
        }
    };

    public String getTest_title() {
        return test_title;
    }

    public String getTest_views() {
        return test_views;
    }

    public String getTestID() {
        return testID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(test_title);
        dest.writeString(test_views);
        dest.writeString(testID);
    }
}
