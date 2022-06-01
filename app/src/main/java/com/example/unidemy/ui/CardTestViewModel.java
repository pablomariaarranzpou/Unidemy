package com.example.unidemy.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class CardTestViewModel extends AndroidViewModel implements DatabaseAdapter.tsInterface {

    private final MutableLiveData<ArrayList<CardTest>> mCardTests;
    private final MutableLiveData<String> mToast;
    private String courseId;
    private final Application mAplicattion;

    public CardTestViewModel(@NonNull Application application, String courseId) {
        super(application);
        mAplicattion = application;
        mCardTests = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCourseTests(courseId);
    }

    public LiveData<ArrayList<CardTest>> getTestCards(){
        return mCardTests;
    }

    public CardTest getTestCard(int idx){
        return mCardTests.getValue().get(idx);
    }


    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setTests(ArrayList<CardTest> tc) {
        this.mCardTests.setValue(tc);
    }
}
