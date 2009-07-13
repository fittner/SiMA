package PropertyTest;

import java.util.ArrayList;

public class clsElementGroup2 {
	private ArrayList<clsDualElement> moElementList;
	
	public clsElementGroup2() {
		moElementList = new ArrayList<clsDualElement>();
	}
	public clsElementGroup2(int num) {
		moElementList = new ArrayList<clsDualElement>();
		for (int i=0; i<num;i++) {
			addElement(i, i+1);
		}
	}
	
	public void addElement(clsDualElement poE) {
		moElementList.add(poE);
	}
	
	public void addElement(int A, int B) {
		moElementList.add(new clsDualElement(A+","+B, A, B));
	}
	
	public clsDualElement getElement(int key) {
		return moElementList.get(key);
	}
	
	public String toString() {
		String res = "";
		for (clsDualElement oE:moElementList) {
			res += oE+" || ";
		}
		return res;
	}
}
