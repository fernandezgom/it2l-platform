/**
 * 
 */
package com.italk2learn.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.italk2learn.sna.inter.IStudentNeedsAnalysis;
import com.italk2learn.vo.StudentNeedsAnalysisRequestVO;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@Scope("session")
@RequestMapping("/sna")
public class StudentNeedsAnalysisController {
       
	private IStudentNeedsAnalysis snaService;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentNeedsAnalysisController.class);

	@Autowired
    public StudentNeedsAnalysisController(IStudentNeedsAnalysis snaService) {
		this.setSnaService(snaService);
	}
	
	/**
	 * Sending feedback type to Student Needs Analysis
	 * 
	 * @input Feedback type 
	 */
	@RequestMapping(value = "/sendFeedbackTypeToSNA",method = RequestMethod.POST)
	@ResponseBody
	public void sendFeedbackTypeToSNA(@RequestBody StudentNeedsAnalysisRequestVO snaRequest) {
		logger.info("JLF --- StudentNeedsAnalysisController sendFeedbackTypeToSNA()");
		try {
			getSnaService().sendFeedbackTypeToSNA(snaRequest.getFeedbackType());
		} catch (Exception e){
			logger.error(e.toString());
		}
	}

	public IStudentNeedsAnalysis getSnaService() {
		return snaService;
	}

	public void setSnaService(IStudentNeedsAnalysis snaService) {
		this.snaService = snaService;
	}
	

	
}
