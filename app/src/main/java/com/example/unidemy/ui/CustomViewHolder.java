package com.example.unidemy.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Provide a reference to the type of views that you are using
 * (custom ViewHolder).
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {


    private final ImageButton playButton;

    public CustomViewHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        playButton = (ImageButton) view.findViewById(R.id.);
    }



    public ImageButton getPlayButton() {
        return playButton;
    }

}

