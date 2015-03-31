package com.italk2learn.util;

import java.util.HashMap;

public class ExercisesConverter {
	
	private HashMap<String, String> exercise= new HashMap<String, String>();
	
	public ExercisesConverter() {
		exercise.put("task2.2","FractionsLab20");
		exercise.put("task2.1","FractionsLab10");
		exercise.put("task2.4.setB.liqu","FractionsLab24");
		exercise.put("task2.4.setA.area","FractionsLab25");
	}

	public HashMap<String, String> getExercise() {
		return exercise;
	}

	public void setExercise(HashMap<String, String> exercise) {
		this.exercise = exercise;
	}

}
