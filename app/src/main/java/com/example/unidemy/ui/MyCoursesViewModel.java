package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

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
        alstring = new ArrayList<String>();
        da.getUserCourses(mauth.getCurrentUser().getUid());
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<CursoCard>> getCursoCards(){
        return mCursoCards;
    }

    public CursoCard getCursoCard(int idx){
        if(alstring.contains(mCursoCards.getValue().get(idx)) ){
            return mCursoCards.getValue().get(idx);
    }
        return null;
    }

    public LiveData<String> getToast(){
        return mToast;
    }




    @Override
    public void getUserCourses(ArrayList<String> ac) {
        alstring = ac;
    }
}
