package com.example.unidemy.ui;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private Context parentContext;
    private FirebaseAuth mAuth;
    private CursoCard cc;
    private String userId;
    private FirebaseFirestore firestore;
    private VideoRecyclerView_ViewModel viewmodelm;
    private RecyclerView mmRecyclerView;
    private ArrayList<String> videos;
    private String id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        parentContext = getBaseContext();
        firestore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        setContentView(R.layout.activity_view_course);
        mmRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_videos);
        mmRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCourse.this,
                LinearLayoutManager.HORIZONTAL, false));
        ind_course_views_txt = (TextView) findViewById(R.id.ind_course_views);
        ind_course_title_txt = (TextView) findViewById(R.id.ind_course_title);
        ind_owner_txt = (TextView) findViewById(R.id.ind_owner_txt);
        ind_course_rating_txt = (TextView) findViewById(R.id.ind_course_rating);
        ind_course_description = (TextView) findViewById(R.id.ind_course_description);
        ind_btn_pagar = (Button) findViewById(R.id.ind_btn_pagar);
        ind_btn_opinar = (Button) findViewById((R.id.ind_btn_pagar));
        play_button = (ImageButton) findViewById(R.id.course_image);


        if (getIntent().hasExtra("selectedCourse")) {
            cc = (CursoCard) getIntent().getParcelableExtra("selectedCourse");
            ind_course_views_txt.setText(cc.getCourse_views());
            ind_course_title_txt.setText(cc.getCourse_title());
            ind_owner_txt.setText(cc.getOwner());
            ind_course_rating_txt.setText(cc.getCourse_rating());
            ind_course_description.setText(cc.getCourse_description());
            videos = cc.getCourse_videos();
            id = cc.getCourse_id();
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
                                "Curso " + cc.getCourse_title() + " pagado con éxito!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        setLiveDataObservers();
    }

        public void setLiveDataObservers() {
            //Subscribe the activity to the observable
            viewmodelm = new ViewModelProvider(this, new VideoRecyclerView_ViewModelFactory(this.getApplication(), this.getCourseId())).get(VideoRecyclerView_ViewModel.class);
            CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, new ArrayList<VideoCard>());
            final Observer<ArrayList<VideoCard>> observer = new Observer<ArrayList<VideoCard>>() {
                @Override
                public void onChanged(ArrayList<VideoCard> ac) {
                    CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, ac);
                    mmRecyclerView.swapAdapter(newAdapter, false);
                    newAdapter.notifyDataSetChanged();
                }
            };

            final Observer<String> observerToast = new Observer<String>() {
                @Override
                public void onChanged(String t) {
                    //Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
                }
            };

            viewmodelm.getCursoCards().observe(this, observer);
            viewmodelm.getToast().observe(this, observerToast);

        }

    public String getCourseId(){
        return this.id;
    }



    }





