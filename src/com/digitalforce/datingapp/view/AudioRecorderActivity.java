/**
 * 
 */
package com.digitalforce.datingapp.view;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;

/**
 * @author FARHAN
 *
 */


public class AudioRecorderActivity extends BaseActivity
{
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_recorder);

		mTimeTv = (TextView)findViewById(R.id.timer_txtv);

		File f = new File(android.os.Environment.getExternalStorageDirectory(), "farhantemp.3gpp");

		mAudioMediaUrl = f.getAbsolutePath();

		prepareMediaPlayer();

		startBtn = (Button)findViewById(R.id.start);
		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRecordingTimer();
				start(v);
			}
		});

		stopBtn = (Button)findViewById(R.id.stop);
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopTimer();
				stop(v);
                Intent i = new Intent();
                i.putExtra(AppConstants.RECORDED_AUDIO_URL,mAudioMediaUrl);
				setResult(RESULT_OK,i);
				finish();
			}
		});

		playBtn = (Button)findViewById(R.id.play);
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				play(v);	
			}
		});

		stopPlayBtn = (Button)findViewById(R.id.stopPlay);
		stopPlayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopPlay(v);
				stopTimer();
				
			}
		});
	}

	private void prepareMediaPlayer() {
		time = 0;
		if(myRecorder==null){
			File f = createFile(3);
            //mAudioMediaUrl = f.getAbsolutePath();
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

		playBtn.setEnabled(false);
		stopPlayBtn.setEnabled(true);

		Toast.makeText(getApplicationContext(), "Start recording...", 
				Toast.LENGTH_SHORT).show();
	}

	public void stop(View view){
		try {
			myRecorder.stop();
			myRecorder.release();
			myRecorder  = null;

			stopBtn.setEnabled(false);
			playBtn.setEnabled(true);

			startBtn.setEnabled(true);

			Toast.makeText(getApplicationContext(), "Stop recording...",
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
			stopPlayBtn.setEnabled(true);

			Toast.makeText(getApplicationContext(), "Start play the recording...", 
					Toast.LENGTH_SHORT).show();
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

				Toast.makeText(getApplicationContext(), "Stop playing the recording...", 
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}


	class AudioTimerTask extends TimerTask {

		@Override
		public void run() {
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					if(time<=59){
						mTimeTv.setText(getTime(time));
						time++;
						
					}else{
						stopTimer();
                        Intent i = new Intent();
                        i.putExtra(AppConstants.RECORDED_AUDIO_URL,mAudioMediaUrl);
						setResult(RESULT_OK,i);

						finish();
					}
					
				}});
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
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}


}

