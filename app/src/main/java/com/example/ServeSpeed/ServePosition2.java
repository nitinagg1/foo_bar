package com.example.ServeSpeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by lenovo on 5/24/14.
 */
public class ServePosition2 extends Activity {

    ImageView ball,tennisCourt;
    View touchView2;
    float TouchX,TouchY;
    public static float y_org;
    public static float y_court;
    public static float x_org;
    public static float x_court;

    public static float y_orgDp;
    public static float y_courtDp;
    public static float x_orgDP;
    public static float x_courtDP;

    public static float scaleInDP;
    public static float scaleInPixels;
    public static float ballX;
    public static float ballY;
    DisplayMetrics dm;

    Animation toZoomIn;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serve_position2);
        ball = (ImageView)findViewById(R.id.ball);
        tennisCourt = (ImageView)findViewById(R.id.tennis_court2);
        touchView2 =findViewById(R.id.locate1);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        x_org=dm.widthPixels;
        y_org=dm.heightPixels;
        /*
        FrameLayout.LayoutParams mParams1 = (FrameLayout.LayoutParams) ball.getLayoutParams();
        mParams1.leftMargin=(int) x_org - (int)x_org/2 - (int)convertDpToPixel(25,getApplicationContext())/2;
        mParams1.topMargin=(int)y_org- (int)y_org/2 - (int)convertDpToPixel(100,getApplicationContext())/2;
        */
        //windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        //windowheight = getWindowManager().getDefaultDisplay().getHeight();

        ball.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int eid = event.getAction();
                switch (eid) {
                    case MotionEvent.ACTION_MOVE:
                        if(ServePosition.servePositionX>=180 && ServePosition.servePositionY==50)
                        {
                            FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) ball.getLayoutParams();
                            int x_cord = (int) event.getRawX();
                            int y_cord = (int) event.getRawY();
                            x_orgDP=convertPixelsToDp(x_org,getApplicationContext());
                            y_orgDp=convertPixelsToDp(y_org,getApplicationContext());
                            y_courtDp=y_orgDp-100;
                            float length=(float)23.78;
                            float breadth=(float)10.97;
                            x_courtDP=y_courtDp*breadth/length;
                            x_court=convertDpToPixel(x_courtDP,getApplicationContext());
                            y_court=convertDpToPixel(y_courtDp,getApplicationContext());
                            scaleInDP=y_courtDp/length;
                            float boxy1=y_orgDp/2;
                            float boxx1=x_orgDP/2;
                            float boxx2=boxx1-(float)4.115*scaleInDP;
                            float boxy2=boxy1+(float)6.40*scaleInDP;
                            float boxx1P=convertDpToPixel(boxx1,getApplicationContext());
                            float boxy1P=convertDpToPixel(boxy1,getApplicationContext());
                            float boxx2P=convertDpToPixel(boxx2,getApplicationContext());
                            float boxy2P=convertDpToPixel(boxy2,getApplicationContext());
                           /* x_cord=x_cord+ 100;
                            y_cord=y_cord -100;
                            boxx1P=boxx1P+100;
                            boxy2P=boxy2P-100;
                            boxx2P=boxx2P+100;
                            boxy1P=boxy1P-100;
                            */
                            if(x_cord>boxx1P)
                            {
                                x_cord=(int)boxx1P;
                            }
                            else if(x_cord<boxx2P+(int)convertDpToPixel((float)12.5,getApplicationContext()))
                            {
                                x_cord=(int)boxx2P +(int)convertDpToPixel((float)12.5,getApplicationContext());
                            }
                            if(y_cord>boxy2P-(int)convertDpToPixel(25,getApplicationContext()))
                            {
                                y_cord=(int)boxy2P-(int)convertDpToPixel(25,getApplicationContext());
                            }
                            if(y_cord<boxy1P)
                            {
                                y_cord=(int)boxy1P;
                            }
                            mParams.leftMargin = x_cord -(int)x_org/2;
                            mParams.topMargin = y_cord - (int)y_org/2;
                            ball.setLayoutParams(mParams);
                            break;
                        }

                        else if(ServePosition.servePositionX<180 && ServePosition.servePositionY==50)
                        {
                            FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) ball.getLayoutParams();
                            int x_cord = (int) event.getRawX();
                            int y_cord = (int) event.getRawY();
                            x_orgDP=convertPixelsToDp(x_org,getApplicationContext());
                            y_orgDp=convertPixelsToDp(y_org,getApplicationContext());
                            y_courtDp=y_orgDp-100;
                            float length=(float)23.78;
                            float breadth=(float)10.97;
                            x_courtDP=y_courtDp*breadth/length;
                            x_court=convertDpToPixel(x_courtDP,getApplicationContext());
                            y_court=convertDpToPixel(y_courtDp,getApplicationContext());
                            scaleInDP=y_courtDp/length;
                            float boxy1=y_orgDp/2;
                            float boxx1=x_orgDP/2;
                            float boxx2=boxx1+(float)4.115*scaleInDP;
                            float boxy2=boxy1+(float)6.40*scaleInDP;
                            float boxx1P=convertDpToPixel(boxx1,getApplicationContext());
                            float boxy1P=convertDpToPixel(boxy1,getApplicationContext());
                            float boxx2P=convertDpToPixel(boxx2,getApplicationContext());
                            float boxy2P=convertDpToPixel(boxy2,getApplicationContext());
                            if(x_cord<boxx1P)
                            {
                                x_cord=(int)boxx1P;
                            }
                            else if(x_cord>boxx2P-(int)convertDpToPixel((float)12.5,getApplicationContext()))
                            {
                                x_cord=(int)boxx2P - (int)convertDpToPixel((float)12.5,getApplicationContext());
                            }
                            if(y_cord>boxy2P-(int)convertDpToPixel(25,getApplicationContext()))
                            {
                                y_cord=(int)boxy2P-(int)convertDpToPixel(25,getApplicationContext());
                            }
                            if(y_cord<boxy1P)
                            {
                                y_cord=(int)boxy1P;
                            }
                            mParams.leftMargin = x_cord -(int)x_org/2;
                            mParams.topMargin = y_cord - (int)y_org/2;
                            ball.setLayoutParams(mParams);
                            break;
                        }

                        else if(ServePosition.servePositionX<180 && ServePosition.servePositionY>50)

                        {
                            FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) ball.getLayoutParams();
                            int x_cord = (int) event.getRawX();
                            int y_cord = (int) event.getRawY();
                            x_orgDP=convertPixelsToDp(x_org,getApplicationContext());
                            y_orgDp=convertPixelsToDp(y_org,getApplicationContext());
                            y_courtDp=y_orgDp-100;
                            float length=(float)23.78;
                            float breadth=(float)10.97;
                            x_courtDP=y_courtDp*breadth/length;
                            x_court=convertDpToPixel(x_courtDP,getApplicationContext());
                            y_court=convertDpToPixel(y_courtDp,getApplicationContext());
                            scaleInDP=y_courtDp/length;
                            float boxy1=y_orgDp/2;
                            float boxx1=x_orgDP/2;
                            float boxx2=boxx1+(float)4.115*scaleInDP;
                            float boxy2=boxy1-(float)6.40*scaleInDP;
                            float boxx1P=convertDpToPixel(boxx1,getApplicationContext());
                            float boxy1P=convertDpToPixel(boxy1,getApplicationContext());
                            float boxx2P=convertDpToPixel(boxx2,getApplicationContext());
                            float boxy2P=convertDpToPixel(boxy2,getApplicationContext());
                            if(x_cord<boxx1P)
                            {
                                x_cord=(int)boxx1P;
                            }
                            else if(x_cord>boxx2P-(int)convertDpToPixel((float)12.5,getApplicationContext()))
                            {
                                x_cord=(int)boxx2P - (int)convertDpToPixel((float)12.5,getApplicationContext());
                            }
                            if(y_cord<boxy2P+(int)convertDpToPixel(25,getApplicationContext()))
                            {
                                y_cord=(int)boxy2P+(int)convertDpToPixel(25,getApplicationContext());
                            }
                            if(y_cord>boxy1P)
                            {
                                y_cord=(int)boxy1P;
                            }
                            mParams.leftMargin = x_cord -(int)x_org/2;
                            mParams.topMargin = y_cord - (int)y_org/2;
                            ball.setLayoutParams(mParams);
                            break;
                        }

                        else
                        {
                            FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) ball.getLayoutParams();
                            int x_cord = (int) event.getRawX();
                            int y_cord = (int) event.getRawY();
                            x_orgDP=convertPixelsToDp(x_org,getApplicationContext());
                            y_orgDp=convertPixelsToDp(y_org,getApplicationContext());
                            y_courtDp=y_orgDp-100;
                            float length=(float)23.78;
                            float breadth=(float)10.97;
                            x_courtDP=y_courtDp*breadth/length;
                            x_court=convertDpToPixel(x_courtDP,getApplicationContext());
                            y_court=convertDpToPixel(y_courtDp,getApplicationContext());
                            scaleInDP=y_courtDp/length;
                            float boxy1=y_orgDp/2;
                            float boxx1=x_orgDP/2;
                            float boxx2=boxx1-(float)4.115*scaleInDP;
                            float boxy2=boxy1-(float)6.40*scaleInDP;
                            float boxx1P=convertDpToPixel(boxx1,getApplicationContext());
                            float boxy1P=convertDpToPixel(boxy1,getApplicationContext());
                            float boxx2P=convertDpToPixel(boxx2,getApplicationContext());
                            float boxy2P=convertDpToPixel(boxy2,getApplicationContext());
                            if(x_cord>boxx1P)
                            {
                                x_cord=(int)boxx1P;
                            }
                            if(x_cord<boxx2P+(int)convertDpToPixel((float)12.5,getApplicationContext()))
                            {
                                x_cord=(int)boxx2P +(int)convertDpToPixel((float)12.5,getApplicationContext());
                            }

                            if(y_cord<boxy2P+(int)convertDpToPixel(25,getApplicationContext()))
                            {
                                y_cord=(int)boxy2P+(int)convertDpToPixel(25,getApplicationContext());
                            }
                            if(y_cord>boxy1P)
                            {
                                y_cord=(int)boxy1P;
                            }
                            mParams.leftMargin = x_cord -(int)x_org/2;
                            mParams.topMargin = y_cord - (int)y_org/2;
                            ball.setLayoutParams(mParams);
                            break;
                        }

                    case MotionEvent.ACTION_UP:
                        int[] test1= new int[2];
                        //ball.getLocationOnScreen(test1);
                        float ballleft=ball.getLeft();
                        float balltop= ball.getTop();
                        ballX=convertPixelsToDp(ballleft,getApplicationContext())+(float)12.5;
                        ballY=convertPixelsToDp(balltop,getApplicationContext())+(float)12.5;

                        Toast.makeText(getApplicationContext(), "x-" +ballX  + "y-" + ballY, Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void CalculateSpeed(View view)
    {
        Intent i= new Intent(this,CalculateSpeedClass.class);
        startActivity(i);

    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


}

