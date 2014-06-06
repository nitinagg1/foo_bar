package com.example.ServeSpeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 4/13/14.
 */
public class DisplaySpeed extends Activity {

    public TextView tv;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_speed);
        tv=(TextView)findViewById(R.id.textViewSpeed);
    }
    public void GiveResult(View view)
    {
        PlayVideoSelect.RacquetTouchOnce=0;
        double bouncex= BounceCoordinates.bouncepointX;
        double bouncey=BounceCoordinates.bouncepointY;

        double contactx=ServeCoordinates.servepointX;
        double contacty=ServeCoordinates.servepointY;

        int contactTime=PlayVideoSelect.racquetContactTime;
        int bounceTime=PlayVideoSelect.ballBounceTime;
        double height=2.70;
        double distanceY=bouncey-contacty;
        double distanceX=contactx-bouncex;
        double distance1;
        double distance2=Math.sqrt(distanceX*distanceX +distanceY*distanceY);
        double meterConverter=41.65;
        //distance2=distance2-distanceY;
        distance2=distance2/meterConverter;
        distance1= Math.sqrt(distance2*distance2 + height*height);
        //distance2=distance2/meterConverter;

        double finaldistance=distance1;
        double time=bounceTime-contactTime;
        double speed=((finaldistance*3600)/time);
        int speedInt=(int)speed;
        tv.setText(speedInt);
        tv.setVisibility(0);

    }
}
