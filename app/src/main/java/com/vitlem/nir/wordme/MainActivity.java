package com.vitlem.nir.wordme;

import android.Manifest;
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
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private ArrayList<RPClass> arrayRPCobject = new ArrayList<RPClass>();
    private RPClass TempRPCobject= null;
    private boolean Recording = false ;
    private boolean Playing = true ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        final Button RecordButton = findViewById(R.id.RecordButton);
        RecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RecordButton","ClickRecordButton");
                if (TempRPCobject != null) {
                    Log.i("RecordButton","TempRPCobject=!Null");
                    if (!Recording) {
                        RecordButton.setText("מקליט");
                        Log.i("RecordButton","startRecording");
                        Recording = true;
                        RPClass TempRPCobject = new RPClass();
                        TempRPCobject.SetFileName("1");
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
                        TempRPCobject.SetFileName("1");
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
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }
}
