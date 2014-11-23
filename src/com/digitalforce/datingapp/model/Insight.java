package com.digitalforce.datingapp.model;

import com.farru.android.parcelable.GlobalParcelable;

/**
 * Created by m.farhan on 11/12/14.
 */
public class Insight extends GlobalParcelable{
    private String agebetween18and19;
    private String agebetween20and24;
    private String agebetween25and29;
    private String replyRate;
    private String weight;
    private String height;

    public String getAgebetween18and19() {
        return agebetween18and19;
    }

    public void setAgebetween18and19(String agebetween18and19) {
        this.agebetween18and19 = agebetween18and19;
    }

    public String getAgebetween20and24() {
        return agebetween20and24;
    }

    public void setAgebetween20and24(String agebetween20and24) {
        this.agebetween20and24 = agebetween20and24;
    }

    public String getAgebetween25and29() {
        return agebetween25and29;
    }

    public void setAgebetween25and29(String agebetween25and29) {
        this.agebetween25and29 = agebetween25and29;
    }

    public String getReplyRate() {
        return replyRate;
    }

    public void setReplyRate(String replyRate) {
        this.replyRate = replyRate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
