package com.example.acer.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final int time = 5000;
    private Button btn_backward, btn_play, btn_pause, btn_forward;
    private MediaPlayer mediaPlayer;
    private TextView tvElapsedTime;
    private TextView tvDuration;
    private SeekBar seekBar;
    private long duration;
    private long elaspedTime;
    private Handler handler;
    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            elaspedTime = mediaPlayer.getCurrentPosition();

            seekBar.setProgress((int) elaspedTime);

            long minute = TimeUnit.MILLISECONDS.toMinutes(elaspedTime); // 2
            long second = TimeUnit.MILLISECONDS.toSeconds(elaspedTime) -
                    TimeUnit.MINUTES.toSeconds(minute);

            tvElapsedTime.setText(String.format("%d Min %d Sec", minute, second));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_backward = findViewById(R.id.btn_backward);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_forward = findViewById(R.id.btn_forward);
        btn_backward.setClickable(false);
        btn_forward.setClickable(false);
        btn_pause.setClickable(false);
        handler = new Handler();

        tvElapsedTime = findViewById(R.id.tv_elapsedTime);
        tvDuration = findViewById(R.id.tv_duration);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.song1);
        duration = mediaPlayer.getDuration();

        seekBar.setMax((int) duration);

        long minute = TimeUnit.MILLISECONDS.toMinutes(duration); // 2
        long second = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(minute);

        tvDuration.setText(String.format("%d Min %d Sec", minute, second));

        elaspedTime = 0;
    }

    public void play(View view) {
        mediaPlayer.start();

        handler.postDelayed(updateTime, 1000);
        btn_play.setClickable(false);
        btn_pause.setClickable(true);
        btn_backward.setClickable(true);
        btn_forward.setClickable(true);

    }

    public void backward(View view) {
        int position = mediaPlayer.getCurrentPosition();
        if ((position - time) > 0) {
            mediaPlayer.seekTo(position - time);

        } else {
            Toast.makeText(this, "You can't backward", Toast.LENGTH_SHORT).show();
        }


    }

    public void pause(View view) {
        mediaPlayer.pause();
        btn_play.setClickable(true);
        btn_pause.setClickable(false);
        btn_backward.setClickable(true);
        btn_forward.setClickable(true);

    }

    public void forward(View view) {
        int position = mediaPlayer.getCurrentPosition();
        if ((position + time) < duration) {
            mediaPlayer.seekTo(position + time);
        } else {
            Toast.makeText(this, "You can't forward", Toast.LENGTH_SHORT).show();
        }

    }
}
