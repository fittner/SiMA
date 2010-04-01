package PropertyTest.objects;

import PropertyTest.Properties.clsBWProperties;

public abstract class clsElement {
	public static final String P_NAME = "name";
		
	private String moName;
	
	public clsElement(String Name) {
		moName = Name;
	}
	
	public clsElement(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
	@Override
	public String toString() {
		return "'"+moName+"' ";
	}

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		moName = poProp.getPropertyString(pre+P_NAME);
	}
	
}
