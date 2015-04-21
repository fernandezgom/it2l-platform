package com.italk2learn.bo;

import java.io.File;
import java.util.Locale;
import java.util.Set;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.italk2learn.bo.inter.ISpeechProductionBO;
import com.italk2learn.vo.SpeechProductionRequestVO;
import com.italk2learn.vo.SpeechProductionResponseVO;

@Service("speechProductionBO")
public class SpeechProductionBO implements ISpeechProductionBO  {
	

	private static final Logger logger = LoggerFactory.getLogger(SpeechProductionBO.class);
	
	
    public SpeechProductionResponseVO generateAudioFile(SpeechProductionRequestVO request) {
    	SpeechProductionResponseVO response= new SpeechProductionResponseVO();
		try {
//			MaryInterface marytts = new LocalMaryInterface();
//			Set<String> voices = marytts.getAvailableVoices();
//			logger.info("I currently have " + marytts.getAvailableVoices() + " voices in "
//				    + marytts.getAvailableLocales() + " languages available.");
//			logger.info("Out of these, " + marytts.getAvailableVoices(Locale.GERMAN) + " are for German.");
//			logger.info("Out of these, " + marytts.getAvailableVoices(Locale.ENGLISH) + " are for English.");
//			if (request.getLanguage().equals(SpeechProductionRequestVO.ENGLISH)){
//				marytts.setVoice("cmu-slt-hsmm");
//			}
//			else {
//				marytts.setVoice("bits1-hsmm");
//			}
//			AudioInputStream audio = marytts.generateAudio(request.getMessage());
//			AudioFormat audioFormat = audio.getFormat();
//			logger.info("Channels: "+audioFormat.getChannels());
//			logger.info("Encoding: "+audioFormat.getEncoding());
//			logger.info("Frame Rate: "+audioFormat.getFrameRate());
//			logger.info("Frame Size: "+audioFormat.getFrameSize());
//			logger.info("Sample Rate: "+audioFormat.getSampleRate());
//			logger.info("Sample size (bits): "+audioFormat.getSampleSizeInBits());
//			logger.info("Big endian: "+audioFormat.isBigEndian());
//			logger.info("Audio Format String: "+audioFormat.toString());
//	        AudioInputStream encodedASI = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, audio);
//
//	        try{
//	            int i = AudioSystem.write(encodedASI, AudioFileFormat.Type.WAVE, new File(request.getMessage().hashCode()+".wav"));
//	            logger.info("Bytes Written: "+i);
//	            response.setFile(request.getMessage().hashCode()+".wav");
//	        }catch(Exception e){
//	            e.printStackTrace();
//	        }
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			e.printStackTrace();
		}
    	return response;
    }


}
