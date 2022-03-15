package com.example.unidemy.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

        EditText etEmail;
        private FirebaseAuth mAuth;
        Button btn_volver;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgotpassword);
            btn_volver = findViewById(R.id.btn_volverlogin);
            viewInitializations();
        }

        void viewInitializations() {
            etEmail = findViewById(R.id.et_email);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        boolean validateInput() {

            if (etEmail.getText().toString().equals("")) {
                etEmail.setError("Por favor, enviar un correo");
                return false;
            }

            if (!isEmailValid(etEmail.getText().toString())) {
                etEmail.setError("Formato inválido");
                return false;
            }


            return true;
        }

        boolean isEmailValid(String email) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }


        public void performCodeVerify (View v) {
            if (validateInput()) {

                String email = etEmail.getText().toString();

                Intent intent = new Intent(this, ForgotPassword.class);
                startActivity(intent);
                Toast.makeText(this,"Mail enviado. Revise su bandeja de entrada",Toast.LENGTH_SHORT).show();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this,
                                            "Mail de recuperación enviado",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

    public void performReturn (View v) {

        startActivity(new Intent(ForgotPassword.this, Login.class ));

    }
}


