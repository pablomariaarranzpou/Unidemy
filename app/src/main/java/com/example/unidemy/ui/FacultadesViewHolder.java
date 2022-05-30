package com.example.unidemy.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class FacultadesViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{


    FacultadesAdapter.OnFacultadListener onFacultadListener;
    private final TextView nombreFacultad;

    public FacultadesViewHolder(@NonNull View itemView, FacultadesAdapter.OnFacultadListener onFacultadListener) {
        super(itemView);
        nombreFacultad=itemView.findViewById(R.id.facultad_title);
        itemView.setOnClickListener((this));
        this.onFacultadListener = onFacultadListener;
    }


    public TextView getNombreFacultad() {
        return nombreFacultad;
    }


    @Override
    public void onClick(View v) {
        onFacultadListener.onFacultadClick(getAdapterPosition());
    }
}
