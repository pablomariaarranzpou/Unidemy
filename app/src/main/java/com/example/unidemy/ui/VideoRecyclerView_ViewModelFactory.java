package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VideoRecyclerView_ViewModelFactory implements ViewModelProvider.Factory{


    private Application mApplication;
    private String mParam;


    public VideoRecyclerView_ViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new VideoRecyclerView_ViewModel(mApplication, mParam);
    }

}
