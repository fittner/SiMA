/**
 * ePsychicInstance.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 12:09:57
 */
package mind.functionalmodel;

import java.awt.Color;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 12:09:57
 * 
 */
public enum ePsychicInstance {
	BODY (Color.ORANGE),
	ID (Color.GREEN),
	EGO (Color.BLUE),
	SUPEREGO (Color.RED);
	
	private final Color moColor;
	
	Color getColor(){
		return moColor;
	}

	ePsychicInstance(Color poColor) {
		moColor = poColor;
	}
}
