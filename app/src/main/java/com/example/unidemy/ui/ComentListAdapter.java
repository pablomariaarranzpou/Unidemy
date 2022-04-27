package com.example.unidemy.ui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.unidemy.R;

import java.util.List;

public class ComentListAdapter extends RecyclerView.Adapter<ComentViewHolder> {

    List<String> comentList;
    Context context;

    public ComentListAdapter(List<String> comentList, Context context) {
        this.comentList = comentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ComentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coment_item, parent, false);
        ComentViewHolder holder = new ComentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComentViewHolder holder, int position) {
        holder.coment.setText(comentList.get(position));
    }

    @Override
    public int getItemCount() {
        return comentList.size();
    }

}