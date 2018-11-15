package com.vitlem.nir.wordme;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private ArrayList<RPClass> arrayRPCobject = new ArrayList<RPClass>();
    private RPClass TempRPCobject= null;
    private boolean Recording = false ;
    private boolean Playing = true ;
    public final int MY_PERMISSIONS_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST);

        final Button RecordButton = findViewById(R.id.RecordButton);
        RecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RecordButton","ClickRecordButton");
                if (TempRPCobject == null) {
                    Log.i("RecordButton","TempRPCobject=!Null");
                    if (!Recording) {
                        RecordButton.setText("מקליט");
                        Log.i("RecordButton","startRecording");
                        Recording = true;
                        RPClass TempRPCobject = new RPClass();
                        TempRPCobject.SetFileName(getApplicationContext(),"1");
                        TempRPCobject.startRecording();
                    } else {
                        Log.i("RecordButton","stopRecording");
                        RecordButton.setText("הקלט");
                        Recording = false;
                        TempRPCobject.stopRecording();
                    }
                }

            }
        });

        final Button PlayButton = findViewById(R.id.PlayButton );
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PlayButton","ClickPlayButton");
                if (TempRPCobject != null) {
                    Log.i("PlayButton","TempRPCobject=!Null");
                    if (!Playing) {
                        PlayButton.setText("משמיע");
                        Log.i("PlayButton","startPlaying");
                        Playing = true;
                        TempRPCobject.SetFileName(getApplication(),"1");
                        TempRPCobject.startPlaying();
                    } else {
                        PlayButton.setText("השמע");
                        Log.i("PlayButton","startPlaying");
                        Playing = false;
                        TempRPCobject.stopPlaying();
                    }
                }
            }
        });

        final Button AddButton = findViewById(R.id.AddButton );
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddButton","ClickAddButtonn");
                if (TempRPCobject != null) {
                    Log.i("AddButton","TempRPCobject=!Null");
                    arrayRPCobject.add(TempRPCobject);
                }
            }
        });

        final Button RunButton = findViewById(R.id.RunButton );
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RunButton","ClickRunButton");
                if (!arrayRPCobject.isEmpty() ) {
                    Log.i("arrayRPCobject","arrayRPCobjectisNotEmpty");
                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }

    }
}
