package com.italk2learn.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.italk2learn.bo.inter.IFractionsLabBO;
import com.italk2learn.dao.inter.IFractionsLabDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.FractionsLabRequestVO;
import com.italk2learn.vo.FractionsLabResponseVO;

@Service("fractionsLabBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class FractionsLabBO implements IFractionsLabBO{
	
	public FractionsLabBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(FractionsLabBO.class);
	
	private IFractionsLabDAO fractionsLabDAO;
	
	@Autowired
	public FractionsLabBO(IFractionsLabDAO fl) {
		this.setFractionsLabDAO(fl);
	}
	
	public FractionsLabResponseVO saveEventFL(FractionsLabRequestVO request) throws ITalk2LearnException{
		FractionsLabResponseVO response= new FractionsLabResponseVO();
		try {
			response.setResponse(getFractionsLabDAO().saveEvent(request.getEvent(),request.getIdUser(),request.getIdExercise()));
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return response;
	}

	public IFractionsLabDAO getFractionsLabDAO() {
		return fractionsLabDAO;
	}

	public void setFractionsLabDAO(IFractionsLabDAO fractionsLabDAO) {
		this.fractionsLabDAO = fractionsLabDAO;
	}

}
