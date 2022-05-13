package com.example.unidemy.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

import java.util.ArrayList;

public class ViewVideo extends AppCompatActivity implements MediaPlayer.OnCompletionListener{


    VideoView vw;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        vw = (VideoView) findViewById(R.id.videoView);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);
        vw.setVideoPath("https://firebasestorage.googleapis.com/v0/b/unidemy-a5397.appspot.com/o/videos%2FPENAL%20DE%20MIGUELITO%20%5BHD%5D.mp4?alt=media&token=ff55ae0c-4659-43be-89c8-a4058dc43778");
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
                startActivity(new Intent(ViewVideo.this, MyCourses.class));
            }
        }
    }
}
