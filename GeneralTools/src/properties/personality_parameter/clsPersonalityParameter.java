/**
 * CHANGELOG
 *
 * 30.11.2012 LuHe - File created
 *
 */
package properties.personality_parameter;

import java.util.Set;

import properties.clsProperties;


/**
 * DOCUMENT (LuHe) - insert description 
 * 
 * @author LuHe
 * 30.11.2012, 08:34:31
 * 
 */
public class clsPersonalityParameter {

	public final String P_NAME ="name";
	public final String P_DESCRIPTION ="description";
	public final String P_VALUE ="value";
	
	private String moDescription;
	private String moValue;
	private String moName;
	
	public clsPersonalityParameter(String poName, clsProperties poProp){
		moName=poName;
		applyProperties("",poProp);
	}

	
	private void applyProperties(String poPrefix, clsProperties poProp){
		//String pre = clsProperties.addDot(poPrefix);
		
		Set<Object> keys =poProp.keySet();
		for(Object o : keys){
			//delete id
/*			if(o.toString().equals(P_NAME)){
				mType= ePersonalityParameterType.valueOf(poProp.getProperty(o.toString()));
			}
			else */if (o.toString().equals(P_DESCRIPTION)){
				moDescription = poProp.getProperty(o.toString());
			}
			else if (o.toString().equals(P_VALUE)){
				moValue = poProp.getProperty(o.toString());
			}
			else{
				//wrong value in the config file 
			}
		}
		
		
				
	}
	
	public String getParameter(String poValue){
		if(moName.equals(poValue)) return moValue;
		else return null;
		
	}
	
	public String getParameter(){
		return moValue;
		
	}
	
	/** 
	 * Returns the value of the parameter parsed to double
	 *
	 * @since 25.01.2013 08:34:18
	 *
	 * @return
	 */
	public Double getParameterDouble(){
		return Double.parseDouble(moValue);
	}
	/** 
	 * Returns the value of the parameter parsed to integer
	 *
	 * @since 25.01.2013 08:34:18
	 *
	 * @return
	 */
	public int getParameterInt(){
		return Integer.parseInt(moValue);
	}
	
	
	/** 
	 * Returns the value of the parameter parsed to boolean
	 *
	 * @since 25.01.2013 08:34:18
	 *
	 * @return
	 */
	public boolean getParameterBoolean(){
		return Boolean.parseBoolean(moValue);
	}
	
	
	/**
	 * @since 30.11.2012 08:53:34
	 * 
	 * @return the moDescription
	 */
	public String getMoDescription() {
		return moDescription;
	}
	
	@Override
	public String toString(){
		return moName.toString() +" "+ moValue.toString();
	}
	
	public String getName(){
		return moName.toString();
	}
	
}
