package com.example.unidemy.adapters;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DocumentRecyclerView_ViewModelFactory implements ViewModelProvider.Factory{
    private Application mApplication;
    private String mParam;


    public DocumentRecyclerView_ViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DocumentRecyclerView_ViewModel(mApplication, mParam);
    }
}
