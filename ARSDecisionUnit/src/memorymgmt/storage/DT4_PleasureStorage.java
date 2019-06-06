/**
 * CHANGELOG
 *
 * 12.10.2011 zottl					- first proper implementation according to current specification
 * 14.07.2011 deutsch 			- refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package memorymgmt.storage;

import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInterfaceDescription;

import java.util.ArrayList;
import java.util.Arrays;

import modules.interfaces.D4_1_receive;
import modules.interfaces.D4_1_send;
import modules.interfaces.D4_2_receive;
import modules.interfaces.D4_3_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsDriveMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.helpstructures.clsPair;
import base.tools.toText;

/**
 * Storage module for pleasure coming from the drive system
 * set the actual drive list to DT4, this automatically calculates the pleasure and this value can the be used everywhere
 * 
 * @author muchitsch
 * @since 12.10.2011
 */
public class DT4_PleasureStorage 
implements itfInspectorInternalState, itfInterfaceDescription, D4_1_receive, D4_1_send, D4_2_receive, D4_3_send{

    private double mnSystemPleasureValue = 0.0;
    private String werte="";
    private double mnSystemUnpleasureValue = 0.0;
    private double mnSystemPleasureValueOld = 0.0;
	private ArrayList<clsDriveMesh> moAllDrivesLastStep;
	private DT5_LearningIntensityBuffer moLearningIntensityBuffer;

	public DT4_PleasureStorage(DT5_LearningIntensityBuffer poLearningIntensityBuffer) {
	    moLearningIntensityBuffer = poLearningIntensityBuffer;
	}


	/**
	 * @since 18.07.2012 15:32:09
	 * 
	 * @param moAllDrivesLastStep the moAllDrivesLastStep to set
	 */
	public void setAllDrivesLastStep(ArrayList<clsDriveMesh> moAllDrivesActualStep) {
		
		if(moAllDrivesLastStep!=null && !moAllDrivesLastStep.isEmpty())
		{
		    double nNewPleasureValue = 0.0;
		    double nNewUnpleasureValue = 0.0;
            //go through the list of drives from last step, and calculate the pleasure out of the reduction
			
			for( clsDriveMesh oOldDMEntry : moAllDrivesLastStep){
				
				//find the drive from the list from last step
				for( clsDriveMesh oNewDMEntry : moAllDrivesActualStep){
					if(	oOldDMEntry.getActualDriveSourceAsENUM() == oNewDMEntry.getActualDriveSourceAsENUM() &&
						oOldDMEntry.getContentType() == oNewDMEntry.getContentType() &&
						oOldDMEntry.getPartialDrive() == oNewDMEntry.getPartialDrive()	&&
						// drive component have to be considered to
						oOldDMEntry.getDriveComponent() == oNewDMEntry.getDriveComponent() ) {
							//old drive is the same as the new one, found a match... calculate pleasure
						
							double tmpCalc = oOldDMEntry.getQuotaOfAffect() - oNewDMEntry.getQuotaOfAffect();
							double newtmpCalc = ((tmpCalc*tmpCalc) + (oNewDMEntry.getQuotaOfAffect()*oNewDMEntry.getQuotaOfAffect()))/2;
							//Pleasure cannot be negative
							if(tmpCalc > 0)
							{
                                //nNewPleasureValue += tmpCalc * oNewDMEntry.getQuotaOfAffect()*40;
                                nNewPleasureValue += tmpCalc;
                                werte += oNewDMEntry.getChartShortString() + tmpCalc+"\n";
							}
							nNewUnpleasureValue += ((tmpCalc*tmpCalc) + (oNewDMEntry.getQuotaOfAffect()*oNewDMEntry.getQuotaOfAffect()));
						}
				}
				
			}
			
			//dynamiic protion of pleasure
			/*if((nNewPleasureValue - this.mnSystemPleasureValue) > 0)
				nNewPleasureValue = nNewPleasureValue + (nNewPleasureValue - this.mnSystemPleasureValue);
			
			
			*/
			this.mnSystemPleasureValue =nNewPleasureValue;
			werte += "mnSystemPleasureValue" + nNewPleasureValue+"\n";
			this.mnSystemUnpleasureValue = nNewUnpleasureValue;
			if(this.mnSystemPleasureValue>1.0)this.mnSystemPleasureValue=1.0;
			if(this.mnSystemUnpleasureValue>1.0)this.mnSystemUnpleasureValue=1.0;
		}
		
		
		//overwrite old ones with new ones for next step calculation
		this.moAllDrivesLastStep = moAllDrivesActualStep;
	}
	

	
	private ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> groupDrives (ArrayList<clsDriveMesh> moDrives){
	       ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> oDrivePairs = new ArrayList<clsPair<clsDriveMesh,clsDriveMesh>>();
	        
	        ArrayList<clsDriveMesh> used = new ArrayList<clsDriveMesh>();
	        for(clsDriveMesh oDrive: moDrives){
	            if(!used.contains(oDrive)){
	                clsDriveMesh oCorresponingDrive=null;
	                for(clsDriveMesh oSecDrive: moDrives){
	                    if(!oSecDrive.equals(oDrive)){
	                        if(oSecDrive.getActualBodyOrifice().toString().equals(oDrive.getActualBodyOrifice().toString()) && oSecDrive.getActualDriveSource().toString().equals(oDrive.getActualDriveSource().toString())){
	                            oCorresponingDrive = oSecDrive;
	                            break;
	                        }
	                    }
	                }
	                if(oCorresponingDrive != null){
	                    if(oDrive.getDriveComponent().equals(eDriveComponent.AGGRESSIVE)){
	                        oDrivePairs.add(new clsPair<clsDriveMesh,clsDriveMesh>(oDrive,oCorresponingDrive));
	                    }
	                    else{
	                        oDrivePairs.add(new clsPair<clsDriveMesh,clsDriveMesh>(oCorresponingDrive,oDrive));
	                    }
	                    
	                    used.add(oCorresponingDrive);
	                }
	                else{
	                    //there is no corresponing drive
	                    //shouldn't happen!
	                    
	                }
	                used.add(oDrive);
	                
	            }
	            
	        }
	        return oDrivePairs;
	}


	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return  "Storage module for pleasure coming from the drive system.";
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D4_1) );
	}

	

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.h1("System Pleasure");
		text += toText.valueToTEXT("mnSystemPleasureValue", mnSystemPleasureValue);
		
		return text;
	}



	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_receive#receive_D3_1(double)
	 */
	@Override
	public void receive_D4_1(ArrayList<clsDriveMesh> poActualDriveCandidates) {
		this.setAllDrivesLastStep(poActualDriveCandidates);
	}
    /* (non-Javadoc)
    *
    * @since Apr 15, 2013 1:04:19 PM
    * 
    * @see pa._v38.interfaces.modules.D4_2_receive#D4_2receive(double)
    */
   @Override
   public void D4_2receive(double poValue) {
       
       if(poValue > 0){
           mnSystemPleasureValue += poValue;
       
           if(mnSystemPleasureValue > 1) mnSystemPleasureValue=1.0;
       }
   }
   
   public void resetPleasure(){
       mnSystemPleasureValueOld = mnSystemPleasureValue;
       mnSystemPleasureValue=0;
   }
   
   public void calculateDynamicPortionOfPleasure(){
       //dynamiic protion of lust
       if((mnSystemPleasureValue - this.mnSystemPleasureValueOld) > 0)
           mnSystemPleasureValue = mnSystemPleasureValue + (mnSystemPleasureValue - this.mnSystemPleasureValueOld);
   }

	/* (non-Javadoc)
	 *
	 * @since 13.06.2012 14:01:53
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		// TODO (muchitsch) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:33:59
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
	 */
	@Override
	public double send_D4_1() {
		return mnSystemPleasureValue;
		
	}

	   /* (non-Javadoc)
    *
    * @since 07.05.2012 12:33:59
    * 
    * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
    */
	@Override
   public double send_D4_3() {
       return mnSystemUnpleasureValue;
       
   }





	

}