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
	
	private static final String LIQUIDS= "liqu";
	private static final String AREA= "area";
	private static final String NUMBERS= "numb";
	private static final String SETS= "sets";
	
	/**
	 * JLF: Creates a TIP file and saves into the system 
	 */
    public static void createTIPFile(String name, String description){
		logger.info("JLF --- createTIPFile --- Creates a TIP file and saves into the system ");
		boolean[] values = {true,true,true,true};
		ResourceBundle rb= ResourceBundle.getBundle("italk2learn-config");
		String _TIPPATH=rb.getString("tippath");
		if (name.contains(LIQUIDS)){
			final boolean[] newRepresentationsFL = {false,false,false,true};
			values= newRepresentationsFL;
		} else if (name.contains(AREA)) {
			final boolean[] newRepresentationsFL = {false,true,false,false};
			values= newRepresentationsFL;
		} else if (name.contains(NUMBERS)){
			final boolean[] newRepresentationsFL = {true,false,false,false};
			values= newRepresentationsFL;
		} else if (name.contains(SETS)) {
			final boolean[] newRepresentationsFL = {false,false,true,false};
			values= newRepresentationsFL;
		} else {
			final boolean[] newRepresentationsFL = {true,true,true,true};
			values= newRepresentationsFL;
		}
        try {
        	JSONObject result = new JSONObject();
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
            JSONObject obj1 = new JSONObject();
            obj1.put("item", "lines");
            obj1.put("active", values[0]);
            array.add(obj1);
            JSONObject obj2 = new JSONObject();
            obj2.put("item", "rectangles");
            obj2.put("active", values[1]);
            array.add(obj2);
            JSONObject obj3 = new JSONObject();
            obj3.put("item", "sets");
            obj3.put("active", values[2]);
            array.add(obj3);
            JSONObject obj4 = new JSONObject();
            obj4.put("item", "liquids");
            obj4.put("active", values[3]);
            array.add(obj4);
            result.put("initial_configuration", array);
            result.put("extra_information", "");
            // JLF: These lines are commented to hide tip scroll button
//            objDesc.put("id", name);
//            objDesc.put("title", name);
//            objDesc.put("desctiption", description);
//            objDesc.put("showAtStartup", "false");
//            result.put("task_description", objDesc);
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
