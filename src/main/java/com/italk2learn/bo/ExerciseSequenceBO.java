package com.italk2learn.bo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.italk2learn.bo.inter.IExerciseSequenceBO;
import com.italk2learn.dao.inter.IExerciseDAO;
import com.italk2learn.dao.inter.IExerciseQuizDAO;
import com.italk2learn.dao.inter.ISequenceDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.repositories.ExercisesRepository;
import com.italk2learn.util.ExerciseAssembler;
import com.italk2learn.vo.ExerciseQuizRequestVO;
import com.italk2learn.vo.ExerciseQuizResponseVO;
import com.italk2learn.vo.ExerciseSequenceRequestVO;
import com.italk2learn.vo.ExerciseSequenceResponseVO;
import com.italk2learn.vo.WhizzRequestVO;

@Service("exerciseSequenceBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class ExerciseSequenceBO implements IExerciseSequenceBO  {
	

	public ExerciseSequenceBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(ExerciseSequenceBO.class);
	
	public IExerciseDAO exerciseDAO;
	
	public ISequenceDAO sequenceDAO;
	
	public IExerciseQuizDAO exerciseQuizDAO;
	

	@Autowired
    private ExercisesRepository exercisesRepository; 
	
	@Autowired
	public ExerciseSequenceBO(IExerciseDAO exerciseDAO,ISequenceDAO sequenceDAO, IExerciseQuizDAO exerciseQuizDAO) {
		this.exerciseDAO = exerciseDAO;
		this.sequenceDAO = sequenceDAO;
		this.exerciseQuizDAO= exerciseQuizDAO;
	}
	
    public ExerciseSequenceResponseVO findAllExercises(ExerciseSequenceRequestVO request) {
    	ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
		try {
			response.setResponse(ExerciseAssembler.toExerciseVOs(getExerciseDAO().getAllExercises()));
		} catch (ITalk2LearnException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
		}
    	return response;
    }

	public ExerciseSequenceResponseVO getExerciseSequence(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setResponse(ExerciseAssembler.toExerciseVOs(getExerciseDAO().getSequenceExercises(request.getIdUser())));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getSpecificExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(ExerciseAssembler.toExerciseVOs(getExerciseDAO().getSpecificExercise(request.getIdExercise())));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getIdExerciseFromSequence(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(ExerciseAssembler.toExerciseVOs(getExerciseDAO().getIDExerciseFromSequencer(request.getNameExercise())));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getNextExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(getExerciseDAO().getNextExercise(request.getIdUser(), request.getIdExercise()));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getWholeViewFromIDSequencer(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(getExerciseDAO().getWholeViewFromIDSequencer(request.getIdUser(), request.getIdVPSExercise()));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getIDSequencer(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setIdSequencer(getExerciseDAO().getIDSequencer(request.getIdExercise()).getIdSequencer());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getBackExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(getExerciseDAO().getBackExercise(request.getIdUser(), request.getIdExercise()));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO insertNextIDExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException {
		try {			
			getExerciseDAO().setNextExercise(request.getIdUser(), request.getIdExercise(), request.getIdNextexercise(),request.getFeedback());
			return new ExerciseSequenceResponseVO();
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO insertSequenceByUser(ExerciseSequenceRequestVO request) throws ITalk2LearnException {
		try {			
			List<String[]> ids = new ArrayList<String[]>();
			ids.add(request.getSequence());
			getSequenceDAO().insertSequenceByUser(request.getIdUser(), request.getSequence());
			return new ExerciseSequenceResponseVO();
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	
	public ExerciseSequenceResponseVO insertCurrentExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			getExerciseDAO().insertCurrentExercise(request.getIdUser(), request.getIdExercise());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO insertCurrentVPSExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			getExerciseDAO().insertCurrentVPSExercise(request.getIdUser(), request.getIdVPSExercise(), request.getIdExercise());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO insertCondition(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			getExerciseDAO().insertCondition(request.getIdUser(), request.getCondition());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO insertLastScore(WhizzRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			getExerciseDAO().insertLastScore(request.getIdUser(), Integer.parseInt(request.getWhizz().getScore()));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseSequenceResponseVO getFirstExercise(ExerciseSequenceRequestVO request) throws ITalk2LearnException{
		try {
			ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
			response.setExercise(ExerciseAssembler.toExerciseVOs(getExerciseDAO().getFirstExercise(request.getIdExercise())));
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public ExerciseQuizResponseVO storeExerciseQuiz(ExerciseQuizRequestVO request) throws ITalk2LearnException{
		logger.info("JLF WhizzExerciseBO storeWhizzInfo() --- Storing Whizz data on the database");
		ExerciseQuizResponseVO response= new ExerciseQuizResponseVO();
		try {
			getExerciseQuizDAO().storeExerciseQuiz(request.getIdUser(), request.getExName(),request.getExView(), request.getData(), request.getTypeQuiz());
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return response;
	}
	

	public IExerciseDAO getExerciseDAO() {
		return exerciseDAO;
	}

	public void setExerciseDAO(IExerciseDAO exerciseDAO) {
		this.exerciseDAO = exerciseDAO;
	}
	
	public ISequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	public void setSequenceDAO(ISequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

    public IExerciseQuizDAO getExerciseQuizDAO() {
		return exerciseQuizDAO;
	}

	public void setExerciseQuizDAO(IExerciseQuizDAO exerciseQuizDAO) {
		this.exerciseQuizDAO = exerciseQuizDAO;
	}
}
