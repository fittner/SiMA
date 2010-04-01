package PropertyTest.objects;

import PropertyTest.Properties.clsBWProperties;



public class clsElementDual extends clsElement {
	public static final String P_A = "A";
	public static final String P_B = "B";
	
	private int mnA;
	private int mnB;
	
	public clsElementDual(String Name, int A, int B) {
		super(Name);
		setA(A);
		setB(B);
	}
	
	public clsElementDual(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}	
	
	
	public void setA(int mnA) {
		this.mnA = mnA;
	}
	public int getA() {
		return mnA;
	}
	public void setB(int mnB) {
		this.mnB = mnB;
	}
	public int getB() {
		return mnB;
	}

	@Override
	public String toString() {
		return super.toString()+"A: "+mnA+"/B: "+mnB;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NAME, "dual");
		oProp.setProperty(pre+P_A, "0");
		oProp.setProperty(pre+P_B, "0");
		
		return oProp;
	}

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		mnA = poProp.getPropertyInt(pre+P_A);
		mnB = poProp.getPropertyInt(pre+P_B);		
	}
}
