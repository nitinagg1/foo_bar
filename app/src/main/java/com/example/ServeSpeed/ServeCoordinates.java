package com.example.ServeSpeed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by lenovo on 4/8/14.
 */
public class ServeCoordinates extends Activity
{
    float TouchX,TouchY;
    public static float sx,sy,fx,fy;
    View touchView2;
    ImageView touchView;
    Bitmap test;
    public static float servepointX, servepointY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serve_coordinates);
        touchView=(ImageView)findViewById(R.id.imageView);
        touchView2 =findViewById(R.id.locate);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int eventAction = event.getAction();
        switch(eventAction) {
            case MotionEvent.ACTION_MOVE:
                float TouchXX = event.getX();
                float TouchYY = event.getY();
                //Toast.makeText(this,"x-"+TouchX+"y-"+TouchY,Toast.LENGTH_SHORT).show();
                placeImage(TouchXX, TouchYY);
                break;
            case MotionEvent.ACTION_UP:
                TouchX = event.getX();
                TouchY = event.getY();
                Toast.makeText(this,"x-"+TouchX+"y-"+TouchY,Toast.LENGTH_SHORT).show();
                placeImage(TouchX, TouchY);
                break;
        }
        return true;
    }

    private void placeImage(float X, float Y) {
        int touchX = (int) X;
        int touchY = (int) Y;



        //placing at center of touch
        int viewWidth = touchView2.getWidth();
        int viewHeight = touchView2.getHeight();
        viewWidth = viewWidth / 2;
        viewHeight = viewHeight / 2;
        touchView2.setX(touchX);
        touchView2.setY(touchY);
        //touchView2.layout(touchX -viewWidth , touchY-viewHeight , touchX + viewWidth, touchY+ viewHeight);
        touchView2.setVisibility(0);
        // Toast.makeText(this,"x-" +touchView2.getX()+"y-"+touchView2.getY(),Toast.LENGTH_SHORT).show();


    }

    public void NoteServingPosition(View view)
    {
        servepointX=TouchX;
        servepointY=TouchY;
        PlayVideoSelect2 p1= new PlayVideoSelect2();
        Intent intent= new Intent(this,PlayVideoSelect2.class);
        startActivity(intent);
    }

}
