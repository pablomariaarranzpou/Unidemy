package activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.example.unidemy.adapters.SaveSharedPreference;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private TextInputLayout user_name, pass_word;
    FirebaseAuth mAuth;


    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    SaveSharedPreference.setUserName(Login.this, email);
                    startActivity(new Intent(Login.this, RecyclerViewActivity.class));

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