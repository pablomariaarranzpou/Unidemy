package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyCourses extends AppCompatActivity {
    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
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
        mActivity = this;
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firstore.collection("Users").document(userID);

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
    }
}
