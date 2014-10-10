package com.farru.android.ui;

import com.farru.android.network.ServiceResponse;

/**
 * @author m.farhan
 */
public interface IScreen {
	
	void handleUiUpdate(ServiceResponse serviceResponse);
	void onEvent(int eventId, Object eventData);
}
