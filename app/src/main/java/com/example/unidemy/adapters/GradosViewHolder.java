package com.example.unidemy.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class GradosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    GradosAdapter.OnGradoListener onGradoListener;
    private final TextView nombreGrado;


    public GradosViewHolder(@NonNull View itemView, GradosAdapter.OnGradoListener onGradoListener) {
        super(itemView);
        nombreGrado = itemView.findViewById(R.id.grado_title);
        itemView.setOnClickListener((this));
        this.onGradoListener = onGradoListener;
    }

    public TextView getNombreGrado() {
        return nombreGrado;
    }

    @Override
    public void onClick(View v) {
        onGradoListener.OnGradoClick(getAdapterPosition());
    }
}
