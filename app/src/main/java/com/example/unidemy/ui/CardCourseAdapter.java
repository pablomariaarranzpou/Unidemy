package com.example.unidemy.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

public class CardCourseAdapter extends RecyclerView.Adapter<CardCourseHolder> {

    private final ArrayList<CursoCard> localDataSet;
    private final Context parentContext;
    private OnCourseListener onCourseListener;


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CardCourseAdapter(Context current, ArrayList<CursoCard> dataSet, OnCourseListener onCourseListener ) {
        parentContext = current;
        localDataSet = dataSet;
        this.onCourseListener = onCourseListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardCourseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_item, viewGroup, false);

        return new CardCourseHolder(view, onCourseListener);
    }





    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardCourseHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //position = viewHolder.getAdapterPosition();
        viewHolder.getCourse_description().setText(
                localDataSet.get(position).getCourse_description());

        viewHolder.getCourse_rating().setText(
                localDataSet.get(position).getCourse_rating());

        viewHolder.getCourse_title().setText(
                localDataSet.get(position).getCourse_title());

        viewHolder.getCourse_views().setText(
                localDataSet.get(position).getCourse_views());



        ImageButton playButton = viewHolder.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnCourseListener{
        void onCourseClick(int position);
    }

}
