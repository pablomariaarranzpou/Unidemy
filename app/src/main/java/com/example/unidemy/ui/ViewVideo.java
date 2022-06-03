package com.example.unidemy.ui;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

public class ViewVideo extends AppCompatActivity implements MediaPlayer.OnCompletionListener{


    VideoView vw;
    VideoCard vc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_videoview);
        vw = (VideoView) findViewById(R.id.videoView);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        if (getIntent().hasExtra("selectedVideo")) {
            vc = (VideoCard) getIntent().getParcelableExtra("selectedVideo");
            vw.setVideoPath(vc.getUrl());
        }
        vw.start();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("¡Felicidades has acabado este tema!");
        MyListener m = new MyListener();
        obj.setPositiveButton("Volver a ver", m);
        obj.setNegativeButton("salir", m);
        obj.setMessage("Quieres repasar el curso o salir?");
        obj.show();
    }
    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }
            else {
                finish();
            }
        }
    }


}
