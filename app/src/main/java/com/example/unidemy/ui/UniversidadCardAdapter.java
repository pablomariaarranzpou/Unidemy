package com.example.unidemy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

import model.UniversidadCard;

public class UniversidadCardAdapter extends RecyclerView.Adapter<UniversidadCardHolder> {
    private final ArrayList<UniversidadCard> localDataSet;
    private final Context parentContext;
    UniversidadCardAdapter.OnUniversidadListener onUListener;

    public UniversidadCardAdapter(ArrayList<UniversidadCard> localDataSet, Context parentContext, UniversidadCardAdapter.OnUniversidadListener onUListener) {
        this.localDataSet = localDataSet;
        this.parentContext = parentContext;
        this.onUListener = onUListener;
    }

    @NonNull
    @Override
    public UniversidadCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.universidad_item, parent, false);

        return new UniversidadCardHolder(view,onUListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversidadCardHolder holder, int position) {
    holder.getNombreUniversidad().setText(this.localDataSet.get(position).getNombre());
    }


    @Override
    public int getItemCount() {
        if (localDataSet!= null) {
            return localDataSet.size();
        }
        return 0;
    }
    public interface OnUniversidadListener {
        void onUniversidadClick(int position);
    }

}
