package com.italk2learn.vo;

import java.util.List;

public class CTATResponseVO extends ResponseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean response;
	
	private List<String> exLogs;

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public List<String> getExLogs() {
		return exLogs;
	}

	public void setExLogs(List<String> exLogs) {
		this.exLogs = exLogs;
	}

}
