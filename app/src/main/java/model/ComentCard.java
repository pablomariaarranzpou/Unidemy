package model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class ComentCard implements Parcelable {

    private String coment_content, coment_name, coment_id;
    private float coment_rating, coment_notafinal;
    private Timestamp timestamp;


    public ComentCard(String coment_content, String coment_name,
                      double coment_rating, Timestamp timestamp, String coment_id, double coment_notafinal) {
        this.coment_content = coment_content;
        this.coment_name = coment_name;
        this.coment_notafinal = (float)coment_notafinal;
        this.coment_rating = (float)coment_rating;
        this.timestamp = timestamp;
        this.coment_id = coment_id;
    }

    protected ComentCard(Parcel in) {
        coment_content = in.readString();
        coment_name = in.readString();
        coment_rating = in.readFloat();
        timestamp = in.readParcelable(Timestamp.class.getClassLoader());
        coment_id = in.readString();
        coment_notafinal = in.readFloat();
    }

    public static final Creator<ComentCard> CREATOR = new Creator<ComentCard>() {
        @Override
        public ComentCard createFromParcel(Parcel in) {
            return new ComentCard(in);
        }

        @Override
        public ComentCard[] newArray(int size) {
            return new ComentCard[size];
        }
    };



    public String getComent_content() {
        return coment_content;
    }

    public String getComent_name() {
        return coment_name;
    }

    public float getComent_rating() {
        return coment_rating;
    }
    public String getComent_ratingString() {
        return Float.toString(coment_rating);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getComent_id() {
        return coment_id;
    }

    public static Creator<ComentCard> getCREATOR() {
        return CREATOR;
    }

    public float getComent_notafinal() {
        return coment_notafinal;
    }

    public String getComent_notafinalString(){
        return Float.toString(coment_notafinal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coment_content);
        parcel.writeString(coment_name);
        parcel.writeFloat(coment_rating);
        parcel.writeParcelable(timestamp, i);
        parcel.writeString(coment_id);
        parcel.writeFloat(coment_notafinal);
    }
}