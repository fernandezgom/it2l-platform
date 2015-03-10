package com.italk2learn.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.italk2learn.bo.TaskIndependentSupportBO;
import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.bo.inter.IPerceiveDifficultyTaskBO;
import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.tis.inter.ITISWrapper;
import com.italk2learn.vo.AudioRequestVO;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.PTDRequestVO;
import com.italk2learn.vo.PTDResponseVO;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.SpeechRecognitionResponseVO;
import com.italk2learn.vo.TaskIndependentSupportRequestVO;

/**
 * JLF: Handles requests for the application speech recognition.
 */
@Controller
@Scope("session")
@RequestMapping("/speechRecognition")
public class SpeechRecognitionController{
	

	private static final Logger logger = LoggerFactory.getLogger(SpeechRecognitionController.class);
	
	//Request petition
	private SpeechRecognitionRequestVO request;
    
	//Response petition
	private SpeechRecognitionResponseVO response= new SpeechRecognitionResponseVO();
	
	private String username;
	
	/*Services*/
	private ISpeechRecognitionBO speechRecognitionService;
	private IPerceiveDifficultyTaskBO perceiveDifficultyTaskService;
	private ILoginUserService loginUserService;
	private ITISWrapper TISWrapperService;

    @Autowired
    public SpeechRecognitionController(ISpeechRecognitionBO speechRecognition,IPerceiveDifficultyTaskBO perceiveDifficultyTask, ILoginUserService loginUserService, ITISWrapper tisWrapper) {
    	this.speechRecognitionService=speechRecognition;
    	this.perceiveDifficultyTaskService=perceiveDifficultyTask;
    	this.loginUserService=loginUserService;
    	this.setTISWrapperService(tisWrapper);
    }
	
	/**
	 * Main method to send audio to the speech recogniser, this method is used each 5 seconds once the speech audio component is on
	 */
	@RequestMapping(value = "/sendData",method = RequestMethod.POST)
	@ResponseBody
	public void sendData(@RequestBody byte[] body) {
		logger.info("JLF --- SpeechRecognitionController sendData --- Sending data to the speech recogniser");
		request= new SpeechRecognitionRequestVO();
		AudioRequestVO reqad=new AudioRequestVO();
		request.setHeaderVO(new HeaderVO());
		reqad.setHeaderVO(new HeaderVO());
		try {
			request.getHeaderVO().setLoginUser(getUsername());
			reqad.getHeaderVO().setLoginUser(getUsername());
			request.setData(body);
			reqad.setAudio(body);
			getSpeechRecognitionService().concatenateAudioStream(reqad);
			response=((SpeechRecognitionResponseVO) getSpeechRecognitionService().sendNewAudioChunk(request));
			TaskIndependentSupportRequestVO tisObject= new TaskIndependentSupportRequestVO();
			tisObject.setWords(response.getLiveResponse());
			getTISWrapperService().sendSpeechOutputToSupport(tisObject);
		} catch (Exception e){
			logger.error(e.toString());
		}
	}
	
	
	/**
	 * Method that initialises ASREngine to be prepared to accept chunks of audio
	 */
	@RequestMapping(value = "/initEngine",method = RequestMethod.GET)
	@ResponseBody
	public Boolean initASREngine(@RequestParam(value = "user") String user, HttpServletRequest req) {
		logger.info("JLF --- SpeechRecognitionController initEngine --- Initialising speech recognition engine, user="+user);
		request= new SpeechRecognitionRequestVO();
		request.setHeaderVO(new HeaderVO());
		this.setUsername(user);
		request.getHeaderVO().setLoginUser(user);
		try {
			response=((SpeechRecognitionResponseVO) getSpeechRecognitionService().initASREngine(request));
			//JLF: Sending first chunk always, otherwise SAIL software crashes
			if (response.isOpen()){
				sendData(new byte[0]);
			}
			return response.isOpen();
		} catch (Exception e){
			logger.error(e.toString());
		}
		return response.isOpen();
	}
	
	/**
	 * Main method to get a transcription of Sails Software
	 */
	@RequestMapping(value = "/closeEngine",method = RequestMethod.POST)
	@ResponseBody
	public String closeASREngine(@RequestBody byte[] body) {
		logger.info("JLF --- SpeechRecognitionController closeEngine --- Closing speech recognition engine");
		request= new SpeechRecognitionRequestVO();
		AudioRequestVO reqad=new AudioRequestVO();
		if (body==null)
			body=new byte[0];
		try {
			request.setHeaderVO(new HeaderVO());
			reqad.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(getUsername());
			reqad.getHeaderVO().setLoginUser(getUsername());
			request.getHeaderVO().setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			request.setData(body);
			reqad.setAudio(body);
			getSpeechRecognitionService().concatenateAudioStream(reqad);
			request.setFinalByteArray(getSpeechRecognitionService().getCurrentAudioFromPlatform(reqad).getAudio());
			getSpeechRecognitionService().saveByteArray(request);
			response=((SpeechRecognitionResponseVO) getSpeechRecognitionService().closeASREngine(request));
			return response.getResponse();
		} catch (Exception e){
			logger.error(e.toString());
		}
		return response.getResponse();
	}
	
	/**
	 * Method to get the perceive difficulty task at the end of each task
	 */
	@RequestMapping(value = "/callPTD",method = RequestMethod.POST)
	@ResponseBody
	public int callPTD(@RequestBody byte[] body) {
		logger.info("JLF --- SpeechRecognitionController callPTD --- Get Perceive difficulty task from Audio based difficulty classifier");
		PTDRequestVO req= new PTDRequestVO();
		PTDResponseVO res= new PTDResponseVO();
		AudioRequestVO reqad=new AudioRequestVO();
		try {
			req.setHeaderVO(new HeaderVO());
			req.getHeaderVO().setLoginUser(getUsername());
			reqad.setHeaderVO(new HeaderVO());
			reqad.getHeaderVO().setLoginUser(getUsername());
			req.getHeaderVO().setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			req.setAudioByteArray(getSpeechRecognitionService().getCurrentAudioFromPlatform(reqad).getAudio());
			res=getPerceiveDifficultyTaskService().callPTD(req);
			logger.info("Emotion: "+res.getPTD());
			return res.getPTD();
		} catch (Exception e){
			logger.error(e.toString());
		}
		return res.getPTD();
	}
	
	public ILoginUserService getLoginUserService() {
		return loginUserService;
	}

	public void setLoginUserService(ILoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	public ISpeechRecognitionBO getSpeechRecognitionService() {
		return speechRecognitionService;
	}

	public void setSpeechRecognitionService(ISpeechRecognitionBO speechRecognitionService) {
		this.speechRecognitionService = speechRecognitionService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public IPerceiveDifficultyTaskBO getPerceiveDifficultyTaskService() {
		return perceiveDifficultyTaskService;
	}

	public void setPerceiveDifficultyTaskService(
			IPerceiveDifficultyTaskBO perceiveDifficultyTaskService) {
		this.perceiveDifficultyTaskService = perceiveDifficultyTaskService;
	}

	public ITISWrapper getTISWrapperService() {
		return TISWrapperService;
	}

	public void setTISWrapperService(ITISWrapper tISWrapperService) {
		TISWrapperService = tISWrapperService;
	}

}
