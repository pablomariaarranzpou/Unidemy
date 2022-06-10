package activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {

    private TextView user_email, user_info_university, user_info_faculty, user_info_grade;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String TAG = "USERINFO", university_code, faculty_code, grade_code;
    private Button btn_test;
    private Toolbar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_userinfo);
        firestore = FirebaseFirestore.getInstance();

        user_email = findViewById(R.id.user_reset_email);


        user_info_university = findViewById(R.id.user_info_university);
        user_info_faculty = findViewById(R.id.user_info_faculty);
        user_info_grade = findViewById(R.id.user_info_grade);
        mAuth = FirebaseAuth.getInstance();
        btn_test = findViewById(R.id.button_reset_test);
        user_email.setText(mAuth.getCurrentUser().getEmail());
        DocumentReference documentReference = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        faculty_code = document.getString("user_faculty");
                        grade_code = document.getString("user_grade");
                        university_code = document.getString("user_university");
                        Log.d("HOLA", " uni " + university_code + " fac " + faculty_code + " grade " + grade_code);
                        Task<DocumentSnapshot> documentReferenceuni = firestore.collection("Universidades")
                                .document(university_code).get().
                                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                                DocumentSnapshot document2 = task2.getResult();
                                                if (task2.isSuccessful()) {
                                                    if (document2.exists()) {
                                                        Log.d("uni?", "222 " + document2.getData());
                                                        Task<DocumentSnapshot> documentReferenceuni = firestore.collection("Universidades")
                                                                .document(document2.getId()).collection("Facultades").document(faculty_code).get().
                                                                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task3) {
                                                                                DocumentSnapshot document3 = task3.getResult();
                                                                                if (task3.isSuccessful()) {
                                                                                    if (document3.exists()) {
                                                                                        Task<DocumentSnapshot> documentReferenceuni = firestore.collection("Universidades")
                                                                                                .document(university_code).collection("Facultades").document(faculty_code).collection("Grado").document(grade_code).
                                                                                                        get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task4) {
                                                                                                        DocumentSnapshot document4 = task4.getResult();
                                                                                                        if (task4.isSuccessful()) {
                                                                                                            if (document4.exists()) {
                                                                                                                user_info_university.setText(document2.getString("uni_name"));
                                                                                                                user_info_faculty.setText(document3.getString("facu_name"));
                                                                                                                user_info_grade.setText(document4.getString("grado_name"));
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            }
                                                                        });
                                                    }
                                                }

                                            } });
                    }
                }
            }
        });



        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, StartTestUniversidad.class);
                startActivity(intent);
                finish();
            }

        });







    }
}


