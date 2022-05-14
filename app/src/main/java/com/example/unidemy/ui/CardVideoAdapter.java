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

public class CardVideoAdapter extends RecyclerView.Adapter<CardVideoHolder>{

    private final ArrayList<VideoCard> localDataSet;
    private final Context parentContext;


    public CardVideoAdapter(Context current, ArrayList<VideoCard> dataSet) {
        parentContext = current;
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CardVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new CardVideoHolder(view);
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
        void onCourseClick(int position);
    }
}
