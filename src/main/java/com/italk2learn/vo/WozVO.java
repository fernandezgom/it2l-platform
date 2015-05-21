package com.italk2learn.vo;

public class WozVO {
	
	private String user;
	private String idNextexercise;
	private String[] sequence;
	private int condition;
	private String lang; 
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getIdNextexercise() {
		return idNextexercise;
	}
	public void setIdNextexercise(String idNextexercise) {
		this.idNextexercise = idNextexercise;
	}
	public String[] getSequence() {
		return sequence;
	}
	public void setSequence(String[] sequence) {
		this.sequence = sequence;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

}
