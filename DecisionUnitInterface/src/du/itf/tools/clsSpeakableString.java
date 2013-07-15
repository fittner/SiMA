package du.itf.tools;

/**
 * DOCUMENT (MW) -
 * 
 * @author MW
 * 26.02.2013, 19:29:43
 * 
 */
public class clsSpeakableString implements itfSpeakable{
	public String oData;
	
	public clsSpeakableString(String oString) {
		oData = new String (oString);
	}

	@Override
	public itfSpeakable speak() {
		clsSpeakableString oSpeakableString = new clsSpeakableString(oData);
		
		return oSpeakableString;	
	}
	
	@Override
	public String toString(){
		return oData.toString();
	}

}
