package com.example.unidemy.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardCourseAdapter extends RecyclerView.Adapter<CardCourseHolder> {

    private final ArrayList<CursoCard> localDataSet;
    private final Context parentContext;
    private OnCourseListener onCourseListener;
    private ArrayList<CursoCard> filtrado;


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CardCourseAdapter(Context current, ArrayList<CursoCard> dataSet, OnCourseListener onCourseListener ) {
        parentContext = current;
        localDataSet = dataSet;
        filtrado = dataSet;
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




    public void filtrado(@NonNull final String txtBuscar) {

        int longitud = txtBuscar.length();
        if (longitud == 0) {
            filtrado.clear();
            filtrado.addAll(localDataSet);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<CursoCard> collecion = filtrado.stream()
                        .filter(i -> i.getCourse_title().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                filtrado.clear();
                filtrado.addAll(collecion);
            } else {
                for (CursoCard c : localDataSet) {
                    if (c.getCourse_title().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        filtrado.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
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
