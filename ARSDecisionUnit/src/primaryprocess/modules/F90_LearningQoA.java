/**
 * F90_LearningQoA.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author fittner
 * 05.08.2011, 10:20:03
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.eInterfaces;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;

/**
 * DOCUMENT (fittner)  
 * 
 * @author fittner
 * 05.08.2011, 10:23:13
 *  
 */
public class F90_LearningQoA extends clsModuleBaseKB {

	public static final String P_MODULENUMBER = "90";
	private ArrayList<clsDriveMesh> moLearningStorage_DM = new ArrayList<clsDriveMesh>();
	

	/**
	 * DOCUMENT (fittner) 
	 * 
	 * @author fittner
	 * 03.07.2018, 15:52:25
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F90_LearningQoA(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poLongTermMemory, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
			super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";

		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic()
	{
	    
	    // Lernen der bereits vorhandenen Objekte --> Merdge mit LongTermMemory!!!
	    
//	    boolean test = false;
//	    if(test==false)
//	    {
//	        test = true;
//	    }
//	    this.getLongTermMemory();
	    
      ArrayList<clsDriveMesh> poLerningCandidates = clsShortTermMemoryMF.getLearningDMs();
      if(  poLerningCandidates != null
        && (!poLerningCandidates.isEmpty())
        )
      {
          clsShortTermMemoryMF test = new clsShortTermMemoryMF(null);
          if(test.getChangedMoment())
          {   for (clsDriveMesh oLearningDM : poLerningCandidates)
              {
                  boolean found_DM = false;
                  clsDriveMesh oLearningStorageDM=null;
                  for (clsDriveMesh oLearningStorageDM_Loop: moLearningStorage_DM)
                  {
                      if (   oLearningStorageDM_Loop.getActualDriveSourceAsENUM() == oLearningDM.getActualDriveSourceAsENUM()
                          && oLearningStorageDM_Loop.getPartialDrive() == oLearningDM.getPartialDrive()
                             // drive component have to be considered to
                          && oLearningStorageDM_Loop.getDriveComponent() == oLearningDM.getDriveComponent()
                          && oLearningStorageDM_Loop.getActualDriveAim() != null
                          && !oLearningStorageDM_Loop.getActualDriveAim().isNullObject()
                          && oLearningStorageDM_Loop.getActualDriveAim().isEquivalentDataStructure(oLearningDM.getActualDriveAim())
                          && oLearningStorageDM_Loop.getActualDriveObject().isEquivalentDataStructure(oLearningDM.getActualDriveObject())
                         )
                      {
                          found_DM = true;
                          oLearningStorageDM = oLearningStorageDM_Loop;
                      }
                  }

                  if(!found_DM)
                  {
                      oLearningStorageDM = oLearningDM;
                      moLearningStorage_DM.add(oLearningDM);
                  }
                      
                  /* Aggregate Weights */
                  /* Finde DM in Memory */
               
                  oLearningStorageDM.setSatisfactionWeightLearning_no(oLearningStorageDM.getSatisfactionWeightLearning_no()+oLearningStorageDM.getExpectedSatisfactionWeight_no()*oLearningStorageDM.getLearningIntensity());
                  oLearningStorageDM.setSatisfactionWeightLearning_low(oLearningStorageDM.getSatisfactionWeightLearning_low()+oLearningStorageDM.getExpectedSatisfactionWeight_low()*oLearningStorageDM.getLearningIntensity());
                  oLearningStorageDM.setSatisfactionWeightLearning_mid(oLearningStorageDM.getSatisfactionWeightLearning_mid()+oLearningStorageDM.getExpectedSatisfactionWeight_mid()*oLearningStorageDM.getLearningIntensity());
                  oLearningStorageDM.setSatisfactionWeightLearning_high(oLearningStorageDM.getSatisfactionWeightLearning_high()+oLearningStorageDM.getExpectedSatisfactionWeight_high()*oLearningStorageDM.getLearningIntensity());

                  if(oLearningDM.getLearning())
                  {
                      moLearningStorage_DM.add(null);
                  }
            }
        }
    }
      
//      if(moAllDrivesXSteps!=null && !moAllDrivesXSteps.isEmpty() )
//      {
//          double nNewPleasureValue = 0.0;
//          //go through the list of drives from last step, and calculate the pleasure out of the reduction
//          int i=0;
//          
//          for( clsDriveMesh oOldDMEntry : moAllDrivesXSteps)
//          {
//              //find the drive from the list from last step
//              for( clsDriveMesh oNewDMEntry : moAllDrivesActualStep)
//              {
//                  if(    oOldDMEntry.getActualDriveSourceAsENUM() == oNewDMEntry.getActualDriveSourceAsENUM()
//                      && oOldDMEntry.getContentType() == oNewDMEntry.getContentType()
//                      && oOldDMEntry.getPartialDrive() == oNewDMEntry.getPartialDrive()
//                         // drive component have to be considered to
//                      && oOldDMEntry.getDriveComponent() == oNewDMEntry.getDriveComponent()
//                    )
//                  {
//                      //old drive is the same as the new one, found a match... calculate pleasure
//                  
//                      double mrQuotaOfAffect = oOldDMEntry.getQuotaOfAffect();
//                      double tmpCalc = mrQuotaOfAffect - oNewDMEntry.getQuotaOfAffect();
//                      
//                      
//                      //Pleasure cannot be negative
//                      // If Pleasure is negativ --> No pleasure any more
//                      if(tmpCalc < 0)
//                      {
//                          tmpCalc = 0;
//                          moAllDrivesXSteps.get(i).setQuotaOfAffect(mrQuotaOfAffect);
//                          moPleasure = moAllDrivesXSteps.get(i).getPleasureSumMax();
//                          moPleasures.add(moAllDrivesXSteps.get(i).getPleasureSum());
//                          if(!moAllDrivesXSteps.get(i).getRisingQoA())
//                          {
//                              moAllDrivesXSteps.get(i).setLearning();
//                          }
//                          else
//                          {
//                              moAllDrivesXSteps.get(i).resetLearning();
//                          }
//                          moAllDrivesXSteps.get(i).setRisingQoA();
//                      }
//                      
//                      if(tmpCalc == 0)
//                      {
//                          moAllDrivesXSteps.get(i).setLearningCnt(moAllDrivesXSteps.get(i).getLearningCnt()+1);
//                      }
//                      else
//                      {
//                          moAllDrivesXSteps.get(i).resetLearning();
//                          moAllDrivesXSteps.get(i).resetRisingQoA();
//                          if(moAllDrivesXSteps.get(i).getLearningCnt() > 0)
//                          {
//                              moAllDrivesXSteps.get(i).setQuotaOfAffect(mrQuotaOfAffect);
//                          }
//                          moAllDrivesXSteps.get(i).setLearningCnt(0);
//                          moAllDrivesXSteps.get(i).setPleasureSum(tmpCalc);
//                      }
//                      
//                      if( moAllDrivesXSteps.get(i).getPleasureSum() > moAllDrivesXSteps.get(i).getPleasureSumMax())
//                      {
//                          moAllDrivesXSteps.get(i).setPleasureSumMax(moAllDrivesXSteps.get(i).getPleasureSum());
//                      }
//                      else if( moAllDrivesXSteps.get(i).getPleasureSum() < moAllDrivesXSteps.get(i).getPleasureSumMax())
//                      {
//                          tmpCalc = 0;
//                      }
//                      
//                      //if (oDMEntryPleasure.getLearningCnt() > 5)
//                      //{
//                      //    
//                      //}
//                  }
//              }
//              moAllDrivesActualStep.get(i).setPleasureSumMax(moAllDrivesXSteps.get(i).getPleasureSumMax());
//              i++;
//          }
//      }

	}


	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (fittner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (fittner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Learning";
		// TODO (fittner) - give a en description
		
	}

    /* (non-Javadoc)
     *
     * @since 03.07.2018 14:22:43
     * 
     * @see base.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        // TODO (noName) - Auto-generated method stub
        
    }

}
