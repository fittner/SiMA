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
	BubbleRed("bubble_red.png"),
	BubbleGreen("bubble_green.png"),
	Cake("cake.png"),
	Plant01("plant01.png"),
	Rock01("rock1.png"),
	SmartExcrement("smartexcrement.png"),
	Overlay_Action_Eating("eating.png");
		
	
	private String moFilename;
	private eImages(String poFilename) {
		moFilename = poFilename;
	}
	
	public String getFilename() {
		return moFilename;
	}
}
