package com.digitalforce.datingapp.social.facebook;

import com.sromku.simple.fb.entities.Profile;

/**
 * Created by 201101044 on 3/15/14.
 */
public interface ProfileHelper {
    public void onProfileFail(String reasong);

    public void onProfileException(Throwable t);

    public void onProfile(Profile p);
}
