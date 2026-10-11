package com.shopzone.app.dto;

public class ErrorDto {
	
	private String errorCode;
	private String msg;
	private String errorDetails;
	private String errorType;
	private String errorTimestamp;
	private String suggestedAction;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorTimestamp() {
		return errorTimestamp;
	}
	public void setErrorTimestamp(String errorTimestamp) {
		this.errorTimestamp = errorTimestamp;
	}
	public String getSuggestedAction() {
		return suggestedAction;
	}
	public void setSuggestedAction(String suggestedAction) {
		this.suggestedAction = suggestedAction;
	}
	
	
	

}
