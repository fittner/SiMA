/**
 * CHANGELOG
 *
 * 30.11.2012 LuHe - File created
 *
 */
package pa._v38.personality.parameter;

import java.util.ArrayList;
import java.util.Set;


import config.clsProperties;

/**
 * DOCUMENT (LuHe) - insert description 
 * 
 * @author LuHe
 * 30.11.2012, 08:33:36
 * 
 */
public class clsPersonalityParameterModule  implements Comparable<clsPersonalityParameterModule>{

	private final String moModuleNumber;
	private ArrayList<clsPersonalityParameter> moParameters;
	
	public clsPersonalityParameterModule(String poModuleNumber, clsProperties oProp){
		moModuleNumber = poModuleNumber;
		moParameters= new ArrayList<clsPersonalityParameter>();
		applyProperties("",oProp);
		
	}
	
	
	private void applyProperties(String poPrefix, clsProperties poProp){
		//String pre = clsProperties.addDot(poPrefix);
		
		ArrayList<String> parameters = new ArrayList<String>();
		
		//generate an arraylist of all ids
		Set<Object> keys =poProp.keySet();
		for(Object o : keys){
			String key = o.toString();
			String id = key.substring(0, key.indexOf('.'));
			if(!parameters.contains(id)) parameters.add(id);
		}
		
		//create the personality Parameters
		
		for(String oKey:parameters){
			//String key= clsProperties.addDot(oKey);
			clsProperties oProp = poProp.getSubset(oKey);

			clsPersonalityParameter oPersonalityParamater = new clsPersonalityParameter(oKey,oProp);
			moParameters.add(oPersonalityParamater);
		}
		
				
	}
	
	
	
	
	public clsPersonalityParameter getParameter(String poValue){
		for(clsPersonalityParameter iParameter: moParameters){
			if(iParameter.getParameter(poValue)!=null){
				return iParameter;
			}
		}
		return null;
	}

	
	public ArrayList<clsPersonalityParameter> getAllParamaters(){
		return moParameters;
	}
	/**
	 * @since 30.11.2012 08:49:57
	 * 
	 * @return the moModuleNumber
	 */
	public String getMoModuleNumber() {
		return moModuleNumber;
	}
	
	@Override
	public String toString(){
		return moModuleNumber;
	}


	/* (non-Javadoc)
	 *
	 * @since 17.12.2012 19:22:13
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(clsPersonalityParameterModule arg0) {
			int arg = Integer.parseInt(arg0.getMoModuleNumber().substring(1));
			int me = Integer.parseInt(this.getMoModuleNumber().substring(1));
			if((arg0.getMoModuleNumber().equals(this.getMoModuleNumber()))) return 0;
			else if(arg <me) return 1;
			else if(arg > me) return -1;
		
		return 0;
	}
	
}
