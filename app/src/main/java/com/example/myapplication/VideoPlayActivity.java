package com.example.myapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener{
    private VideoView videoView;
    private MediaController controller;
    ImageView iv_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoView=(VideoView) findViewById(R.id.videoview);
        iv_play=(ImageView) findViewById(R.id.btn_play);
        videoView.setVideoURI(Uri.parse("http://10.132.212.206:8082/video/secret.mp4"));
        controller=new MediaController(this);
        videoView.setMediaController(controller);
        iv_play.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_play:
                play();
                break;
        }
    }
    private void play(){
        if (videoView!=null&&videoView.isPlaying()){
            iv_play.setImageResource(android.R.drawable.ic_media_play);
            videoView.stopPlayback();
            return;
        }
        videoView.start();
        iv_play.setImageResource(android.R.drawable.ic_media_pause);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                iv_play.setImageResource(android.R.drawable.ic_media_play);
            }
        });
    }
}
