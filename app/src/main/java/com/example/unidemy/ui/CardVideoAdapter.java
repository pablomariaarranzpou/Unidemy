package com.example.unidemy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

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
        holder.getVideo_views().setText(
                localDataSet.get(position).getVideo_views());

        holder.getVideo_title().setText(
                localDataSet.get(position).getVideo_title());

        ImageButton playButton = holder.getPlayButton();
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
