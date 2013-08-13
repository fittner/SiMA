package du.enums;

/**
 * enum in den DuInterfaces das mappt auf eImages
 * 
 * @author muchitsch
 * 04.08.2009, 15:28:01
 * 
 */
public enum eSpeechExpression {
	NONE("none.png"),
	EAT("Overlay_Speech_EAT"),
	INVITED("Overlay_Speech_INVITED"),
	SHARE("Overlay_Speech_SHARE");

	private String moeImagesString;
	
	private eSpeechExpression(String poeImagesString) {
		moeImagesString = poeImagesString;
	}
	
	public String getEImagesString() {
		return moeImagesString;
	}

}
