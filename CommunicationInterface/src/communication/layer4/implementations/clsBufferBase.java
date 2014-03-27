package communication.layer4.implementations;

import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

public abstract class clsBufferBase {

	public abstract void put(clsDataPoint poData);
	public abstract clsDataPoint getNext();
	public abstract boolean hasNext();
	public abstract ArrayList<clsDataPoint> getData();
}
