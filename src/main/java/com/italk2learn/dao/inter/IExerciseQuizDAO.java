package com.italk2learn.dao.inter;

import com.italk2learn.exception.ITalk2LearnException;

public interface IExerciseQuizDAO {
	
	public boolean storeExerciseQuiz(int idUser, String exName, String exView,String data, int typeQuiz) throws ITalk2LearnException;

}
