/**
 * 
 */
package com.digitalforce.datingapp.widgets;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author FARHAN
 *
 */


public  class MapScrollLayout extends RelativeLayout {

	private long lastTouched = 0;
	private static final long SCROLL_TIME = 200L; // 200 Milliseconds, but you can adjust that to your liking
	private UpdateMapAfterUserInterection updateMapAfterUserInterection;

	public MapScrollLayout(Context context) {
		super(context);
		// Force the host activity to implement the UpdateMapAfterUserInterection Interface
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastTouched = SystemClock.uptimeMillis();
			break;
		case MotionEvent.ACTION_UP:
			final long now = SystemClock.uptimeMillis();
			if (now - lastTouched > SCROLL_TIME) {
				// Update the map
				updateMapAfterUserInterection.onUpdateMapAfterUserInterection();
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	// Map Activity must implement this interface
    public interface UpdateMapAfterUserInterection {
        public void onUpdateMapAfterUserInterection();
    }
    
    
    public void setUpdateMapListener(UpdateMapAfterUserInterection updateMapAfterUserInterection){
    	this.updateMapAfterUserInterection = updateMapAfterUserInterection;
    }
}
