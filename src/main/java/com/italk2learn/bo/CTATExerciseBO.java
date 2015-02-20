package com.italk2learn.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.italk2learn.bo.inter.ICTATExerciseBO;
import com.italk2learn.dao.inter.ICTATExerciseDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.vo.CTATRequestVO;
import com.italk2learn.vo.CTATResponseVO;

@Service("ctatExerciseBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class CTATExerciseBO implements ICTATExerciseBO{
	
	private static final Logger logger = LoggerFactory.getLogger(CTATExerciseBO.class);
	
	private ICTATExerciseDAO exerciseCTATDAO;
	
	@Autowired
	public CTATExerciseBO(ICTATExerciseDAO exerciseDAO) {
		this.exerciseCTATDAO = exerciseDAO;
	}
	
	
	public CTATResponseVO storageLog(CTATRequestVO request) throws ITalk2LearnException{
		logger.info("JLF CTATExerciseBO storageLog --- Storing log on the database");
		try {
			CTATResponseVO response= new CTATResponseVO();
			response.setResponse(getExerciseCTATDAO().storageLog(request.getIdUser(), request.getIdExercise(),request.getLog()));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	public ICTATExerciseDAO getExerciseCTATDAO() {
		return exerciseCTATDAO;
	}

	public void setExerciseCTATDAO(ICTATExerciseDAO exerciseDAO) {
		this.exerciseCTATDAO = exerciseDAO;
	}

}
