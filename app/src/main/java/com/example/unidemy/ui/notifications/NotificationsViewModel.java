package com.example.unidemy.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí aparecerán las novedades de tus asignaturas actuales");
    }

    public LiveData<String> getText() {
        return mText;
    }
}