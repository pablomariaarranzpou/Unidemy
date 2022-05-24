package com.example.unidemy.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

public class Privacity extends AppCompatActivity {

    private TextView privacytxt;
    private Context parentContext;
    private AppCompatActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mActivity = this;
        setContentView(R.layout.activity_privacy);
        parentContext = this.getBaseContext();
        privacytxt = findViewById(R.id.privacidad_text);


    }
}
