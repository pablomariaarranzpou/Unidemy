package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewCourse extends AppCompatActivity implements CardVideoAdapter.OnVideoListener {


    private TextView ind_course_views_txt, ind_course_title_txt, ind_owner_txt, ind_course_rating_txt, ind_course_description;
    private ImageView play_button;
    private Button ind_btn_pagar, ind_btn_opinar;
    private Context parentContext;
    private FirebaseAuth mAuth;
    private CursoCard cc;
    private AppCompatActivity mActivity;
    private String userId;
    private FirebaseFirestore firestore;
    private VideoRecyclerView_ViewModel viewmodelm;
    private RecyclerView mmRecyclerView;
    private ArrayList<String> videos;
    private String id, portada_txt;
    private TextView txt_titulodocumentos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
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
        ind_btn_opinar = (Button) findViewById((R.id.ind_btn_opinar));
        play_button = (ImageView) findViewById(R.id.course_image);
        txt_titulodocumentos = findViewById(R.id.textView15);



        if (getIntent().hasExtra("selectedCourse")) {


            cc = (CursoCard) getIntent().getParcelableExtra("selectedCourse");
            Log.d("HAY LINK?", cc.getCourse_porta()+"?");
            ind_course_views_txt.setText(cc.getCourse_views());
            ind_course_title_txt.setText(cc.getCourse_title());
            ind_owner_txt.setText(cc.getOwner());


            Task<DocumentSnapshot> documentReference = firestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    ArrayList<String> acc = (ArrayList<String>) document.get("userCourses");
                                    checkIfPaid(acc);
                                }
                            }

                        }

                    });
            ind_course_rating_txt.setText(cc.getCourse_rating());
            ind_course_description.setText(cc.getCourse_description());
            videos = cc.getCourse_videos();
            id = cc.getCourse_id();
            if(cc.getCourse_porta() != null){
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/unidemy-a5397.appspot.com/o/images%2Fportada_curso_1.jpg?alt=media&token=2e0feea9-26c2-4dc5-a7ac-0990a3d5068e").into(play_button);
            }

            if (getIntent().hasExtra("selectedPortada")){

                portada_txt = getIntent().getExtras().getString("selectedPortada");
                Picasso.get().load(portada_txt).into(play_button);
            }else{
                Log.d("NOTHING", "Nothing in intent");
            }

        }

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourse.this, ViewVideo.class);
                intent.putExtra("selectedVideo", new VideoCard("TUTORIAL", "50", "https://firebasestorage.googleapis.com/v0/b/unidemy-a5397.appspot.com/o/videos%2FAT-cm_ZJWYEoBtI5KH30lAPvugNQ.mp4?alt=media&token=4f67229d-c2ec-44f2-8a1d-69e5f30b8c00"));
                startActivity(intent);
            }
        });
        ind_btn_opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCourse.this, RecyclerViewComents.class);
                intent.putExtra("course_id",id);
                startActivity(intent);
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
                        ind_btn_pagar.setVisibility(View.GONE);
                    }
                });
            }
        });
        setLiveDataObservers();
    }

    private void checkIfPaid(ArrayList<String> acc) {

        if(acc.contains(this.id)){
            ind_btn_pagar.setVisibility(View.GONE);
        }

    }

    public void setLiveDataObservers() {
            //Subscribe the activity to the observable
            viewmodelm = new ViewModelProvider(this, new VideoRecyclerView_ViewModelFactory(this.getApplication(), this.getCourseId())).get(VideoRecyclerView_ViewModel.class);
            CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, new ArrayList<VideoCard>(), (CardVideoAdapter.OnVideoListener) mActivity);
            final Observer<ArrayList<VideoCard>> observer = new Observer<ArrayList<VideoCard>>() {
                @Override
                public void onChanged(ArrayList<VideoCard> ac) {
                    CardVideoAdapter newAdapter = new CardVideoAdapter(parentContext, ac, (CardVideoAdapter.OnVideoListener) mActivity);
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


    @Override
    public void onVideoClick (int position) {
        Intent intent = new Intent(this, ViewVideo.class);
        intent.putExtra("selectedVideo", viewmodelm.getVideoCard(position));
        startActivity(intent);
    }




}





