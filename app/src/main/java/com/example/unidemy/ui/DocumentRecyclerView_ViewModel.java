package com.example.unidemy.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import model.DocumentCard;

public class DocumentRecyclerView_ViewModel extends AndroidViewModel implements DatabaseAdapter.dcInterface {

    private final MutableLiveData<ArrayList<DocumentCard>> mDocumentCards;
    private final MutableLiveData<String> mToast;
    private String courseId;
    private final Application mAplicattion;


    public DocumentRecyclerView_ViewModel(@NonNull Application application, String courseId) {
        super(application);
        mAplicattion = application;
        mDocumentCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCourseDocuments(courseId);
    }
    //public getter. Not mutable , read-only
    public LiveData<ArrayList<DocumentCard>> getDocumentCards(){
        return mDocumentCards;
    }

    public DocumentCard getDocumentCard(int idx){
        return mDocumentCards.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    //communicates user inputs and updates the result in the viewModel

    @Override
    public void setDocumentsOnCourse(ArrayList<DocumentCard> dc) {
        this.mDocumentCards.setValue(dc);
    }
}
