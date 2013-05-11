package PropertyTest.objects;

import PropertyTest.Properties.clsProperties;

public class clsElementMono extends clsElement {
	public static final String P_A = "A";
	
	private int mnA;
	
	public clsElementMono(String Name, int A) {
		super(Name);
		mnA = A;
	}
	
	public clsElementMono(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}	
	
	public void setA(int A) {
		mnA = A;
	}
	
	public int getA() {
		return mnA;
	}
	
	@Override
	public String toString() {
		return super.toString()+"A: "+mnA;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_NAME, "mono");
		oProp.setProperty(pre+P_A, "0");
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		mnA = poProp.getPropertyInt(pre+P_A);
	}	
}
