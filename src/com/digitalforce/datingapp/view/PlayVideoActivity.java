/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;

/**
 * @author m.farhan
 *
 */
public class PlayVideoActivity extends BaseActivity{

    // Declare variables
    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    //String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    //private String VideoURL = "http://dfoutsourcingagency.com/dating/webservice/userData/23.mp4";

    private String VideoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_play_video);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);

        VideoURL  = getIntent().getStringExtra(AppConstants.USER_VIDEO_URL);

        getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
        pDialog.setTitle("Fetching Video");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

    }

    @Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub
		
	}

}
