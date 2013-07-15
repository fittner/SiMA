/**
 * clsActionSpeech.java: BW - du.itf.actions
 * 
 * @author MW
 * 25.02.2013, 14:02:37
 */
package du.itf.actions;
import du.itf.tools.clsAbstractSpeech;


/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 25.02.2013, 14:02:37
 * 
 */
public class clsActionSpeech extends clsActionCommand {

	private clsAbstractSpeech moAbstractSpeech;

	public clsActionSpeech(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech=oAbstractSpeech;
	}
	
	@Override
	public String getLog() {
		// TODO MW
		return "<Speech>" + "" + "</Speech>"; 
	}

	public clsAbstractSpeech getData() {
		return moAbstractSpeech;
	}
	
	public void setData(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech=oAbstractSpeech;
	}
	
	public void addData(clsAbstractSpeech oAbstractSpeech) {
		moAbstractSpeech=oAbstractSpeech;
	}
}
