package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewComents extends AppCompatActivity {

    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private String selected_course_id;
    private Button btn_add_comment;
    private RecyclerViewComents_ViewModel viewmodelm;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private boolean pagado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comentlist);
        parentContext = this.getBaseContext();
        mActivity = this;
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        btn_add_comment = this.findViewById(R.id.btn_addComment);
        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.commentList);

        if (getIntent().hasExtra("course_id")){
            this.selected_course_id = getIntent().getExtras().getString("course_id");
        }

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

        Task<DocumentSnapshot> documentReferenceRating = firestore.collection("Curso").document(selected_course_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        ArrayList<String> arr = (ArrayList<String>) doc.get("course_coments");
                        Task<QuerySnapshot> documentReferenceRating = firestore.collection("Coment").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if (task.isSuccessful()) {
                                            Double media = 0.0;
                                            Float mediaf;
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if(document.exists()) {
                                                    media += document.getDouble("coment_rating");
                                                }
                                            }
                                            media = media / arr.size();
                                            media = Math.round(media * 10.0) / 10.0;
                                            mediaf = media.floatValue();
                                            Task<Void> documentReferenceRating = firestore.collection("Curso").document(selected_course_id)
                                                    .update("course_rating", mediaf.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            //
                                                        }
                                                    });
                                        }

                                    }
                                });

                    }
                });




        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pagado) {
                    Intent intent = new Intent(RecyclerViewComents.this, ActivityAddComent.class);
                    intent.putExtra("course", selected_course_id);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(RecyclerViewComents.this,
                            "Debes pagar un curso para poder añadir tu opinión",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        setLiveDataObservers();

    }

    private void checkIfPaid(ArrayList<String> acc) {

        if(acc.contains(this.selected_course_id)){
            pagado = true;
        }else{
            pagado = false;
        }

    }

    public String getCourseId(){
        return this.selected_course_id;
    }

    public void setLiveDataObservers() {
        viewmodelm = new ViewModelProvider(this, new ComentRecyclerViewFactory(this.getApplication(), this.getCourseId())).get(RecyclerViewComents_ViewModel.class);
        CommentListAdapter newAdapter = new CommentListAdapter(new ArrayList<ComentCard>(), parentContext);
        final Observer<ArrayList<ComentCard>> observer = new Observer<ArrayList<ComentCard>>() {
            @Override
            public void onChanged(ArrayList<ComentCard> ac) {
                CommentListAdapter newAdapter = new CommentListAdapter(ac, parentContext);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };
        viewmodelm.getComentCards().observe(this, observer);
        viewmodelm.getToast().observe(this, observerToast);

    }
}
