package PropertyTest.objects;

import java.util.Properties;

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
	
	public clsElementDual(String poPrefix, Properties poProp) {
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
	
	public static Properties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		Properties oProp = new Properties();
		
		oProp.setProperty(pre+P_NAME, "dual");
		oProp.setProperty(pre+P_A, "0");
		oProp.setProperty(pre+P_B, "0");
		
		return oProp;
	}

	private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		mnA = Integer.parseInt(poProp.getProperty(pre+P_A));
		mnB = Integer.parseInt(poProp.getProperty(pre+P_B));		
	}
}
