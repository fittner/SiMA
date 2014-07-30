package communication.datatypes;

import java.util.ArrayList;


public class clsDataContainer {
	private ArrayList<clsDataPoint> moData;

	public clsDataContainer() {
		moData = new ArrayList<clsDataPoint>();
	}

	public ArrayList<clsDataPoint> getData() {
		return moData;
	}

	public void addDataPoint(clsDataPoint poDataPoint) {
		if(poDataPoint!= null){
			moData.add(poDataPoint);
		}
	}
	public void addDataPoints (ArrayList<clsDataPoint> oList){
		for(clsDataPoint oData: oList){
			moData.add(oData);
		}
	}
	
	public String toString(){
		String oRetVal="";
		for(clsDataPoint oDataPoint : moData){
			oRetVal += oDataPoint +"\n";
		}
		return oRetVal;
		
	}
	
	 
	
	
}
