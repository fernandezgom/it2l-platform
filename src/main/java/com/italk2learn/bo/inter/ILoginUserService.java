package com.italk2learn.bo.inter;

import java.util.List;

import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.UserDetailsVO;


public interface ILoginUserService {

	boolean getLoginUserInfo(HeaderVO header) throws Exception;
	public Integer getIdUserInfoUserInfo(HeaderVO header) throws ITalk2LearnException ;
	List<UserDetailsVO> getUserData() throws Exception;
	void setUserData(UserDetailsVO messageForm) throws Exception;
}
