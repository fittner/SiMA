package PropertyTest;

public abstract class clsElement {
	private String moName;
	
	public clsElement(String Name) {
		moName = Name;
	}
	
	public String toString() {
		return "'"+moName+"' ";
	}

}
