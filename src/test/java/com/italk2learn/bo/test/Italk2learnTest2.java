package com.italk2learn.bo.test;

import org.apache.log4j.Logger;
import org.gienah.testing.junit.Configuration;
import org.gienah.testing.junit.Dependency;
import org.gienah.testing.junit.SpringRunner;
import org.gienah.testing.junit.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.vo.HeaderVO;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.SpeechRecognitionResponseVO;

@RunWith(value = SpringRunner.class)
@Configuration(locations = { "spring/web-application-config.xml" })
public class Italk2learnTest2 {
	private static final Logger LOGGER = Logger
			.getLogger(Italk2learnTest2.class);
			
			@Dependency
			private ISpeechRecognitionBO speechRecognitionService;

			@Test
			@Transactional
			public void getSpeechRecognitionTest() throws Exception{
				LOGGER.info("TESTING getSpeechRecognition");
				SpeechRecognitionRequestVO request= new SpeechRecognitionRequestVO();
				//request.setHeaderVO(CheckConstants.HEADER_ES);
				boolean testOk = false;
				request.setHeaderVO(new HeaderVO());
				request.getHeaderVO().setLoginUser("tludmetal");
				try {
					final SpeechRecognitionResponseVO response = this.speechRecognitionService.getSpeechRecognition(request);
					testOk = true;
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error(e);
				}
				Assert.assertTrue(testOk);
			}
			
			@Test
			@Transactional
			public void parseTranscriptionTest() throws Exception{
				LOGGER.info("TESTING parseTranscription");
				SpeechRecognitionRequestVO request= new SpeechRecognitionRequestVO();
				//request.setHeaderVO(CheckConstants.HEADER_ES);
				boolean testOk = false;
				request.setHeaderVO(new HeaderVO());
				request.getHeaderVO().setLoginUser("tludmetal");
				try {
					final SpeechRecognitionResponseVO response = this.speechRecognitionService.parseTranscription(request);
					testOk = true;
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error(e);
				}
				Assert.assertTrue(testOk);
			}

			public ISpeechRecognitionBO getSpeechRecognitionService() {
				return speechRecognitionService;
			}

			public void setSpeechRecognitionService(
					ISpeechRecognitionBO speechRecognitionService) {
				this.speechRecognitionService = speechRecognitionService;
			}

}
