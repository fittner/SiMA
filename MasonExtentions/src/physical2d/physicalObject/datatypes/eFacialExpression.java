package physical2d.physicalObject.datatypes;

/**
 * enum in den DuInterfaces das mappt auf eImages
 * 
 * @author muchitsch
 * 04.08.2009, 15:28:01
 * 
 */
public enum eFacialExpression {
	NONE("none.png"),
	ANGER("Overlay_FacialExpression_ANGER"),
	DISGUST("Overlay_FacialExpression_DISGUST"),
	FEAR("Overlay_FacialExpression_FEAR"),
	JOY("Overlay_FacialExpression_JOY"),
	NEUTRAL("Overlay_FacialExpression_NEUTRAL"),
	SADNESS("Overlay_FacialExpression_SADNESS"),
	SURPRISE("Overlay_FacialExpression_SURPRISE");
//	EAT("Overlay_Speech_EAT"),
//	YES("Overlay_Speech_YES");
	private String moeImagesString;
	
	private eFacialExpression(String poeImagesString) {
		moeImagesString = poeImagesString;
	}
	
	public String getEImagesString() {
		return moeImagesString;
	}

}
