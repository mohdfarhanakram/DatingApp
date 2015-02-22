package com.digitalforce.datingapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.listener.AudioRecordingCompleteListener;
import com.digitalforce.datingapp.listener.BlockReportListener;
import com.digitalforce.datingapp.view.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by m.farhan on 2/18/2015.
 */

public class AudioRecordingDialog extends Dialog {

    private AudioRecordingCompleteListener mListener;

    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;
    private Button startBtn;
    private Button stopBtn;
    private Button playBtn;
    private Button stopPlayBtn;

    private int time = 0;

    private TextView mTimeTv;

    private AudioTimerTask mAudioTimerTask;
    private Timer mTimer;

    private String mEncodeString;

    private String mAudioMediaUrl;

    private Context mContext;

    public AudioRecordingDialog(Context context,AudioRecordingCompleteListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_audio_recorder);

        mTimeTv = (TextView)findViewById(R.id.timer_txtv);

        File f = new File(android.os.Environment.getExternalStorageDirectory(), "farhantemp.3gpp");

        mAudioMediaUrl = f.getAbsolutePath();

        prepareMediaPlayer();

        startBtn = (Button)findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startRecordingTimer();
                start(v);
            }
        });

        stopBtn = (Button)findViewById(R.id.stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopTimer();
                stop(v);
                mListener.onRecordingCompleted(mAudioMediaUrl);
                dismiss();
            }
        });

        playBtn = (Button)findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                play(v);
            }
        });

        stopPlayBtn = (Button)findViewById(R.id.stopPlay);
        stopPlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopPlay(v);
                stopTimer();

            }
        });

        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
                stop(view);
                dismiss();
            }
        });

    }


    private void prepareMediaPlayer() {
        time = 0;
        if(myRecorder==null){

            myRecorder = new MediaRecorder();
            myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myRecorder.setOutputFile(mAudioMediaUrl);
        }

    }

    public void start(View view){
        try {

            prepareMediaPlayer();

            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);

        startBtn.setBackgroundResource(R.drawable.cancel_button_no_txt);
        startBtn.setTextColor(Color.parseColor("#6F6F6F"));

        stopBtn.setBackgroundResource(R.drawable.ok_button_no_txt);
        stopBtn.setTextColor(Color.WHITE);


        playBtn.setEnabled(false);
        stopPlayBtn.setEnabled(true);

    }

    public void stop(View view){
        try {
            myRecorder.stop();
            myRecorder.release();
            myRecorder  = null;

            stopBtn.setEnabled(false);
            playBtn.setEnabled(true);

            startBtn.setEnabled(true);

            Toast.makeText(mContext, "Stop recording...",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }

    public void play(View view) {
        try{
            myPlayer = new MediaPlayer();
            myPlayer.setDataSource(outputFile);
            myPlayer.prepare();
            myPlayer.start();

            playBtn.setEnabled(false);
            playBtn.setBackgroundResource(R.drawable.cancel_button_no_txt);
            stopPlayBtn.setEnabled(true);
            stopPlayBtn.setBackgroundResource(R.drawable.ok_button_no_txt);

            Toast.makeText(mContext, "Start play the recording...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stopPlay(View view) {
        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.release();
                myPlayer = null;
                playBtn.setEnabled(true);
                stopPlayBtn.setEnabled(false);

                Toast.makeText(mContext, "Stop playing the recording...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    class AudioTimerTask extends TimerTask {

        @Override
        public void run() {
            ((BaseActivity)mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (time <= 59) {
                        mTimeTv.setText(getTime(time));
                        time++;

                    } else {
                        stopTimer();
                        mListener.onRecordingCompleted(mAudioMediaUrl);
                        dismiss();
                    }

                }
            });
        }


    }


    private String getTime(int time){

        String timeString = time+"";
        if(time<=9){
            timeString = "0"+timeString;
        }

        timeString = "00:"+timeString+"/01:00";

        return timeString;

    }


    private void startRecordingTimer(){
        stopTimer();
        mAudioTimerTask = new AudioTimerTask();
        mTimer = new Timer();
        mTimer.schedule(mAudioTimerTask, 1000, 1000);
    }

    private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(myRecorder!=null){
            myRecorder.release();
            myRecorder  = null;
        }
    }

}
