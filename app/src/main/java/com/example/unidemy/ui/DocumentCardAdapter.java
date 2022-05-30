package com.example.unidemy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

public class DocumentCardAdapter extends RecyclerView.Adapter<CardDocumentHolder>{


    private final ArrayList<DocumentCard> localDataSet;
    private final Context parentContext;
    private DocumentCardAdapter.OnDocumentListener onDocumentListener;

    public DocumentCardAdapter(ArrayList<DocumentCard> localDataSet, Context parentContext, DocumentCardAdapter.OnDocumentListener onDocumentListener) {
        this.localDataSet = localDataSet;
        this.parentContext = parentContext;
        this.onDocumentListener = onDocumentListener;
    }

    @NonNull
    @Override
    public CardDocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_item, parent, false);

        return new CardDocumentHolder(view, onDocumentListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CardDocumentHolder holder, int position) {
        holder.getDocument_title().setText(
                localDataSet.get(position).getDocument_title());

        holder.getDocument_views().setText(
                localDataSet.get(position).getDocument_view());

        ImageButton playButton = holder.getDocuments_btn();
    }

    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

    public interface OnDocumentListener{
        void onDocumentClick(int position);
    }
}
