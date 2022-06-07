package com.example.unidemy.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

public class CardTestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView test_title, test_views;
    private ImageButton test_btn;
    CardTestAdapter.OnTestListener onTestListener;

    public CardTestHolder(@NonNull View itemView, CardTestAdapter.OnTestListener onTestListener) {
        super(itemView);
        test_title = (TextView) itemView.findViewById(R.id.test_title);
        test_views = (TextView) itemView.findViewById(R.id.test_views);
        test_btn = (ImageButton)  itemView.findViewById(R.id.test_start_btn);
        itemView.setOnClickListener((this));
        this.onTestListener = onTestListener;
    }

    public TextView getTest_title() {
        return test_title;
    }

    public TextView getTest_views() {
        return test_views;
    }

    public ImageButton getTest_btn() {
        return test_btn;
    }

    @Override
    public void onClick(View v) {
        onTestListener.onTestClickClick(getAdapterPosition());
    }
}
