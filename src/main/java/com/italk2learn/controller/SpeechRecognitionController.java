package com.italk2learn.controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

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

import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.bo.inter.IPerceiveDifficultyTaskBO;
import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.sna.inter.IStudentNeedsAnalysis;
import com.italk2learn.speech.util.EnginesMap;
import com.italk2learn.tis.inter.ITISWrapper;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.SpeechRecognitionResponseVO;

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
	private IStudentNeedsAnalysis snaService;

	@Autowired
    public SpeechRecognitionController(ISpeechRecognitionBO speechRecognition,IPerceiveDifficultyTaskBO perceiveDifficultyTask, ILoginUserService loginUserService, ITISWrapper tisWrapper, IStudentNeedsAnalysis snaService) {
    	this.speechRecognitionService=speechRecognition;
    	this.perceiveDifficultyTaskService=perceiveDifficultyTask;
    	this.loginUserService=loginUserService;
    	this.setSnaService(snaService);
    	this.setTISWrapperService(tisWrapper);
    }
	
	/**
	 * Main method to send audio to the speech recogniser, this method is used each 5 seconds once the speech audio component is on
	 */
	@RequestMapping(value = "/sendData",method = RequestMethod.POST)
	@ResponseBody
	public void sendData(@RequestBody byte[] body) {
		logger.info("JLF --- SpeechRecognitionController sendData --- Sending data to the speech recogniser");
		Future<SpeechRecognitionResponseVO> respSR;
		request= new SpeechRecognitionRequestVO();
		request.setHeaderVO(new HeaderVO());
		try {
			request.getHeaderVO().setLoginUser(getUsername());
			request.setData(body);
			getSpeechRecognitionService().concatenateAudioStream(request);
			getSpeechRecognitionService().createAudioFile(request);
			respSR=getSpeechRecognitionService().sendNewAudioChunk(request);
			request=null;
			body=null;
			while (!respSR.isDone()) {
	            Thread.sleep(10); //10-millisecond pause between each check
	        }
			for (int i=0;i<respSR.get().getLiveResponse().size();i++)
				logger.info("liveResponse word="+ respSR.get().getLiveResponse().get(i));
			getTISWrapperService().sendSpeechOutputToSupport(getUsername(), respSR.get().getLiveResponse());
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
		if (body==null)
			body=new byte[0];
		try {
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(getUsername());
			request.getHeaderVO().setIdUser(getLoginUserService().getIdUserInfo(request.getHeaderVO()));
			request.setData(body);
			getSpeechRecognitionService().concatenateAudioStream(request);
			//request.setFinalByteArray(getSpeechRecognitionService().getCurrentAudioFromPlatform(request).getAudio());
			//getSpeechRecognitionService().saveByteArray(request);
			response=((SpeechRecognitionResponseVO) getSpeechRecognitionService().closeASREngine(request));
			saveTranscription(response.getResponse(), getUsername());
			request=null;
			body=null;
			return response.getResponse();
		} catch (Exception e){
			logger.error(e.toString());
		}
		return response.getResponse();
	}
	
	/**
	 * Method that get 
	 */
	@RequestMapping(value = "/speechRecoMonitor",method = RequestMethod.GET)
	@ResponseBody
	public String speechRecoMonitor() {
		logger.info("JLF --- Speech Recognition Monitor");
		return EnginesMap.getInstance().getAllInfo();
	}
	
	
	public void saveTranscription(String text, String user) {
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String path=rb.getString("transcriptions");
		try {
			if (text!=null && !text.isEmpty() && text.length() > 1) {
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				PrintWriter out = new PrintWriter(path+user+"-"+dateFormat.format(date)+".txt");
				out.println(text);
				out.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
    public IStudentNeedsAnalysis getSnaService() {
		return snaService;
	}

	public void setSnaService(IStudentNeedsAnalysis snaService) {
		this.snaService = snaService;
	}
	
}
