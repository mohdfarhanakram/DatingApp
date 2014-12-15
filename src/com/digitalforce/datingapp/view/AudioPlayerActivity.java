package com.digitalforce.datingapp.view;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;

/**
 * Created by FARHAN on 12/15/2014.
 */
public class AudioPlayerActivity extends BaseActivity{

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        String url = getIntent().getStringExtra(AppConstants.RECORDED_AUDIO_URL);
        playAudio(url);
    }



    private void playAudio(String url){

        try{
            showProgressDialog();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // Listen for if the audio file can't be prepared
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    removeProgressDialog();
                    return false;
                }
            });
            // Attach to when audio file is prepared for playing
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    removeProgressDialog();
                    mediaPlayer.start();
                }

            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    removeProgressDialog();
                }
            });

            // Set the data source to the remote URL
            mediaPlayer.setDataSource(url);
            // Trigger an async preparation which will file listener when completed
            mediaPlayer.prepareAsync();


        }catch(Exception e){
            Log.e("Farhan ", e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    public void onEvent(int eventId, Object eventData) {

    }
}
