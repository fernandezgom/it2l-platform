/**
 * 
 */
package com.italk2learn.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.italk2learn.bo.inter.ITaskIndependentSupportBO;
import com.italk2learn.tis.inter.ITISWrapper;
import com.italk2learn.vo.FractionsLabRequestVO;
import com.italk2learn.vo.FractionsLabResponseVO;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.TaskIndependentSupportRequestVO;
import com.italk2learn.vo.TaskIndependentSupportResponseVO;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@Scope("session")
@RequestMapping("/tis")
public class TaskIndependentSupportController {
       
	private LdapUserDetailsImpl user;
	
//	JLF:Services
	private ITaskIndependentSupportBO tisService;
	private ITISWrapper TISWrapperService;
	
	private static final Logger logger = LoggerFactory.getLogger(TaskIndependentSupportController.class);

	@Autowired
    public TaskIndependentSupportController(ITaskIndependentSupportBO tisService, ITISWrapper tisWrapper) {
		this.tisService=tisService;
		this.TISWrapperService=tisWrapper;
	}
	
	
	/**
	 * Test task independent support receiving the id from the file. 
	 * 
	 * @input Number of file is requested to send to the engine. 
	 * 
	 * @return the name of the page
	 */
	@RequestMapping(value = "/testTIS",method = RequestMethod.GET)
	@ResponseBody
	public TaskIndependentSupportResponseVO testTaskIndependentSupport(@RequestParam(value = "option") String op) {
		logger.info("JLF --- TaskIndependentSupportController testTaskIndependentSupport()");
		TaskIndependentSupportRequestVO request= new TaskIndependentSupportRequestVO();
		try {
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
			request.setFile(Integer.parseInt(op));
			TaskIndependentSupportResponseVO res= getTisService().sendRealSpeechToSupport(request);
			return res;
		} catch (Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	/**
	 * JLF: Controller to call TIS from TDS
	 */
	@RequestMapping(value = "/callTIS", method = RequestMethod.POST)
	@ResponseBody
	public TaskIndependentSupportResponseVO callTIDfromTDS(@RequestBody TaskIndependentSupportRequestVO tisRequest){
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController callTIDfromTDS --- Calling TIS from TDS "+"User: "+user.getUsername());
		logger.info("feedbackTest="+ tisRequest.getFeedbackText()+" ,currentFeedbackType="+tisRequest.getCurrentFeedbackType()+ " ,level="+ tisRequest.getLevel()+" ,followed"+tisRequest.getFollowed());
        try {
        	tisRequest.setHeaderVO(new HeaderVO());
        	tisRequest.getHeaderVO().setLoginUser(user.getUsername());
			TaskIndependentSupportResponseVO res=getTisService().callTISfromTID(tisRequest);
			return res;
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
        return null;
	}
	
	/**
	 * JLF: Controller to call TIS from TDS sending an event when done button is pressed
	 */
	@RequestMapping(value = "/sendDoneButtonPressed", method = RequestMethod.POST)
	@ResponseBody
	public TaskIndependentSupportResponseVO sendDoneButtonPressedToTIS(@RequestBody TaskIndependentSupportRequestVO tisRequest){
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController sendDoneButtonPressedToTIS --- Calling TIS from TDS "+"User: "+user.getUsername());
        try {
        	tisRequest.setHeaderVO(new HeaderVO());
        	tisRequest.getHeaderVO().setLoginUser(user.getUsername());
			TaskIndependentSupportResponseVO res=getTisService().sendDoneButtonPressedToTIS(tisRequest);
			return res;
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
        return null;
	}
	
	/**
	 * JLF: Controller to call TIS from TDS
	 */
	@RequestMapping(value = "/setFractionsLabinUse", method = RequestMethod.POST)
	@ResponseBody
	public void setFractionsLabinUse(@RequestBody TaskIndependentSupportRequestVO tisRequest){
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController setFractionsLabinUse ---  "+"User: "+user.getUsername());
		logger.info("setFractionsLabinUse="+ tisRequest.isFlEnable());
		TaskIndependentSupportRequestVO request= new TaskIndependentSupportRequestVO();
        try {
        	request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
			request.setFlEnable(tisRequest.isFlEnable());
			getTisService().setFractionsLabinUse(request);
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	
	/**
	 * Get the task independent support emotions
	 *
	 */
	@RequestMapping(value = "/getTIEmotions",method = RequestMethod.GET)
	@ResponseBody
	public FractionsLabResponseVO getTIEmotions(@Valid @ModelAttribute("emotions") FractionsLabRequestVO emotions) {
		logger.info("JLF --- TaskIndependentSupportController  getTIEmotions()");
		FractionsLabResponseVO response= new FractionsLabResponseVO();
		try {
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return response;
		} catch (Exception e){
			logger.error(e.toString());
		}
		return response;
	}
	
	
	/**
	 * Checking TIS Wrapper to see if something has changed
	 *
	 */
	@RequestMapping(value = "/checkTISWrapper",method = RequestMethod.GET)
	@ResponseBody
	public TaskIndependentSupportResponseVO checkTISWrapper() {
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController checkTISWrapper --- Check if something changed in TISWrapper "+"User: "+user.getUsername());
		TaskIndependentSupportRequestVO request= new TaskIndependentSupportRequestVO();
        try {
        	request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
			TaskIndependentSupportResponseVO res=getTisService().checkTISWrapper(request);
			return res;
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
        return null;
	}
	
	
	/**
	 * Initialise TISWrapper component
	 *
	 */
	@RequestMapping(value = "/initialiseTISWrapper",method = RequestMethod.GET)
	@ResponseBody
	public void initialiseTISWrapper(@RequestParam(value = "enable") String enable) {
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController initialiseTISWrapper --- Initialise TIS Wrapper "+"User: "+user.getUsername());
        try {
        	if (Boolean.valueOf(enable)==true){
        		getTISWrapperService().startTIS();
        	} else {
        		getTISWrapperService().stopTimers();
        	}
        	
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	
	/**
	 * Initialise TISWrapper component
	 *
	 */
	@RequestMapping(value = "/managePopup",method = RequestMethod.GET)
	@ResponseBody
	public void managePopupTISWrapper(@RequestParam(value = "enable") String enable) {
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController initialiseTISWrapper --- Initialise TIS Wrapper "+"User: "+user.getUsername());
        try {
        	if (Boolean.valueOf(enable)==true){
        		getTISWrapperService().popUpOpen();
        	} else {
        		getTISWrapperService().popUpClosed();
        	}
        	
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}
	
	/**
	 * Checking TIS Wrapper to see if something has changed
	 *
	 */
	@RequestMapping(value = "/startNewExercise",method = RequestMethod.GET)
	@ResponseBody
	public TaskIndependentSupportResponseVO startNewExercise() {
		user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("JLF --- TaskIndependentSupportController startNewExercise --- Call when start a new exercise "+"User: "+user.getUsername());
		TaskIndependentSupportRequestVO request= new TaskIndependentSupportRequestVO();
        try {
        	request.setHeaderVO(new HeaderVO());
			request.getHeaderVO().setLoginUser(user.getUsername());
			TaskIndependentSupportResponseVO res=getTisService().startNewExercise(request);
			return res;
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
        return null;
	}
	
	/**
	 * Get the task independent support emotions
	 * 
	 */
	@RequestMapping(value = "/getTIFeedback",method = RequestMethod.GET)
	@ResponseBody
	public FractionsLabResponseVO getTIFeedback(@Valid @ModelAttribute("feeback") FractionsLabRequestVO feedback) {
		logger.info("JLF --- TaskIndependentSupportController getTIFeedback()");
		FractionsLabResponseVO response= new FractionsLabResponseVO();
		try {
			user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return response;
		} catch (Exception e){
			logger.error(e.toString());
		}
		return response;
	}


	public ITaskIndependentSupportBO getTisService() {
		return tisService;
	}


	public void setTisService(ITaskIndependentSupportBO tisService) {
		this.tisService = tisService;
	}


	public ITISWrapper getTISWrapperService() {
		return TISWrapperService;
	}


	public void setTISWrapperService(ITISWrapper tISWrapperService) {
		TISWrapperService = tISWrapperService;
	}
	
}
