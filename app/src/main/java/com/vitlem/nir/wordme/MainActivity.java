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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private ArrayList<String> arrayRPCobject = new ArrayList<String>();
    private  RPClass TempRPCobject= null;
    private boolean Recording = false ;

    public final int MY_PERMISSIONS_REQUEST=1;
    private String TempFileName;
    private Integer index= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST);

        final Button RunButton = findViewById(R.id.RunButton );
        final Button PlayButton = findViewById(R.id.PlayButton );
        final Button AddButton = findViewById(R.id.AddButton );
        final Button RecordButton = findViewById(R.id.RecordButton);
        final Button SaveButton = findViewById(R.id.SaveButton);
        final Button LoaddButton = findViewById(R.id.LoadButton);
        final TextView tSum = findViewById(R.id.tSumWords);


        EditText et= (EditText)findViewById(R.id.tBet);
        et.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((EditText)view).setText("");

            }
        });

        tSum.setText( String.valueOf(index)  );

        RecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RecordButton", "ClickRecordButton");
                if (!Recording) {
                    RecordButton.setText("Recording");
                    RunButton.setEnabled(false);
                    PlayButton.setEnabled(false);
                    AddButton.setEnabled(false);
                    Log.i("RecordButton", "startRecording");
                    Toast.makeText(MainActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                    Recording = true;
                    TempRPCobject = new RPClass();
                    TempFileName = getFileName()+".3pg";
                    TempRPCobject.SetFileName(getApplicationContext(), TempFileName);
                    TempRPCobject.startRecording();
                } else {
                    if (TempRPCobject != null) {
                        Log.i("RecordButton", "TempRPCobject!=Null");
                        Log.i("RecordButton", "stopRecording");
                        Toast.makeText(MainActivity.this, "Recording Completed",
                                Toast.LENGTH_LONG).show();
                        RecordButton.setText("Record");
                        RunButton.setEnabled(true);
                        PlayButton.setEnabled(true);
                        AddButton.setEnabled(true);
                        Recording = false;
                        TempRPCobject.stopRecording();
                    } else {
                        Log.i("RecordButton", "Stop Record TempRPCobject==Null");
                    }
                }
            }


        });


        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PlayButton","ClickPlayButton");
                if (TempRPCobject != null) {
                    Log.i("PlayButton","TempRPCobject=!Null");
                    if (TempFileName!="") {
                        if (!TempRPCobject.Playing) {
                            PlayButton.setText("Playing");
                            Log.i("PlayButton", "startPlaying");
                            Toast.makeText(MainActivity.this, "Recording Playing",
                                    Toast.LENGTH_LONG).show();
                            TempRPCobject.SetFileName(getApplicationContext(), TempFileName);
                            TempRPCobject.startPlaying();
                            PlayButton.setText("Play");
                        } else {
                           // PlayButton.setText("Play");
                           // Log.i("PlayButton", "startPlaying");
                            TempRPCobject.stopPlaying();
                        }
                    }else
                    {
                        Toast.makeText(MainActivity.this, "Temp File Name is Empty, Record First",
                                Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddButton","ClickAddButtonn");
                if (TempRPCobject != null) {
                    arrayRPCobject.add(TempRPCobject.GetFileName());
                    Toast.makeText(MainActivity.this, "Add new File",
                            Toast.LENGTH_LONG).show();
                    index++;
                    tSum.setText(  String.valueOf(index)  );
                    Log.i("AddButton","TempRPCobject.GetFileName() " + TempRPCobject.GetFileName());
                    TempRPCobject=null;
                }
            }
        });


        RunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RunButton","ClickRunButton");
                if (!arrayRPCobject.isEmpty() ) {
                    Log.i("arrayRPCobject", "arrayRPCobjectisNotEmpty");

                    RPClass rTempPalyingObject = new RPClass();
                    TextView tv= findViewById(R.id.tBet);
                    try {
                        rTempPalyingObject.tBet = Integer.valueOf(tv.getText().toString())*1000;
                    }catch(Exception e)
                    {
                        rTempPalyingObject.tBet=10000;
                    }
                    rTempPalyingObject.startPlaying(arrayRPCobject);


                }
                else
                {
                    Log.i("arrayRPCobject","arrayRPCobjectisEmpty");
                    Toast.makeText(MainActivity.this, "Array File Name is Empty, Record First",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SaveButton","ClickSaveButton");

            }
        });

        LoaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LoaddButton","ClickLoaddButton");

            }
        });
    }


    private String getFileName()
    {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
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
