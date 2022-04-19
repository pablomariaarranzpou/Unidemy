package com.example.unidemy.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

/**
 * Provide a reference to the type of views that you are using
 * (custom ViewHolder).
 */
public class CardCourseHolder extends RecyclerView.ViewHolder {

    private final TextView course_title;
    private final TextView course_description;
    private final TextView course_rating;
    private final TextView course_views;
    private final ImageButton playButton;

    public CardCourseHolder(View view) {
        super(view);
        // Define click listener for the ViewHolder's View
        playButton = (ImageButton) view.findViewById(R.id.play_button);
        course_title = (TextView) view.findViewById(R.id.course_title);
        course_description = (TextView) view.findViewById(R.id.course_description);
        course_rating = (TextView)view.findViewById(R.id.course_rating);
        course_views = (TextView)view.findViewById(R.id.course_views);

    }

    public TextView getCourse_title() {
        return course_title;
    }

    public TextView getCourse_description() {
        return course_description;
    }

    public TextView getCourse_rating() {
        return course_rating;
    }

    public TextView getCourse_views() {
        return course_views;
    }

    public ImageButton getPlayButton() {
        return playButton;
    }

}
