package com.example.unidemy.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MyCoursesViewModel extends AndroidViewModel implements DatabaseAdapter.usInterface {

    private final MutableLiveData<ArrayList<CursoCard>> mCursoCards;
    private final MutableLiveData<String> mToast;
    private ArrayList<String> alstring;
    private FirebaseAuth mauth;


    //Constructor
    public MyCoursesViewModel(Application application){
        super(application);
        mCursoCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mauth = FirebaseAuth.getInstance();
        DatabaseAdapter da = new DatabaseAdapter(this);

        da.getUserCourses(mauth.getCurrentUser().getUid());


    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<CursoCard>> getCursoCards(){
        return mCursoCards;
    }

    public CursoCard getCursoCard(int idx){
        return mCursoCards.getValue().get(idx);
    }

    public LiveData<String> getToast(){
        return mToast;
    }

    @Override
    public void setUserCourses(ArrayList<CursoCard> cc) {
        this.mCursoCards.setValue(cc);
    }
}
