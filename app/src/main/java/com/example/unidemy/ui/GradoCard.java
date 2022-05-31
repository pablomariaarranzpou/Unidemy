package com.example.unidemy.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class GradoCard implements Parcelable {
    private String nombre, gradoID;


    public GradoCard(String nombre, String uniID) {
        this.nombre = nombre;
        this.gradoID = uniID;
    }

    protected GradoCard(Parcel in) {

        nombre = in.readString();
        gradoID = in.readString();
    }

    public static final Parcelable.Creator<GradoCard> CREATOR = new Parcelable.Creator<GradoCard>() {
        @Override
        public GradoCard createFromParcel(Parcel in) {
            return new GradoCard(in);
        }

        @Override
        public GradoCard[] newArray(int size) {
            return new GradoCard[size];
        }
    };

    public String getNombre() {

        return nombre;
    }

    public String getGradoID(){
        return this.gradoID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(nombre);
        parcel.writeString(gradoID);
    }
}
