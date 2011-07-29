/**
 * 
 * clsAwareContentStorage.java: DecisionUnits - pa.memory
 * @author zeilinger
 * 20.10.2009, 22:40:09
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.loader.clsAwareContentLoader;
import pa._v19.tools.clsTripple;
import config.clsProperties;

/**
 * 
 * @author zeilinger
 * 20.10.2009, 22:40:09
 * 
 */
@Deprecated
public class clsAwareContentStorage {
	public HashMap<String,ArrayList<clsTripple<clsPrimaryInformation,clsPrimaryInformation,clsPrimaryInformation>>> moAwareContent;
	
	public clsAwareContentStorage(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moAwareContent = clsAwareContentLoader.createAwareContent();
    }
	
	private void applyProperties(String poPrefix, clsProperties poProp){		
//			String pre = clsProperties.addDot(poPrefix);
	    }	
	    
	    public static clsProperties getDefaultProperties(String poPrefix) {
//	    	String pre = clsProperties.addDot(poPrefix);
	    	
	    	clsProperties oProp = new clsProperties();
				
			return oProp;
	    }

		/**
		 *
		 * @author zeilinger
		 * 20.10.2009, 23:42:56
		 *
		 * @param clsTriple
		 * @return
		 */
		public clsPrimaryInformation getMappedContent(
				clsTripple<String, String, String> poSearchParam) {
			
			String oEntityType = poSearchParam.a; 
			String oCurrentContext = poSearchParam.b; 
			String oRepressedContent = poSearchParam.c; 
			
			for(clsTripple <clsPrimaryInformation,clsPrimaryInformation,clsPrimaryInformation> oElement : moAwareContent.get(oEntityType)){
				String oSearchContext =  oElement.a.moTP.moContent.toString();
				String oSearchRepContent = oElement.b.moTP.moContent.toString(); 
				
				if( oSearchContext.equals(oCurrentContext)&&oSearchRepContent.equals(oRepressedContent)){
					return oElement.c; 
				}
			}
		
			return null;
		}
}
