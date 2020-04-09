package com.example.prca_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
    private static Button Stop;
    private static Button Play;
    private static Button Record;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Record = (Button) findViewById(R.id.rcd);
        Play = (Button) findViewById(R.id.ply);
        Stop = (Button) findViewById(R.id.stp);


        if (!hasMicrophone())
        {
            Stop.setEnabled(false);
            Play.setEnabled(false);
            Record.setEnabled(false);
        } else {
            Play.setEnabled(false);
            Stop.setEnabled(false);
        }

        audioFilePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/myaudio.3gp";

    }

    protected boolean hasMicrophone() {

            PackageManager pmanager = this.getPackageManager();
            return pmanager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio (View view) throws IOException
    {
        isRecording = true;
        Stop.setEnabled(true);
        Play.setEnabled(false);
        Record.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public void stopAudio (View view)
    {

        Stop.setEnabled(false);
        Play.setEnabled(true);

        if (isRecording)
        {
            Record.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            Record.setEnabled(true);
        }
    }

    public void playAudio (View view) throws IOException
    {
        Play.setEnabled(false);
        Record.setEnabled(false);
        Stop.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}

