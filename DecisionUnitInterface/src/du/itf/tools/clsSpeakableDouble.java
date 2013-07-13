package du.itf.tools;

/**
 * DOCUMENT (MW) -
 * 
 * @author MW
 * 26.02.2013, 19:29:43
 * 
 */
public class clsSpeakableDouble implements itfSpeakable{
	public Double oData;
	
	public clsSpeakableDouble(Double oDouble) {
		oData = new Double (oDouble);
	}

	@Override
	public itfSpeakable speak() {
		clsSpeakableDouble oSpeakableDouble = new clsSpeakableDouble(oData);
		
		return oSpeakableDouble;	
	}
	
	@Override
	public String toString(){
		return oData.toString();
	}
}
