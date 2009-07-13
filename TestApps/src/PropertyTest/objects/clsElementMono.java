package PropertyTest.objects;

import java.util.Properties;

public class clsElementMono extends clsElement {
	public static final String P_A = "A";
	
	private int mnA;
	
	public clsElementMono(String Name, int A) {
		super(Name);
		mnA = A;
	}
	
	public clsElementMono(String poPrefix, Properties poProp) {
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
	
	public static Properties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		Properties oProp = new Properties();
		
		oProp.setProperty(pre+P_NAME, "mono");
		oProp.setProperty(pre+P_A, "0");
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		mnA = Integer.parseInt(poProp.getProperty(pre+P_A));
	}	
}
