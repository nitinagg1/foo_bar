package com.example.ServeSpeed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.lang.Object;

public class PlayVideoSelect2 extends Activity implements SurfaceHolder.Callback{

    public static int pausetime=0;
    public static BitmapDrawable bitmapDrawable;
    public TextView startTimeField,endTimeField;
    private MediaPlayer mediaPlayer;
    final int RESULT_LOAD_VIDEO=1001;
    private double startTime = 0;
    private double finalTime = 0;
    public static int racquetContactTime=0;
    public static int ballBounceTime=0;
    private double selectedtime2=0;
    private Handler myHandler = new Handler();
    private int forwardTime = 250;
    private int backwardTime = 250;
    private SeekBar seekbar;
    private ImageButton playButton,pauseButton,slowMotion;
    //private SurfaceView view;
    private MyVideoView view;
    private SurfaceHolder holder;
    public static int RacquetTouchOnce=0;
    public Bitmap bitmap;
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    Timer timer;
    String TAG = "servespeed";
    private int mFrameRate = 33;
    Uri selectVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_videoplayer);
        view = (MyVideoView) findViewById (R.id.videoView);
        holder = view.getHolder();
        //holder.setKeepScreenOn (true);
        holder.addCallback(this);
        startTimeField =(TextView)findViewById(R.id.textView10);
        endTimeField =(TextView)findViewById(R.id.textView20);
        seekbar = (SeekBar)findViewById(R.id.seekBar10);
        playButton = (ImageButton)findViewById(R.id.play);
        pauseButton = (ImageButton)findViewById(R.id.pause);
        slowMotion = (ImageButton)findViewById(R.id.slowmotion);
        MainActivity m1= new MainActivity();
        selectVideo=m1.ReturnVideoUri();
        pauseButton.setEnabled(false);
        playButton.setEnabled(false);
        slowMotion.setEnabled(false);
        timer = new Timer();

    }

    public void RacquetContactTime(View view) throws InterruptedException {
        if(RacquetTouchOnce==0)
        {
            racquetContactTime = mediaPlayer.getCurrentPosition();
            Toast.makeText(this, "The time is " + racquetContactTime, Toast.LENGTH_SHORT).show();
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            // ToDo  -- TakeScreenShot();
            CallServeCoordinates();
            RacquetTouchOnce=1;
        }
        else
        {
            ballBounceTime = mediaPlayer.getCurrentPosition();
            Toast.makeText(this,"The time is "+ballBounceTime,Toast.LENGTH_SHORT).show();
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            CallBounceCoordinates();
        }
    }

    public void CallBounceCoordinates()
    {
        Intent intent= new Intent(this,ServePosition2.class);
        startActivity(intent);
    }

    public void CallServeCoordinates()
    {
        Intent intent= new Intent(this,ServePosition.class);
        startActivity(intent);
        play(view);
    }

    public void play(View view)
    {
        //timer.cancel();
        myHandler.removeCallbacks(startSlow);
        myHandler.removeCallbacks(stopSlow);
        synchronized (this) {
            //mediaPlayer.setDisplay(holder);
            mediaPlayer.start();
            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();
            seekbar.setMax((int) finalTime);
            endTimeField.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) finalTime))
                    )
            );
            startTimeField.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime))
                    )
            );
            seekbar.setProgress((int) startTime);
            seekbar.setClickable(true);
            pauseButton.setEnabled(true);
            slowMotion.setEnabled(true);
            playButton.setEnabled(false);
            myHandler.postDelayed(UpdateWithSeekBarOrUpdateSeekBar, 100);
        }
    }

    /*
    public void playslowmotion(View view)
    {
        timer = new Timer();
        playButton.setEnabled(true);
        synchronized (this) {
            Toast.makeText(this, "SlowMotion", Toast.LENGTH_SHORT).show();
            timer.schedule(new TimerTask() {
                public void run() {
                    mediaPlayer.start();
                }
            }, 0, 200);
            timer.schedule(new TimerTask() {
                public void run() {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }
            }, 100, 200);
        }
    }
    */
    public void playslowmotion(View view)
    {
        playButton.setEnabled(true);
            myHandler.postDelayed(startSlow, 0);
            //myHandler.postDelayed(stopSlow, 10);
    }

    private Runnable startSlow = new Runnable() {
        @Override
        public void run() {
            mediaPlayer.start();
            SystemClock.sleep(10);
            mediaPlayer.pause();
            boolean boo = myHandler.postDelayed(this, 100);
            Log.d(TAG, "start and bool :" + boo);
        }
    };

    private Runnable stopSlow = new Runnable() {
        @Override
        public void run() {
                mediaPlayer.pause();
            boolean boo = myHandler.postDelayed(this, 200);
            Log.d(TAG, "stop and bool :" + boo);
        }
    };

    private Runnable UpdateWithSeekBarOrUpdateSeekBar = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            startTimeField.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int value = seekBar.getProgress();
                    startTime = value;
                    mediaPlayer.seekTo((int) startTime);
                }
            });
            myHandler.postDelayed(this, 100);
        }
    };

    public void pause(View view){
        //timer.cancel();
        myHandler.removeCallbacks(startSlow);
        myHandler.removeCallbacks(stopSlow);
        Toast.makeText(getApplicationContext(), "Pausing", Toast.LENGTH_SHORT).show();
        if (!mediaPlayer.isPlaying())
            return;
        mediaPlayer.pause();
        pausetime=mediaPlayer.getCurrentPosition();
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //calculateFrameRate();
        Log.d(TAG,"onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        myHandler.removeCallbacks(UpdateWithSeekBarOrUpdateSeekBar);
        myHandler.removeCallbacks(stopSlow);
        myHandler.removeCallbacks(startSlow);
        mediaPlayer.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surface created");
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDisplay(holder);
        }
        catch(Exception e)
        {
            Log.d(TAG,"exception e :" + e);
        }
        try {
            mediaPlayer.setDataSource(getApplicationContext(), selectVideo);
            mediaPlayer.prepare();
        }catch (Exception e)
        {
            Log.d(TAG,"exception e :" + e);
        }
        //mediaPlayer = MediaPlayer.create(this, selectVideo);
        if (mediaPlayer != null)
            playButton.setEnabled(true);

        mediaPlayer.setScreenOnWhilePlaying(true);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfacedestroyed");
        myHandler.removeCallbacks(UpdateWithSeekBarOrUpdateSeekBar);
        myHandler.removeCallbacks(stopSlow);
        myHandler.removeCallbacks(startSlow);
        mediaPlayer.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
       Log.d(TAG,"surface changed");
    }
}

