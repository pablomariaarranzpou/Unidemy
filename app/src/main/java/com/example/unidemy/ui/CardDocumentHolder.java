package com.example.unidemy.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class CardDocumentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView document_title;
    private final TextView document_views;
    private final ImageButton documents_btn;
    DocumentCardAdapter.OnDocumentListener onDocumentListener;

    public CardDocumentHolder(@NonNull View itemView, DocumentCardAdapter.OnDocumentListener onVideoListener) {
        super(itemView);
        document_title = (TextView) itemView.findViewById(R.id.document_title);
        document_views = (TextView) itemView.findViewById(R.id.document_views);
        documents_btn = (ImageButton)  itemView.findViewById(R.id.play_button_document);
        itemView.setOnClickListener((this));
        this.onDocumentListener = onVideoListener;
    }

    public TextView getDocument_title() {
        return document_title;
    }

    public TextView getDocument_views() {
        return document_views;
    }

    public ImageButton getDocuments_btn() {
        return documents_btn;
    }

    @Override
    public void onClick(View view) {
        onDocumentListener.onDocumentClick(getAdapterPosition());
    }
}
