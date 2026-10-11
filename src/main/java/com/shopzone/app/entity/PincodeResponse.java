package com.shopzone.app.entity;

import java.util.List;

public class PincodeResponse {

    private String Message;
    private String Status;
    private List<PostOffice> PostOffice;
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public List<PostOffice> getPostOffice() {
		return PostOffice;
	}
	public void setPostOffice(List<PostOffice> postOffice) {
		PostOffice = postOffice;
	}
	@Override
	public String toString() {
		return "PincodeResponse [Message=" + Message + ", Status=" + Status + ", PostOffice=" + PostOffice + "]";
	}

    // getters and setters
    
    
    
}