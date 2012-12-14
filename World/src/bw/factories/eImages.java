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
	Overlay_Action_EatNothing("Action_Eat_Nothing.png"),
	Overlay_Action_Deposit("Action_Deposit.png"),
	Overlay_Action_MoveForward("Action_MoveForward.png"),
	Overlay_Action_MoveBackward("Action_MoveBackward.png"),
	Overlay_Action_Sleep("Action_Sleep.png"),
	Overlay_Action_TurnLeft("Action_TurnLeft.png"),
	Overlay_Action_TurnRight("Action_TurnRight.png"),
	Overlay_Action_InnerSpeech_Nourish("Thought_Action_Nourish.png"),
	Overlay_Action_InnerSpeech_Schnitzel("Thought_Action_Schnitzel.png"),
	Overlay_Action_OuterSpeech_Share("Speech_Actrion_Share.png"),
	Overlay_Action_OuterSpeech_Eat("Speech_Action_Eat.png"),
	Overlay_Action_OuterSpeech_Invited("Speech_Action_Invited"),	
	Overlay_FacialExpression_ANGER("Expression_Anger.png"),
	Overlay_FacialExpression_DISGUST("Expression_Disgust.png"),
	Overlay_FacialExpression_FEAR("Expression_Fear.png"),
	Overlay_FacialExpression_JOY("Expression_Joy.png"),
	Overlay_FacialExpression_NEUTRAL("Expression_Neutral.png"),
	Overlay_FacialExpression_SADNESS("Expression_Sadness.png"),
	Overlay_FacialExpression_SURPRISE("Expression_Surprise.png");
	
	private String moFilename;
	
	private eImages(String poFilename) {
		moFilename = poFilename;
	}
	
	public String getFilename() {
		return moFilename;
	}
}
