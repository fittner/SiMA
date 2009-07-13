package PropertyTest.objects;

import java.util.Properties;

public class clsGroupTwitter {
	public static final String P_GROUP1 = "group1";
	public static final String P_GROUP2 = "group2";
	public static final String P_GROUP3 = "group3";
	
	private clsGroupElement1 moElementGroup1;
	private clsGroupElement2 moElementGroup2;
	private clsGroupElement3 moElementGroup3;
	
	public clsGroupTwitter() {
		moElementGroup1 = new clsGroupElement1();
		moElementGroup2 = new clsGroupElement2(4);
		moElementGroup3 = new clsGroupElement3();
	}
	public clsGroupTwitter(String poPrefix, Properties poProp) {
		applyProperties(poPrefix, poProp);
	}		
	
	@Override
	public String toString() {
		String res = "";
		
		res += moElementGroup1+" -> "+moElementGroup2+" -> "+moElementGroup3;
		
		return res;
	}

    public static Properties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
				
		Properties oProp = new Properties();
		
		oProp.putAll( clsGroupElement1.getDefaultProperties(pre+P_GROUP1) );
		oProp.putAll( clsGroupElement2.getDefaultProperties(pre+P_GROUP2) );
		oProp.putAll( clsGroupElement3.getDefaultProperties(pre+P_GROUP3) );
		
		return oProp;
	}	

    private void applyProperties(String poPrefix, Properties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		moElementGroup1 = new clsGroupElement1(pre+P_GROUP1, poProp);
		moElementGroup2 = new clsGroupElement2(pre+P_GROUP2, poProp);
		moElementGroup3 = new clsGroupElement3(pre+P_GROUP3, poProp);		
	}
}
