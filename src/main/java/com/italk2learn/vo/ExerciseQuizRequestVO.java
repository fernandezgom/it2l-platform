package com.italk2learn.vo;

public class ExerciseQuizRequestVO extends RequestVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String data;
	private int idUser;
	private String exView;
	private String exName;
	private int typeQuiz;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getTypeQuiz() {
		return typeQuiz;
	}
	public void setTypeQuiz(int typeQuiz) {
		this.typeQuiz = typeQuiz;
	}
	public String getExView() {
		return exView;
	}
	public void setExView(String exView) {
		this.exView = exView;
	}
	public String getExName() {
		return exName;
	}
	public void setExName(String exName) {
		this.exName = exName;
	}

}
