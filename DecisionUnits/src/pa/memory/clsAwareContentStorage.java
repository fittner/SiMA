/**
 * 
 * clsAwareContentStorage.java: DecisionUnits - pa.memory
 * @author zeilinger
 * 20.10.2009, 22:40:09
 */
package pa.memory;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsPrimaryInformation;
import pa.loader.clsAwareContentLoader;
import pa.tools.clsTripple;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.10.2009, 22:40:09
 * 
 */
@Deprecated
public class clsAwareContentStorage {
	public HashMap<String,ArrayList<clsTripple<clsPrimaryInformation,clsPrimaryInformation,clsPrimaryInformation>>> moAwareContent;
	
	public clsAwareContentStorage(String poPrefix, clsBWProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		moAwareContent = clsAwareContentLoader.createAwareContent();
    }
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
//			String pre = clsBWProperties.addDot(poPrefix);
	    }	
	    
	    public static clsBWProperties getDefaultProperties(String poPrefix) {
//	    	String pre = clsBWProperties.addDot(poPrefix);
	    	
	    	clsBWProperties oProp = new clsBWProperties();
				
			return oProp;
	    }

		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 20.10.2009, 23:42:56
		 *
		 * @param clsTripple
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
