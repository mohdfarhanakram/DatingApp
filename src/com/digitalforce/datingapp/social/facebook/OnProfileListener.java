package com.digitalforce.datingapp.social.facebook;

import com.sromku.simple.fb.entities.Profile;

public class OnProfileListener extends com.sromku.simple.fb.listeners.OnProfileListener {
    private ProfileHelper mProfileCallback;

    public OnProfileListener(ProfileHelper ph) {
        mProfileCallback = ph;
    }

    public void onComplete(Profile profile) {
        if (null != mProfileCallback) {
            mProfileCallback.onProfile(profile);
        }
    }

    public void onFail(String reason) {
        if (null != mProfileCallback) {
            mProfileCallback.onProfileFail(reason);
        }
    }

    public void onException(Throwable th) {
        if (null != mProfileCallback) {
            mProfileCallback.onProfileException(th);
        }
    }
}