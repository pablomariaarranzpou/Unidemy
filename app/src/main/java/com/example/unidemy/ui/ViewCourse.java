package com.example.unidemy.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.firebase.auth.FirebaseAuth;

public class ViewCourse extends AppCompatActivity {


    private TextView ind_course_views_txt, ind_course_title_txt, ind_owner_txt, ind_course_rating_txt, ind_course_description;
    private ImageButton play_button;
    private Button ind_btn_pagar;
    FirebaseAuth mAuth;
    CursoCard cc;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_view_course);
        ind_course_views_txt=findViewById(R.id.course_description);
        ind_course_title_txt = findViewById(R.id.course_title);
        ind_owner_txt = findViewById(R.id.ind_owner);
        ind_course_rating_txt = findViewById(R.id.ind_course_rating);
        ind_course_description = findViewById(R.id.ind_course_description);
        ind_btn_pagar = findViewById(R.id.ind_btn_pagar);
        cc = (CursoCard) savedInstanceState.getParcelable("ELEMENT");
        ind_course_views_txt.setText(cc.getCourse_views());
        ind_course_title_txt.setText(cc.getCourse_title());
        ind_owner_txt.setText(cc.getOwner());
        ind_course_rating_txt.setText(cc.getCourse_rating());
        ind_course_description.setText(cc.getCourse_description());
    }



}