package com.example.unidemy.adapters;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import model.CardTest;

public class CardTestAdapter extends RecyclerView.Adapter<CardTestHolder>{


    private final ArrayList<CardTest> localDataSet;
    private final Context parentContext;
    private CardTestAdapter.OnTestListener onTestListener;
    private FirebaseFirestore firestore;


    public CardTestAdapter(ArrayList<CardTest> localDataSet, Context parentContext, CardTestAdapter.OnTestListener onTestListener) {
        firestore = FirebaseFirestore.getInstance();
        this.localDataSet = localDataSet;
        this.parentContext = parentContext;
        this.onTestListener = onTestListener;
    }

    @NonNull
    @Override
    public CardTestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);

        return new CardTestHolder(view, onTestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTestHolder holder, int position) {
        holder.getTest_title().setText(
                localDataSet.get(position).getTest_title());
        Task<QuerySnapshot> documentReference = firestore.collection("Test").document(localDataSet.get(position).getTestID())
                .collection("Question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> retrieved_ac = new ArrayList<String>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Preguntas", document.getId() + " => " + document.getData());
                                retrieved_ac.add("a");
                            }

                         holder.getTest_views().setText(retrieved_ac.size()+" preguntas");

                        } else {
                            Log.d("Preguntas", "Error getting documents: ", task.getException());
                        }
                    }
                });
        holder.getTest_views().setText(
                localDataSet.get(position).getTest_views());

        ImageButton playButton = holder.getTest_btn();
    }

    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnTestListener {
        void onTestClickClick(int position);
    }
}
