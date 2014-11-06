package com.digitalforce.datingapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.view.PlayVideoActivity;

public class VideoFragment extends Fragment{

	private View mView;
	private ProgressDialog pDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.layout_fragment_video, container, false);

        mView.findViewById(R.id.btn_play_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PlayVideoActivity.class);
                startActivity(i);
            }
        });
		return mView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		/*final VideoView videoview = (VideoView)mView.findViewById(R.id.profile_video);


				 // Create a progressbar
		        pDialog = new ProgressDialog(getActivity());
		        // Set progressbar title
		        pDialog.setTitle("Video Streaming");
		        // Set progressbar message
		        pDialog.setMessage("Buffering...");
		        pDialog.setIndeterminate(false);
		        pDialog.setCancelable(false);
		        // Show progressbar
		        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(getActivity());
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(AppConstants.TESTING_VIDEO_URL);
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

*/

}
				

}
