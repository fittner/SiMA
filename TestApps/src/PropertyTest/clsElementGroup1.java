package PropertyTest;

public class clsElementGroup1 {

	private clsDualElement X;
	private clsDualElement Y;
	
	public clsElementGroup1() {
		X = new clsDualElement("1,2", 1, 2);
		Y = new clsDualElement("0,3", 0, 3);
	}
	
	public clsDualElement getElementX() {
		return X;
	}
	
	public clsDualElement getElementY() {
		return Y;
	}
	
	public String toString() {
		return X+" || "+Y;
	}
	
}
