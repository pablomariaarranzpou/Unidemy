package model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoCard implements Parcelable {
    private final String video_title;
    private final String video_views;
    private final String video_url;


    public VideoCard(String video_title, String video_views, String url) {
        this.video_title = video_title;
        this.video_views = video_views;
        this.video_url = url;
    }

    protected VideoCard(Parcel in) {
        video_title = in.readString();
        video_views = in.readString();
        video_url = in.readString();
    }

    public static final Creator<VideoCard> CREATOR = new Creator<VideoCard>() {
        @Override
        public VideoCard createFromParcel(Parcel in) {
            return new VideoCard(in);
        }

        @Override
        public VideoCard[] newArray(int size) {
            return new VideoCard[size];
        }
    };

    public String getVideo_title() {
        return video_title;
    }

    public String getVideo_views() {
        return video_views;
    }

    public String getUrl() {
        return video_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(video_title);
        parcel.writeString(video_views);
        parcel.writeString(video_url);
    }
}
