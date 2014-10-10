package com.farru.android.ui.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author m.farhan
 */
public class CustomAlert extends DialogFragment {

	private static final String	EXTRA_ALERT_ID				= "extra_alert_id";
	private static final String	EXTRA_TITLE					= "extra_title";
	private static final String	EXTRA_MESSAGE				= "extra_message";
	private static final String	EXTRA_POSITIVE_BUTTON_TEXT	= "extra_positive_button_text";

	/**
	 * @param pFragmentActivity
	 * @param pDialogTag
	 * @return
	 */
	public void show(FragmentActivity pFragmentActivity, String pDialogTag) {
		FragmentManager supportFragmentManager = pFragmentActivity.getSupportFragmentManager();
		Bundle args = new Bundle();
		args.putInt(EXTRA_ALERT_ID, alertId);
		args.putString(EXTRA_TITLE, title);
		args.putString(EXTRA_MESSAGE, message);
		args.putString(EXTRA_POSITIVE_BUTTON_TEXT, positiveButton);
		setArguments(args);
		show(supportFragmentManager, pDialogTag);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title and frame from dialog-fragment
		setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/*View dialogRootView = inflater.inflate(R.layout.dialog_custom_alert, container, false);

		Bundle bundle = getArguments();

		String title = bundle.getString(EXTRA_TITLE);
		if (StringUtils.isNullOrEmpty(title)) {
			dialogRootView.findViewById(R.id.txv_alert_title).setVisibility(View.GONE);
		} else {
			((TextView) dialogRootView.findViewById(R.id.txv_alert_title)).setText(title);
		}

		String message = bundle.getString(EXTRA_MESSAGE);
		if (StringUtils.isNullOrEmpty(message)) {
			dialogRootView.findViewById(R.id.txv_alert_message).setVisibility(View.GONE);
		} else {
			((TextView) dialogRootView.findViewById(R.id.txv_alert_message)).setText(message);
		}

		Button btnPositive = (Button) dialogRootView.findViewById(R.id.btn_positive);
		String positiveButtonText = bundle.getString(EXTRA_POSITIVE_BUTTON_TEXT);
		if (StringUtils.isNullOrEmpty(positiveButtonText)) {
			btnPositive.setText(android.R.string.ok);
		} else {
			btnPositive.setText(positiveButtonText);
		}

		final int alertId = bundle.getInt(EXTRA_ALERT_ID);
		btnPositive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activity activity = getActivity();
				if (activity instanceof IScreen) {
					((IScreen) activity).onEvent(Events.CUSTOM_ALERT_POSITIVE_BUTTON, alertId);
					dismiss();
				} else {
					dismiss();
				}
			}
		});

		return dialogRootView;*/
		
		return null;
	}

	/**
	 * Dependencies should not be in constructor
	 */
	private int		alertId;
	private String	title;
	private String	message;
	private String	positiveButton;

	/**
	 * @param alertId
	 *            the alertId to set
	 * 
	 * @return this to allow method chaining
	 */
	public CustomAlert setAlertId(int alertId) {
		this.alertId = alertId;
		return this;
	}

	/**
	 * @param title
	 *            the title to set
	 * 
	 * @return this to allow method chaining
	 */
	public CustomAlert setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * @param message
	 *            the message to set
	 * 
	 * @return this to allow method chaining
	 */
	public CustomAlert setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * @param positiveButton
	 *            the positiveButton to set
	 * 
	 * @return this to allow method chaining
	 */
	public CustomAlert setPositiveButton(String positiveButton) {
		this.positiveButton = positiveButton;
		return this;
	}
}