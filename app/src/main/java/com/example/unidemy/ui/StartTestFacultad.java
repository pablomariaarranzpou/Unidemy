package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartTestFacultad extends AppCompatActivity implements FacultadesAdapter.OnFacultadListener{


    FirebaseAuth mAuth;
    RecyclerView mRecyclerView;
    private AppCompatActivity mActivity;
    private FacultadesViewModel viewmodelm;
    private Context parentContext;
    FirebaseFirestore firstore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testuniversidad2);
        mRecyclerView = findViewById(R.id.recyclerview_facultades);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        parentContext = this.getBaseContext();
        mActivity = this;
        mAuth = FirebaseAuth.getInstance();
        firstore = FirebaseFirestore.getInstance();
        setLiveDataObservers();

    }
    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewmodelm = new ViewModelProvider(this).get(FacultadesViewModel.class);
        FacultadesAdapter newAdapter = new FacultadesAdapter(new ArrayList<FacultadCard>(), parentContext, (FacultadesAdapter.OnFacultadListener) mActivity);
        final Observer<ArrayList<FacultadCard>> observer = new Observer<ArrayList<FacultadCard>>() {
            @Override
            public void onChanged(ArrayList<FacultadCard> ac) {
                FacultadesAdapter newAdapter = new FacultadesAdapter(ac, parentContext, (FacultadesAdapter.OnFacultadListener) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                //Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };


        viewmodelm.getFacultadCards().observe(this, observer);
        viewmodelm.getToast().observe(this, observerToast);

    }



    @Override
    public void onFacultadClick(int position) {
        String id = viewmodelm.getFacultadCards().getValue().get(position).getFacultadID();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firstore.collection("Users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("user_faculty", id);
        firstore.collection("Users").document(userID)
                .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void
                >() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(StartTestFacultad.this, FirstLogin.class));
                finish();
            }
        });
    }
}


