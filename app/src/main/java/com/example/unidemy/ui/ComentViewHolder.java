package com.example.unidemy.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class ComentViewHolder extends RecyclerView.ViewHolder{

    TextView coment;

    public ComentViewHolder(@NonNull View itemView) {
        super(itemView);
        coment=itemView.findViewById(R.id.ind_coment);
    }
}