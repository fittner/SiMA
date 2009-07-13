package PropertyTest;

public class clsMonoElement extends clsElement {
	private int mnA;
	
	public clsMonoElement(String Name, int A) {
		super(Name);
		mnA = A;
	}
	
	public void setA(int A) {
		mnA = A;
	}
	
	public int getA() {
		return mnA;
	}
	
	public String toString() {
		return super.toString()+"A: "+mnA;
	}
}
