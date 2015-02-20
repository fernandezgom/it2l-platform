package com.italk2learn.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.dao.inter.ILoginUserDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.HeaderVO;

/**
 * Login service
 * 
 * @author José Luis Fernández
 * @version 1.0
 */

@Service("loginUserService")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class LoginUserService implements ILoginUserService {

	private static final Logger logger = LoggerFactory.getLogger(LoginUserService.class);
	
	@Autowired
	private ILoginUserDAO loginUserDAO;

	/**
	 * @return the loginUserDAO
	 */
	public ILoginUserDAO getLoginUserDAO() {
		return loginUserDAO;
	}

	public void setLoginUserDAO(ILoginUserDAO loginUserDAO) {
		this.loginUserDAO = loginUserDAO;
	}
	
	public final boolean getLoginUserInfo(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getLoginUserInfo(header);
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return false;
	}
	
	public Integer getIdUserInfo(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(header.getLoginUser()).getIdUser();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
	public Integer getIdExersiceUser(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(header.getLoginUser()).getIdView();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
	//	JLF: Get idSequence from Whizz
	public String getIdExersiceSequenceUser(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(header.getLoginUser()).getIdSequencerView();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
	//	JLF: Get lastScore from Whizz
	public int getLastScoreSequenceUser(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(header.getLoginUser()).getLastScore();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return 0;
	}
	
	//Get condition from the user
	public Integer getCondition(HeaderVO header) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(header.getLoginUser()).getCond();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
	// Get idUser from login user
	public Integer getIdUser(String user) throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(user).getIdUser();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
	
	public Integer getSimpleIdExersiceUser(String user)	throws ITalk2LearnException {
		try {
			return loginUserDAO.getIdUserInfo(user).getIdView();
		} catch (Exception nfe) {
			logger.error(nfe.toString());
		}
		return null;
	}
	
}
