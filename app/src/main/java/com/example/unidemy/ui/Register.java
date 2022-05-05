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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    Button btn2_signup;
    TextInputEditText user_name, pass_word;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user_name=(TextInputEditText)findViewById(R.id.textEditEmailRegister);
        pass_word=(TextInputEditText)findViewById(R.id.textEditPasswordRegister);
        btn2_signup=findViewById(R.id.sign);
        mAuth=FirebaseAuth.getInstance();
        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_name.getText().toString().trim();
                String password= pass_word.getText().toString().trim();
                if(email.isEmpty())
                {
                    user_name.setError("Escriba una dirección de correo");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Correo Electrónico inválido");
                    user_name.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass_word.setError("Es necesario añadir una contraseña.");
                    pass_word.requestFocus();
                    return;
                }
                if(password.length()<3)
                {
                    pass_word.setError("Contraseña de longuitud minima de 3 digitos");
                    pass_word.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"Registrado correctamente!", Toast.LENGTH_SHORT).show();

                            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( tarea ->{
                                if(tarea.isSuccessful())
                                {

                                    startActivity(new Intent(Register.this, FirstLogin.class));

                                }
                                else
                                {
                                    Toast.makeText(Register.this,
                                            "Error al logearse",
                                            Toast.LENGTH_SHORT).show();
                                }

                            });

                        }
                        else
                        {
                            Toast.makeText(Register.this,"Error al logearse, Correo electrónico ya registrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}