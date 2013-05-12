package PropertyTest.objects;

import PropertyTest.Properties.clsProperties;

public class clsObject {
	public static final String P_DUAL = "dual";
	public static final String P_MONO = "mono";
	public static final String P_TWITTER = "twitter";
	public static final String P_GROUP1 = "group1";
	public static final String P_GROUP2 = "group2";
	public static final String P_GROUP3 = "group3";
	
	private clsElementDual moDualElement;
	private clsElementMono moMonoElement;
	private clsGroupElement1 moElementGroup1;
	private clsGroupElement2 moElementGroup2;
	private clsGroupElement3 moElementGroup3;
	private clsGroupTwitter moTwitterGroup;
	
	public clsObject() {
		moMonoElement = new clsElementMono("1", 1);
		moDualElement = new clsElementDual("5,6", 5, 6);
		moElementGroup1 = new clsGroupElement1();
		moElementGroup2 = new clsGroupElement2(4);
		moElementGroup3 = new clsGroupElement3();
		moTwitterGroup = new clsGroupTwitter();
	}
	public clsObject(String poPrefix, clsProperties poProp) {
		applyProperties(poPrefix, poProp);
	}	
	
	@Override
	public String toString() {
		String res = "";
		
		res += "clsMonoElement -> "+moMonoElement+"\n";
		res += "clsElement -> "+moDualElement+"\n";
		res += "clsElementGroup1 -> "+moElementGroup1+"\n";
		res += "clsElementGroup2 -> "+moElementGroup2+"\n";
		res += "clsElementGroup3 -> "+moElementGroup3+"\n";
		res += "clsTwitterGroup -> "+moTwitterGroup+"\n";
		
		return res;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
				
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsElementMono.getDefaultProperties(pre+P_MONO) );
		oProp.putAll( clsElementDual.getDefaultProperties(pre+P_DUAL) );
		oProp.putAll( clsGroupElement1.getDefaultProperties(pre+P_GROUP1) );
		oProp.putAll( clsGroupElement2.getDefaultProperties(pre+P_GROUP2) );
		oProp.putAll( clsGroupElement3.getDefaultProperties(pre+P_GROUP3) );
		oProp.putAll( clsGroupTwitter.getDefaultProperties(pre+P_TWITTER) );
		
		return oProp;		
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}

		moMonoElement = new clsElementMono(pre+P_MONO, poProp);
		moDualElement = new clsElementDual(pre+P_DUAL, poProp);
		moElementGroup1 = new clsGroupElement1(pre+P_GROUP1, poProp);
		moElementGroup2 = new clsGroupElement2(pre+P_GROUP2, poProp);
		moElementGroup3 = new clsGroupElement3(pre+P_GROUP3, poProp);		
		moTwitterGroup = new clsGroupTwitter(pre+P_TWITTER, poProp);		
	}	
}
