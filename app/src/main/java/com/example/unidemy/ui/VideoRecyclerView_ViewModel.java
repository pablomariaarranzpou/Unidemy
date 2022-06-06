package com.example.unidemy.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;

import model.VideoCard;

public class VideoRecyclerView_ViewModel extends AndroidViewModel implements DatabaseAdapter.vrInterface{

        private final MutableLiveData<ArrayList<VideoCard>> mVideoCards;
        private final MutableLiveData<String> mToast;
        private String courseId;
        private final Application mAplicattion;

        //Constructor
        public VideoRecyclerView_ViewModel(Application application, String courseId){
            super(application);
            mAplicattion = application;
            mVideoCards = new MutableLiveData<>();
            mToast = new MutableLiveData<>();
            DatabaseAdapter da = new DatabaseAdapter(this);
            da.getCourseVideos(courseId);
        }

        //public getter. Not mutable , read-only
        public LiveData<ArrayList<VideoCard>> getVideoCards(){
            return mVideoCards;
        }

        public VideoCard getVideoCard(int idx){
            return mVideoCards.getValue().get(idx);
        }


        public LiveData<String> getToast(){
            return mToast;
        }

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setVideoonCourse(ArrayList<VideoCard> cc) {
        this.mVideoCards.setValue(cc);
    }


}
