package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ActivityAddComent extends AppCompatActivity {

    private static final String TAG = "add comment";
    private EditText edit_content, edit_nota;
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    private RatingBar ratingBar_curso;
    private Button btn_add_comment;
    private Context parentContext;
    private FirebaseAuth mAuth;
    private Timestamp time_comment;
    FirebaseFirestore firestore;
    String course_id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_add_comment);
        edit_content = findViewById(R.id.editTextTextMultiLine);
        edit_nota = findViewById(R.id.editTextNumberDecimal);
        ratingBar_curso = findViewById(R.id.ratingbar_curso);
        btn_add_comment = findViewById(R.id.button_add_final_comment);
        firestore = FirebaseFirestore.getInstance();

        if(getIntent().hasExtra("course")){
            course_id = (String) getIntent().getStringExtra("course");
        }

        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_content.getText().toString().trim();
                String nota = edit_nota.getText().toString().trim();
                Float qual = ratingBar_curso.getRating();

                if (qual.toString().length() == 0) {
                    edit_content.setError("Escribe algo en experiencia!");
                    edit_content.requestFocus();

                }
                if (nota.length() == 0 || !isNumeric(nota)) {
                    edit_nota.setError("El formato de la nota es incorrecto");
                    edit_nota.requestFocus();

                }
                if (String.valueOf(ratingBar_curso.getRating()).length() == 0) {

                } else {
                    String comentID;
                    Float notaf = Float.parseFloat(nota);
                    Timestamp time = Timestamp.now();
                    Map<String, Object> data = new HashMap<>();
                    data.put("timestamp", time);
                    data.put("coment_content", content);
                    data.put("coment_rating", qual);
                    data.put("coment_nota", notaf);
                    data.put("coment_name", mAuth.getCurrentUser().getDisplayName());

                    firestore.collection("Coment")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    documentReference.getId();
                                    DocumentReference documentReference2 = firestore.collection("Curso").document(course_id);
                                    documentReference2.update("course_coments", FieldValue.arrayUnion(documentReference.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            finish();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });



                }
            }

            public boolean isNumeric(String strNum) {
                if (strNum == null) {
                    return false;
                }
                return pattern.matcher(strNum).matches();
            }


        });
    }}
