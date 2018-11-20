package com.vitlem.nir.wordme;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RPClass {
    private static String mFileName = null;
    private static MediaRecorder mRecorder = null;
    private static MediaPlayer   mPlayer = null;
    public static boolean Playing = false ;
    public static Integer  tBet;


    public void SetFileName(Context c,String f)
    {
        //mFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +File.separator+ f; //
        mFileName =  c.getFilesDir().getPath().toString() + File.separator +  f;
        Log.i("mFileName",mFileName);
    }

    public String GetFileName()
    {
        return  mFileName;
    }

    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    public void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            Playing=true;
            mPlayer.setDataSource(mFileName);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepare();
            mPlayer.setVolume(100f, 100f);
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // Do something when media player end playing
                    Playing=false;
                    stopPlaying();
                    Log.i("public void startPlaying() ", "End Playing " );

                }
            });
        } catch (IOException e) {
            Log.e("public void startPlaying() ", "prepare() failed " + e.getMessage());
        }
    }

    public void startPlaying(final ArrayList<String> mFileNameArray) {
        mPlayer = new MediaPlayer();
        try {
            Playing=true;
            mPlayer.setDataSource(mFileNameArray.get(0));
            Log.i("public void startPlaying(String mFileName) ", "Start Playing " + mFileNameArray.get(0) );
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepare();
            mPlayer.setVolume(100f, 100f);
            mPlayer.start();
            mFileNameArray.remove(0);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // Do something when media player end playing
                    if (mFileNameArray.isEmpty()){
                        Playing=false;
                        stopPlaying();
                        Log.i("public void startPlaying(String mFileName) ", "End Playing " );
                    }else
                    {
                        try {
                            Thread.sleep(tBet);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        startPlaying(mFileNameArray);
                    }

                }
            });
        } catch (IOException e) {
            Log.i("public void startPlaying() ", "prepare() failed " + e.getMessage());
        }
    }

    public void stopPlaying() {
        Playing=false;
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.i("startRecording", "prepare() failed" + " " + e.getMessage());
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

}
