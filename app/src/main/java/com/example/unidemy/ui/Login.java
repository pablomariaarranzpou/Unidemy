package com.example.unidemy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.unidemy.R;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextInputLayout user_name, pass_word;
    FirebaseAuth mAuth;
    int autoSave;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j = sharedpreferences.getInt("key", 0);

        //Default is 0 so autologin is disabled
        if(j > 0){
            Intent activity = new Intent(getApplicationContext(), RecyclerViewActivity.class);
            startActivity(activity);
        }
        setContentView(R.layout.activity_login);
        user_name=findViewById(R.id.email);
        pass_word=findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_sign = findViewById(R.id.btn_signup);
        Button btn_resetpassw = findViewById(R.id.btn_resetpassw);
        mAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(v -> {
            String email= user_name.getEditText().toString().trim();
            String password=pass_word.getEditText().toString().trim();
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                user_name.setError("Correo no válido");
                user_name.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                pass_word.setError("Necesario incluir contraseña");
                pass_word.requestFocus();
                return;
            }
            if(password.length()<6)
            {
                pass_word.setError("Por su seguirdad la contraseña debe ser de 6 o más caracteres");
                pass_word.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    autoSave = 1;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("key", autoSave);
                    editor.apply();
                    startActivity(new Intent(Login.this, Home.class));

                }
                else
                {
                    Toast.makeText(Login.this,
                            "Usuario o/y contraseña incorrectos",
                            Toast.LENGTH_SHORT).show();
                }

            });
        });
        btn_sign.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class )));
        btn_resetpassw.setOnClickListener(v -> startActivity(new Intent(Login.this, ForgotPassword.class )));
    }

}