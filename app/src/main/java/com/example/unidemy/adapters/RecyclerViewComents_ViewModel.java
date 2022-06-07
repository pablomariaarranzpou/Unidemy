package com.example.unidemy.adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import model.ComentCard;

public class RecyclerViewComents_ViewModel extends AndroidViewModel implements DatabaseAdapter.ccInterface{

    private final MutableLiveData<ArrayList<ComentCard>> mComentCards;
    private final MutableLiveData<String> mToast;
    private final Application mAplicattion;

    public RecyclerViewComents_ViewModel(@NonNull Application application, String courseID) {
        super(application);
        mComentCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mAplicattion = application;
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCourseComents(courseID);

    }

    public LiveData<ArrayList<ComentCard>> getComentCards(){
        return mComentCards;
    }

    public ComentCard getComentCard(int idx){
        return mComentCards.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setComentsOnCourse(ArrayList<ComentCard> cc) {
        this.mComentCards.setValue(cc);
    }
}
