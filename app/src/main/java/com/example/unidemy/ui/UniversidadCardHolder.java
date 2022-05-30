package com.example.unidemy.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class UniversidadCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    UniversidadCardAdapter.OnUniversidadListener onUListener;
    private final TextView nombreUniversidad;


    public UniversidadCardHolder(@NonNull View itemView, UniversidadCardAdapter.OnUniversidadListener onUListener) {
        super(itemView);
        nombreUniversidad=itemView.findViewById(R.id.universidad_title);
        this.onUListener = onUListener;
        itemView.setOnClickListener((this));
    }

    public TextView getNombreUniversidad() {
        return nombreUniversidad;
    }

    @Override
    public void onClick(View view) {
        onUListener.onUniversidadClick(getAdapterPosition());
    }
}
