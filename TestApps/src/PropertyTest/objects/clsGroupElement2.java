package PropertyTest.objects;

import java.util.ArrayList;
import java.util.Properties;

public class clsGroupElement2 {
	public static final String P_NUM_ELEMENTS = "num";
	
	private ArrayList<clsElementDual> moElements;
	
	public clsGroupElement2() {
		moElements = new ArrayList<clsElementDual>();
	}
	public clsGroupElement2(int num) {
		moElements = new ArrayList<clsElementDual>();
		for (int i=0; i<num;i++) {
			addElement(i, i+1);
		}
	}
	public clsGroupElement2(String poPrefix, Properties poProp) {
		moElements = new ArrayList<clsElementDual>();
		
		applyProperties(poPrefix, poProp);
	}
	
	public void addElement(clsElementDual poE) {
		moElements.add(poE);
	}
	
	public void addElement(int A, int B) {
		moElements.add(new clsElementDual(A+","+B, A, B));
	}
	
	public clsElementDual getElement(int key) {
		return moElements.get(key);
	}
	
	@Override
	public String toString() {
		String res = "";
		for (clsElementDual oE:moElements) {
			res += oE+" || ";
		}
		return res;
	}
	
	public static Properties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		int num = 4;
		
		Properties oProp = new Properties();

		oProp.setProperty(pre+P_NUM_ELEMENTS, new Integer(num).toString());
		
		for (int i=0; i<num; i++) {
			oProp.setProperty(pre+i+"."+clsElementDual.P_NAME, i+","+(i+1) );
			oProp.setProperty(pre+i+"."+clsElementDual.P_A, ""+i);
			oProp.setProperty(pre+i+"."+clsElementDual.P_B, ""+(i+1) );			
		}
	
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		int num = Integer.parseInt(poProp.getProperty(pre+P_NUM_ELEMENTS));
		for (int i=0; i<num; i++) {
			String temp = pre+i;
			clsElementDual oED = new clsElementDual(temp, poProp);
			moElements.add( oED );
		}
	}	
}
