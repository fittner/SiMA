package PropertyTest.objects;

import java.util.ArrayList;
import PropertyTest.Properties.clsBWProperties;

public class clsGroupElement3 {
	public static final String P_NUM_ELEMENTS = "num";
	public static final String P_ELEMENT_TYPE = "type";
	public static final String P_ELEMENT_TYPE_MONO = "mono";
	public static final String P_ELEMENT_TYPE_DUAL = "dual";	
	
	private ArrayList<clsElement> moElements;
	
	public clsGroupElement3() {
		moElements = new ArrayList<clsElement>();
		
		add(1);
		add(1,2);
		add(2);
		add(2,3);
		add(3);
		add(3,4);
	}
	public clsGroupElement3(String poPrefix, clsBWProperties poProp) {
		moElements = new ArrayList<clsElement>();
		applyProperties(poPrefix, poProp);
	}	
	
	public void add(int A) {
		moElements.add( new clsElementMono( new Integer(A).toString(), A) );
	}
	
	public void add(int A, int B) {
		moElements.add( new clsElementDual( A+","+B, A, B));
	}

	@Override
	public String toString() {
		String res = "";
		
		for (clsElement oE:moElements) {
			res+= oE+" || ";
		}
		
		return res;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.setProperty(pre+P_NUM_ELEMENTS, new Integer(6).toString());
		
		oProp.setProperty(pre+0+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_MONO );
		oProp.setProperty(pre+0+"."+clsElementDual.P_NAME, "@u1o" );
		oProp.setProperty(pre+0+"."+clsElementDual.P_A, "1");

		oProp.setProperty(pre+1+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_DUAL  );
		oProp.setProperty(pre+1+"."+clsElementDual.P_NAME, "@u1,2o" );
		oProp.setProperty(pre+1+"."+clsElementDual.P_A, "1");
		oProp.setProperty(pre+1+"."+clsElementDual.P_B, "2");
		
		oProp.setProperty(pre+2+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_MONO );
		oProp.setProperty(pre+2+"."+clsElementDual.P_NAME, "@u2o" );
		oProp.setProperty(pre+2+"."+clsElementDual.P_A, "2");

		oProp.setProperty(pre+3+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_DUAL  );
		oProp.setProperty(pre+3+"."+clsElementDual.P_NAME, "@u2,3o" );
		oProp.setProperty(pre+3+"."+clsElementDual.P_A, "2");
		oProp.setProperty(pre+3+"."+clsElementDual.P_B, "3");
	
		oProp.setProperty(pre+4+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_MONO );
		oProp.setProperty(pre+4+"."+clsElementDual.P_NAME, "@u3o" );
		oProp.setProperty(pre+4+"."+clsElementDual.P_A, "3");

		oProp.setProperty(pre+5+"."+P_ELEMENT_TYPE, P_ELEMENT_TYPE_DUAL );
		oProp.setProperty(pre+5+"."+clsElementDual.P_NAME, "@u3,4o" );
		oProp.setProperty(pre+5+"."+clsElementDual.P_A, "3");
		oProp.setProperty(pre+5+"."+clsElementDual.P_B, "4");
	
		return oProp;
	}		
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		int num = poProp.getPropertyInt(pre+P_NUM_ELEMENTS);
		for (int i=0; i<num; i++) {
			String oType = poProp.getPropertyString(pre+i+"."+P_ELEMENT_TYPE);
			if (oType.equals(P_ELEMENT_TYPE_MONO)) {
				moElements.add( new clsElementMono(pre+i, poProp) );
			} else if (oType.equals(P_ELEMENT_TYPE_DUAL)) {
				moElements.add( new clsElementDual(pre+i, poProp) );				
			}
		}
	}		
}
