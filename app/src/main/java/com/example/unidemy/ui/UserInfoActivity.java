package com.example.unidemy.ui;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {

    private TextView  user_email;
    private FirebaseAuth mAuth;
    private TextInputLayout layout_firstname, layout_email;
    private FirebaseFirestore firestore;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userinfo);
        firestore = FirebaseFirestore.getInstance();

        user_email = findViewById(R.id.user_reset_email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        layout_email = findViewById(R.id.layout_email);
        user_email.setText(mAuth.getCurrentUser().getEmail());
        DocumentReference documentReference = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());

    }


}
