package com.italk2learn.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.bo.inter.ITaskIndependentSupportBO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.tis.TISWrapper;
import com.italk2learn.tis.inter.ITISWrapper;
import com.italk2learn.vo.AudioRequestVO;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.SpeechRecognitionResponseVO;
import com.italk2learn.vo.TaskIndependentSupportRequestVO;
import com.italk2learn.vo.TaskIndependentSupportResponseVO;

@Service("taskIndependentSupportBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class TaskIndependentSupportBO implements ITaskIndependentSupportBO  {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskIndependentSupportBO.class);
	
	
	/*Services*/
	private ISpeechRecognitionBO speechRecognitionService;
	private ITISWrapper TISWrapperService;
	private ILoginUserService loginUserService;
	
	private static final int ARRAY_SIZE = 500000;
	private static final int NUM_SECONDS = 5 * 1000;
	
	private SpeechRecognitionRequestVO request= new SpeechRecognitionRequestVO();
	private SpeechRecognitionResponseVO liveResponse=new SpeechRecognitionResponseVO();
	private List<byte[]> audioChunks=new ArrayList<byte[]>();
	
	private int counter=0;
	private boolean oneChunk=true;
	private boolean loop=true;
	
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public TaskIndependentSupportBO(ITISWrapper tisWrapper, ISpeechRecognitionBO speechRecognition, ILoginUserService loginUserService) {
		this.TISWrapperService=tisWrapper;
		this.speechRecognitionService=speechRecognition;
		this.setLoginUserService(loginUserService);
	}
	
	public ITISWrapper getTISWrapperService() {
		return TISWrapperService;
	}

	public void setTISWrapperService(ITISWrapper tISWrapperService) {
		TISWrapperService = tISWrapperService;
	}

	public TaskIndependentSupportResponseVO sendNextWords(TaskIndependentSupportRequestVO request) throws ITalk2LearnException{
		logger.info("sendNextWords()--- ");
		try {
			TaskIndependentSupportResponseVO response= new TaskIndependentSupportResponseVO();
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	public TaskIndependentSupportResponseVO getStudentInfo(TaskIndependentSupportRequestVO request) throws ITalk2LearnException{
		logger.info("getStudentInfo()---");
		try {
			TaskIndependentSupportResponseVO response= new TaskIndependentSupportResponseVO();
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	/**
	 * Method to calling Task Independent Support from Task Dependent Support, it receives some parameters from TDS
	 */
	public TaskIndependentSupportResponseVO callTISfromTID(TaskIndependentSupportRequestVO request) throws ITalk2LearnException{
		logger.info("JLF TaskIndependentSupportBO callTISfromTID() --- Calling Task Independent Support from Task Dependent Support");
		TaskIndependentSupportResponseVO response= new TaskIndependentSupportResponseVO();
		LdapUserDetailsImpl user=(LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
			getTISWrapperService().setAudio(audioSt);
			reqsr.setFinalByteArray(audioSt);
			getSpeechRecognitionService().saveByteArray(reqsr);
			getTISWrapperService().sendTDStoTIS(request.getFeedbackText(), request.getCurrentFeedbackType(), request.getLevel(), request.getFollowed(), request.isViewed());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	/**
	 * Method to set if Fractions Lab is currently in use by the user at the platform
	 */
	public TaskIndependentSupportResponseVO setFractionsLabinUse(TaskIndependentSupportRequestVO request) throws ITalk2LearnException{
		logger.info("JLF TaskIndependentSupportBO setFractionsLabinUse() --- Setting fractionLab in use");
		TaskIndependentSupportResponseVO response= new TaskIndependentSupportResponseVO();
		try {
			getTISWrapperService().setFractionsLabinUse(request.isFlEnable());
			return response;
		}
		catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	
	//JLF Method to test Task Independent Support. Just for demo purposes, no longer required
	public TaskIndependentSupportResponseVO sendRealSpeechToSupport(TaskIndependentSupportRequestVO req) throws Exception{
		TaskIndependentSupportResponseVO resultado=new TaskIndependentSupportResponseVO();
		final int dataSize = (int) (Runtime.getRuntime().maxMemory());
		logger.info("Max amount of memory is: "+dataSize);
		request.setHeaderVO(new HeaderVO());
		request.getHeaderVO().setLoginUser("student1");
		request.setInstance(12);
		File f;
		switch (req.getFile()) {
			case 1: f=new File("complicated-example-mono.wav");
					req.setCheckMathKeywords(false);
					break;
			case 2: f=new File("no-maths-vocab-example-01-mono.wav");
					req.setCheckMathKeywords(true);
					break;
			case 3: f=new File("maths-example-mono.wav");
					req.setCheckMathKeywords(true);
					break;
			case 4: f=new File("hard-example-mono.wav");
					req.setCheckMathKeywords(false);
					break;		
			default: f=new File("maths-example-02-mono.wav");
					break;		
		}
		long l=f.length();
		long numChunks=l/ARRAY_SIZE;
		logger.info("the file is " + l + " bytes long");
		FileInputStream finp = null;
		byte[] b=new byte[(int)l];
		try {
			finp = new FileInputStream(f);
			int i;
			i=finp.read(b);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.toString());
			e1.printStackTrace();
		}
		if (!oneChunk) {
			int initialChunk=0;
			int finalChunk=(int)l/(int)numChunks;
			for (int i=0;i<numChunks;i++){
				byte[] aux=Arrays.copyOfRange(b, initialChunk, finalChunk);
				audioChunks.add(aux);
				logger.info("Chunk "+i+" starts at "+initialChunk+" bytes and finish at "+finalChunk+" bytes");
				initialChunk=finalChunk+1;
				finalChunk=finalChunk+((int)l/(int)numChunks);
			}
			if (initialChunk<l){
				logger.info("Last chunk starts at "+initialChunk+" bytes and finish at "+l+" bytes");
				audioChunks.add(Arrays.copyOfRange(b, initialChunk, (int)l-1));
			}
		}
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("user", "student1");
		vars.put("instance", "12");
		vars.put("server", "localhost");
		vars.put("language", "en_ux");
		vars.put("model", "base");
		try {
			//Call initEngineService of an available instance
			TISWrapper res= new TISWrapper();
			Boolean isOpen=this.restTemplate.getForObject("http://193.61.29.166:8092/italk2learnsm/speechRecognition/initEngine?user={user}&instance={instance}&server={server}&language={language}&model={model}",Boolean.class, vars);
			if (isOpen){
				Timer timer = new Timer();
				//JLF:Send chunk each NUM_SECONDS
				if (!oneChunk)
					timer.scheduleAtFixedRate(new SpeechTask(), NUM_SECONDS,NUM_SECONDS);
				else {
					logger.info("Sent in one chunk");
					request.setData(b);
		    		liveResponse=restTemplate.postForObject("http://193.61.29.166:8092/italk2learnsm/speechRecognition/sendData", request, SpeechRecognitionResponseVO.class);
		    		String response=restTemplate.getForObject("http://193.61.29.166:8092/italk2learnsm/speechRecognition/closeEngine?instance={instance}",String.class, "12");
		    		if (response!=null){
		    			List<String> words= new ArrayList<String>();
		    			String[] auxWords=response.split(" ");	
		    			for (String aux : auxWords){
		    				System.out.println("word: "+aux);
		    				words.add(aux);
		    			}
		    			req.setWords(words);
		    			res.sendSpeechOutputToSupport(req);
		    			System.out.println("Output: "+response);
		    			loop=false;
		    		}
				}
			}
			while (loop){ }
			logger.info("All jobs finished");
			resultado.setPopUpWindow(res.getPopUpWindow());
			resultado.setMessage(res.getMessage());
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return null;
	}
	
	public ISpeechRecognitionBO getSpeechRecognitionService() {
		return speechRecognitionService;
	}

	public void setSpeechRecognitionService(ISpeechRecognitionBO speechRecognitionService) {
		this.speechRecognitionService = speechRecognitionService;
	}

	public ILoginUserService getLoginUserService() {
		return loginUserService;
	}

	public void setLoginUserService(ILoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	class SpeechTask extends TimerTask {
		    public void run() {
		    	if (counter<audioChunks.size()) {
		    		int aux=counter+1;
		    		logger.info("Sending chunk: " + aux);
		    		logger.info("the chunk is " + audioChunks.get(counter).length + " bytes long");
		    		request.setData(audioChunks.get(counter));
		    		liveResponse=restTemplate.postForObject("http://193.61.29.166:8092/italk2learnsm/speechRecognition/sendData", request, SpeechRecognitionResponseVO.class);
		    		counter++;
			    }
		    	else {
		    		String response=restTemplate.getForObject("http://193.61.29.166:8092/italk2learnsm/speechRecognition/closeEngine?instance={instance}",String.class, "12");
		    		if (response!=null){
		    			logger.info("Output: "+response);
		    			Assert.assertTrue(true);
		    		}
		    		loop=false;
		    	}
		    }
		}
	
}
