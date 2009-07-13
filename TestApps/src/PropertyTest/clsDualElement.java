package PropertyTest;

public class clsDualElement extends clsElement {
	private int mnA;
	private int mnB;
	
	public clsDualElement(String Name, int A, int B) {
		super(Name);
		setA(A);
		setB(B);
	}
	
	public void setA(int mnA) {
		this.mnA = mnA;
	}
	public int getA() {
		return mnA;
	}
	public void setB(int mnB) {
		this.mnB = mnB;
	}
	public int getB() {
		return mnB;
	}

	public String toString() {
		return super.toString()+"A: "+mnA+"/B: "+mnB;
	}
}
