/**
 * clsSpeechSystem.java: BW - bw.body.internalSystem
 * 
 * @author MW
 * 25.02.2013, 12:57:01
 */
package complexbody.internalSystems;


import properties.clsProperties;
import entities.actionProxies.itfAPSpeakable;
import body.itfStepUpdateInternalState;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 25.02.2013, 13:30:54
 * 
 */
public class clsSpeechSystem implements itfStepUpdateInternalState, itfAPSpeakable {
    
	
	public clsSpeechSystem(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
		
	}

	@Override
	public void stepUpdateInternalState() {
		// TODO MW 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 21.07.2013 12:21:41
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPSpeakable#trySpeak(double)
	 */
	@Override
	public double trySpeak(double pfForce) {
		// TODO (hinterleitner) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since 21.07.2013 12:21:41
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPSpeakable#Speak(double)
	 */
	@Override
	public void Speak(double pfForce) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}		
	
	
}
