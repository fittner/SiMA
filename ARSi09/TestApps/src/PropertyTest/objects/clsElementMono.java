package PropertyTest.objects;

import PropertyTest.Properties.clsBWProperties;

public class clsElementMono extends clsElement {
	public static final String P_A = "A";
	
	private int mnA;
	
	public clsElementMono(String Name, int A) {
		super(Name);
		mnA = A;
	}
	
	public clsElementMono(String poPrefix, clsBWProperties poProp) {
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
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NAME, "mono");
		oProp.setProperty(pre+P_A, "0");
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		mnA = poProp.getPropertyInt(pre+P_A);
	}	
}
