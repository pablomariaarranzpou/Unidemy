package com.example.unidemy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.firebase.auth.FirebaseAuth;


public class FirstLogin extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btn_startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);
        btn_startTest = findViewById(R.id.btn_startTest);
        btn_startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(FirstLogin.this, StartTestUniversidad.class ));
                finish();
            }
        });

    }

}