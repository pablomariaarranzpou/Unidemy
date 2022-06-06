package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartTestUniversidad extends AppCompatActivity implements UniversidadCardAdapter.OnUniversidadListener {


    FirebaseAuth mAuth;
    RecyclerView mRecyclerView;
    private AppCompatActivity mActivity;
    private Universidades_ViewModel viewmodelm;
    private Context parentContext;
    FirebaseFirestore firstore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testuniversidad);
        mActivity = this;
        mRecyclerView = findViewById(R.id.recyclerview_universidades);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        parentContext = this.getBaseContext();
        mAuth = FirebaseAuth.getInstance();
        firstore = FirebaseFirestore.getInstance();
        setLiveDataObservers();


    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        this.viewmodelm = new ViewModelProvider(this).get(Universidades_ViewModel.class);
        UniversidadCardAdapter newAdapter = new UniversidadCardAdapter(new ArrayList<UniversidadCard>(), parentContext, (UniversidadCardAdapter.OnUniversidadListener) mActivity);
        final Observer<ArrayList<UniversidadCard>> observer = new Observer<ArrayList<UniversidadCard>>() {
            @Override
            public void onChanged(ArrayList<UniversidadCard> ac) {
                UniversidadCardAdapter newAdapter = new UniversidadCardAdapter(ac, parentContext, (UniversidadCardAdapter.OnUniversidadListener) mActivity);
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


        viewmodelm.getUniversidadCard().observe(this, observer);
        viewmodelm.getToast().observe(this, observerToast);

    }

    @Override
    public void onUniversidadClick(int position) {

        String id = viewmodelm.getUniversidadCard().getValue().get(position).getUniID();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firstore.collection("Users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("user_university", id);
        firstore.collection("Users").document(userID)
                .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void
                >() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(StartTestUniversidad.this, StartTestFacultad.class));
                finish();
            }
        });
    }
}
