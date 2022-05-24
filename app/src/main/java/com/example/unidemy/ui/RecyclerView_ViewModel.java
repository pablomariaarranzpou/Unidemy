package com.example.unidemy.ui;


        import android.app.Application;
        import android.util.Log;

        import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;



public class RecyclerView_ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<CursoCard>> mCursoCards;
    private final MutableLiveData<String> mToast;


    //Constructor
    public RecyclerView_ViewModel(Application application){
        super(application);

        mCursoCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        DatabaseAdapter da = new DatabaseAdapter(this);
        da.getCollectionCursos();
    }

    //public getter. Not mutable , read-only
    public LiveData<ArrayList<CursoCard>> getCursoCards(){
        return mCursoCards;
    }

    public CursoCard getCursoCard(int idx){
        Log.d("ADAPT", mCursoCards.getValue().get(idx).getCourse_porta());
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