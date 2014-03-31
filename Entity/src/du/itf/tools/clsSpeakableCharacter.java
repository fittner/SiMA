package du.itf.tools;

/**
 * DOCUMENT (MW) -
 * 
 * @author MW
 * 26.02.2013, 19:29:43
 * 
 */
public class clsSpeakableCharacter implements itfSpeakable{
	public Character oData;
	
	public clsSpeakableCharacter(Character oCharacter) {
		oData = new Character (oCharacter);
	}

	@Override
	public itfSpeakable speak() {
		clsSpeakableCharacter oSpeakableCharacter = new clsSpeakableCharacter(oData);
		
		return oSpeakableCharacter;	
	}
	
	@Override
	public String toString(){
		return oData.toString();
	}

}
