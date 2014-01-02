package PropertyTest.objects;

import PropertyTest.Properties.clsProperties;

public abstract class clsElement {
	public static final String P_NAME = "name";
		
	private String moName;
	
	public clsElement(String Name) {
		moName = Name;
	}
	
	public clsElement(String poPrefix, clsProperties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
	@Override
	public String toString() {
		return "'"+moName+"' ";
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		moName = poProp.getPropertyString(pre+P_NAME);
	}
	
}
