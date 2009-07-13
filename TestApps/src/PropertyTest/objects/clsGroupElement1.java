package PropertyTest.objects;

import java.util.Properties;

public class clsGroupElement1   {
	public static final String P_X = "X";
	public static final String P_Y = "Y";	

	private clsElementDual X;
	private clsElementDual Y;
	
	public clsGroupElement1() {
		X = new clsElementDual("1,2", 1, 2);
		Y = new clsElementDual("0,3", 0, 3);
	}
	
	public clsGroupElement1(String poPrefix, Properties poProp) {
		applyProperties(poPrefix, poProp);
	}	
	
	public clsElementDual getElementX() {
		return X;
	}
	
	public clsElementDual getElementY() {
		return Y;
	}
	
	@Override
	public String toString() {
		return X+" || "+Y;
	}

	public static Properties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		Properties oProp = new Properties();

		oProp.setProperty(pre+P_X+"."+clsElementDual.P_NAME, "1,2");
		oProp.setProperty(pre+P_X+"."+clsElementDual.P_A, "1");
		oProp.setProperty(pre+P_X+"."+clsElementDual.P_B, "2");
		
		oProp.setProperty(pre+P_Y+"."+clsElementDual.P_NAME, "0,3");
		oProp.setProperty(pre+P_Y+"."+clsElementDual.P_A, "0");
		oProp.setProperty(pre+P_Y+"."+clsElementDual.P_B, "3");

		return oProp;
	}

	private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		X = new clsElementDual(pre+P_X, poProp);
		Y = new clsElementDual(pre+P_Y, poProp);
	}
	
}
