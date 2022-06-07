package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.example.unidemy.adapters.CardCourseAdapter;
import com.example.unidemy.adapters.RecyclerView_ViewModel;
import com.example.unidemy.adapters.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import model.CursoCard;

public class RecyclerViewActivity extends AppCompatActivity implements CardCourseAdapter.OnCourseListener {




    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;
    private TextView texto_no_courses;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private Toolbar toolbar;
    private String grade, university, faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(RecyclerViewActivity.this, Login.class));
            finish();
        }else {
            DocumentReference documentReference2 = firestore.collection("Users").document(mAuth.getCurrentUser().getUid());
            documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()) {
                        grade = doc.getString("user_grade");
                        university = doc.getString("user_unviersity");
                        faculty = doc.getString("user_faculty");
                    }
                    if (grade == null ) {
                        startActivity(new Intent(RecyclerViewActivity.this, StartTestUniversidad.class));
                        finish();
                    }
                    if (SaveSharedPreference.getUserName(RecyclerViewActivity.this).length() == 0 || mAuth.getCurrentUser().getUid().length() == 0) {
                        startActivity(new Intent(RecyclerViewActivity.this, Login.class));
                        finish();
                    }
                }
            });


            mActivity = this;
            setContentView(R.layout.activity_view_courses_list);
            parentContext = this.getBaseContext();
            mActivity = this;
            texto_no_courses = findViewById(R.id.texto_no_courses);
            texto_no_courses.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.banner_final);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            navigationView = findViewById(R.id.btm_navigator);
            mRecyclerView = findViewById(R.id.recyclerview_cursos);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
            navView.setSelectedItemId(R.id.navigation_recyclerview);
            navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.navigation_searcher:
                            startActivity(new Intent(getApplicationContext(), Searcher.class));
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        case R.id.navigation_mycourses:
                            startActivity(new Intent(getApplicationContext(), MyCourses.class));
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        case R.id.navigation_recyclerview:
                            return true;

                    }


                    return false;
                }
            });

            setLiveDataObservers();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(RecyclerViewActivity.this, MenuLateral.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(RecyclerView_ViewModel.class);
        CardCourseAdapter newAdapter = new CardCourseAdapter(parentContext, new ArrayList<CursoCard>(), (CardCourseAdapter.OnCourseListener) mActivity);
        final Observer<ArrayList<CursoCard>> observer = new Observer<ArrayList<CursoCard>>() {
            @Override
            public void onChanged(ArrayList<CursoCard> ac) {
                CardCourseAdapter newAdapter = new CardCourseAdapter(parentContext, ac, (CardCourseAdapter.OnCourseListener) mActivity);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
                checkIfEmpty(newAdapter);
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                //Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getCursoCards().observe(this, observer);
        viewModel.getToast().observe(this, observerToast);

    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, ViewCourse.class);
        intent.putExtra("selectedCourse", viewModel.getCursoCard(position));
        Log.d("CLICK", "Portada: "+ viewModel.getCursoCard(position).getCourse_porta());
        intent.putExtra("selectedPortada", viewModel.getCursoCard(position).getCourse_porta());
        startActivity(intent);
        finish();

    }

    void checkIfEmpty(CardCourseAdapter newAdapter){
        if(newAdapter.getItemCount() != 0) texto_no_courses.setVisibility(View.GONE);
        else texto_no_courses.setVisibility(View.VISIBLE);
    }



}
