package datageneration.manipulators;

import java.io.File;
import java.net.URI;

import datageneration.clsManipulation;

public class clsFramesManipulation extends clsManipulation {

	public clsFramesManipulation(URI oTarget) {
		super(oTarget);
	}

	@Override
	public void run() {
		log.error("Frames manipulation not implemented yet");
		
		File oTargetFile = openFile();
	}
}
