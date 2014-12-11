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

import android.widget.TextView;
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
        mwebViewTC.getSettings().setJavaScriptEnabled(true);
        //mwebViewTC.getSettings().setPluginsEnabled(true);
		/*file=new File("file:///android_asset/google_privacy.pdf");
		mwebViewTC.getSettings().setJavaScriptEnabled(true);*/
		mwebViewTC.loadUrl(AppConstants.TERM_AND_CONDITION_LINK);
       // mwebViewTC.loadUrl("https://docs.google.com/gview?embedded=true&url="+ AppConstants.PDF_URL);
		//CopyReadAssets();
		
		mbtnAccept.setOnClickListener(this);
		mbtnCancel.setOnClickListener(this);

        setTCData();
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

    private void setTCData(){
        ((TextView)findViewById(R.id.tc_text_1)).setText(DatingConstants.HEADER_TEXT_1);
        ((TextView)findViewById(R.id.tc_text_2)).setText(DatingConstants.HEADER_TEXT_2);
        ((TextView)findViewById(R.id.tc_text_3)).setText(DatingConstants.HEADER_TEXT_3);

        ((TextView)findViewById(R.id.tc_text_4)).setText(DatingConstants.ELIGIBILITY_TEXT_1);
        ((TextView)findViewById(R.id.tc_text_5)).setText(DatingConstants.ELIGIBILITY_TEXT_2);
        ((TextView)findViewById(R.id.tc_text_6)).setText(DatingConstants.ELIGIBILITY_TEXT_3);

        ((TextView)findViewById(R.id.tc_service)).setText(DatingConstants.TC_SERVICE_TEXT);
        ((TextView)findViewById(R.id.tc_service_1)).setText(DatingConstants.SERVICEABILITY_TEXT_1);
        ((TextView)findViewById(R.id.tc_service_2)).setText(DatingConstants.SERVICEABILITY_TEXT_2);
        ((TextView)findViewById(R.id.tc_service_3)).setText(DatingConstants.SERVICEABILITY_TEXT_3);
        ((TextView)findViewById(R.id.tc_service_4)).setText(DatingConstants.SERVICEABILITY_TEXT_4);
        ((TextView)findViewById(R.id.tc_service_5)).setText(DatingConstants.SERVICEABILITY_TEXT_5);
        ((TextView)findViewById(R.id.tc_service_6)).setText(DatingConstants.SERVICEABILITY_TEXT_6);
        ((TextView)findViewById(R.id.tc_service_7)).setText(DatingConstants.SERVICEABILITY_TEXT_7);
        ((TextView)findViewById(R.id.tc_service_8)).setText(DatingConstants.SERVICEABILITY_TEXT_8);
        ((TextView)findViewById(R.id.tc_service_9)).setText(DatingConstants.SERVICEABILITY_TEXT_9);
        ((TextView)findViewById(R.id.tc_service_10)).setText(DatingConstants.SERVICEABILITY_TEXT_10);
        ((TextView)findViewById(R.id.tc_service_11)).setText(DatingConstants.SERVICEABILITY_TEXT_11);
        ((TextView)findViewById(R.id.tc_service_12)).setText(DatingConstants.SERVICEABILITY_TEXT_12);
        ((TextView)findViewById(R.id.tc_service_13)).setText(DatingConstants.SERVICEABILITY_TEXT_13);

        ((TextView)findViewById(R.id.tc_proprietary_1)).setText(DatingConstants.PROPRIETARY_1);
        ((TextView)findViewById(R.id.tc_proprietary_2)).setText(DatingConstants.PROPRIETARY_2);
        ((TextView)findViewById(R.id.tc_proprietary_3)).setText(DatingConstants.PROPRIETARY_3);
        ((TextView)findViewById(R.id.tc_proprietary_4)).setText(DatingConstants.PROPRIETARY_4);

        ((TextView)findViewById(R.id.tc_use_1)).setText(DatingConstants.USE_1);
        ((TextView)findViewById(R.id.tc_use_2)).setText(DatingConstants.USE_2);
        ((TextView)findViewById(R.id.tc_use_3)).setText(DatingConstants.USE_3);

        ((TextView)findViewById(R.id.tc_sponsors)).setText(DatingConstants.SPONSORS);

        ((TextView)findViewById(R.id.tc_disclaimer_1)).setText(DatingConstants.DISCLAIMER_1);
        ((TextView)findViewById(R.id.tc_disclaimer_2)).setText(DatingConstants.DISCLAIMER_2);
        ((TextView)findViewById(R.id.tc_disclaimer_3)).setText(DatingConstants.DISCLAIMER_3);

        ((TextView)findViewById(R.id.tc_liability_1)).setText(DatingConstants.LIABILITY_1);
        ((TextView)findViewById(R.id.tc_liability_2)).setText(DatingConstants.LIABILITY_2);
        ((TextView)findViewById(R.id.tc_liability_3)).setText(DatingConstants.LIABILITY_3);

        ((TextView)findViewById(R.id.tc_ideminification)).setText(DatingConstants.INDEMNIFICATION);
        ((TextView)findViewById(R.id.tc_trem_termination)).setText(DatingConstants.TERMINATION);

        ((TextView)findViewById(R.id.tc_arbitration_1)).setText(DatingConstants.ARBITRATION_1);
        ((TextView)findViewById(R.id.tc_arbitration_2)).setText(DatingConstants.ARBITRATION_2);
        ((TextView)findViewById(R.id.tc_arbitration_3)).setText(DatingConstants.ARBITRATION_3);
        ((TextView)findViewById(R.id.tc_arbitration_4)).setText(DatingConstants.ARBITRATION_4);
        ((TextView)findViewById(R.id.tc_arbitration_5)).setText(DatingConstants.ARBITRATION_5);

        ((TextView)findViewById(R.id.tc_general_provisions)).setText(DatingConstants.GENERAL_PROVISIONS);

        ((TextView)findViewById(R.id.tc_digital_millenium)).setText(DatingConstants.DIGITAL_MILLENNIUM);
        ((TextView)findViewById(R.id.tc_digital_millenium_1)).setText(DatingConstants.DIGITAL_MILLENIUM_1);
        ((TextView)findViewById(R.id.tc_digital_millenium_2)).setText(DatingConstants.DIGITAL_MILLENIUM_2);
        ((TextView)findViewById(R.id.tc_digital_millenium_3)).setText(DatingConstants.DIGITAL_MILLENIUM_3);
        ((TextView)findViewById(R.id.tc_digital_millenium_4)).setText(DatingConstants.DIGITAL_MILLENIUM_4);
        ((TextView)findViewById(R.id.tc_digital_millenium_5)).setText(DatingConstants.DIGITAL_MILLENIUM_5);
        ((TextView)findViewById(R.id.tc_digital_millenium_6)).setText(DatingConstants.DIGITAL_MILLENIUM_6);

        ((TextView)findViewById(R.id.tc_digital_millenium_other)).setText(DatingConstants.DMC_OTHER);

        ((TextView)findViewById(R.id.tc_digital_millenium_add_1)).setText(DatingConstants.DMC_OTHER_1);
        ((TextView)findViewById(R.id.tc_digital_millenium_add_2)).setText(DatingConstants.DMC_OTHER_2);
        ((TextView)findViewById(R.id.tc_digital_millenium_add_3)).setText(DatingConstants.DMC_OTHER_3);

        ((TextView)findViewById(R.id.tc_revision_date)).setText(DatingConstants.REVISION_DATE);



    }

}


