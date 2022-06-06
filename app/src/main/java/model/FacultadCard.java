package model;

import android.os.Parcel;
import android.os.Parcelable;

public class FacultadCard implements Parcelable {




    private String nombre, facultadID;


    public FacultadCard(String nombre, String uniID) {
        this.nombre = nombre;
        this.facultadID = uniID;
    }

    protected FacultadCard(Parcel in) {

        nombre = in.readString();
        facultadID = in.readString();
    }

    public static final Creator<FacultadCard> CREATOR = new Creator<FacultadCard>() {
        @Override
        public FacultadCard createFromParcel(Parcel in) {
            return new FacultadCard(in);
        }

        @Override
        public FacultadCard[] newArray(int size) {
            return new FacultadCard[size];
        }
    };

    public String getNombre() {

        return nombre;
    }

    public String getFacultadID(){
        return this.facultadID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(nombre);
        parcel.writeString(facultadID);
    }
}
