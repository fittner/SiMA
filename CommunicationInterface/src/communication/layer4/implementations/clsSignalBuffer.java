package communication.layer4.implementations;

import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

public class clsSignalBuffer extends clsBufferBase{

	private clsDataPoint moData;
	@Override
	public void put(clsDataPoint poData) {
		moData = poData;
		
	}

	@Override
	public clsDataPoint getNext() {
		return moData;
	}

	@Override
	public boolean hasNext() {
		if(moData==null) return false;
		else return true;
	}

	@Override
	public ArrayList<clsDataPoint> getData() {
		ArrayList<clsDataPoint> oRetVal = new ArrayList<clsDataPoint>();
		oRetVal.add(moData);
		return oRetVal;
	}

}
