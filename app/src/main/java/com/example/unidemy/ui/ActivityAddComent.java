package com.example.unidemy.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

public class ActivityAddComent extends AppCompatActivity {

    EditText edit_content, edit_nota;
    RatingBar ratingBar_curso;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_comment);
        edit_content = findViewById(R.id.editTextTextMultiLine);
        edit_nota = findViewById(R.id.editTextNumberDecimal);
        ratingBar_curso = findViewById(R.id.ratingbar_curso);

    }
}
