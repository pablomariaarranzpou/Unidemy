package com.example.unidemy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewCourse extends AppCompatActivity {


    private TextView ind_course_views_txt, ind_course_title_txt, ind_owner_txt, ind_course_rating_txt, ind_course_description;
    private ImageButton play_button;
    private Button ind_btn_pagar, ind_btn_opinar;
    FirebaseAuth mAuth;
    CursoCard cc;
    String userId;
    FirebaseFirestore firestore;
    ArrayList<Integer> videos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        setContentView(R.layout.activity_view_course);
        ind_course_views_txt= (TextView) findViewById(R.id.ind_course_views);
        ind_course_title_txt = (TextView) findViewById(R.id.ind_course_title);
        ind_owner_txt = (TextView) findViewById(R.id.ind_owner_txt);
        ind_course_rating_txt = (TextView) findViewById(R.id.ind_course_rating);
        ind_course_description = (TextView) findViewById(R.id.ind_course_description);
        ind_btn_pagar = (Button) findViewById(R.id.ind_btn_pagar);
        ind_btn_opinar = (Button) findViewById((R.id.ind_btn_pagar));
        play_button = (ImageButton) findViewById(R.id.course_image);
        if(getIntent().hasExtra("selectedCourse")){
            cc = (CursoCard) getIntent().getParcelableExtra("selectedCourse");
            ind_course_views_txt.setText(cc.getCourse_views());
            ind_course_title_txt.setText(cc.getCourse_title());
            ind_owner_txt.setText(cc.getOwner());
            ind_course_rating_txt.setText(cc.getCourse_rating());
            ind_course_description.setText(cc.getCourse_description());
        }
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewCourse.this, ViewVideo.class));
            }
        });
        ind_btn_opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ind_btn_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firestore.collection("Users").document(userId);
                documentReference.update("userCourses", FieldValue.arrayUnion(cc.getCourse_id())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ns", "DocumentSnapshot successfully updated!");
                        Toast.makeText(ViewCourse.this,
                                "Curso "+ cc.getCourse_title() +" pagado con éxito!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }




}
