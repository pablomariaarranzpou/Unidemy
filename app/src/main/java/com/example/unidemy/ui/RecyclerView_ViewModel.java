package com.example.unidemy.ui;


        import android.app.Application;

        import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;



public class RecyclerView_ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<CursoCard>> mCursoCards;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModel";

    //Constructor
    public RecyclerView_ViewModel(Application application){
        super(application);

        mCursoCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCollection();
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

    //communicates user inputs and updates the result in the viewModel
    @Override
    public void setCollection(ArrayList<CursoCard> ac) {
        mCursoCards.setValue(ac);
    }



    public void setToast(String t) {
        mToast.setValue(t);
    }
}