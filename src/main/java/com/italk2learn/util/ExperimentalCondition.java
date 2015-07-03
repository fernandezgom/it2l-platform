package com.italk2learn.util;

public class ExperimentalCondition {
	
	/*
     * Full system
     */
	public static final int FULL_SYSTEM=1;
    /*
     * No speech reco, no speech synthesis, no speech-based TIS, no
	*	interaction-based TIS, i.e. no TIS at all
     */
	public static final int NO_SPEECH=2;
    /*
     * No ELE, no SNA, no interaction-based TIS
     */
	public static final int NO_ELE=3;
	/*
     * Fixed ELE + FL
     */
	public static final int FL_ELE=4;

}
