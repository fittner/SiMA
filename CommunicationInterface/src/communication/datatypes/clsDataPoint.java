package communication.datatypes;

import java.util.ArrayList;

public class clsDataPoint {
	private String moType;
	private String moValue;
	private String moBufferType="";
	
	private ArrayList<clsDataPoint> moAssociations;	
	
	
	public clsDataPoint(){
		moAssociations = new ArrayList<clsDataPoint>();
	}
	public clsDataPoint(String poType, String poValue){
		moAssociations = new ArrayList<clsDataPoint>();
		moType = poType;
		moValue = poValue;
	}
	public String getType() {
		return moType;
	}
	public void setType(String poType) {
		this.moType = poType;
	}
	public String getValue() {
		return moValue;
	}
	public void setValue(String poValue) {
		this.moValue = poValue;
	}
	public clsDataPoint getAssociation(String poType){
		for(clsDataPoint oAss: moAssociations){
			if (oAss.getType().equals(poType)) return oAss;
		}
		return null;
	}
	public boolean hasAssociation (String poType){
		for(clsDataPoint oAss: moAssociations){
			if (oAss.getType().equals(poType)) return true;
		}
		return false;
	}
	
	
	public String getBufferType() {
		return moBufferType;
	}
	public void setBufferType(String moBufferType) {
		this.moBufferType = moBufferType;
	}
	public void addAssociation(clsDataPoint poDataPoint){
		moAssociations.add(poDataPoint);
	}
	
	public ArrayList<clsDataPoint> getAssociatedDataPoints(){
		return moAssociations;
	}
	public String toString(){
		String oRetVal=moType +" - "+ moValue;
		if(moAssociations.size()!=0) oRetVal += ": ";
		for(clsDataPoint oDataPoint : moAssociations){
			oRetVal += oDataPoint.toString()+" ";
		}
		return oRetVal;
	}
}
