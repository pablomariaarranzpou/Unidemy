package com.example.unidemy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unidemy.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewComents extends AppCompatActivity {

    private Context parentContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView_ViewModel viewModel;
    private String selected_course_id;
    private Button btn_add_comment;
    private RecyclerViewComents_ViewModel viewmodelm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comentlist);
        parentContext = this.getBaseContext();
        mActivity = this;
        btn_add_comment = this.findViewById(R.id.btn_addComment);
        // Define RecyclerView elements: 1) Layout Manager and 2) Adapter
        mRecyclerView = findViewById(R.id.commentList);

        if (getIntent().hasExtra("course_id")){
            this.selected_course_id = getIntent().getExtras().getString("course_id");
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecyclerViewComents.this, ActivityAddComent.class);
                intent.putExtra("course", selected_course_id);
                startActivity(intent);
                finish();
            }
        });
        setLiveDataObservers();

    }

    public String getCourseId(){
        return this.selected_course_id;
    }

    public void setLiveDataObservers() {
        viewmodelm = new ViewModelProvider(this, new ComentRecyclerViewFactory(this.getApplication(), this.getCourseId())).get(RecyclerViewComents_ViewModel.class);
        CommentListAdapter newAdapter = new CommentListAdapter(new ArrayList<ComentCard>(), parentContext);
        final Observer<ArrayList<ComentCard>> observer = new Observer<ArrayList<ComentCard>>() {
            @Override
            public void onChanged(ArrayList<ComentCard> ac) {
                CommentListAdapter newAdapter = new CommentListAdapter(ac, parentContext);
                mRecyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> observerToast = new Observer<String>() {
            @Override
            public void onChanged(String t) {
                Toast.makeText(parentContext, t, Toast.LENGTH_SHORT).show();
            }
        };
        viewmodelm.getComentCards().observe(this, observer);
        viewmodelm.getToast().observe(this, observerToast);

    }
}
