package com.example.unidemy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class FirstLogin extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btn_startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        btn_startTest.setOnClickListener(v -> startActivity(new Intent(FirstLogin.this, StartTestUniversidad.class )));
    }

}