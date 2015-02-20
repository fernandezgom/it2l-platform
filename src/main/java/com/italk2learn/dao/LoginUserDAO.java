package com.italk2learn.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hibernate.dto.User;
import com.italk2learn.dao.inter.ILoginUserDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.HeaderVO;

/**
 * 
 * @author José Luis Fernández
 * 
 */
@Repository
public class LoginUserDAO extends HibernateDaoSupport implements ILoginUserDAO {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	public final Session getITalk2LearnSession() {
		return this.getSessionFactory().getCurrentSession();
	}
	
	public boolean getLoginUserInfo(HeaderVO header) throws ITalk2LearnException {
		try {
			return (getIdUserInfo(header.getLoginUser())!=null);
		} catch (Exception e){
			throw new ITalk2LearnException(e);
		}
	}
	
	public User getIdUserInfo(String loginUser) throws ITalk2LearnException {
		try {
			final Criteria criteria = getITalk2LearnSession().createCriteria(User.class);
			criteria.setMaxResults(1);
			criteria.add(Restrictions.eq("user", loginUser));
			criteria.setResultTransformer(Criteria.ROOT_ENTITY);
			return (User)criteria.uniqueResult();
		} catch (Exception e){
			throw new ITalk2LearnException(e);
		}
	}

}
