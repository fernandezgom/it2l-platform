package com.italk2learn.bo.inter;

import com.italk2learn.vo.SpeechProductionRequestVO;
import com.italk2learn.vo.SpeechProductionResponseVO;

public interface ISpeechProductionBO {
	
	public SpeechProductionResponseVO generateAudioFile(SpeechProductionRequestVO request);

}
