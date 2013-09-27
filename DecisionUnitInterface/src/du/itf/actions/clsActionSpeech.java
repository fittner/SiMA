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
 * 
 */
public class clsActionSpeech extends clsActionCommand {

	@SuppressWarnings("unused")
	private eInternalActionIntensity moAbstractSpeech;

	public clsActionSpeech(eInternalActionIntensity heavy) {
		moAbstractSpeech=heavy;
	}
	
	@Override
	public String getLog() {
		// TODO MW
		return "<Speech>" + "" + "</Speech>"; 
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
