package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Searcher extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchView txtBuscar;

        setContentView(R.layout.activity_view_courses_list);
        parentContext = this.getBaseContext();
        mActivity = this;
        txtBuscar = findViewById(R.id.txtBuscar);
        navigationView = findViewById(R.id.btm_navigator);
        txtBuscar.setOnQueryTextListener(this);
        setContentView(R.layout.fragment_searcher);
        BottomNavigationView navView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setSelectedItemId(R.id.navigation_searcher);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_searcher:
                        return true;
                    case R.id.navigation_mycourses:
                        startActivity(new Intent(getApplicationContext(), MyCourses.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_recyclerview:
                        startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
