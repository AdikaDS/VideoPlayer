package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private final static String LOCAL_VIDEO = "draw_video";
    private static final String ONLINE_VIDEO = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1";

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video_view);

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoView.seekTo(1);
                mVideoView.start();
            }
        });
    }

    public Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        } else {
            // Untuk mengeplay video dari local butuh url
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);

        }
    }

    public void initializePlayer() {
//        // Memanggil video dari local
//        Uri videoUri = getMedia(LOCAL_VIDEO);
//        mVideoView.setVideoURI(videoUri);
        // Memanggil video dari local
        Uri videoUri = getMedia(ONLINE_VIDEO);
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }

    public void relesaePlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        relesaePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }
}