package com.example.unidemy.ui;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

import static com.google.android.gms.common.util.CollectionUtils.setOf;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.unidemy.R;
import com.example.unidemy.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = (BottomNavigationView)findViewById(R.id.btm_navigator);
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
                        startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });



    }
}