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
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
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
                  
                  ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
                  new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
                  
                  ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
                  poSearchPattern.add(oLearningStorageDM);
                  
                  // search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
                  oSearchResult = this.getLongTermMemory().searchEntity(eDataType.DM, poSearchPattern);
                  
                  boolean found = false;
                  clsDriveMesh oLongTermMemoryDM = null;
                  clsDriveMesh oLongTermMemoryDM_Found = null;
                  
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
                                  found = true;
                                  oLongTermMemoryDM_Found = oLongTermMemoryDM;                                  
                              }
                          }
                      }
                      // End Check for LTM Elements
                      if(found)
                      {
                          oLongTermMemoryDM = oLongTermMemoryDM_Found;
                          
                          double mrSatisfactionWeight_low = 0;
                          double mrSatisfactionWeight_mid = 0;
                          double mrSatisfactionWeight_high = 0;
                          boolean found_high=false;
                          boolean found_mid=false;
                          boolean found_low=false;
                          
                          
                          for (clsAssociation oAssToImage : oLongTermMemoryDM.getInternalAssociatedContent()) {
                              if(oAssToImage.getAssociationElementB().getContentType().toString().equals("SATISFACTION"))
                              {
                                  if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("HIGH"))
                                  {
                                      mrSatisfactionWeight_high = oAssToImage.getMrLearning();
                                      found_high=true;
                                  }
                                  if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("MID"))
                                  {
                                      mrSatisfactionWeight_mid  = oAssToImage.getMrLearning();
                                      found_mid=true;
                                  }
                                  if(((clsThingPresentationMesh)(oAssToImage.getAssociationElementB())).getContent().toString().equals("LOW"))
                                  {
                                      mrSatisfactionWeight_low = oAssToImage.getMrLearning();
                                      found_low=true;
                                  }
                              }
                          }
                          if(  !found_high
                            || !found_mid
                            || !found_low
                            )
                          {
                              // set weight
                              ArrayList<clsThingPresentationMesh> TPM_array = new ArrayList<clsThingPresentationMesh>();
                              clsAssociation oNewAssoziation = null;
                              ArrayList<clsAssociation> poAssociatedDataStructures = new ArrayList<clsAssociation>();
                              if(!found_high)
                              {
                                  TPM_array.add((clsThingPresentationMesh)clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.SATISFACTION, new ArrayList<clsThingPresentation>(),  "HIGH" )));
                              }
                              if(!found_mid)
                              { 
                                  TPM_array.add((clsThingPresentationMesh)clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.SATISFACTION, new ArrayList<clsThingPresentation>(),  "MID" )));
                              }
                              if(!found_low)
                              {
                                  TPM_array.add((clsThingPresentationMesh)clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.SATISFACTION, new ArrayList<clsThingPresentation>(),  "LOW" )));
                              }
                              for(clsThingPresentationMesh TPM:TPM_array)
                              {
                              try{
                                  int ID = this.getLongTermMemory().getID();
                                  oNewAssoziation = CreateAssoziation(oLongTermMemoryDM,TPM,0.0,ID);
                              }
                              catch (Exception ex) {
//                                ex.printStackTrace();
                              }
                              poAssociatedDataStructures.add(oNewAssoziation);
                              oLongTermMemoryDM.addInternalAssociations(poAssociatedDataStructures);
                              }
                              
                          }
                          
                          if(  (  (mrSatisfactionWeight_low == 0)
                               && (mrSatisfactionWeight_mid == 0)
                               && (mrSatisfactionWeight_high == 0)
                               )
                            || (oLearningStorageDM.getExpPleasureMaxRise() > 0)
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
                      else // Not found in LTM
                      {
                          oLongTermMemoryDM = null;
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
	
    /**
     * Creates a DM out of the entry, and adds necessary information, source, etc
     * @throws Exception 
     *
     * @since 16.07.2012 15:20:26
     *
     */
    private clsAssociation CreateAssoziation(clsDriveMesh poDM, clsThingPresentationMesh poTPM, double prWeight, int ID) throws Exception {
        
        clsAssociation oDriveAssociationCandidate  = null;

        //create the DM
        oDriveAssociationCandidate = (clsAssociation)clsDataStructureGenerator.generateASSOCIATIONDM(poDM, poTPM, prWeight,ID);
        oDriveAssociationCandidate.setMrLearning(0.0);

        return oDriveAssociationCandidate;
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
