package com.example.unidemy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

import model.FacultadCard;

public class FacultadesAdapter extends RecyclerView.Adapter<FacultadesViewHolder>{


    private final ArrayList<FacultadCard> localDataSet;
    private final Context parentContext;
    FacultadesAdapter.OnFacultadListener onFacultadListener;


    public FacultadesAdapter(ArrayList<FacultadCard> localDataSet, Context parentContext, OnFacultadListener onFacultadListener) {
        this.localDataSet = localDataSet;
        this.parentContext = parentContext;
        this.onFacultadListener = onFacultadListener;
    }

    @NonNull
    @Override
    public FacultadesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facultad_item, parent, false);

        return new FacultadesViewHolder(view,onFacultadListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultadesViewHolder holder, int position) {
        holder.getNombreFacultad().setText(this.localDataSet.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        if (localDataSet!= null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnFacultadListener {
        void onFacultadClick(int position);
    }
}
