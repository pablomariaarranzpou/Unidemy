package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CardTestViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private String mParam;


    public CardTestViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CardTestViewModel(mApplication, mParam);
    }
}
