package com.italk2learn.controller;

import java.io.Serializable;
import java.sql.Timestamp;
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
import MFSeq.WhizzSequencer;

import com.italk2learn.bo.inter.ICTATExerciseBO;
import com.italk2learn.bo.inter.IExerciseSequenceBO;
import com.italk2learn.bo.inter.IFractionsLabBO;
import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.bo.inter.IWhizzExerciseBO;
import com.italk2learn.sna.inter.IStudentNeedsAnalysis;
import com.italk2learn.util.ComputeScoreFTUtil;
import com.italk2learn.util.ExercisesConverter;
import com.italk2learn.vo.AudioRequestVO;
import com.italk2learn.vo.CTATRequestVO;
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
	
	private ExerciseSequenceRequestVO request;
	
	private static final Logger logger = LoggerFactory.getLogger(ExercisesSequenceController.class);

	private static ExerciseSequenceResponseVO response= new ExerciseSequenceResponseVO();
	
	
	/*Services*/
	private IExerciseSequenceBO exerciseSequenceService;
	private ILoginUserService loginUserService;
	private IWhizzExerciseBO whizzExerciseBO;
	private IFractionsLabBO fractionsLabBO;
	private IStudentNeedsAnalysis snaService;
	private ISpeechRecognitionBO speechRecognitionService;
	private ICTATExerciseBO ctatExerciseBO;


    @Autowired
    public ExercisesSequenceController(IExerciseSequenceBO exerciseSequence, ILoginUserService loginUserService, IWhizzExerciseBO whizzExerciseBO, IFractionsLabBO fractionsLabBO, IStudentNeedsAnalysis snaService, ISpeechRecognitionBO speechRecognition, ICTATExerciseBO ctatExerciseBO) {
    	this.exerciseSequenceService = exerciseSequence;
    	this.loginUserService=loginUserService;
    	this.setWhizzExerciseBO(whizzExerciseBO);
    	this.setFractionsLabBO(fractionsLabBO);
    	this.setSnaService(snaService);
    	this.setSpeechRecognitionService(speechRecognition);
    	this.setCtatExerciseBO(ctatExerciseBO);
    }
	
	/**
	 * Initial method to get first exercise of sequence
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView initSequence(Model model) {
		logger.info("JLF --- ExerciseSequence Main Controller");
		ModelAndView modelAndView = new ModelAndView();
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
			switch (getLoginUserService().getCondition(request.getHeaderVO())) {
				case 1:	modelAndView.setViewName(response.getExercise().getView()+"/"+ response.getExercise().getExercise());
						break;
				case 2:	modelAndView.setViewName(response.getExercise().getView()+"/"+ response.getExercise().getExercise());
						break;
				case 3:	modelAndView=getVygotskyPolicySequencerExerciseWhizz(request);
						break;
				case 4:	modelAndView=getVygotskyPolicySequencerExerciseCTAT(request);
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
				modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
				return modelAndView;
			}
			else {
				switch (getLoginUserService().getCondition(request.getHeaderVO())) {
					case 1:	return getStateMachineSequencerExercise(request);
					case 2:	return getStateMachineSequencerExercise(request);//JLF: No speech
					case 3:	return getVygotskyPolicySequencerExerciseWhizz(request);
					case 4:	return getVygotskyPolicySequencerExerciseCTAT(request);
					case 5:	return getStudentNeedsAnalysisExercise(request);
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
	 * Get the exercise from the Vygotsky Policy Sequencer 
	 */
	private ModelAndView getVygotskyPolicySequencerExerciseWhizz(ExerciseSequenceRequestVO request){
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String TRIAL = rb.getString("vps.trial");
		logger.info("JLF --- getVygotskyPolicySequencerExerciseWhizz() --- Get the exercise from the Vygotsky Policy Sequencer "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
//		DBManagment manag = new DBManagment();
		Date date= new Date();
		Timestamp timestamp= new Timestamp(date.getTime());
		int studentId; //22516;
		int prevStudentScore;//95;
		String prevLessonId;//"GB0875AAx0100";
		String whizzLessonSuggestion = "GB0900CAx0200";
		try {
			prevLessonId = getLoginUserService().getIdExersiceSequenceUser(request.getHeaderVO()).toString();
			studentId = getLoginUserService().getIdUserInfo(request.getHeaderVO());
			prevStudentScore=getLoginUserService().getLastScoreSequenceUser(request.getHeaderVO());
			String ID= WhizzSequencer.next(studentId, prevLessonId, prevStudentScore, timestamp, whizzLessonSuggestion, Boolean.parseBoolean(TRIAL));
			if (ID==null || ID.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			//JLF: Getting exercise to store as well as the idSequencer
			request.setNameExercise(ID);
			request.setIdExercise(getExerciseSequenceService().getSpecificExercise(request).getExercise().getIdExercise());
			request.setIdVPSExercise(ID);
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			ExerciseVO response=getExerciseSequenceService().getWholeViewFromIDSequencer(request).getExercise();
			modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
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
			String ID= FTSequencer.next(studentId, prevLessonId, prevStudentScore, timestamp, whizzLessonSuggestion);
			if (ID==null || ID.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			request.setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			//JLF: Getting exercise to store as well as the idSequencer
			request.setNameExercise(ID);
			request.setIdExercise(getExerciseSequenceService().getSpecificExercise(request).getExercise().getIdExercise());
			request.setIdVPSExercise(ID);
			getExerciseSequenceService().insertCurrentVPSExercise(request);
			ExerciseVO response=getExerciseSequenceService().getWholeViewFromIDSequencer(request).getExercise();
			modelAndView.setViewName(response.getView()+"/"+ response.getExercise());
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
	private ModelAndView getStudentNeedsAnalysisExercise(ExerciseSequenceRequestVO request){
		logger.info("JLF --- getStudentNeedsAnalysisExercise() --- Get the exercise from student needs analysis "+"User= "+this.getUsername());
		ModelAndView modelAndView = new ModelAndView();
		ExercisesConverter ec= new ExercisesConverter();
		AudioRequestVO reqad=new AudioRequestVO();
		SpeechRecognitionRequestVO reqsr=new SpeechRecognitionRequestVO();
		byte[] audioSt;
		try {
			reqad.setHeaderVO(new HeaderVO());
			reqad.getHeaderVO().setLoginUser(user.getUsername());
			reqsr.setHeaderVO(new HeaderVO());
			reqsr.getHeaderVO().setLoginUser(user.getUsername());
			reqsr.getHeaderVO().setIdUser(getLoginUserService().getIdUserInfo(reqsr.getHeaderVO()));
			audioSt=getSpeechRecognitionService().getCurrentAudioFromPlatform(reqad).getAudio();
			getSnaService().setAudio(audioSt);
			String response=getSnaService().getNextTask();
			if (response==null || response.equals("")){
				return getStateMachineSequencerExercise(request);
			}
			modelAndView.setViewName("fractionsLabViews/"+ ec.getExercise().get(response));
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

}
