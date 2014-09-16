
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganHeart extends clsBodyOrgan {
	
	public static final String P_DEFAULTHEARTRATE = "defaultheartrate"; // 80
	public static final String P_DEFAULTBLOODPRESSURESYSTOLIC = "defaultbloodpressuresystolic"; // 80
	public static final String P_DEFAULTBLOODPRESSUREDIASTOLIC = "defaultbloodpressurediastolic"; // 120
	public static final String P_DEFAULTCHEEKSREDNINGTRESHHOLD = "defaultcheeksredningtreshold"; // 0.39
	
	private int mnHeartRate;
	private double mrBloodPressureSystolic;
	private double mrBloodPressureDiastolic;
	
	private double mrCheeksRedningTreshold;
	private double mrBloodPressureIntensity;
	
	private double mrMinBloodPressureSystolic = 50;
	private double mrMaxBloodPressureSystolic = 120;
	private double mrMinBloodPressureDiastolic = 80;
	private double mrMaxBloodPressureDiastolic = 190;
	private int mnMinHeartRate = 0;
	private int mnMaxHeartRate = 220; // set the default max heart rate
	
	public clsBodyOrganHeart(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTHEARTRATE, "80");
		oProp.setProperty(pre+P_DEFAULTBLOODPRESSURESYSTOLIC, "80");
		oProp.setProperty(pre+P_DEFAULTBLOODPRESSUREDIASTOLIC, "120");
		oProp.setProperty(pre+P_DEFAULTCHEEKSREDNINGTRESHHOLD, "0.39");
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
	    
	    mnHeartRate = poProp.getPropertyInt(pre+P_DEFAULTHEARTRATE);
	    mrBloodPressureSystolic = poProp.getPropertyDouble(pre+P_DEFAULTBLOODPRESSURESYSTOLIC);
	    mrBloodPressureDiastolic = poProp.getPropertyDouble(pre+P_DEFAULTBLOODPRESSUREDIASTOLIC);
	    mrCheeksRedningTreshold = poProp.getPropertyDouble(pre+P_DEFAULTCHEEKSREDNINGTRESHHOLD);
		
	}
	
	public double getBloodPressureIntensity(){
		
		double diastolicIntensity = (mrBloodPressureDiastolic - mrMinBloodPressureDiastolic) / (mrMaxBloodPressureDiastolic - mrMinBloodPressureDiastolic);
		double systolicIntensity = (mrBloodPressureSystolic - mrMinBloodPressureSystolic) / (mrMaxBloodPressureSystolic - mrMinBloodPressureSystolic);
		
		if( (diastolicIntensity + systolicIntensity) / 2 >= this.mrCheeksRedningTreshold ){
			this.mrBloodPressureIntensity = ( (diastolicIntensity + systolicIntensity) / 2 - this.mrCheeksRedningTreshold / (1.0 - this.mrCheeksRedningTreshold ) );
			return this.mrBloodPressureIntensity;
		}
		else{
			return 0.0;
		}
	}
	
	public double getBloodPressureSystolic() {
		if(mrBloodPressureSystolic < mrMinBloodPressureSystolic){
			setBloodPressureSystolic( mrMinBloodPressureSystolic );
		}
		else if( mrBloodPressureSystolic >= mrMaxBloodPressureSystolic){
			setBloodPressureSystolic( mrMaxBloodPressureSystolic );
		}
		
		return mrBloodPressureSystolic;
	}
	
	private void setBloodPressureSystolic(double pnBloodPressureSystolic) {
		this.mrBloodPressureSystolic = pnBloodPressureSystolic;
	}
	
	public void affectBloodPressureSystolic(double prIntensity) {
		this.mrBloodPressureSystolic += prIntensity * ( mrMaxBloodPressureSystolic - mrMinBloodPressureSystolic );
	}
	
	public double getBloodPressureDiastolic() {
		if(mrBloodPressureDiastolic < mrMinBloodPressureDiastolic){
			setBloodPressureDiastolic( mrMinBloodPressureDiastolic );
		}
		else if( mrBloodPressureDiastolic >= mrMaxBloodPressureDiastolic){
			setBloodPressureDiastolic( mrMaxBloodPressureDiastolic );
		}
		
		return mrBloodPressureDiastolic;
	}
	
	private void setBloodPressureDiastolic(double pnBloodPressureDiastolic) {
		this.mrBloodPressureDiastolic = pnBloodPressureDiastolic;
	}
	
	public void affectBloodPressureDiastolic(double prIntensity) {
		this.mrBloodPressureDiastolic += prIntensity * ( mrMaxBloodPressureDiastolic - mrMinBloodPressureDiastolic );
	}
	
	public int getHeartRate() {
		if(mnHeartRate < mnMinHeartRate){
			setHeartRate( mnMinHeartRate );
		}
		else if( mnHeartRate >= mnMaxHeartRate){
			setHeartRate( mnMaxHeartRate );
		}
		
		return mnHeartRate;
	}
	
	private void setHeartRate(int pnHeartRate) {
		this.mnHeartRate = pnHeartRate;
	}
	
	public void affectHeartRate(double prIntensity) {
		this.mnHeartRate += prIntensity;
	}
	
	public double getCheeksRedningTreshold() {
		return mrCheeksRedningTreshold;
	}
	
	public void setCheeksRedningTreshold(double pnCheeksRedningTreshold) {
		this.mrCheeksRedningTreshold = pnCheeksRedningTreshold;
	}
}
