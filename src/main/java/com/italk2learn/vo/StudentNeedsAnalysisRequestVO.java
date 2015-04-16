package com.italk2learn.vo;

public class StudentNeedsAnalysisRequestVO extends RequestVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String feedbackType;
	
	private String representationType;
	
	private boolean english;

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getRepresentationType() {
		return representationType;
	}

	public void setRepresentationType(String representationType) {
		this.representationType = representationType;
	}

	public boolean isEnglish() {
		return english;
	}

	public void setEnglish(boolean english) {
		this.english = english;
	}


}
