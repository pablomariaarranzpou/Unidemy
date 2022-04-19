package com.example.unidemy.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses_list);
        parentContext = this.getBaseContext();
        mActivity = this;



        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        setLiveDataObservers();


    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(RecyclerView_ViewModel.class);

        final Observer<ArrayList<CursoCard>> observer = new Observer<ArrayList<CursoCard>>() {
            @Override
            public void onChanged(ArrayList<CursoCard> ac) {
                CardCourseAdapter newAdapter = new CardCourseAdapter(parentContext, ac);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };

    }






}
