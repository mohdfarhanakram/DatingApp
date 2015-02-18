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
        baseModel.setSuccessMsg(jsonObject.optString("success"));
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

            if (eventType == ApiEvent.SHOW_PRIVATE_PICTURE_EVENT || eventType == ApiEvent.SHOW_PUBLIC_PICTURE_EVENT) {
                baseModel.setMessageCode(100);
            }

            if (baseModel.getMessageCode() == 100) {
                response.setErrorCode(ServiceResponse.SUCCESS);
                parseJsonData(response);
            } else if (baseModel.getMessageCode() == 102 || baseModel.getMessageCode() == 101) {
                response.setErrorCode(ServiceResponse.MESSAGE_ERROR);
                handleError(response);
            } else {

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
            case ApiEvent.USER_NEAR_BY_EVENT:
            case ApiEvent.SHOW_PROFILE_EVENT:
            case ApiEvent.ADD_TO_FAVOURITE:
            case ApiEvent.REMOVE_TO_FAVOURITE:
            case ApiEvent.SHOW_FAVOURITED_BY:
            case ApiEvent.SHOW_MY_FAVOURITE:
            case ApiEvent.UPDATE_PROFILE_EVENT:
            case ApiEvent.UPLOAD_PROFILE_PIC_EVENT:
            case ApiEvent.UPLOAD_PRIVATE_PICTURE_EVENT:
            case ApiEvent.UPLOAD_PUBLIC_PICTURE_EVENT:
            case ApiEvent.MATCH_FINDER_EVENT:
            case ApiEvent.My_BUZZ_EVENT:
            case ApiEvent.UPLOAD_PROFILE_VIDEO_EVENT:
            case ApiEvent.UPLOAD_PROFILE_AUDIO_EVENT:
            case ApiEvent.WHOS_VIEWD_U_EVENT:
            case ApiEvent.ON_LINE_USER:
            case ApiEvent.CHAT_HISTORY_EVENT:
            case ApiEvent.SEND_MSG_EVENT:
            case ApiEvent.MARK_INTERESTED_EVENT:
            case ApiEvent.MARK_NOT_INTERESTED_EVENT:
            case ApiEvent.SEARCH_EVENT:
            case ApiEvent.LIKE_EVENT:
            case ApiEvent.DELETE_PUBLIC_PHOTO_EVENT:
            case ApiEvent.DELETE_PRIVATE_PHOTO_EVENT:
            case ApiEvent.REPORT_USER_EVENT:
            case ApiEvent.BLOCK_USER_EVENT:
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
            case ApiEvent.LOGIN_FB_GMAIL_EVENT:
                response.setResponseObject(JsonParser.parseLoginJson(jsonObject));
                break;
            case ApiEvent.SIGN_UP_EVENT:
                response.setResponseObject(JsonParser.parseSignUpJson(jsonObject));
                break;
            case ApiEvent.FORGOT_PASSWORD_EVENT:
                response.setResponseObject(JsonParser.parseForgotPasswordJson(jsonObject));
                break;
            case ApiEvent.USER_NEAR_BY_EVENT:
            case ApiEvent.SHOW_FAVOURITED_BY:
            case ApiEvent.SHOW_MY_FAVOURITE:
            case ApiEvent.MATCH_FINDER_EVENT:
            case ApiEvent.My_BUZZ_EVENT:
            case ApiEvent.WHOS_VIEWD_U_EVENT:
            case ApiEvent.ON_LINE_USER:
            case ApiEvent.SEARCH_EVENT:
                response.setResponseObject(JsonParser.parseNearByUserJson(jsonObject));
                break;
            case ApiEvent.SHOW_PROFILE_EVENT:
                response.setResponseObject(JsonParser.parseNearByUserJson(jsonObject));
                break;
            case ApiEvent.SHOW_PRIVATE_PICTURE_EVENT:
            case ApiEvent.SHOW_PUBLIC_PICTURE_EVENT:
                response.setResponseObject(JsonParser.parseMyPicture(jsonObject));
                break;
            case ApiEvent.UPLOAD_PROFILE_PIC_EVENT:
                response.setResponseObject(JsonParser.parseUploadImageJson(jsonObject));
                break;
            case ApiEvent.UPLOAD_PRIVATE_PICTURE_EVENT:
            case ApiEvent.UPLOAD_PUBLIC_PICTURE_EVENT:
                response.setResponseObject(JsonParser.parseAddPrivatePublicPhotoJson(jsonObject));
                break;
            case ApiEvent.CHAT_HISTORY_EVENT:
                response.setResponseObject(JsonParser.parseUserChat(jsonObject));
                break;
            case ApiEvent.SEND_MSG_EVENT:
                break;
            case ApiEvent.MARK_INTERESTED_EVENT:
            case ApiEvent.MARK_NOT_INTERESTED_EVENT:
            case ApiEvent.LIKE_EVENT:
            case ApiEvent.DELETE_PUBLIC_PHOTO_EVENT:
            case ApiEvent.DELETE_PRIVATE_PHOTO_EVENT:
                break;
            case ApiEvent.REPORT_USER_EVENT:
            case ApiEvent.BLOCK_USER_EVENT:
                break;
            default:
                break;
        }

    }


}
