package com.example.unidemy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

import model.GradoCard;

public class GradosAdapter extends RecyclerView.Adapter<GradosViewHolder>{


    private final ArrayList<GradoCard> localDataSet;
    private final Context parentContext;
    GradosAdapter.OnGradoListener onGradoListener;


    public GradosAdapter(ArrayList<GradoCard> localDataSet, Context parentContext, GradosAdapter.OnGradoListener onGradoListener) {
        this.localDataSet = localDataSet;
        this.parentContext = parentContext;
        this.onGradoListener = onGradoListener;
    }

    @NonNull
    @Override
    public GradosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grado_item, parent, false);

        return new GradosViewHolder(view, onGradoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GradosViewHolder holder, int position) {
        holder.getNombreGrado().setText(this.localDataSet.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        if (localDataSet!= null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnGradoListener {
        void OnGradoClick(int position);
    }
}
