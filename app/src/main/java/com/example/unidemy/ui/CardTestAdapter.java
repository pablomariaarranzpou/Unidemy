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

public class CardTestAdapter extends RecyclerView.Adapter<CardTestHolder>{


    private final ArrayList<CardTest> localDataSet;
    private final Context parentContext;
    private CardTestAdapter.OnTestListener onTestListener;


    public CardTestAdapter(ArrayList<CardTest> localDataSet, Context parentContext, CardTestAdapter.OnTestListener onTestListener) {
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
