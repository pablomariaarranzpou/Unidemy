package com.example.unidemy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.example.unidemy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MenuLateral extends AppCompatActivity {


    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral_activity);
        navView = findViewById(R.id.menu_grande);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logout:
                        logout();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.changeInfo:
                    case R.id.help_section:
                    case R.id.politics:
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });
    }
    public void logout(){
            SaveSharedPreference.clearUserName(MenuLateral.this);
            Intent i = new Intent(MenuLateral.this, Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
    }


}
