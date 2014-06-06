package com.example.ServeSpeed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 4/7/14.
 */
public class PlayVideoSelect extends Activity {
    public RelativeLayout R1;
    public static int pausetime=0;
    public int frwdtimetocompare=0;
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
    private Handler myHandler = new Handler();;
    private int forwardTime = 250;
    private int backwardTime = 250;
    private SeekBar seekbar;
    private ImageButton playButton,pauseButton;
    private SurfaceView view;
    private SurfaceHolder holder;
    public static int oneTimeOnly = 0;
    public static int RacquetTouchOnce=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_video);
        view = (SurfaceView) findViewById (R.id.surface);
        holder = view.getHolder();
        holder.setKeepScreenOn (true);
        //songName = (TextView)findViewById(R.id.textView4);
        startTimeField =(TextView)findViewById(R.id.textView1);
        endTimeField =(TextView)findViewById(R.id.textView2);
        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        playButton = (ImageButton)findViewById(R.id.imageButton1);
        pauseButton = (ImageButton)findViewById(R.id.imageButton2);
        MainActivity m1= new MainActivity();
        Uri selectVideo=m1.ReturnVideoUri();
        //songName.setText("first video");
        mediaPlayer = MediaPlayer.create(this, selectVideo);


    }
    public void RacquetContactTime(View view) throws InterruptedException {
        stopslowmo=1;
        if(RacquetTouchOnce==0)
        {
            racquetContactTime = mediaPlayer.getCurrentPosition();
            Toast.makeText(this, "The time is " + racquetContactTime, Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            //TakeScreenShot();





            CallServeCoordinates();


            RacquetTouchOnce=1;
            stopslowmo=0;

        }
        else
        {
            ballBounceTime=mediaPlayer.getCurrentPosition();
            Toast.makeText(this,"The time is "+ballBounceTime,Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            CallBounceCoordinates();
        }
        //canvas.drawBitmap(b, 0, 0, null);
    }
    public void TakeScreenShot()
    {
       //R1=(RelativeLayout)findViewById(R.id.RelativeLayouts);
        SurfaceView vvv=(SurfaceView)findViewById(R.id.surface);

        View v1 = vvv.getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bm = v1.getDrawingCache();
        bitmapDrawable = new BitmapDrawable(bm);

    }

    public void CallBounceCoordinates()
    {
        Intent intent= new Intent(this,BounceCoordinates.class);
        startActivity(intent);
    }
    public void CallServeCoordinates()
    {
        Intent intent= new Intent(this,ServeCoordinates.class);
        startActivity(intent);
        play(view);

    }
    public void play(View view)
    {
        mediaPlayer.setDisplay(holder);
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        if(oneTimeOnly == 0){
            seekbar.setMax((int) finalTime);
        }

        endTimeField.setText(String.format("%dm,%ds",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) finalTime)))
        );
        startTimeField.setText(String.format("%dm,%ds",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) startTime)))
        );
        seekbar.setProgress((int)startTime);
        //mediaPlayer.setDisplay (holder);
        //mediaPlayer.setLooping (true);
        seekbar.setClickable(true);

        pauseButton.setEnabled(true);
/*

*/



        //myHandler.postDelayed(UpdateSongTime,100);
        //pauseButton.setEnabled(true);
        //playButton.setEnabled(false);
    }
    public void playslowmotion(View view)
    {
        Toast.makeText(this, "SlowMotion",
                Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();

        Timer t1= new Timer();


            TimerTask slowtimer= new TimerTask()
            {

            @Override
                public void run() {
                if(stopslowmo==1)
                {
                    this.cancel();
                    stopslowmo=0;
                }
                else
                {
                    //int aa=mediaPlayer.getCurrentPosition();
                    mediaPlayer.start();
                    mediaPlayer.pause();
                }
                }
            };

        t1.scheduleAtFixedRate(slowtimer, 0, 20);



    }
/*
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            startTimeField.setText(String.format("%dm,%ds",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);

            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    //startTime=i;
                    //mediaPlayer.seekTo((int) startTime);
                    ///myHandler.postDelayed(this,100);
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

*/

    public void pause(View view){
        Toast.makeText(getApplicationContext(), "Pausing",
                Toast.LENGTH_SHORT).show();
        stopslowmo=1;
        mediaPlayer.pause();
        pausetime=mediaPlayer.getCurrentPosition();
        pauseButton.setEnabled(true);
        playButton.setEnabled(true);
    }

    public void forward(View view){
        Timer t1= new Timer();
        mediaPlayer.pause();
        frwdtimetocompare=mediaPlayer.getCurrentPosition();
        TimerTask slowtimer= new TimerTask()
        {
            int oncerun=0;
            @Override
            public void run() {

               int presentposition=mediaPlayer.getCurrentPosition();
                    if(frwdtimetocompare<presentposition)
                    {
                        oncerun++;
                    }
                if(oncerun>=2)
                {
                    this.cancel();
                    oncerun=0;
                }
                else
                {
                    mediaPlayer.start();
                    mediaPlayer.pause();
                }

            }
        };

        t1.scheduleAtFixedRate(slowtimer, 0, 10);

    }
    public void rewind(View view){
        Timer t1= new Timer();
         mediaPlayer.pause();
        frwdtimetocompare=mediaPlayer.getCurrentPosition();
        frwdtimetocompare=frwdtimetocompare-70;
        mediaPlayer.seekTo(frwdtimetocompare);
        //mediaPlayer.start();
        //mediaPlayer.pause();

        TimerTask slowtimer= new TimerTask()
        {
            int oncerun=0;
            @Override
            public void run() {

                int presentposition=mediaPlayer.getCurrentPosition();
                if(frwdtimetocompare<presentposition)
                {
                    oncerun++;
                }
                if(oncerun>=1)
                {
                    this.cancel();
                    oncerun=0;
                }
                else
                {
                    mediaPlayer.start();
                    mediaPlayer.pause();
                }

            }
        };

        t1.scheduleAtFixedRate(slowtimer, 0, 10);

    }

}
