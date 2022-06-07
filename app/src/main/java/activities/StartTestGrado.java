package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;
import com.example.unidemy.adapters.GradosAdapter;
import com.example.unidemy.adapters.GradosViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.GradoCard;

public class StartTestGrado extends AppCompatActivity implements GradosAdapter.OnGradoListener {

    FirebaseAuth mAuth;
    RecyclerView mRecyclerView;
    private AppCompatActivity mActivity;
    private GradosViewModel viewmodelm;
    private Context parentContext;
    FirebaseFirestore firstore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testuniversidad3);
        mActivity = this;
        mRecyclerView = findViewById(R.id.recyclerview_grados);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        parentContext = this.getBaseContext();
        mAuth = FirebaseAuth.getInstance();
        firstore = FirebaseFirestore.getInstance();
        setLiveDataObservers();

    }
    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewmodelm = new ViewModelProvider(this).get(GradosViewModel.class);
        GradosAdapter newAdapter = new GradosAdapter(new ArrayList<GradoCard>(), parentContext, (GradosAdapter.OnGradoListener) mActivity);
        final Observer<ArrayList<GradoCard>> observer = new Observer<ArrayList<GradoCard>>() {
            @Override
            public void onChanged(ArrayList<GradoCard> ac) {
                GradosAdapter newAdapter = new GradosAdapter(ac, parentContext, (GradosAdapter.OnGradoListener) mActivity);
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


        viewmodelm.getGradoCards().observe(this, observer);
        viewmodelm.getToast().observe(this, observerToast);

    }



    @Override
    public void OnGradoClick(int position) {
        String id = viewmodelm.getGradoCards().getValue().get(position).getGradoID();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firstore.collection("Users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("user_grade", id);
        firstore.collection("Users").document(userID)
                .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void
                >() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(StartTestGrado.this, RecyclerViewActivity.class));
                finish();
            }
        });
    }
}
