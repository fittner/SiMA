package PropertyTest;

public class clsObject {
	private clsDualElement moDualElement;
	private clsMonoElement moMonoElement;
	private clsElementGroup1 moElementGroup1;
	private clsElementGroup2 moElementGroup2;
	private clsElementGroup3 moElementGroup3;
	
	public clsObject() {
		moMonoElement = new clsMonoElement("1", 1);
		moDualElement = new clsDualElement("5,6", 5, 6);
		moElementGroup1 = new clsElementGroup1();
		moElementGroup2 = new clsElementGroup2(4);
		moElementGroup3 = new clsElementGroup3();
	}
	
	public String toString() {
		String res = "";
		
		res += "clsMonoElement -> "+moMonoElement+"\n";
		res += "clsElement -> "+moDualElement+"\n";
		res += "clsElementGroup1 -> "+moElementGroup1+"\n";
		res += "clsElementGroup2 -> "+moElementGroup2+"\n";
		res += "clsElementGroup3 -> "+moElementGroup3+"\n";
		
		return res;
	}
}
