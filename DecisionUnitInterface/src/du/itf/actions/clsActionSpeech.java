/**
 * clsActionSpeech.java: BW - du.itf.actions
 * 
 * @author MW
 * 25.02.2013, 14:02:37
 */
package du.itf.actions;
import du.enums.eInternalActionIntensity;


/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 25.02.2013, 14:02:37
 * @param <clsWordPresentationMesh>
 * 
 */
public class clsActionSpeech<clsWordPresentationMesh> extends clsActionCommand {

	private eInternalActionIntensity moAbstractSpeech;
	private clsWordPresentationMesh moWording;
	private String New_Wording;
	private String New_Wording_shown;
	
	public clsActionSpeech(clsWordPresentationMesh poWording) {
	
	     moWording = poWording;
	     
	    New_Wording = moWording.toString();
	    
	    if (New_Wording.equals("::CONCEPT::1:SPEAK_YES:")){
	    	New_Wording_shown  = "Speak_Yes";
	    }
	    
	    if (New_Wording.equals("::CONCEPT::1:SPEAK_SHARE:")){
	    	New_Wording_shown  = "Speak_Share";
	    }
	}
	
	@Override
	public String getLog() {
		// TODO MW
		return "<Speech>" + New_Wording_shown + "</Speech>"; 
		 
	}

	public eInternalActionIntensity getData() {
		return moAbstractSpeech;
	}
	
	public void setData(eInternalActionIntensity oAbstractSpeech) {
		moAbstractSpeech=oAbstractSpeech;
	}
	
	public void addData(eInternalActionIntensity oAbstractSpeech) {
		moAbstractSpeech=oAbstractSpeech;
	}
}
