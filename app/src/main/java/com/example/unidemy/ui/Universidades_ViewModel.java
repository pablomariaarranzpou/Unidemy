package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Universidades_ViewModel extends AndroidViewModel implements DatabaseAdapter.uniInterface{

    private final MutableLiveData<ArrayList<UniversidadCard>> mUniversidadCards;
    private final MutableLiveData<String> mToast;


    public Universidades_ViewModel(Application application) {
        super(application);
        mUniversidadCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCollectionUniversidades();
    }
    //public getter. Not mutable , read-only
    public LiveData<ArrayList<UniversidadCard>> getUniversidadCard(){
        return mUniversidadCards;
    }

    public UniversidadCard getUniversidadCard(int idx){
        return mUniversidadCards.getValue().get(idx);

    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel


    public void setToast(String t) {
        mToast.setValue(t);
    }

    @Override
    public void setUniversidades(ArrayList<UniversidadCard> ac) {
        this.mUniversidadCards.setValue(ac);

    }
}
