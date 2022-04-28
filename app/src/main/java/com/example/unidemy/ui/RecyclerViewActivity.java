package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements CardCourseAdapter.OnCourseListener{

    private final String TAG = "MainActivity";


    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses_list);
        parentContext = this.getBaseContext();
        mActivity = this;
        navigationView = findViewById(R.id.btm_navigator);
        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.recyclerview_cursos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        setLiveDataObservers();


    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(RecyclerView_ViewModel.class);
        CardCourseAdapter newAdapter = new CardCourseAdapter(parentContext, new ArrayList<CursoCard>(), (CardCourseAdapter.OnCourseListener) mActivity);
        final Observer<ArrayList<CursoCard>> observer = new Observer<ArrayList<CursoCard>>() {
            @Override
            public void onChanged(ArrayList<CursoCard> ac) {
                CardCourseAdapter newAdapter = new CardCourseAdapter(parentContext, ac, (CardCourseAdapter.OnCourseListener) mActivity);
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

        viewModel.getCursoCards().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);

    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, ViewCourse.class);
        intent.putExtra("selectedCourse", viewModel.getCursoCard(position));
        startActivity(intent);

    }
}
