package com.example.unidemy.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyCoursesAdapter extends RecyclerView.Adapter<CardCourseHolder>{
    private final ArrayList<CursoCard> localDataSet;
    private ArrayList<String> userPropertyID;
    private Context parentContext;
    private OnCourseListener onCourseListener;
    private final FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    String userId;
    int size;






    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public MyCoursesAdapter(Context current, ArrayList<CursoCard> dataSet, OnCourseListener onCourseListener ) {
        parentContext = current;
        size = 0;
        localDataSet = dataSet;
        this.onCourseListener = onCourseListener;
        this.firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();;
   }

    // Create new views (invoked by the layout manager)
    @Override
    public CardCourseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.course_item, viewGroup, false);

        return new CardCourseHolder(view, (MyCoursesAdapter.OnCourseListener) onCourseListener);
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

    @Override
    public int getItemCount() {

            if (localDataSet != null) {
                return localDataSet.size();
            }
            return 0;

    }


    public interface OnCourseListener extends CardCourseAdapter.OnCourseListener {
        void onCourseClick(int position);
    }



}

