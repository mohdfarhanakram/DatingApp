/**
 * 
 */
package com.farru.android.network;

import com.farru.android.volley.VolleyError;

/**
 * @author FARHAN
 *
 */


public class VolleyReqError extends VolleyError {
    private int mEventType;
    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }


    public VolleyReqError(VolleyError error)
    {
        super(error.networkResponse);
    }
    public VolleyReqError(String messg) {
        super(messg);
    }

    public VolleyReqError() {
        super();
    }
}
