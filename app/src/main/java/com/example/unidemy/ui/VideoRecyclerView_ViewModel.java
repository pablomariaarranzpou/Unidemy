package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class VideoRecyclerView_ViewModel extends AndroidViewModel{

        private final MutableLiveData<ArrayList<VideoCard>> mCursoCards;
        private final MutableLiveData<String> mToast;


        //Constructor
        public VideoRecyclerView_ViewModel(Application application){
            super(application);

            mCursoCards = new MutableLiveData<>();
            mToast = new MutableLiveData<>();

        }

        //public getter. Not mutable , read-only
        public LiveData<ArrayList<VideoCard>> getCursoCards(){
            return mCursoCards;
        }

        public VideoCard getVideoCard(int idx){
            return mCursoCards.getValue().get(idx);
        }

        public LiveData<String> getToast(){
            return mToast;
        }

        //communicates user inputs and updates the result in the viewModel

}
