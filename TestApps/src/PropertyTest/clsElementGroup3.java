package PropertyTest;

import java.util.ArrayList;

public class clsElementGroup3 {
	private ArrayList<clsElement> moElements;
	
	public clsElementGroup3() {
		moElements = new ArrayList<clsElement>();
		
		add(1);
		add(1,2);
		add(2);
		add(2,3);
		add(3);
		add(3,4);
	}
	
	public void add(int A) {
		moElements.add( new clsMonoElement( new Integer(A).toString(), A) );
	}
	
	public void add(int A, int B) {
		moElements.add( new clsDualElement( A+","+B, A, B));
	}

	public String toString() {
		String res = "";
		
		for (clsElement oE:moElements) {
			res+= oE+" || ";
		}
		
		return res;
	}
	
}
