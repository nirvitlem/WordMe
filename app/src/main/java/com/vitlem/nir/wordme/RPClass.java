package com.vitlem.nir.wordme;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class RPClass {
    private static String mFileName = null;
    private static MediaRecorder mRecorder = null;
    private static MediaPlayer   mPlayer = null;
    private static final String LOG_TAG = "AudioRecordTest";

    public void SetFileName(Context c,String f)
    {
        //mFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +File.separator+ f; //
        mFileName =  c.getFilesDir().getPath().toString() + File.separator +  f;
        Log.i("mFileName",mFileName);
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
            mPlayer.setDataSource(mFileName);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepare();
            mPlayer.setVolume(100f, 100f);
            mPlayer.start();
        } catch (IOException e) {
            Log.e("    public void startPlaying() ", "prepare() failed " + e.getMessage());
        }
    }

    public void stopPlaying() {
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
