package com.example.ServeSpeed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by lenovo on 6/8/14.
 */
public class CalculateSpeedClass extends Activity{

    TextView tv;
    ImageView image;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speeed_display);
        tv = (TextView)findViewById(R.id.textView);
        image = (ImageView)findViewById(R.id.snapshot);
        int speeddisplay=CalcSpeed();
        tv.setText("Speed " + speeddisplay + "Km/hr");
        tv.setVisibility(View.VISIBLE);
        Bitmap bm = BitmapFactory.decodeFile(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "test.jpg");
        image.setImageBitmap(bm);
    }

    private int CalcSpeed()
    {
        float finalshoeX,finalshoeY,finalballX,finalballY,time1,time2;

        time1= PlayVideo.racquetContactTime;
        time2= PlayVideo.ballBounceTime;
        finalshoeX=ServePosition.servePositionX;
        finalshoeY=ServePosition.servePositionY;
        finalballX= ServePosition2.ballX;
        finalballY=ServePosition2.ballY;
        float scale=ServePosition.scaleInDP;
        float distanceX;
        float distanceY;

        if(finalballX>finalshoeX)
            distanceX=finalballX-finalballX;
        else
            distanceX = finalshoeX-finalballX;

        if(finalballY>finalshoeY)
            distanceY=finalballY-finalshoeY;
        else
            distanceY=finalshoeY-finalballY;

        float distanceXinM=distanceX/scale;
        float distanceYinM=distanceY/scale;

        float height=(float)2.60;

        float time=time2-time1;

        float finaldistanceinM= (float)Math.sqrt(distanceXinM * distanceXinM + distanceYinM * distanceYinM);
        float totalDistance= (float)Math.sqrt(finaldistanceinM*finaldistanceinM + height*height);
        float totalDistanceint=(int)totalDistance;
        float speed= totalDistance*3600/time;
        speed= speed* (float)1.2;
        int speedint=(int)speed;
        return speedint;
    }
}
