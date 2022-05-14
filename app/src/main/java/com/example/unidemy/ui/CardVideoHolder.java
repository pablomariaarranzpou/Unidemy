package com.example.unidemy.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class CardVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView video_title;
    private final TextView video_views;
    private final ImageButton video_playButton;
    CardVideoAdapter.OnVideoListener onVideoListener;

    public CardVideoHolder(@NonNull View itemView, CardVideoAdapter.OnVideoListener onVideoListener) {
        super(itemView);
        video_title = (TextView) itemView.findViewById(R.id.video_title);
        video_views = (TextView) itemView.findViewById(R.id.video_title);
        video_playButton = (ImageButton)  itemView.findViewById(R.id.play_button_video);
        itemView.setOnClickListener((this));

    }
    public TextView getVideo_title() {
        return video_title;
    }

    public TextView getVideo_views() {
        return video_views;
    }

    public ImageButton getPlayButton() {
        return video_playButton;
    }

    @Override
    public void onClick(View view) {
        onVideoListener.onVideoClick(getAdapterPosition());
    }
}
