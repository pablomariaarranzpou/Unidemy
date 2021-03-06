package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.example.unidemy.adapters.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button btn2_signup;
    TextInputEditText user_name, pass_word, name;
    FirebaseAuth mAuth;
    AppCompatActivity mActivity = this;
    FirebaseFirestore firstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Context mActivty = this;
        firstore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.textEditFullNameRegister);
        user_name=findViewById(R.id.textEditNameRegister);
        pass_word=findViewById(R.id.textEditPasswordRegister);
        btn2_signup=findViewById(R.id.sign);
        mAuth=FirebaseAuth.getInstance();
        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_name.getText().toString().trim();
                String password= pass_word.getText().toString().trim();
                String name_s = name.getText().toString();
                if(name_s.isEmpty()){
                    name.setError("Escriba un nombre");
                    name.requestFocus();
                    return;
                }
                if(email.isEmpty())
                {
                    user_name.setError("Escriba una direcci??n de correo");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Correo Electr??nico inv??lido");
                    user_name.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass_word.setError("Es necesario a??adir una contrase??a.");
                    pass_word.requestFocus();
                    return;
                }
                if(password.length()<3)
                {
                    pass_word.setError("Contrase??a de longuitud minima de 3 digitos");
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
                                    SaveSharedPreference.setUserName(Register.this, email);
                                    userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = firstore.collection("Users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                            user.put("mail", email);
                                            user.put("FullName", name.getText().toString().trim());
                                            user.put("userCourses", new ArrayList<String>());
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void
                                            >() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(name.getText().toString().trim())
                                                            .build();
                                                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                                    user.updateProfile(profileUpdates)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    startActivity(new Intent(Register.this, FirstLogin.class));
                                                                }
                                                            });

                                                }
                                            });


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
                            Toast.makeText(Register.this,"Error al logearse, Correo electr??nico ya registrado",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}