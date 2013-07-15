/**
 * CHANGELOG
 *
 * 07.05.2013 Owner - File created
 *
 */
package du.itf.tools;

/**
 * DOCUMENT (Owner) - insert description 
 * 
 * @author Owner
 * 07.05.2013, 17:01:32
 * 
 */
public class clsSpeakableStringOrdered extends clsSpeakableString {
	public String oContextInfo;
	/**
	 * DOCUMENT (Owner) - insert description 
	 *
	 * @since 07.05.2013 17:01:54
	 *
	 * @param oString
	 * @param i 
	 */
	public clsSpeakableStringOrdered(String oString, int i) {
		super(oString);
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(i);
		oContextInfo = sb.toString();
	}
	
	private clsSpeakableStringOrdered(String oData) {
		super (oData);
	}

	public String getContextInfo(){
		return oContextInfo;
	}
	
	@Override
	public itfSpeakable speak() {
		clsSpeakableStringOrdered oSpeakableStringOrdered = new clsSpeakableStringOrdered(oData);
		oSpeakableStringOrdered.oContextInfo = oContextInfo;
				
		return oSpeakableStringOrdered;	
	}
	

}
