package com.digitalforce.datingapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import com.digitalforce.datingapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.model.UserInfo;
import com.digitalforce.datingapp.view.BaseActivity;
import com.digitalforce.datingapp.view.PlayVideoActivity;
import com.farru.android.ui.widget.CustomAlert;
import com.farru.android.utill.StringUtils;

public class AudioFragment extends Fragment{

    private View mView;
    private ProgressDialog pDialog;

    private UserInfo mUserInfo;

    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_fragment_audio, container, false);


        mView.findViewById(R.id.btn_play_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isNullOrEmpty(mUserInfo.getAudio()))
                   playAudio(mUserInfo.getAudio());
                else
                    ((BaseActivity)(getActivity())).showCommonError("No profile audio.");

            }
        });
        return mView;
    }





    public void userInfoInstance(UserInfo userInfo){
        mUserInfo = userInfo;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*if(mUserInfo!=null && !StringUtils.isNullOrEmpty(mUserInfo.getAudio())){
            mView.findViewById(R.id.btn_play_audio).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.audio_txt_view).setVisibility(View.GONE);

        }else{
            mView.findViewById(R.id.btn_play_audio).setVisibility(View.GONE);
            mView.findViewById(R.id.audio_txt_view).setVisibility(View.VISIBLE);

        }*/
    }

    private void playAudio(String url){

        try{
            ((BaseActivity)getActivity()).showProgressDialog();
            ((ImageView)mView.findViewById(R.id.btn_play_audio)).setEnabled(false);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // Listen for if the audio file can't be prepared
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    ((BaseActivity)getActivity()).removeProgressDialog();
                    ((ImageView)mView.findViewById(R.id.btn_play_audio)).setEnabled(true);
                    // ... react appropriately ...
                    // The MediaPlayer has moved to the Error state, must be reset!
                    return false;
                }
            });
            // Attach to when audio file is prepared for playing
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    ((BaseActivity)getActivity()).removeProgressDialog();
                    ((ImageView)mView.findViewById(R.id.btn_play_audio)).setEnabled(false);
                    mediaPlayer.start();
                }

            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    ((ImageView)mView.findViewById(R.id.btn_play_audio)).setEnabled(true);
                }
            });

            // Set the data source to the remote URL
            mediaPlayer.setDataSource(url);
            // Trigger an async preparation which will file listener when completed
            mediaPlayer.prepareAsync();


        }catch(Exception e){
            Log.e("Farhan ",e.getMessage());
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
}
