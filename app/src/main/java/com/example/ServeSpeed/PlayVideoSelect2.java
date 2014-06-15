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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.lang.Object;

public class PlayVideoSelect2 extends Activity {

    public static int pausetime=0;
    public int stopslowmo=0;
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
    private SurfaceView view;
    private SurfaceHolder holder;
    public static int RacquetTouchOnce=0;
    public Bitmap bitmap;
    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
    Timer timer;
    String TAG = "servespeed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_videoplayer);
        view = (VideoView) findViewById (R.id.videoView);
        holder = view.getHolder();
        holder.setKeepScreenOn (true);
        //songName = (TextView)findViewById(R.id.textView4);
        startTimeField =(TextView)findViewById(R.id.textView10);
        endTimeField =(TextView)findViewById(R.id.textView20);
        seekbar = (SeekBar)findViewById(R.id.seekBar10);
        playButton = (ImageButton)findViewById(R.id.play);
        pauseButton = (ImageButton)findViewById(R.id.pause);
        slowMotion = (ImageButton)findViewById(R.id.slowmotion);
        MainActivity m1= new MainActivity();
        Uri selectVideo=m1.ReturnVideoUri();
        pauseButton.setEnabled(false);
        playButton.setEnabled(false);
        slowMotion.setEnabled(false);
        timer = new Timer();
        mediaPlayer = MediaPlayer.create(this, selectVideo);
        if (mediaPlayer != null)
            playButton.setEnabled(true);
    }

    public void RacquetContactTime(View view) throws InterruptedException {
        stopslowmo=1;
        if(RacquetTouchOnce==0)
        {
            racquetContactTime = mediaPlayer.getCurrentPosition();
            Toast.makeText(this, "The time is " + racquetContactTime, Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            // ToDo  -- TakeScreenShot();
            CallServeCoordinates();
            RacquetTouchOnce=1;
            stopslowmo=0;
        }
        else
        {
            ballBounceTime = mediaPlayer.getCurrentPosition();
            Toast.makeText(this,"The time is "+ballBounceTime,Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            CallBounceCoordinates();
        }
    }

    public Bitmap getBitmapOfView(View v)
    {
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bmp = rootview.getDrawingCache();
        return bmp;
    }

    public void createImageFromBitmap(Bitmap bmp)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File( Environment.getExternalStorageDirectory() +
                "/capturedscreen.jpg");
        try
        {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            ostream.write(bytes.toByteArray());
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
            mediaPlayer.setDisplay(holder);
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
        synchronized (this) {
            myHandler.postDelayed(stopSlow, 0);
            myHandler.postDelayed(startSlow, 0);
        }
    }

    private Runnable startSlow = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "start");
            mediaPlayer.start();
            myHandler.postDelayed(this, 10);
        }
    };

    private Runnable stopSlow = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying())
            {
                Log.d(TAG, "stop");
                mediaPlayer.pause();
            }
            myHandler.postDelayed(this, 10);
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
        stopslowmo=1;
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
        //timer.cancel();
        //timer.purge();
        mediaPlayer.release();
    }
}
