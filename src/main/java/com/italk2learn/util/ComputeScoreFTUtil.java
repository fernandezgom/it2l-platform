package com.italk2learn.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.italk2learn.tis.MathsVocabDetector;

public class ComputeScoreFTUtil {
	
	private float score;
	private int numWrong;
	private int numHint;
	
	public ComputeScoreFTUtil(List<String> logs, String taskName){
		super();
		String res=Character.toString(taskName.charAt(4));
		//JLF: string name taskx where x >= 5 has the second version whereas the other ones has v1. This is the id mapping of the string names and the iTalk2LEarn platform IDs:
		if (res!= null && !res.equals("") && Integer.parseInt(res)>=5)
			computeScoreV2(logs);
		else if (res!= null && !res.equals("")) 
			computeScoreV1(logs);			
	}

	private void computeScoreV1(List<String> logs) {

		// takes the log and remove the corrupted xml string
		//String log = studInteractions.getString(3);
		for (int i=0;i<logs.size();i++) {
			String log= logs.get(i);
			String result;
			String[] tmp2 = null;
			try {
				result = java.net.URLDecoder.decode(log, "UTF-8");
				tmp2 = result.split("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("tmp length " + tmp2.length);
			int maxNumHint=0;
			if (tmp2.length == 3) {
				result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + tmp2[1]	+ tmp2[2];
				// here you should call all the lines available for the specific
				// student and for the task he just compelted
				// here you start a for over all the lines you have
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				NodeList nList = null;
				String idStr = "not";
				Document doc = null;
				try {
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(result.getBytes("utf-8"))));
					doc.getDocumentElement().normalize();
					nList = doc.getElementsByTagName("log_action");
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
	
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						try {
							// check if it is a valid outcome
							idStr = nList.item(0).getAttributes().getNamedItem("action_id").getNodeValue();
							//
							// give an exception if the value is not present and the
							// entry is not to be considered
	//						System.out.println("New line German and valid result IDStud "
	//										+ studID
	//										+ "PrevIDStud "
	//										+ previousStudID
	//										+ " outcome1 "
	//										+ outcome1
	//										+ " ContID "
	//										+ Exid
	//										+ " PrevContId " + previousContID);
							System.out.println(result);
							// compute numWrong numHint and MaxNumHint
							if (idStr.equals("TUTOR_ACTION RESULT")) {
	
								System.out.println(result + "\n");
								System.out.println();
	
								NodeList nList2 = doc.getElementsByTagName("action_evaluation");
								String outcome = nList2.item(0).getFirstChild().getNodeValue();
	
								if (outcome.equals("InCorrect")) {
									this.numWrong++;
								}
							} else if (idStr.equals("TUTOR_ACTION HINT_MSG")) {
	
								System.out.println(result + "\n");
								System.out.println();
	
								NodeList nList2 = doc.getElementsByTagName("action_evaluation");
								String outcome = nList2.item(0).getAttributes().getNamedItem("current_hint_number").getNodeValue();
								maxNumHint = Integer.parseInt(nList2.item(0).getAttributes().getNamedItem("total_hints_available").getNodeValue());
	
								if (this.numHint < maxNumHint)
									this.numHint++;
	
								System.out.println("num Hint: " + outcome
										+ " num Hint computed: " + this.numHint
										+ " totalHintNum: " + maxNumHint);
	
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// here you end the for
					// use it to compute the score once you have computed numWrong,
					// numHint and max NumHInt
	
					if (maxNumHint != 0)
						this.score = 1 - (numWrong * 0.1f + (numHint / maxNumHint));
					else
						this.score = 1 - (numWrong * 0.1f);
	
					if (this.score < 0)
						this.score = 0;
	
				}
			}
		}
	}

	private void computeScoreV2(List<String> logs) {
		// takes the log and remove the corrupted xml string
		//String log = studInteractions.getString(3);
		for (int i=0;i<logs.size();i++) {
			String log= logs.get(i);
			String result = null;
			try {
				result = java.net.URLDecoder.decode(log, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] tmp2 = result.split("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>");
			int maxNumHint=0;
			System.out.println("tmp length " + tmp2.length);
			if (tmp2.length == 3) {
				result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + tmp2[1]	+ tmp2[2];
				// here you should call all the lines available for the specific
				// student and for the task he just compelted
				// here you start a for over all the lines you have
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;
				Document doc = null;
				try {
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(result.getBytes("utf-8"))));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("log_action");
				String idStr = "not";
				for (int temp3 = 0; temp3 < nList.getLength(); temp3++) {
					Node nNode3 = nList.item(temp3);
					if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode3;
						NodeList nList2 = eElement.getElementsByTagName("semantic_event");
						String idStr2 = nList2.item(0).getAttributes().getNamedItem("name").getNodeValue();
						System.out.println(idStr2);
						if (idStr2.equals("HINT_MSG")) {
							NodeList nList4 = doc.getElementsByTagName("action_evaluation");
							String outcome = nList4.item(0).getAttributes().getNamedItem("current_hint_number").getNodeValue();
							maxNumHint = Integer.parseInt(nList4.item(0).getAttributes().getNamedItem("total_hints_available").getNodeValue());
							if (numHint < maxNumHint)
								numHint++;
	
							System.out.println("num Hint: " + outcome
									+ " num Hint computed: " + numHint
									+ " totalHintNum: " + maxNumHint);
	
						} else if (idStr2.equals("RESULT")) {
							NodeList nList4 = doc.getElementsByTagName("action_evaluation");
							String outcome = nList4.item(0).getFirstChild().getNodeValue();
	
							if (outcome.equals("INCORRECT")) {
								numWrong++;
							}
							System.out.println(outcome + " " + numWrong
									+ " score=100;");
							this.score = 0;
						} else
							System.out.println("other tag " + idStr2);
					}
				}
			}
			// here you end the for
			// use these lines to compute the score once you have computed numWrong,
			// numHint and max NumHInt
	
			if (maxNumHint != 0)
				this.score = 1 - (numWrong * 0.1f + (numHint / maxNumHint));
			else
				this.score = 1 - (numWrong * 0.1f);
	
			if (this.score < 0)
				this.score = 0;
		}

	}
	
	public int getScoreRounded(){
		return Math.round(getScore()*100);
	}
	
	public int getNumWrong() {
		return numWrong;
	}

	public void setNumWrong(int numWrong) {
		this.numWrong = numWrong;
	}

	public int getNumHint() {
		return numHint;
	}

	public void setNumHint(int numHint) {
		this.numHint = numHint;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
