package com.example.unidemy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

import model.ComentCard;


public class CommentListAdapter extends RecyclerView.Adapter<ComentViewHolder> {

    private final ArrayList<ComentCard> commentList;
    private final Context context;



    public CommentListAdapter(ArrayList<ComentCard> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }




    @NonNull
    @Override
    public ComentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coment_item, parent, false);

        return new ComentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentViewHolder holder, int position) {
        holder.getComent_content().setText(commentList.get(position).getComent_content());
        holder.getComent_date().setText(commentList.get(position).getTimestamp().toDate().toString());
        holder.getComent_name().setText(commentList.get(position).getComent_name());
        holder.getComent_notafinal().setText(commentList.get(position).getComent_notafinalString());
        holder.getComent_rating().setText(commentList.get(position).getComent_ratingString());
    }

    @Override
    public int getItemCount() {
        if (commentList != null) {
            return commentList.size();
        }
        return 0;
    }

}