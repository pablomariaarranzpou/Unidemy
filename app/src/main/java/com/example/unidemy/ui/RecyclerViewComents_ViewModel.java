package com.example.unidemy.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class RecyclerViewComents_ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<ComentCard>> mComentCards;
    private final MutableLiveData<String> mToast;

    public RecyclerViewComents_ViewModel(@NonNull Application application) {
        super(application);
        mComentCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
    }

    @Override
    public void setCollection(ArrayList<CursoCard> ac) {

    }

    @Override
    public void setToast(String s) {

    }
}
