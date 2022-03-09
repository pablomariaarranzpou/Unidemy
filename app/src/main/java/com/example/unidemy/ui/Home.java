package com.example.unidemy.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Home extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView bienvenida_asignaturas_txt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bienvenida_asignaturas_txt =  findViewById(R.id.bienvenida_asignaturas_txt);
        bienvenida_asignaturas_txt.setText(
                "¡Aquí tienes tus asignaturas "+ Objects.requireNonNull(mAuth.getCurrentUser())
                        .getDisplayName() +"!");

    }

}