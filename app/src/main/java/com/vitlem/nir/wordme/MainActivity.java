package com.vitlem.nir.wordme;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private ArrayList<String> arrayRPCobject = new ArrayList<String>();
    private  RPClass TempRPCobject= null;
    private boolean Recording = false ;


    public final int MY_PERMISSIONS_REQUEST=1;
    public static Context mainc;
    private String TempFileName;
    private Integer index= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getApplicationContext().getResources();
        mainc=getApplicationContext();
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
       // MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");



        //Locale locale = new Locale("en","US");
        //Locale.setDefault(locale);

       // Configuration config = new Configuration();
        //config.locale = locale;

      //  res.updateConfiguration(config, res.getDisplayMetrics());
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET},MY_PERMISSIONS_REQUEST);
        //ConstraintLayout linrtl=(ConstraintLayout)findViewById(R.id.Mlayout);
        //linrtl.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        MobileAds.initialize(this, "ca-app-pub-3373354348631607~9109325808");
        AdView mAdView = findViewById(R.id.adViewUP);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = findViewById(R.id.adViewDown);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final Button RunButton = findViewById(R.id.RunButton );
        final Button PlayButton = findViewById(R.id.PlayButton );
        final Button AddButton = findViewById(R.id.AddButton );
        final Button RecordButton = findViewById(R.id.RecordButton);
        final Button SaveButton = findViewById(R.id.SaveButton);
        final Button LoadButton = findViewById(R.id.LoadButton);
        final Button hButton = findViewById(R.id.bHelp);
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
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist, Toast.LENGTH_LONG).show();
                } else {
                    if (!Recording) {
                        RecordButton.setText(R.string.RecordingText);
                        RunButton.setEnabled(false);
                        PlayButton.setEnabled(false);
                        AddButton.setEnabled(false);
                        Log.i("RecordButton", "startRecording");
                        Toast.makeText(MainActivity.this, R.string.Recordingstarted,
                                Toast.LENGTH_LONG).show();
                        Recording = true;
                        TempRPCobject = new RPClass();
                        TempFileName = getFileName() + ".3pg";
                        TempRPCobject.SetFileName(getApplicationContext(), TempFileName);
                        TempRPCobject.startRecording();
                    } else {
                        if (TempRPCobject != null) {
                            Log.i("RecordButton", "TempRPCobject!=Null");
                            Log.i("RecordButton", "stopRecording");
                            Toast.makeText(MainActivity.this, R.string.RecordingCompleted,
                                    Toast.LENGTH_LONG).show();
                            RecordButton.setText(R.string.RecordText);
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
            }
        });


        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PlayButton", "ClickPlayButton");
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist, Toast.LENGTH_LONG).show();
                } else {
                    if (TempRPCobject != null) {
                        Log.i("PlayButton", "TempRPCobject=!Null");
                        if (TempFileName != "") {
                            if (!TempRPCobject.Playing) {
                                PlayButton.setText("Playing");
                                Log.i("PlayButton", "startPlaying");
                                Toast.makeText(MainActivity.this, R.string.RecordingPlaying,
                                        Toast.LENGTH_LONG).show();
                                TempRPCobject.SetFileName(getApplicationContext(), TempFileName);
                                TempRPCobject.startPlaying();
                                PlayButton.setText("Play");
                            } else {
                                // PlayButton.setText("Play");
                                // Log.i("PlayButton", "startPlaying");
                                TempRPCobject.stopPlaying();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, R.string.Playtemp,
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AddButton", "ClickAddButtonn");
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist, Toast.LENGTH_LONG).show();
                } else {
                    if (TempRPCobject != null) {
                        arrayRPCobject.add(TempRPCobject.GetFileName());
                        Toast.makeText(MainActivity.this, R.string.AddnewFile,
                                Toast.LENGTH_LONG).show();
                        index++;
                        tSum.setText(String.valueOf(index));
                        Log.i("AddButton", "TempRPCobject.GetFileName() " + TempRPCobject.GetFileName());
                        TempRPCobject = null;
                    }
                }
            }
        });


        hButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("hButton","ClickhButtonn");
                showAddItemDialog(MainActivity.this);
            }
        });

        RunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist,Toast.LENGTH_LONG).show();
                } else {
                    Log.i("RunButton", "ClickRunButton");
                    if (!arrayRPCobject.isEmpty()) {
                        Log.i("arrayRPCobject", "arrayRPCobjectisNotEmpty");

                        RPClass rTempPalyingObject = new RPClass();
                        TextView tv = findViewById(R.id.tBet);
                        try {
                            rTempPalyingObject.tBet = Integer.valueOf(tv.getText().toString()) * 1000;
                        } catch (Exception e) {
                            rTempPalyingObject.tBet = 10000;
                        }
                        //new Thread(new Runnable() {
                            //@Override
                          //  public void run() {
                                rTempPalyingObject.startPlaying(arrayRPCobject);
                          //  }
                     //   }).run();



                    } else {
                        Log.i("arrayRPCobject", "arrayRPCobjectisEmpty");
                        Toast.makeText(MainActivity.this, R.string.arrayisempty,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            String m_chosen;
            @Override
            public void onClick(View view) {
                Log.i("SaveButton", "ClickSaveButton");
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist, Toast.LENGTH_LONG).show();
                } else {
                    if (!arrayRPCobject.isEmpty()) {
                        Log.i("Saved", "arrayRPCobject is not empty");

                        SimpleFileDialog FileSaveDialog = new SimpleFileDialog(MainActivity.this, "FileSave",
                                new SimpleFileDialog.SimpleFileDialogListener() {
                                    @Override
                                    public void onChosenDir(String chosenDir) {
                                        // The code in this function will be executed when the dialog OK button is pushed
                                        m_chosen = chosenDir;
                                        Toast.makeText(MainActivity.this, R.string.filechosen + " : " +
                                                m_chosen, Toast.LENGTH_LONG).show();
                                        write(getApplicationContext(), arrayRPCobject, m_chosen);
                                    }
                                });

                        //You can change the default filename using the public variable "Default_File_Name"
                        FileSaveDialog.Default_File_Name = "mylist";
                        FileSaveDialog.chooseFile_or_Dir();

                    } else {
                        Toast.makeText(MainActivity.this, R.string.emptylisttosave + " : " +
                                m_chosen, Toast.LENGTH_LONG).show();
                        Log.i("Saved", "arrayRPCobject is  empty");
                    }
                }
            }
        });

        LoadButton.setOnClickListener(new View.OnClickListener() {
            String m_chosen;
            @Override
            public void onClick(View view) {
                if (RPClass.Playing) {
                    Toast.makeText(MainActivity.this, R.string.playlist, Toast.LENGTH_LONG).show();
                } else {
                    SimpleFileDialog FileOpenDialog = new SimpleFileDialog(MainActivity.this, "FileOpen",
                            new SimpleFileDialog.SimpleFileDialogListener() {
                                @Override
                                public void onChosenDir(String chosenDir) {
                                    // The code in this function will be executed when the dialog OK button is pushed
                                    m_chosen = chosenDir;
                                    Toast.makeText(MainActivity.this, "Chosen FileOpenDialog File: " +
                                            m_chosen, Toast.LENGTH_LONG).show();
                                    Log.i("LoaddButton", "ClickLoaddButton " + m_chosen);
                                    try {
                                        if (m_chosen != "") {
                                            arrayRPCobject.clear();
                                            arrayRPCobject = read(getApplicationContext(), m_chosen);
                                            tSum.setText(String.valueOf(arrayRPCobject.size()));
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, R.string.fileerror + " : " +
                                                m_chosen, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                    //You can change the default filename using the public variable "Default_File_Name"
                    FileOpenDialog.Default_File_Name = "";
                    FileOpenDialog.chooseFile_or_Dir(getApplicationContext().getFilesDir().getAbsolutePath()
                            + File.separator + "serlization");


                }
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

    public static void write(Context context, Object nameOfClassGetterSetter,String fName) {
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = fName;
        ObjectOutput out = null;

        try {
            out= new ObjectOutputStream(new FileOutputStream(filename + ".slr"));
           // out = new ObjectOutputStream(new FileOutputStream(directory
            //        + File.separator + filename));
            out.writeObject(nameOfClassGetterSetter);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(Context context,String fName) {

        ObjectInputStream input = null;
        ArrayList<String> ReturnClass = null;
        String filename = fName;
        //File directory = new File(context.getFilesDir().getAbsolutePath()
        //
        //
        //       + File.separator + "serlization");
        try {

            input = new ObjectInputStream(new FileInputStream(fName));
           // input = new ObjectInputStream(new FileInputStream(directory
            //        + File.separator + filename));
            ReturnClass = (ArrayList<String>) input.readObject();
            input.close();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnClass;
    }
    @Override
    protected void onResume() {
        super.onResume();


    }

    private void showAddItemDialog(Context c) {
        final AdView mAdHelpView = new AdView(c);
        mAdHelpView.setAdSize(AdSize.BANNER);
        mAdHelpView.setAdUnitId("ca-app-pub-3373354348631607/2063661029");
        AdRequest adHelpRequest = new AdRequest.Builder().build();

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(R.string.HelpWindow)
                .setMessage(R.string.HelpText)

                .setView(mAdHelpView)
              /*  .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                    }
                })*/
                .setNegativeButton("Close", null)
                .create();
        dialog.show();
        mAdHelpView.loadAd(adHelpRequest);
    }

}
