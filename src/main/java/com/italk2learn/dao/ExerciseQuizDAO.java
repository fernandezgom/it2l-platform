package com.italk2learn.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hibernate.dto.Exercisequiz;
import com.hibernate.dto.User;
import com.italk2learn.dao.inter.IExerciseQuizDAO;
import com.italk2learn.exception.ITalk2LearnException;

@Repository
public class ExerciseQuizDAO extends HibernateDaoSupport implements IExerciseQuizDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public final Session getITalk2LearnSession() {
		return this.getSessionFactory().getCurrentSession();
	}

	public boolean storeExerciseQuiz(int idUser, String exName, String exView, String data, int typeQuiz) throws ITalk2LearnException {
		final Session session = this.getITalk2LearnSession();
		try{
			Exercisequiz eq=new Exercisequiz();
			User us= (User) session.load(User.class, idUser);
			eq.setExName(exName);
			eq.setExView(exView);
			eq.setUser(us);
			eq.setJsonval(data);
			eq.setTypeQuiz(typeQuiz);
			session.saveOrUpdate(eq);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			throw new ITalk2LearnException(e);
		}
	}
	

}
