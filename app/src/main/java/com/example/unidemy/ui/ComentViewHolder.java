package com.example.unidemy.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class ComentViewHolder extends RecyclerView.ViewHolder{

    TextView coment_content, coment_name, coment_notafinal, coment_rating, coment_date;

    public ComentViewHolder(@NonNull View itemView) {
        super(itemView);
        coment_content =itemView.findViewById(R.id.comment_content);
        coment_name = itemView.findViewById(R.id.coment_name);
        coment_notafinal = itemView.findViewById(R.id.coment_nota);
        coment_rating = itemView.findViewById(R.id.coment_rating);
        coment_date = itemView.findViewById(R.id.coment_date);
    }

    public TextView getComent_content() {
        return coment_content;
    }

    public TextView getComent_name() {
        return coment_name;
    }

    public TextView getComent_notafinal() {
        return coment_notafinal;
    }

    public TextView getComent_rating() {
        return coment_rating;
    }

    public TextView getComent_date() {
        return coment_date;
    }
}