/**
 *
 */
package com.farru.android.network;

import java.util.Hashtable;

import org.json.JSONObject;

import com.digitalforce.datingapp.model.DatingBaseModel;

/**
 *
 */
public class ServiceResponse {

    private long apiResponseTime;
    private String message;
    public static final int VALIDATION_ERROR = 4;
    public static final int MESSAGE_ERROR = 5;
    public static final int SUCCESS = 6;

    private JSONObject jsonResponse;
    private String errorText;
    private int errorCode = 3;

    private Exception exception;
    private Hashtable headers;
    private String errorMessages;
    private DatingBaseModel baseModel;
    private Object responseObject;
    private int eventType;

    private Object mRequestData = null;

    public long getParsingTime() {
        return parsingTime;
    }

    public void setParsingTime(long parsingTime) {
        this.parsingTime = parsingTime;
    }

    public long getApiResponseTime() {
        return apiResponseTime;
    }

    public void setApiResponseTime(long apiResponseTime) {
        this.apiResponseTime = apiResponseTime;
    }

    private long parsingTime;
    public static final int EXCEPTION = 1;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   

    public Object getRequestData() {
        return mRequestData;
    }

    public void setRequestData(Object requestData) {
        this.mRequestData = requestData;
    }

    public boolean isRetryLimitExceeded() {
        return isRetryLimitExceeded;
    }

    public void setRetryLimitExceeded(boolean isRetryLimitExceeded) {
        this.isRetryLimitExceeded = isRetryLimitExceeded;
    }

    private boolean isRetryLimitExceeded;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    private String addressId;


    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }


    /**
     * @return the jabongBaseModel
     */
    public DatingBaseModel getBaseModel() {
        return baseModel;
    }

    /**
     * @param BaseModel the BaseModel to set
     */
    public void setBaseModel(DatingBaseModel baseModel) {
        this.baseModel = baseModel;
    }


    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }


    public Hashtable getHeaders() {
        return headers;
    }

    public void setHeaders(Hashtable headers) {
        this.headers = headers;
    }

    public boolean isError() {
        return getErrorCode() > 0;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
