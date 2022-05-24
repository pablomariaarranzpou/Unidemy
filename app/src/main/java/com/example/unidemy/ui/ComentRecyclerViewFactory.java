package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ComentRecyclerViewFactory implements ViewModelProvider.Factory{


    private Application mApplication;
    private String mParam;


    public ComentRecyclerViewFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecyclerViewComents_ViewModel(mApplication, mParam);
    }

}