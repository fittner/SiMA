package communication.layer4.implementations;

import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

public class clsEventBuffer extends clsBufferBase{

	private ArrayList<clsDataPoint> moData = new ArrayList<clsDataPoint>();
	@Override
	public void put(clsDataPoint poData) {
		moData.add(poData);
		
	}

	@Override
	public clsDataPoint getNext(){
		clsDataPoint oRetVal = moData.get(0);
		moData.remove(0);
		return oRetVal;
	}

	@Override
	public boolean hasNext() {
		if(moData !=null && moData.size()!=0) return true;
		else return false;
	}

	@Override
	public ArrayList<clsDataPoint> getData() {
		ArrayList<clsDataPoint> oRetVal = (ArrayList<clsDataPoint>) moData.clone();
		moData.clear();
		return oRetVal;
	}

}
