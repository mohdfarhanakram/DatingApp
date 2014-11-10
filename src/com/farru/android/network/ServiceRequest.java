package com.farru.android.network;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;



/**
 * Storage class for data related to a service request which is to be executed
 * by {@link HttpClientConnection}
 */
public class ServiceRequest {
	private String				url;
	private byte				priority;
	private int					httpMethod;
	private HttpEntity			postData;
	private boolean				isCompressed;
	private int					requestTimeOut = 5*60*1000;
	private String[]			headerNames;
	private String[]			headerValues;
	private boolean				isCancelled;

	/**
	 * Used while returning the response data
	 */
	private int					dataType;
	private Object				requestData;


	/**
	 * 
	 * @param url
	 *            //set the url for request
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * get the requested url
	 * 
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param headerNames
	 * @param headerValues
	 */
	public void setHttpHeaders(String[] headerNames, String[] headerValues) {
		this.headerNames = headerNames;
		this.headerValues = headerValues;
	}

	/**
	 * get the requested http header name
	 * 
	 * @return header name
	 */
	public String[] getHeaderNames() {
		return this.headerNames;
	}

	/**
	 * get the requested http header value
	 * 
	 * @return url
	 */
	public String[] getHeaderValues() {
		return this.headerValues;
	}

	/**
	 * set the priority of the request
	 * 
	 * @param priority
	 */
	public void setPriority(byte priority) {
		this.priority = priority;
	}

	/**
	 * get the request priority
	 * 
	 * @return priority
	 */
	public byte getPriority() {
		return priority;
	}

	/**
	 * set the request post data or the parameters
	 * 
	 * @param postData
	 */
	public void setPostData(HttpEntity postData) {
		this.postData = postData;
	}

	/**
	 * get the requested parameters
	 * 
	 * @return postData
	 */
	public HttpEntity getPostData() {
		return this.postData;
	}

	/**
	 * set parameter to compress the request data
	 * 
	 * @param isCompressed
	 */
	public void setIsCompressed(boolean isCompressed) {
		this.isCompressed = isCompressed;
	}

	/**
	 * get the request is compressed
	 * 
	 * @return isCompressed
	 */
	public boolean getIsCompressed() {
		return this.isCompressed;
	}

	/**
	 * set the time in miliseconds when the request has to be time out
	 * 
	 * @param requestTimeOut
	 */
	public void setRequestTimeOut(int requestTimeOut) {
		this.requestTimeOut = requestTimeOut;
	}

	/**
	 * get when the request has to be time out
	 * 
	 * @return requestTimeOut
	 */
	public int getRequestTimeOut() {
		return this.requestTimeOut;
	}

	/**
	 * @return the httpMethod
	 */
	public int getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod
	 *            the httpMethod to set
	 */
	public void setHttpMethod(int httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the dataType
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the requestData
	 */
	public Object getRequestData() {
		return requestData;
	}

	/**
	 * @param requestData
	 *            the requestData to set
	 */
	public void setRequestData(Object requestData) {
		this.requestData = requestData;
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * @param isCancelled
	 *            the isCancelled to set
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


}
