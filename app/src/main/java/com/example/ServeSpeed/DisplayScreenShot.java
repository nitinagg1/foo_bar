package com.example.ServeSpeed;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by lenovo on 4/13/14.
 */
public class DisplayScreenShot extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_screenshot);
        ImageView v=(ImageView)findViewById(R.id.screenshotid);
        v.setBackgroundDrawable(PlayVideoSelect.bitmapDrawable);
        v.setVisibility(0);
    }
}
