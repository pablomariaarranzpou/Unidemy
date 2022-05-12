package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyCourses extends AppCompatActivity implements MyCoursesAdapter.OnCourseListener{
    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private MyCoursesViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;
    private FirebaseFirestore firstore;
    private String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mycourses);
        mAuth = FirebaseAuth.getInstance();
        parentContext = this.getBaseContext();
        firstore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.recyclerview_mycourses);
        mActivity = this;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        BottomNavigationView navView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setSelectedItemId(R.id.navigation_mycourses);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_searcher:
                        startActivity(new Intent(getApplicationContext(), Searcher.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_mycourses:
                        return true;
                    case R.id.navigation_recyclerview:
                        startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });

        setLiveDataObservers();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(MyCoursesViewModel.class);
        MyCoursesAdapter newAdapter = new MyCoursesAdapter(parentContext, new ArrayList<CursoCard>(), (MyCoursesAdapter.OnCourseListener) mActivity);
        final Observer<ArrayList<CursoCard>> observer = new Observer<ArrayList<CursoCard>>() {
            @Override
            public void onChanged(ArrayList<CursoCard> ac) {
                MyCoursesAdapter newAdapter = new MyCoursesAdapter(parentContext, ac, (MyCoursesAdapter.OnCourseListener) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                //Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
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

