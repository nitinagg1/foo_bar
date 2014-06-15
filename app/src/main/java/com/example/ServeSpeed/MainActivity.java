package com.example.ServeSpeed;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    final int RESULT_LOAD_VIDEO=1001;
    public static Uri selectedVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.Capture_Calculate:
                onCaptureCalculate(item);
                break;
            case R.id.Choose_Calculate:
                onChooseCalculate(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCaptureCalculate(MenuItem item)
    {
        final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_VIDEO);
    }

    private void CallPlayVideoAct()
    {
        Intent intent= new Intent(this,PlayVideoSelect2.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent videoReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, videoReturnedIntent);

        switch(requestCode)
        {
            case RESULT_LOAD_VIDEO:
                if(resultCode == RESULT_OK)
                {
                    selectedVideo = videoReturnedIntent.getData();
                }
            case 1000:
                if (resultCode == RESULT_OK)
                {
                }
        }
        CallPlayVideoAct();
    }

    public Uri ReturnVideoUri()
    {
        return selectedVideo;
    }

    public void onChooseCalculate(MenuItem item)
    {
        selectedVideo= GenerateTimeStampUri();
        if(selectedVideo!=null)
        {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,selectedVideo);
            startActivityForResult(intent,1000);
        }
    }

    File getDirectory()
    {
        File outPutDir=null;
        String externalstoragestate= Environment.getExternalStorageState();
        if(externalstoragestate.equals(Environment.MEDIA_MOUNTED))
        {
            File fileDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            outPutDir= new File(fileDir,"Nitin123");
            if(!outPutDir.exists())
            {
                if(!outPutDir.mkdirs())
                {
                    Toast.makeText(this, "output directory making failed", Toast.LENGTH_LONG).show();
                    outPutDir=null;
                }
            }
        }
        return outPutDir;
    }

    Uri GenerateTimeStampUri()
    {
        Uri TimeStampuri=null;
        File outputDir=getDirectory();

        if(outputDir!=null)
        {
            String Timestamp= new SimpleDateFormat("yyyymmdd_hhmmss").format(new Date());
            String ImageName= "VID_"+Timestamp+".mp4";
            File photofile= new File(outputDir,ImageName);
            TimeStampuri=Uri.fromFile(photofile);
        }
        return TimeStampuri;
    }
}
