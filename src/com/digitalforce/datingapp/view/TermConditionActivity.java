package com.digitalforce.datingapp.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.constants.DatingConstants;
import com.digitalforce.datingapp.persistance.DatingAppPreference;

public class TermConditionActivity extends Activity implements OnClickListener{

	private Button mbtnCancel, mbtnAccept;
	private WebView mwebViewTC;
	private File file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_term_condition);
		
		mwebViewTC = (WebView) findViewById(R.id.web_tc);
		mbtnCancel = (Button) findViewById(R.id.btn_tc_cancel);
		mbtnAccept = (Button) findViewById(R.id.btn_tc_accept);
		
		/*file=new File("file:///android_asset/google_privacy.pdf");
		mwebViewTC.getSettings().setJavaScriptEnabled(true);*/
		mwebViewTC.loadUrl(AppConstants.TERM_AND_CONDITION_LINK);
		
		//CopyReadAssets();
		
		mbtnAccept.setOnClickListener(this);
		mbtnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_tc_accept:
			DatingAppPreference.putBoolean(DatingAppPreference.USER_TC_ACCEPT, true, this);
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			finish();
			break;
		case R.id.btn_tc_cancel:
			finish();
			break;

		default:
			break;
		}
		
	}
	private void CopyReadAssets()
    {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), "google_privacy.pdf");
        try
        {
            in = assetManager.open("google_privacy.pdf");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() + "/google_privacy.pdf"),
                "application/pdf");

        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

}
