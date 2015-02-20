package com.italk2learn.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.italk2learn.bo.inter.IWhizzExerciseBO;
import com.italk2learn.dao.inter.IWhizzExerciseDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.WhizzRequestVO;
import com.italk2learn.vo.WhizzResponseVO;

@Service("whizzExerciseBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class WhizzExerciseBO implements IWhizzExerciseBO{
	
	private static final Logger logger = LoggerFactory.getLogger(WhizzExerciseBO.class);
	
	private IWhizzExerciseDAO exerciseWhizzDAO;
	
	@Autowired
	public WhizzExerciseBO(IWhizzExerciseDAO exerciseDAO) {
		this.setExerciseWhizzDAO(exerciseDAO);
	}
	
	public WhizzResponseVO storeWhizzInfo(WhizzRequestVO request) throws ITalk2LearnException{
		logger.info("JLF WhizzExerciseBO storeWhizzInfo() --- Storing Whizz data on the database");
		WhizzResponseVO response= new WhizzResponseVO();
		try {
			response.setResponse(getExerciseWhizzDAO().storeDataWhizz(request.getIdUser(), request.getIdExercise(), request.getWhizz()));
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return response;
	}

	public IWhizzExerciseDAO getExerciseWhizzDAO() {
		return exerciseWhizzDAO;
	}

	public void setExerciseWhizzDAO(IWhizzExerciseDAO exerciseWhizzDAO) {
		this.exerciseWhizzDAO = exerciseWhizzDAO;
	}

}
