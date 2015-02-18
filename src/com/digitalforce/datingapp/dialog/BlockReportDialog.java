package com.digitalforce.datingapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.listener.BlockReportListener;

/**
 * Created by FARHAN on 2/14/2015.
 */


public class BlockReportDialog extends Dialog implements android.view.View.OnClickListener{

      private BlockReportListener mListener;

    public BlockReportDialog(Context context,BlockReportListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_block_report);

        findViewById(R.id.btn_report).setOnClickListener(this);
        findViewById(R.id.btn_block).setOnClickListener(this);
        findViewById(R.id.close_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_report:
                dismiss();
                mListener.onBlockReport(false);
                break;
            case R.id.btn_block:
                mListener.onBlockReport(true);
                dismiss();
                break;
            case R.id.close_btn:
                dismiss();
                break;

            default:
                break;
        }
    }

}

