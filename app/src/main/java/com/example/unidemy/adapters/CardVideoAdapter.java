package com.example.unidemy.adapters;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;
import java.util.HashMap;

import model.VideoCard;

public class CardVideoAdapter extends RecyclerView.Adapter<CardVideoHolder>{

    private final ArrayList<VideoCard> localDataSet;
    private final Context parentContext;
    private CardVideoAdapter.OnVideoListener onVideoListener;


    public CardVideoAdapter(Context current, ArrayList<VideoCard> dataSet, CardVideoAdapter.OnVideoListener onVideoListener) {
        parentContext = current;
        localDataSet = dataSet;
        this.onVideoListener = onVideoListener;
    }

    @NonNull
    @Override
    public CardVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new CardVideoHolder(view, onVideoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardVideoHolder holder, int position) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(localDataSet.get(position).getUrl(), new HashMap<String, String>());
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time);
        retriever.release();
        String duration=convertMillieToHMmSs(timeInMillisec);


        holder.getVideo_views().setText(duration);

        holder.getVideo_title().setText(
                localDataSet.get(position).getVideo_title());

        ImageButton playButton = holder.getPlayButton();
    }

    private String convertMillieToHMmSs(long timeInMillisec) {
        long seconds = (timeInMillisec / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
        else {
            return String.format("%02d:%02d" , minute, second);
        }
    }


    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnVideoListener{
        void onVideoClick(int position);
    }
}
