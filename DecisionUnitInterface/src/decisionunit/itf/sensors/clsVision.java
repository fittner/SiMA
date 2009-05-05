package decisionunit.itf.sensors;

import java.util.ArrayList;

public class clsVision extends clsSensorExtern {
	private ArrayList<clsVisionEntry> moEntries = new ArrayList<clsVisionEntry>();
	
	public void add(clsVisionEntry poEntry) {
		moEntries.add(poEntry);
	}
	
	public ArrayList<clsVisionEntry> getList() {
		return moEntries;
	}
}
