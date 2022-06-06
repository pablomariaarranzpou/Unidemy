package com.example.unidemy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
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
                        Intent i = new Intent(MenuLateral.this, UserInfoActivity.class);
                        overridePendingTransition(0,0);
                        startActivity(i);
                        return true;
                    case R.id.help_section:
                        Intent intere = new Intent(MenuLateral.this, ActivityHelp.class);
                        overridePendingTransition(0,0);
                        startActivity(intere);
                        return true;
                    case R.id.politics:
                        Intent inter = new Intent(MenuLateral.this, Privacity.class);
                        overridePendingTransition(0,0);
                        startActivity(inter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inen = new Intent(MenuLateral.this,RecyclerViewActivity.class);
        startActivity(inen);
        finish();
    }
}
