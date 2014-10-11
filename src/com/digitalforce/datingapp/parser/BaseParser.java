package com.digitalforce.datingapp.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.digitalforce.datingapp.constants.ApiEvent;
import com.digitalforce.datingapp.model.DatingBaseModel;
import com.farru.android.network.ServiceResponse;
import com.farru.android.parser.IParser;

/**
 * Created  on 31/1/14.
 */
public class BaseParser implements IParser {
	
	

    public ServiceResponse parseData(int eventType, String data) {
        ServiceResponse response = null;
        try {
            response = handleJsonResponse(eventType, new JSONObject(data));
        } catch (Exception e) {
            response = new ServiceResponse();
            response.setErrorCode(ServiceResponse.EXCEPTION);
        }
        return response;
    }

    protected DatingBaseModel parseBaseData(JSONObject jsonObject)
            throws JSONException {

    	DatingBaseModel baseModel = new DatingBaseModel();
    	baseModel.setMessageCode(jsonObject.optInt("MessageCode", 102));
    	baseModel.setErrorMsg(jsonObject.optString("error"));
        return baseModel;
    }

    protected ServiceResponse handleJsonResponse(int eventType, JSONObject jsonObject) {
        ServiceResponse response = new ServiceResponse();
        try {
        	DatingBaseModel baseModel = parseBaseData(jsonObject);
            response.setJsonResponse(jsonObject);
            response.setEventType(eventType);
            response.setJsonResponse(jsonObject);
            response.setBaseModel(baseModel);
            
            if(baseModel.getMessageCode()==100){
            	response.setErrorCode(ServiceResponse.SUCCESS);
            	parseJsonData(response);
            }else if(baseModel.getMessageCode()==102 || baseModel.getMessageCode()==101){
            	response.setErrorCode(ServiceResponse.MESSAGE_ERROR);
            	handleError(response);
            }else{
            	
            }
            

        } catch (Exception e) {
            response.setErrorCode(ServiceResponse.EXCEPTION);
            e.printStackTrace();
        }
        return response;
    }

   

    protected void handleError(ServiceResponse response) throws JSONException {
    	JSONObject jsonObject = response.getJsonResponse();
    	
    	switch (response.getEventType()) {
        case ApiEvent.LOGIN_EVENT:
        case ApiEvent.SIGN_UP_EVENT:
        case ApiEvent.FORGOT_PASSWORD_EVENT:
        	response.setErrorMessages(jsonObject.optString("error", null));
        	break;
            default:
                break;
        }

       
    }

    protected void parseJsonData(ServiceResponse response) throws JSONException {
    	JSONObject jsonObject = response.getJsonResponse();
        switch (response.getEventType()) {
        case ApiEvent.LOGIN_EVENT:
        	response.setResponseObject(JsonParser.parseLoginJson(jsonObject));
        	break;
        case ApiEvent.SIGN_UP_EVENT:
        	response.setResponseObject(JsonParser.parseSignUpJson(jsonObject));
        	break;
        case ApiEvent.FORGOT_PASSWORD_EVENT:
        	response.setResponseObject(JsonParser.parseForgotPasswordJson(jsonObject));
        	break;
        case ApiEvent.USER_NEAR_BY_EVENT:
        	response.setResponseObject(JsonParser.parseNearByUserJson(jsonObject));
        	break;
            default:
                break;
        }

    }


}
