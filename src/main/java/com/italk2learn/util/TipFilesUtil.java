package com.italk2learn.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipFilesUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TipFilesUtil.class);
	
	/**
	 * JLF: Creates a TIP file and saves into the system 
	 */
    public static void createTIPFile(String name, String description, boolean[] values){
		logger.info("JLF --- createTIPFile --- Creates a TIP file and saves into the system ");
		logger.info("id_exercise="+name+" ,values="+values.toString());
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String _TIPPATH=rb.getString("tippath");
        try {
        	JSONObject result = new JSONObject();
            JSONObject obj = new JSONObject();
            JSONObject objDesc = new JSONObject();
            JSONArray array = new JSONArray();
//            obj.put("numerator", 5);
//            obj.put("denominator", 5);
//            obj.put("partition", 5);
//            obj.put("type", "MoonSet");
//            JSONObject pos = new JSONObject();
//            pos.put("x", -4);
//            pos.put("y", 0);
//            obj.put("position", pos);
//            obj.put("color", "yellow");
//            array.add(obj);
            //JLF: The initial model is empty
            result.put("initial_model", new JSONArray());
            obj.put("item", "lines");
            obj.put("active", values[0]);
            array.add(obj);
            obj.clear();
            obj.put("item", "rectangles");
            obj.put("active", values[1]);
            array.add(obj);
            obj.clear();
            obj.put("item", "sets");
            obj.put("active", values[2]);
            array.add(obj);
            obj.clear();
            obj.put("item", "liquids");
            obj.put("active", values[3]);
            array.add(obj);
            result.put("initial_configuration", array);
            result.put("extra_information", "");
            objDesc.put("id", name);
            objDesc.put("title", name);
            objDesc.put("desctiption", description);
            objDesc.put("showAtStartup", "false");
            result.put("task_description", objDesc);
            FileWriter file = new FileWriter(_TIPPATH + name+ ".tip");
            try {
                file.write(result.toJSONString());
                logger.info("Successfully Copied JSON Object to File..."+ name+ ".tip");
                logger.debug("\nJSON Object: " + result);
     
            } catch (IOException e) {
                e.printStackTrace();
     
            } finally {
                file.flush();
                file.close();
            }
        } catch (Exception ex) {
        	logger.error(ex.toString());
        }
	}

}
