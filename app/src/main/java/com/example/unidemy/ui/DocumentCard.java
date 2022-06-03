package com.example.unidemy.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class DocumentCard implements Parcelable {

    private final String document_title;
    private final String document_view;
    private final String document_url;


    public DocumentCard(String document_title, String document_view, String document_url) {
        this.document_title = document_title;
        this.document_view = document_view;
        this.document_url = document_url;
    }

    protected DocumentCard(Parcel in) {
        document_title = in.readString();
        document_view = in.readString();
        document_url = in.readString();
    }

    public static final Creator<DocumentCard> CREATOR = new Creator<DocumentCard>() {
        @Override
        public DocumentCard createFromParcel(Parcel in) {
            return new DocumentCard(in);
        }

        @Override
        public DocumentCard[] newArray(int size) {
            return new DocumentCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(document_title);
        parcel.writeString(document_view);
        parcel.writeString(document_url);
    }

    public String getDocument_title() {
        return document_title;
    }

    public String getDocument_view() {
        return document_view;
    }

    public String getDocument_url() {
        return document_url;
    }
}
