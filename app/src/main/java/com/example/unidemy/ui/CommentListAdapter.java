package com.example.unidemy.ui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.unidemy.R;

import java.util.List;



public class CommentListAdapter extends RecyclerView.Adapter<ComentViewHolder> {

    public CommentListAdapter(List<String> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }


    List<String> commentList;
    Context context;

    @NonNull
    @Override
    public ComentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coment_item,parent,false);
        ComentViewHolder holder = new ComentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentViewHolder holder, int position) {
        holder.coment_content.setText(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}