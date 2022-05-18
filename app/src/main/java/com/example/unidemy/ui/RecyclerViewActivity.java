package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity implements CardCourseAdapter.OnCourseListener{

    private final String TAG = "MainActivity";


    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;
    private FirebaseAuth mAuth;


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if(SaveSharedPreference.getUserName(RecyclerViewActivity.this).length() == 0 || this.mAuth.getCurrentUser().getUid().length() == 0)
        {
            startActivity(new Intent(RecyclerViewActivity.this, Login.class));
            finish();
        }
        mActivity = this;
        setContentView(R.layout.activity_view_courses_list);
        parentContext = this.getBaseContext();
        mActivity = this;

        navigationView = findViewById(R.id.btm_navigator);
        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.recyclerview_cursos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        BottomNavigationView navView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setSelectedItemId(R.id.navigation_recyclerview);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_searcher:
                        startActivity(new Intent(getApplicationContext(), Searcher.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_mycourses:
                        startActivity(new Intent(getApplicationContext(), MyCourses.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_recyclerview:
                        return true;

                }


                return false;
            }
        });

        setLiveDataObservers();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(RecyclerViewActivity.this, MenuLateral.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
