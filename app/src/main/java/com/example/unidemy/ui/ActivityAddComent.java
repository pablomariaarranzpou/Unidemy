package com.example.unidemy.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ActivityAddComent extends AppCompatActivity {

    private EditText edit_content, edit_nota;
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private RatingBar ratingBar_curso;
    private Button btn_add_comment;
    private Context parentContext;
    private FirebaseAuth mAuth;
    private Timestamp time_comment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_add_comment);
        edit_content = findViewById(R.id.editTextTextMultiLine);
        edit_nota = findViewById(R.id.editTextNumberDecimal);
        ratingBar_curso = findViewById(R.id.ratingbar_curso);
        btn_add_comment = findViewById(R.id.button_add_final_comment);


        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_content.getText().toString().trim();
                String nota = edit_nota.getText().toString().trim();
                Float qual = ratingBar_curso.getRating();

                if(qual.toString().length() == 0){
                    edit_content.setError("Escribe algo en experiencia!");
                    edit_content.requestFocus();

                }if (nota.length() == 0 || !isNumeric(nota)){
                    edit_nota.setError("El formato de la nota es incorrecto");
                    edit_nota.requestFocus();

                }if(String.valueOf(ratingBar_curso.getRating()).length() == 0){

                }else{


                }
            }
        });
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }




}
