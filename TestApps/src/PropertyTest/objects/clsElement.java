package PropertyTest.objects;

import java.util.Properties;

public abstract class clsElement {
	public static final String P_NAME = "name";
		
	private String moName;
	
	public clsElement(String Name) {
		moName = Name;
	}
	
	public clsElement(String poPrefix, Properties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
	@Override
	public String toString() {
		return "'"+moName+"' ";
	}

	private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		moName = poProp.getProperty(pre+P_NAME);
	}
	
}
