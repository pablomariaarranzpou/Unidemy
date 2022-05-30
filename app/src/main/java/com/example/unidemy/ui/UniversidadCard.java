package com.example.unidemy.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class UniversidadCard implements Parcelable {
    private String nombre;

    public UniversidadCard(String nombre) {
        this.nombre = nombre;
    }

    protected UniversidadCard(Parcel in) {
        nombre = in.readString();
    }

    public static final Creator<UniversidadCard> CREATOR = new Creator<UniversidadCard>() {
        @Override
        public UniversidadCard createFromParcel(Parcel in) {
            return new UniversidadCard(in);
        }

        @Override
        public UniversidadCard[] newArray(int size) {
            return new UniversidadCard[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
    }
}
