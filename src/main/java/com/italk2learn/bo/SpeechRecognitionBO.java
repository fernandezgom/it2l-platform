package com.italk2learn.bo;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.italk2learn.bo.inter.ISpeechRecognitionBO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.util.ASREngine;
import com.italk2learn.util.LibraryLoader;
import com.italk2learn.vo.SpeechRecognitionRequestVO;
import com.italk2learn.vo.SpeechRecognitionResponseVO;

@Service("speechRecognitionBO")
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class SpeechRecognitionBO implements ISpeechRecognitionBO {

	public SpeechRecognitionResponseVO getSpeechRecognition(SpeechRecognitionRequestVO request) throws ITalk2LearnException{
		try {
			String[] cmd = { "C:\\MMIndexer\\bin\\ASRSample.exe","en_ux","broadcast-news","base","C:\\prueba2.wav" };
	        Process p = Runtime.getRuntime().exec(cmd);
	        p.waitFor();
			return new SpeechRecognitionResponseVO();
		}
		catch (Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public SpeechRecognitionResponseVO parseTranscription(SpeechRecognitionRequestVO request) throws ITalk2LearnException{
		try {
			StringBuffer text = new StringBuffer();
			File transcription = new File("C:\\Italk2LearnEnvironment\\workspaceServer\\Italk2learnFinal\\prueba2.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(transcription);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("nbest");
			System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println("word: " + getValues("word", element));
					text.append(getValues("word", element)+ " ");
				}
			}
			return new SpeechRecognitionResponseVO();

		}
		catch (Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public SpeechRecognitionResponseVO sendDataToSails(SpeechRecognitionRequestVO request) throws ITalk2LearnException{
		try {
			ASREngine.sendDataToSails(request.getData());
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
		return null;
	}
	
	
	private static String getValues(String tag, Element element) {
		StringBuffer text= new StringBuffer();
		for (int i = 0; i < element.getElementsByTagName(tag).getLength(); i++) {
			NodeList nodes = element.getElementsByTagName(tag).item(i).getChildNodes();
			Node node = (Node) nodes.item(0);
			text.append(node.getNodeValue()+ " ");
		}
		return text.toString();
	}

}
