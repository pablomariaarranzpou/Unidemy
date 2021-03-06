package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.example.unidemy.adapters.CardCourseAdapter;
import com.example.unidemy.adapters.RecyclerView_ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import model.CursoCard;

public class Searcher extends AppCompatActivity implements SearchView.OnQueryTextListener, CardCourseAdapter.OnCourseListener {

    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private BottomNavigationView navigationView;
    private NavController navController;
    CardCourseAdapter newAdapter;
    SearchView txtBuscar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_searcher);
        parentContext = this.getBaseContext();
        mActivity = this;
        txtBuscar = findViewById(R.id.txtBuscar);
        navigationView = findViewById(R.id.btm_navigator);
        txtBuscar.setOnQueryTextListener(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.banner_buscador);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView_results);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        BottomNavigationView navView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setSelectedItemId(R.id.navigation_searcher);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_searcher:
                        return true;
                    case R.id.navigation_mycourses:
                        startActivity(new Intent(getApplicationContext(), MyCourses.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_recyclerview:
                        startActivity(new Intent(getApplicationContext(), RecyclerViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
        txtBuscar.setOnQueryTextListener(this);
        setLiveDataObservers();

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
            startActivity(new Intent(Searcher.this, MenuLateral.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel = new ViewModelProvider(this).get(RecyclerView_ViewModel.class);
        newAdapter = new CardCourseAdapter(parentContext, new ArrayList<CursoCard>(), (CardCourseAdapter.OnCourseListener) mActivity);
        final Observer<ArrayList<CursoCard>> observer = new Observer<ArrayList<CursoCard>>() {
            @Override
            public void onChanged(ArrayList<CursoCard> ac) {
                newAdapter = new CardCourseAdapter(parentContext, ac, (CardCourseAdapter.OnCourseListener) mActivity);
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

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        newAdapter.filtrado(s);
        newAdapter.notifyDataSetChanged();
        return false;
    }
}
