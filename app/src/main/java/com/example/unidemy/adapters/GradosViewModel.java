package com.example.unidemy.adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import model.GradoCard;

public class GradosViewModel extends AndroidViewModel implements DatabaseAdapter.ugInterface{


    private final MutableLiveData<ArrayList<GradoCard>> mGradoCards;
    private final MutableLiveData<String> mToast;
    private final FirebaseAuth mAuth;


    public GradosViewModel(@NonNull Application application) {
        super(application);
        mGradoCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        DatabaseAdapter da = new DatabaseAdapter(this);

        da.getUserGrades_Test();
    }

    public LiveData<ArrayList<GradoCard>> getGradoCards(){
        return mGradoCards;
    }

    public GradoCard getGradoCard(int idx){
        return mGradoCards.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setGradoCards(ArrayList<GradoCard> gc) {
        this.mGradoCards.setValue(gc);
    }
}
