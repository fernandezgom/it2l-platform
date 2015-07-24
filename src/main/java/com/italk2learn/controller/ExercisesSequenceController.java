package com.italk2learn.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import MFSeq.FTSequencer;
import MFSeq.SetDB;
import MFSeq.WhizzSequencer;

import com.italk2learn.bo.inter.ICTATExerciseBO;
import com.italk2learn.bo.inter.IExerciseSequenceBO;
import com.italk2learn.bo.inter.IFractionsLabBO;
import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.bo.inter.IWhizzExerciseBO;
import com.italk2learn.sna.exception.SNAException;
import com.italk2learn.sna.inter.IStudentNeedsAnalysis;
import com.italk2learn.tis.inter.ITISWrapper;
import com.italk2learn.util.ComputeScoreFTUtil;
import com.italk2learn.util.ExercisesConverter;
import com.italk2learn.util.ExperimentalCondition;
import com.italk2learn.util.TipFilesUtil;
import com.italk2learn.util.WhizzUtil;
import com.italk2learn.vo.CTATRequestVO;
import com.italk2learn.vo.ExerciseQuizRequestVO;
import com.italk2learn.vo.ExerciseSequenceRequestVO;
import com.italk2learn.vo.ExerciseSequenceResponseVO;
import com.italk2learn.vo.ExerciseVO;
import com.italk2learn.vo.FractionsLabRequestVO;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.WhizzExerciseVO;
import com.italk2learn.vo.WhizzRequestVO;

/**
 * Handles requests for the application exercise sequence.
 */
@Controller
@Scope("session")
@RequestMapping("/sequence")
public class ExercisesSequenceController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static String _RANDOMTEST = "testr";
	
	private LdapUserDetailsImpl user;
	
	private String username;
	
	private String currentExerciseName;
	
	private String currentView;
	
	private String languageBrowser="en";
	
	private ExerciseSequenceRequestVO request;
	
	private static final Logger logger = LoggerFactory.getLogger(ExercisesSequenceController.class);

	private static ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
	
	/*Services*/
	private IExerciseSequenceBO exerciseSequenceService;
	private ILoginUserService loginUserService;
	private IWhizzExerciseBO whizzExerciseBO;
	private IFractionsLabBO fractionsLabBO;
	private IStudentNeedsAnalysis snaService;
	private ITISWrapper TISWrapperService;
	private ISpeechRecognitionBO speechRecognitionService;
	private ICTATExerciseBO ctatExerciseBO;


    @Autowired
    public ExercisesSequenceController(IExerciseSequenceBO exerciseSequence, ILoginUserService loginUserService, IWhizzExerciseBO whizzExerciseBO, IFractionsLabBO fractionsLabBO, IStudentNeedsAnalysis snaService, ISpeechRecognitionBO speechRecognition, ICTATExerciseBO ctatExerciseBO, ITISWrapper tisWrapper) {
    	this.exerciseSequenceService = exerciseSequence;
    	this.loginUserService=loginUserService;
    	this.setWhizzExerciseBO(whizzExerciseBO);
    	this.setFractionsLabBO(fractionsLabBO);
    	this.setSnaService(snaService);
    	this.setSpeechRecognitionService(speechRecognition);
    	this.setCtatExerciseBO(ctatExerciseBO);
    	this.TISWrapperService=tisWrapper;
    }
	
	/**
	 * Initial method to get first exercise of sequence
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView initSequence(Model model) {
		logger.info("JLF --- ExerciseSequence Main Controller");
		ModelAndView modelAndView = new ModelAndView();
		ExercisesConverter ec=new ExercisesConverter();
		try {
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			setUsername(user.getUsername());
			request= new ExerciseSequenceRequestVO();
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(this.getUsername());
			request.setIdExercise(getLoginUserService().getIdExersiceUser(request.getHeaderVO()));
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			response=((ExerciseSequenceResponseVO) getExerciseSequenceService().getFirstExercise(request));
			this.currentExerciseName=response.getExercise().getExercise();
			this.currentView=response.getExercise().getView();
			switch (getLoginUserService().getCondition(request.getHeaderVO())) {
				case ExperimentalCondition.FULL_SYSTEM:	return getStudentNeedsAnalysisExercise(request, true);
				case ExperimentalCondition.NO_SPEECH: return getStudentNeedsAnalysisExercise(request, true);
				case ExperimentalCondition.NO_ELE:	if (currentView.equals(ExerciseVO.WHIZZ) || currentView.equals(ExerciseVO.WHIZZ_TEST)){
														modelAndView.addObject("taskName", currentExerciseName);
														modelAndView.setViewName(currentView+"/GenericSNA");
													} else{
														modelAndView.setViewName(currentView+"/"+currentExerciseName);
													}
													break;
				case ExperimentalCondition.FL_ELE:	if (currentView.equals(ExerciseVO.FRACTIONS_LAB)) {
														TipFilesUtil.createTIPFile(currentExerciseName, ec.getExercise().get(currentExerciseName));
														modelAndView.addObject("taskName", ec.getExercise().get(currentExerciseName));
														modelAndView.addObject("idTask", currentExerciseName);
														modelAndView.setViewName(currentView+"/GenericSNA");
													}
													else if (currentView.equals(ExerciseVO.WHIZZ) || currentView.equals(ExerciseVO.WHIZZ_TEST)){
														modelAndView.addObject("taskName", currentExerciseName);
														modelAndView.setViewName(currentView+"/GenericSNA");
													} else{
														modelAndView.setViewName(currentView+"/"+currentExerciseName);
													}
													break;
				default: modelAndView.setViewName(response.getExercise().getView()+"/"+ response.getExercise().getExercise());
						 break;
			}
			return modelAndView;
		} catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * JLF: Get user connected
	 */
	@RequestMapping(value = "/getUser",method = RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String getUserConnected(Model model) {
		logger.info("JLF --- ExercisesSequence.getUserConnected");
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}
	
	/**
	 * JLF: Set the language browser to select if it is ft task or fl
	 */
	@RequestMapping(value = "/setLanguageBrowser",method = RequestMethod.POST)
	@ResponseBody
	public void postLanguageBrowser(@RequestBody HeaderVO req) {
		logger.info("JLF --- ExercisesSequence.setLanguageBrowser ---");
		if (req.getIdLanguage().contains(HeaderVO.GERMAN)){
			getTISWrapperService().setLanguageInTIStoGerman();
		} else if (req.getIdLanguage().contains(HeaderVO.SPANISH)){
			getTISWrapperService().setLanguageInTIStoSpanish();
		} else {
			getTISWrapperService().setLanguageInTIStoEnglish();
		}
		setLanguageBrowser(req.getIdLanguage());
	}
	
	/**
	 * JLF: Get user connected
	 */
	@RequestMapping(value = "/getCondition",method = RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String getCondition(Model model) {
		logger.info("JLF --- ExercisesSequence.getCondition --- get user condition from the database");
		ExerciseSequenceRequestVO request= new ExerciseSequenceRequestVO();
		request.setHeaderVO(new HeaderVO());
		request.getHeaderVO().setLoginUser(user.getUsername());
		try{
			return getLoginUserService().getCondition(request.getHeaderVO()).toString();
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	
	/**
	 * Get next exercise of a given sequence
	 */
	@RequestMapping(value = "/nextexercise", method = RequestMethod.GET)
    public ModelAndView getNextExercise(@Valid @ModelAttribute("messageInfo") ExerciseVO messageForm){
		logger.info("JLF --- getNextExercise()"+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		ExerciseSequenceRequestVO request= new ExerciseSequenceRequestVO();
		try{
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
			request.setIdExercise(getLoginUserService().getIdExersiceUser(request.getHeaderVO()));
			if (user.getUsername().startsWith(_RANDOMTEST)){
				request.setIdExercise(getRandomExercise(user.getUsername()));
				ExerciseVO response=getExerciseSequenceService().getSpecificExercise(request).getExercise();
				request.setIdExercise(response.getIdExercise());
				this.currentExerciseName=response.getExercise();
				this.currentView=response.getView();
				modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
				return modelAndView;
			}
			else {
				switch (getLoginUserService().getCondition(request.getHeaderVO())) {
					case ExperimentalCondition.FULL_SYSTEM:	return getStudentNeedsAnalysisExercise(request, false);
					case ExperimentalCondition.NO_SPEECH:	return getStudentNeedsAnalysisExercise(request, false);//JLF: No speech
					case ExperimentalCondition.NO_ELE:	return getStateMachineSequencerExercise2ndVersion(request);
					case ExperimentalCondition.FL_ELE:	return getStateMachineSequencerExercise2ndVersionCombined(request);
					default: return getStateMachineSequencerExercise(request);
				}
			}
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get the exercise from the state machine
	 */
	private ModelAndView getStateMachineSequencerExercise(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getStateMachineSequencerExercise() --- Get the exercise from the state machine "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		try{
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			ExerciseVO response=getExerciseSequenceService().getNextExercise(request).getExercise();
			request.setIdExercise(response.getIdExercise());
			getExerciseSequenceService().insertCurrentExercise(request);
			modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
			modelAndView.addObject("messageInfo", response);
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	
	/**
	 * Get the exercise from the state machine
	 */
	private ModelAndView getStateMachineSequencerExercise2ndVersionWhizz(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getStateMachineSequencerExercise() --- Get the exercise from the state machine "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		boolean usingTests=true;
		try{
			if (usingTests){
				if (getCurrentExerciseName().contains("x")){
					modelAndView.addObject("taskName", getCurrentExerciseName().replace("x", "p"));
					modelAndView.setViewName(ExerciseVO.WHIZZ_TEST+"/GenericSNA");
					setCurrentView(ExerciseVO.WHIZZ_TEST);
					setCurrentExerciseName(getCurrentExerciseName().replace("x", "p"));
					return modelAndView;
				}
				else {
					request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
					ExerciseVO response=getExerciseSequenceService().getNextExercise(request).getExercise();
					request.setIdExercise(response.getIdExercise());
					getExerciseSequenceService().insertCurrentExercise(request);
					setCurrentView(response.getView());
					setCurrentExerciseName(response.getExercise());
					modelAndView.addObject("taskName", response.getExercise());
					modelAndView.setViewName(response.getView()+"/GenericSNA");
					return modelAndView;
					
				}
			} else {
				request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
				ExerciseVO response=getExerciseSequenceService().getNextExercise(request).getExercise();
				request.setIdExercise(response.getIdExercise());
				getExerciseSequenceService().insertCurrentExercise(request);
				setCurrentView(response.getView());
				setCurrentExerciseName(response.getExercise());
				modelAndView.addObject("taskName", response.getExercise());
				modelAndView.setViewName(response.getView()+"/GenericSNA");
				return modelAndView;
			}
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get the exercise from the state machine
	 */
	private ModelAndView getStateMachineSequencerExercise2ndVersionCTAT(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getStateMachineSequencerExercise() --- Get the exercise from the state machine "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		try{
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			ExerciseVO response=getExerciseSequenceService().getNextExercise(request).getExercise();
			request.setIdExercise(response.getIdExercise());
			getExerciseSequenceService().insertCurrentExercise(request);
			setCurrentView(response.getView());
			setCurrentExerciseName(response.getExercise());
			modelAndView.setViewName(response.getView()+"/"+response.getExercise());
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	
	private ModelAndView getVygotskyPolicySequencerExercise(ExerciseSequenceRequestVO request){
		if (getLanguageBrowser().contains(HeaderVO.GERMAN))
			return getVygotskyPolicySequencerExerciseCTAT(request);
		else
			return getVygotskyPolicySequencerExerciseWhizz(request);
	}
	
	
	private ModelAndView getStateMachineSequencerExercise2ndVersion(ExerciseSequenceRequestVO request){
		if (getLanguageBrowser().contains(HeaderVO.GERMAN))
			return getStateMachineSequencerExercise2ndVersionCTAT(request);
		else
			return getStateMachineSequencerExercise2ndVersionWhizz(request);
	}
	
	private ModelAndView getStateMachineSequencerExercise2ndVersionCombined(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getStateMachineSequencerExerciseCombined() --- Get the exercise from the state machine "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		ExercisesConverter ec= new ExercisesConverter();
		try{
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			ExerciseVO response=getExerciseSequenceService().getNextExercise(request).getExercise();
			request.setIdExercise(response.getIdExercise());
			getExerciseSequenceService().insertCurrentExercise(request);
			setCurrentView(response.getView());
			setCurrentExerciseName(response.getExercise());
			if (response.getView().equals(ExerciseVO.FRACTIONS_LAB)) {
				TipFilesUtil.createTIPFile(response.getExercise(), ec.getExercise().get(response.getExercise()));
				modelAndView.addObject("taskName", ec.getExercise().get(response.getExercise()));
				modelAndView.addObject("idTask", response.getExercise());
				modelAndView.setViewName(response.getView()+"/GenericSNA");
			}
			else if (response.getView().equals(ExerciseVO.WHIZZ) || response.getView().equals(ExerciseVO.WHIZZ_TEST)){
				modelAndView.addObject("taskName", response.getExercise());
				modelAndView.setViewName(response.getView()+"/GenericSNA");
			} else{
				modelAndView.setViewName(response.getView()+"/"+response.getExercise());
			}
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get the exercise from the Vygotsky Policy Sequencer 
	 */
	private ModelAndView getVygotskyPolicySequencerExerciseWhizz(ExerciseSequenceRequestVO request){
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String TRIAL = rb.getString("vps.trial");
		logger.info("JLF --- getVygotskyPolicySequencerExerciseWhizz() --- Get the exercise from the Vygotsky Policy Sequencer "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		Date date= new Date();
		Timestamp timestamp= new Timestamp(date.getTime());
		int studentId; //22516;
		int prevStudentScore;//95;
		String prevLessonId;//"GB0875AAx0100";
		String whizzLessonSuggestion = "GB0900CAx0200";
		try {
			prevLessonId = WhizzUtil.marshallWhizz(getLoginUserService().getIdExersiceSequenceUser(request.getHeaderVO()).toString());
			studentId = getLoginUserService().getIdUserInfo(request.getHeaderVO());
			prevStudentScore=WhizzUtil.computeScore(getLoginUserService().getLastScoreSequenceUser(request.getHeaderVO()));
			SetDB.SetConnectionAddress(true);
			String ID= WhizzSequencer.next(studentId, prevLessonId, prevStudentScore, timestamp, whizzLessonSuggestion, Integer.parseInt(TRIAL));
			if (ID==null || ID.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			else {
				ID=WhizzUtil.unmarshallWhizz(ID);
			}
			String viewName="";
			if (ID.contains(ExerciseVO.IS_WHIZZ_EXERCISE)){
				viewName=ExerciseVO.WHIZZ;
			} else 
				viewName=ExerciseVO.WHIZZ_TEST;
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			//JLF: Getting exercise to store as well as the idSequencer
			request.setNameExercise(ID);
			request.setIdExercise(getExerciseSequenceService().getSpecificExercise(request).getExercise().getIdExercise());
			request.setIdVPSExercise(ID);
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			modelAndView.addObject("taskName", ID);
			modelAndView.setViewName(viewName+"/GenericSNA");
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get the exercise from the Vygotsky Policy Sequencer 
	 */
	private ModelAndView getVygotskyPolicySequencerExerciseCTAT(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getVygotskyPolicySequencerExerciseCTAT() --- Get the exercise from the Vygotsky Policy Sequencer "+"User= "+this.getUsername());
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String TRIAL = rb.getString("vps.trial");
		ModelAndView modelAndView = new ModelAndView();
		Date date= new Date();
		Timestamp timestamp= new Timestamp(date.getTime());
		int studentId;
		int prevStudentScore;
		String prevLessonId;
		String whizzLessonSuggestion = "GB0900CAx0200";
		CTATRequestVO rqctat=new CTATRequestVO();
		try {
			rqctat.setHeaderVO(new HeaderVO());
			rqctat.getHeaderVO().setLoginUser(user.getUsername());
			prevLessonId = getLoginUserService().getIdExersiceSequenceUser(request.getHeaderVO()).toString();
			studentId = getLoginUserService().getIdUserInfo(request.getHeaderVO());
			ComputeScoreFTUtil cs= new ComputeScoreFTUtil(getCtatExerciseBO().getExerciseLogs(rqctat).getExLogs(), getCurrentExerciseName());
			prevStudentScore=cs.getScoreRounded();
			SetDB.SetConnectionAddress(false);
			String ID= FTSequencer.next(studentId, prevLessonId, prevStudentScore, timestamp, whizzLessonSuggestion, Integer.parseInt(TRIAL));
			if (ID==null || ID.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			//JLF: Getting exercise to store as well as the idSequencer
			request.setNameExercise(ID);
			request.setIdExercise(Integer.parseInt(ID));
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			ExerciseVO response=getExerciseSequenceService().getSpecificExercise(request).getExercise();
			request.setIdVPSExercise(response.getExercise());
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			modelAndView.setViewName(ExerciseVO.FRACTIONS_TUTOR+"/"+ response.getExercise());
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get the exercise from student needs analysis
	 */
	private ModelAndView getStudentNeedsAnalysisExercise(ExerciseSequenceRequestVO request, boolean first){
		logger.info("JLF --- getStudentNeedsAnalysisExercise() --- Get the exercise from student needs analysis "+"User= "+this.getUsername());
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String TRIAL = rb.getString("vps.trial");
		ModelAndView modelAndView = new ModelAndView();
		SpeechRecognitionRequestVO reqad=new SpeechRecognitionRequestVO();
		CTATRequestVO rqctat=new CTATRequestVO();
		try {
			reqad.setHeaderVO(new HeaderVO());
			reqad.getHeaderVO().setLoginUser(user.getUsername());
			rqctat.setHeaderVO(new HeaderVO());
			rqctat.getHeaderVO().setLoginUser(user.getUsername());
			Calendar calendar = Calendar.getInstance();
			Date date= calendar.getTime();
			Timestamp timestamp= new Timestamp(date.getTime());
			int studentId= getLoginUserService().getIdUserInfo(request.getHeaderVO());
			int prevStudentScore=getLoginUserService().getLastScoreSequenceUser(request.getHeaderVO());
			String prevLessonId=getLoginUserService().getIdExersiceSequenceUser(request.getHeaderVO()).toString();
			String whizzLessonSuggestion = "GB0900CAx0200";//JLF: Hardcoded, this parameter is used in both sequencers??

			getSnaService().setStudentModel(studentId);
			if (getCurrentView().equals(ExerciseVO.FRACTIONS_LAB)) {
				getSnaService().setExploratoryExercise(true);
			} else if (getCurrentView().equals(ExerciseVO.WHIZZ)|| getCurrentView().equals(ExerciseVO.WHIZZ_TEST)){
				prevLessonId= WhizzUtil.marshallWhizz(prevLessonId);
				prevStudentScore=WhizzUtil.computeScore(prevStudentScore);
				SetDB.SetConnectionAddress(true);
				getSnaService().setExploratoryExercise(false);
				getSnaService().setWhizzExercise(true);
			} else if (getCurrentView().equals(ExerciseVO.FRACTIONS_TUTOR)){
				//ComputeScoreFTUtil cs= new ComputeScoreFTUtil(getCtatExerciseBO().getExerciseLogs(rqctat).getExLogs(), getCurrentExerciseName());
				prevStudentScore=0;//cs.getScoreRounded();
				ExerciseSequenceRequestVO reqCTAT= new ExerciseSequenceRequestVO();
				reqCTAT.setHeaderVO(new HeaderVO());
				reqCTAT.getHeaderVO().setLoginUser(user.getUsername());
				reqCTAT.setNameExercise(prevLessonId);
				prevLessonId=Integer.toString(getExerciseSequenceService().getIdExerciseFromSequence(reqCTAT).getExercise().getIdExercise());
				SetDB.SetConnectionAddress(false);
				getSnaService().setExploratoryExercise(false);
				getSnaService().setFractionsTutorExercise(true);
			}
			getSnaService().setAudio(getSpeechRecognitionService().getCurrentAudioFromExercise(reqad).getAudio());
			try {
				getSnaService().calculateNextTask(studentId, prevLessonId, prevStudentScore, timestamp, whizzLessonSuggestion, Integer.parseInt(TRIAL), first);
			} catch (SNAException e) {
				// TODO Auto-generated catch block
				logger.error(e.getSnamessage());
				modelAndView.addObject("error", e.getSnamessage());
			}
			String response=getSnaService().getNextTask();
			
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			//JLF: Getting exercise to store as well as the idSequencer
			request.setNameExercise(response);
			request.setIdExercise(getExerciseSequenceService().getSpecificExercise(request).getExercise().getIdExercise());
			request.setIdVPSExercise(response);
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			if (response==null || response.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			String viewName="";			
			if (getSnaService().getEngland()==true){
				if (response.contains("task")==true){
					viewName=ExerciseVO.FRACTIONS_LAB;
					
				} else {
					if (response.contains(ExerciseVO.IS_WHIZZ_EXERCISE)){
						viewName=ExerciseVO.WHIZZ;
					} else 
						viewName=ExerciseVO.WHIZZ_TEST;
				}
			} else {
				if (response.contains("sequencer")==true) {
					viewName=ExerciseVO.FRACTIONS_TUTOR;
					ExerciseSequenceRequestVO reqCTAT= new ExerciseSequenceRequestVO();
					reqCTAT.setHeaderVO(new HeaderVO());
					reqCTAT.getHeaderVO().setLoginUser(user.getUsername());
					reqCTAT.setIdExercise(Integer.parseInt(response.replace("sequencer", "")));
					response=getExerciseSequenceService().getSpecificExercise(reqCTAT).getExercise().getExercise();
				}
				else if (response.contains("graph")==true){
					viewName=ExerciseVO.FRACTIONS_TUTOR;
				} else {
					viewName=ExerciseVO.FRACTIONS_LAB;
				}
			}
			getSnaService().saveStudentModel(studentId);
			setCurrentView(viewName);
			setCurrentExerciseName(response);
			if (viewName.equals(ExerciseVO.FRACTIONS_LAB)){
				TipFilesUtil.createTIPFile(response, getSnaService().getTaskDescription());
				modelAndView.addObject("idTask", response);
				modelAndView.addObject("taskName", getSnaService().getTaskDescription());
			} else if (viewName.equals(ExerciseVO.FRACTIONS_TUTOR)) {
				modelAndView.setViewName(viewName+"/"+response);
				return modelAndView;
			} else {
				modelAndView.addObject("taskName", response);
			}
			modelAndView.setViewName(viewName+"/GenericSNA");
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * Get a ramdon exercise from the database depending of the test account 
	 * testrft: Random fractions tutor
	 * testrflai: Ramdon fractions lab with AI
	 * testrfl: Ramdon fractions lab
	 * 
	 */
	private int getRandomExercise(String user){
		logger.info("JLF --- getRandomExercise() --- Get a ramdon exercise from the database depending of the test account "+"User= "+this.getUsername());
		if (user.startsWith("Get a ramdon exercise from the database depending of the test account")){ //Random whizz
			return randomWithRange(4,8);
		} else if (user.startsWith("testrft")){ //Random fractions tutor
			return randomWithRange(13,28);
		} else if (user.startsWith("testrflai")){ //Ramdon fractions lab with AI
			return randomWithRange(94,99);
		} else if (user.startsWith("testrfl")){ //Ramdon fractions lab
				return randomWithRange(56,60);
		} else{ //Normal ramdon
			return randomWithRange(1,95);
		}
	}
	
	int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
	/**
	 * Get back exercise of a given sequence
	 */
	@Deprecated
	@RequestMapping(value = "/backexercise", method = RequestMethod.GET)
    public ModelAndView getBackExercise(@Valid @ModelAttribute("messageInfo") ExerciseVO messageForm){
		logger.info("JLF --- getBackExercise()"+"User: "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		ExerciseSequenceRequestVO request= new ExerciseSequenceRequestVO();
		try{
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(this.getUsername());
			request.setIdExercise(getLoginUserService().getIdExersiceUser(request.getHeaderVO()));
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			ExerciseVO response=getExerciseSequenceService().getBackExercise(request).getExercise();
			request.setIdExercise(response.getIdExercise());
			getExerciseSequenceService().insertCurrentExercise(request);
			modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
			modelAndView.addObject("messageInfo", response);
			return modelAndView;
		}
		catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			modelAndView.setViewName("redirect:/login");
			return new ModelAndView();
		}
	}
	
	/**
	 * JLF: Controller to store Whizz Data
	 */
	@RequestMapping(value = "/storeWhizzData", method = RequestMethod.POST)
    public @ResponseBody void storeWhizzData(@RequestBody WhizzExerciseVO exercise, HttpServletRequest req){
		logger.info("JLF --- Whizz storeWhizzData --- Storing whizz data on the data base"+" User: "+this.getUsername());
		logger.info("score="+ exercise.getScore()+" ,percentage="+exercise.getPercentage()+" ,time="+exercise.getTime()+" ,help1="+exercise.getHelp1()+" ,help2="+exercise.getHelp2()+" ,help3="+exercise.getHelp3());
		WhizzRequestVO request=new WhizzRequestVO();
        try {
        	user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(this.getUsername());
			request.setIdExercise(getLoginUserService().getIdExersiceUser(request.getHeaderVO()));
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
            request.setWhizz(exercise);
            getWhizzExerciseBO().storeWhizzInfo(request);
            getExerciseSequenceService().insertLastScore(request);
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	
	/**
	 * JLF: Controller to store Whizz Data
	 */
	@RequestMapping(value = "/storeExerciseQuiz", method = RequestMethod.POST)
    public @ResponseBody void storeExerciseQuiz(@RequestBody ExerciseQuizRequestVO exercise, HttpServletRequest req){
		logger.info("JLF --- Whizz storeExerciseQuiz --- Storing whizz data on the data base"+" User: "+this.getUsername());
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
        	request= new ExerciseSequenceRequestVO();
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
        	exercise.setHeaderVO(new HeaderVO());
        	exercise.getHeaderVO().setLoginUser(user.getUsername());
        	if (exercise.getTypeQuiz()==2) {
        		exercise.setExName(getCurrentExerciseName());
        		exercise.setExView(getCurrentView());
        	} else {
        		exercise.setExName("---");
        		exercise.setExView("---");
        	}
        	exercise.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
            getExerciseSequenceService().storeExerciseQuiz(exercise);
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	
	/**
	 * If session is invalidated return to the login page
	 */
	@RequestMapping(value = "/redirectLogin",method = RequestMethod.GET)
	public String redirectLogin() {
		logger.info("JLF --- redirectLogin --- Session is invalidated returning to login page");
		try {
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e){
			logger.info("Returning to login due previous errors");
			logger.error(e.toString());
			return "redirect:/login";
		}
		return null;
	}
	
	/**
	 * JLF: Controller to store a fractions lab event
	 */
	@RequestMapping(value = "/saveFLEvent", method = RequestMethod.POST)
    public @ResponseBody void saveFractionsLabEvent(@RequestBody FractionsLabRequestVO flRequest, HttpServletRequest req){
		logger.info("JLF --- saveFractionsLabEvent --- Saving fractions lab event on the database "+"User: "+this.getUsername());
		logger.info("id_exercise="+flRequest.getIdExercise()+" ,event="+flRequest.getEvent());
		FractionsLabRequestVO request=new FractionsLabRequestVO();
        try {
        	user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(this.getUsername());
			request.setIdExercise(getLoginUserService().getIdExersiceUser(request.getHeaderVO()));
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
            request.setEvent(flRequest.getEvent());
            getFractionsLabBO().saveEventFL(request);
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	

	
	public IExerciseSequenceBO getExerciseSequenceService() {
		return exerciseSequenceService;
	}

	public void setExerciseSequenceService(IExerciseSequenceBO exerciseSequenceService) {
		this.exerciseSequenceService = exerciseSequenceService;
	}


	public static ExerciseSequenceResponseVO getResponse() {
		return response;
	}


	public static void setResponse(ExerciseSequenceResponseVO response) {
		ExercisesSequenceController.response = response;
	}


	public ILoginUserService getLoginUserService() {
		return loginUserService;
	}


	public void setLoginUserService(ILoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	public IWhizzExerciseBO getWhizzExerciseBO() {
		return whizzExerciseBO;
	}

	public void setWhizzExerciseBO(IWhizzExerciseBO whizzExerciseBO) {
		this.whizzExerciseBO = whizzExerciseBO;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public IFractionsLabBO getFractionsLabBO() {
		return fractionsLabBO;
	}

	public void setFractionsLabBO(IFractionsLabBO fractionsLabBO) {
		this.fractionsLabBO = fractionsLabBO;
	}

	public IStudentNeedsAnalysis getSnaService() {
		return snaService;
	}

	public void setSnaService(IStudentNeedsAnalysis snaService) {
		this.snaService = snaService;
	}

	public ISpeechRecognitionBO getSpeechRecognitionService() {
		return speechRecognitionService;
	}

	public void setSpeechRecognitionService(ISpeechRecognitionBO speechRecognitionService) {
		this.speechRecognitionService = speechRecognitionService;
	}

	public ICTATExerciseBO getCtatExerciseBO() {
		return ctatExerciseBO;
	}

	public void setCtatExerciseBO(ICTATExerciseBO ctatExerciseBO) {
		this.ctatExerciseBO = ctatExerciseBO;
	}

	public String getCurrentExerciseName() {
		return currentExerciseName;
	}

	public void setCurrentExerciseName(String currentExerciseName) {
		this.currentExerciseName = currentExerciseName;
	}

	public String getCurrentView() {
		return currentView;
	}

	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}

	public ITISWrapper getTISWrapperService() {
		return TISWrapperService;
	}

	public void setTISWrapperService(ITISWrapper tISWrapperService) {
		TISWrapperService = tISWrapperService;
	}

	public String getLanguageBrowser() {
		return languageBrowser;
	}

	public void setLanguageBrowser(String languageBrowser) {
		this.languageBrowser = languageBrowser;
	}
	
}
