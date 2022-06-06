package com.example.unidemy.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import model.FacultadCard;

public class FacultadesViewModel extends AndroidViewModel implements DatabaseAdapter.facInterface{


    private final MutableLiveData<ArrayList<FacultadCard>> mFacultadCards;
    private final MutableLiveData<String> mToast;


    public FacultadesViewModel(@NonNull Application application) {
        super(application);
        mFacultadCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getUserFaculty();
    }
    public LiveData<ArrayList<FacultadCard>> getFacultadCards(){
        return mFacultadCards;
    }

    public FacultadCard getFacultadCard(int idx){
        return mFacultadCards.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }


    @Override
    public void setFacultades(ArrayList<FacultadCard> fc) {
        this.mFacultadCards.setValue(fc);
    }
}
