package com.example.unidemy.ui;


        import android.app.Application;
        import android.util.Log;

        import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.FirebaseFirestore;


public class RecyclerView_ViewModel extends AndroidViewModel implements DatabaseAdapter.vmInterface{

    private final MutableLiveData<ArrayList<CursoCard>> mCursoCards;
    private final MutableLiveData<String> mToast;
    private final FirebaseAuth mAuth;
    FirebaseFirestore firstore;


    //Constructor
    public RecyclerView_ViewModel(Application application){
        super(application);

        mCursoCards = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
        DatabaseAdapter da = new DatabaseAdapter(this);

        da.getUserGrade(mAuth.getCurrentUser().getUid());
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