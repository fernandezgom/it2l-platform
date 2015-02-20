package com.italk2learn.dao.inter;

import com.hibernate.dto.User;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.HeaderVO;


public interface ILoginUserDAO {

	boolean getLoginUserInfo(HeaderVO header) throws ITalk2LearnException;
	User getIdUserInfo(String loginUser) throws ITalk2LearnException;
}
