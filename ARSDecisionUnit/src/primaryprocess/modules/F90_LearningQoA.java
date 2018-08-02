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
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.eInterfaces;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
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
	public static ArrayList<String> ArrayChanges = new ArrayList<String>();
	ArrayList<clsDriveMesh> moLearningLTM_DM = new ArrayList<clsDriveMesh>();
	ArrayList<clsDriveMesh> moLearningLTMred_DM = new ArrayList<clsDriveMesh>();

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
		    applyProperties(poPrefix, poProp); 
	}
	   
	private void applyProperties(String poPrefix, clsProperties poProp)
	{
	        //String pre = clsProperties.addDot(poPrefix);
	    
	        //nothing to do
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
          ArrayList<String> ArrayChangesloc = ArrayChanges;
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
                          if(oLearningDM.getLearning())
                          {
                              oLearningStorageDM.setLearning(); 
                          }
                          oLearningStorageDM.setActPleasure(oLearningDM.getActPleasure());
                          oLearningStorageDM.setQuotaOfAffect(oLearningDM.getQuotaOfAffect());
                      }
                  }

                  if(!found_DM)
                  {
                      oLearningStorageDM = oLearningDM;
                      moLearningStorage_DM.add(oLearningDM);
                  }

                  clsDriveMesh oLongTermMemoryDM = null;
                  
                  ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
                  new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
                  
                  ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
                  poSearchPattern.add(oLearningStorageDM);
                  
                  // search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
                  oSearchResult = this.getLongTermMemory().searchEntity(eDataType.DM, poSearchPattern);
                  if(oLearningStorageDM.getActPleasure() > 0)
                  {
                      for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
                          // for results of similar memory-DMs (should be various similar DMs)
                          for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList)
                          {
                              oLongTermMemoryDM = (clsDriveMesh)(oSearchPair.b.getMoDataStructure());
                              if (   oLearningStorageDM.getActualDriveSourceAsENUM() == oLongTermMemoryDM.getActualDriveSourceAsENUM()
                                      && oLearningStorageDM.getPartialDrive() == oLongTermMemoryDM.getPartialDrive()
                                         // drive component have to be considered to
                                      && oLearningStorageDM.getDriveComponent() == oLongTermMemoryDM.getDriveComponent()
                                      && oLearningStorageDM.getActualDriveAim() != null
                                      && !oLearningStorageDM.getActualDriveAim().isNullObject()
                                      && oLongTermMemoryDM.getActualDriveAim() != null
                                      && !oLongTermMemoryDM.getActualDriveAim().isNullObject()
                                      && oLearningStorageDM.getActualDriveAim().isEquivalentDataStructure(oLongTermMemoryDM.getActualDriveAim())
                                      && oLearningStorageDM.getActualDriveObject().isEquivalentDataStructure(oLongTermMemoryDM.getActualDriveObject())
                                     )
                              {
                                  double mrSatisfactionWeight_low = 0;
                                  double mrSatisfactionWeight_mid = 0;
                                  double mrSatisfactionWeight_high = 0;
                                  
                                  for (clsAssociation oAssToImage : oLongTermMemoryDM.getInternalAssociatedContent()) {
                                      if(oAssToImage.getAssociationElementB().getContentType().toString().equals("SATISFACTION"))
                                      {
                                          if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("HIGH"))
                                          {
                                              mrSatisfactionWeight_high = oAssToImage.getMrLearning();
                                          }
                                          if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("MID"))
                                          {
                                              mrSatisfactionWeight_mid  = oAssToImage.getMrLearning();
                                          }
                                          if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("LOW"))
                                          {
                                              mrSatisfactionWeight_low = oAssToImage.getMrLearning();
                                          }
                                      }
                                  }
                                  
                                  if(  (mrSatisfactionWeight_low == 0)
                                    && (mrSatisfactionWeight_mid == 0)
                                    && (mrSatisfactionWeight_high == 0)
                                    )
                                  {
                                  
                                      if(oLearningStorageDM.getExpPleasureMax()<0.1)
                                      {
                                          mrSatisfactionWeight_low  = (oLearningStorageDM.getExpPleasureMax() - 0.0)/0.1;
                                          mrSatisfactionWeight_mid  = 0;
                                          mrSatisfactionWeight_high = 0;
                                      }
                                      else if(oLearningStorageDM.getExpPleasureMax()<0.2)
                                      {
                                          mrSatisfactionWeight_low  = (0.2 - oLearningStorageDM.getExpPleasureMax())/0.1;
                                          mrSatisfactionWeight_mid  = (oLearningStorageDM.getExpPleasureMax() - 0.1)/0.1;
                                          mrSatisfactionWeight_high = 0;
                                      }
                                      else if(oLearningStorageDM.getExpPleasureMax()<0.3)
                                      {
                                          mrSatisfactionWeight_low  = 0;
                                          mrSatisfactionWeight_mid  = (0.3 - oLearningStorageDM.getExpPleasureMax())/0.1;
                                          mrSatisfactionWeight_high = (oLearningStorageDM.getExpPleasureMax() - 0.2)/0.1;   
                                      }
                                      else
                                      {
                                          mrSatisfactionWeight_low  = 0;
                                          mrSatisfactionWeight_mid  = 0;
                                          mrSatisfactionWeight_high = 1;  
                                      }
                                      //Set Learning Value
                                      for (clsAssociation oAssToImage : oLongTermMemoryDM.getInternalAssociatedContent()) {
                                          if(oAssToImage.getAssociationElementB().getContentType().toString().equals("SATISFACTION"))
                                          {
                                              if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("HIGH"))
                                              {
                                                  oAssToImage.setMrLearning(mrSatisfactionWeight_high);
                                              }
                                              if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("MID"))
                                              {
                                                  oAssToImage.setMrLearning(mrSatisfactionWeight_mid);
                                              }
                                              if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("LOW"))
                                              {
                                                  oAssToImage.setMrLearning(mrSatisfactionWeight_low);
                                              }
                                          }
                                      }
                                  }
                                  
                                  for (clsAssociation oAssToImage : oLongTermMemoryDM.getInternalAssociatedContent()) {
                                      if(oAssToImage.getAssociationElementB().getContentType().toString().equals("SATISFACTION"))
                                      {
                                          double learning;
                                          // 0,03 und 0,045 bleiben übrig --> bessere Transferformel
                                          //learning = oAssToImage.getMrLearning()*oLearningStorageDM.getQuotaOfAffect()*oLearningStorageDM.getActPleasure()*100;
                                          learning = oAssToImage.getMrLearning()*oLearningStorageDM.getQuotaOfAffect();
                                          if(learning<0)
                                          {
                                              learning = learning * 0.01;
                                          }
                                          double diff;
                                          diff = oAssToImage.getMrLearning()-learning;
                                          if(diff>0)
                                          {
                                              oAssToImage.setMrLearning(diff);
                                              oAssToImage.setMrWeight(oAssToImage.getMrWeight() + learning);
                                          }
                                          else
                                          {
                                              oAssToImage.setMrWeight(oAssToImage.getMrLearning()+oAssToImage.getMrWeight());
                                              oAssToImage.setMrLearning(0);
                                          }
                                          ArrayList<clsAssociation> poSearchPatternAss = new ArrayList<clsAssociation>();
                                          poSearchPatternAss.add(oAssToImage);
                                          this.getLongTermMemory().searchEntityWrite(eDataType.ASSOCIATIONDM, poSearchPatternAss, oAssToImage.getMrWeight(), oAssToImage.getMrLearning());
                                      }
                                  }
                              }
                          }
                      }
                  }
                  else
                  {
                      oSearchResult = null;
                  }
               }
            }
        }
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
