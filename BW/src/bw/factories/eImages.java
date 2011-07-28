/**
 * eImages.java: BW - bw.factories
 * 
 * @author muchitsch
 * 04.05.2011, 11:39:06
 */
package bw.factories;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 04.05.2011, 11:39:06
 * 
 */
public enum eImages {
	NONE("none.png"),
	ARSINRed("arsin_red.png"),
	ARSINGreen("arsin_green.png"),
	ARSINGrey("arsin_grey.png"),
	Cake("cake.png"),
	Plant01("plant01.png"),
	Rock01("rock1.png"),
	SmartExcrement("smartexcrement.png"),
	Overlay_Action_Eat("Action_Eat.png"),
	Overlay_Action_Deposit("Action_Deposit.png"),
	Overlay_Action_MoveForward("Action_MoveForward.png"),
	Overlay_Action_Sleep("Action_Sleep.png"),
	Overlay_Action_TurnLeft("Action_TurnLeft.png"),
	Overlay_Action_TurnRight("Action_TurnRight.png");
	
		
	
	private String moFilename;
	private eImages(String poFilename) {
		moFilename = poFilename;
	}
	
	public String getFilename() {
		return moFilename;
	}
}
