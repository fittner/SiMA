/**
 * F90_LearningQoA.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author fittner
 * 05.08.2011, 10:20:03
 */
package primaryprocess.modules;

import java.text.DecimalFormat;
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
                      }
                  }

                  if(!found_DM)
                  {
                      oLearningStorageDM = oLearningDM;
                      moLearningStorage_DM.add(oLearningDM);
                  }
                      
                  /* Aggregate Weights */
                  /* Finde DM in Memory */
                  double learnIntensity = oLearningStorageDM.getLearningIntensity();
                  DecimalFormat f = new DecimalFormat("#0.00");
                  
                  double SatWeight_no = oLearningStorageDM.getSatisfactionWeightLearning_no();
                  double ExpSatWeight_no = oLearningStorageDM.getExpectedSatisfactionWeight_no();
                  double SatWeight_low = oLearningStorageDM.getSatisfactionWeightLearning_low();
                  double ExpSatWeight_low = oLearningStorageDM.getExpectedSatisfactionWeight_low();
                  double SatWeight_mid = oLearningStorageDM.getSatisfactionWeightLearning_mid();
                  double ExpSatWeight_mid = oLearningStorageDM.getExpectedSatisfactionWeight_mid();
                  double SatWeight_high = oLearningStorageDM.getSatisfactionWeightLearning_high();
                  double ExpSatWeight_high = oLearningStorageDM.getExpectedSatisfactionWeight_high();
                  
                  String ArrayCalc;
                  ArrayCalc =  "A:" + (oLearningStorageDM.getActualDriveAim()!=null?oLearningStorageDM.getActualDriveAim().getContent():"no action") + ";";
                  ArrayCalc += "O:" + (oLearningStorageDM.getActualDriveObject()!=null?oLearningStorageDM.getActualDriveObject().getContent():"no object") + ";";
                  ArrayCalc += "LI:" + f.format(learnIntensity) + ";";
                  ArrayCalc += "SWL_NO:" + f.format(SatWeight_no) + ";";
                  ArrayCalc += "ExSWL_NO:" + f.format(ExpSatWeight_no) + ";";
                  ArrayCalc += "SWL_LOW:" + f.format(SatWeight_low) + ";";
                  ArrayCalc += "ExSWL_LOW:" + f.format(ExpSatWeight_low) + ";";
                  ArrayCalc += "SWL_MID:" + f.format(SatWeight_mid) + ";";
                  ArrayCalc += "ExSWL_MID:" + f.format(ExpSatWeight_mid) + ";";
                  ArrayCalc += "SWL_HIGH:" + f.format(SatWeight_high) + ";";
                  ArrayCalc += "ExSWL_HIGH:" + f.format(ExpSatWeight_high) + ";";
                  
                  oLearningStorageDM.setArrayCalc(ArrayCalc);
                  
                  
                  oLearningStorageDM.setSatisfactionWeightLearning_no(  SatWeight_no   + ExpSatWeight_no   * learnIntensity);
                  oLearningStorageDM.setSatisfactionWeightLearning_low( SatWeight_low  + ExpSatWeight_low  * learnIntensity);
                  oLearningStorageDM.setSatisfactionWeightLearning_mid( SatWeight_mid  + ExpSatWeight_mid  * learnIntensity);
                  oLearningStorageDM.setSatisfactionWeightLearning_high(SatWeight_high + ExpSatWeight_high * learnIntensity);

//                  if(oLearningStorageDM.getLearning())
                  {
                      //moLearningStorage_DM.add(null);
                      /* Suche DM mit identer Objekt Aktions beziehung */
                      for (clsDriveMesh oLearningStorageDM_Loop: moLearningStorage_DM)
                      {

                          oLearningStorageDM = oLearningStorageDM_Loop;
                          clsDriveMesh oLongTermMemoryDM = null;
                          clsDriveMesh oNewLearnedDM = null;
                          
                          ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
                                  new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
                          
                          ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
                          poSearchPattern.add(oLearningStorageDM);
                          
                          // search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
                          oSearchResult = this.getLongTermMemory().searchEntity(eDataType.DM, poSearchPattern);
                          String changes = "";
                          
                          boolean found = false;
                          
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

                                      changes = "|DM:"+ ";";
                                      changes += ":Organ="+oLearningStorageDM.getActualDriveSourceAsENUM()+ ";";
                                      changes += ":DComponent="+oLearningStorageDM.getDriveComponent().toString()+ ";";
                                      changes += ":Action="+(oLearningStorageDM.getActualDriveAim()!=null?oLearningStorageDM.getActualDriveAim().getContent():"no action")+ ";";
                                      changes += ":Object="+(oLearningStorageDM.getActualDriveObject()!=null?oLearningStorageDM.getActualDriveObject().getContent():"no object")+ ";";

                                      oNewLearnedDM = oLongTermMemoryDM;

                                      double value_tmp;
                                      value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_no());
                                      if (oLongTermMemoryDM.getQuotaOfAffect_no() > 1)
                                      {
                                          value_tmp = oLongTermMemoryDM.getQuotaOfAffect_no() + Math.floor(value_tmp/oLongTermMemoryDM.getQuotaOfAffect_no());
                                      }    
                                      if(value_tmp > oLongTermMemoryDM.getQuotaOfAffect_no())
                                      {
                                          changes+="Weight_NO:+" + value_tmp + ";";
                                          oNewLearnedDM.setQuotaOfAffect_no(value_tmp);
                                          //2. Check if the root element can be found in the associated data structures
                                          for (clsAssociation oAssToImage : oNewLearnedDM.getInternalAssociatedContent()) {
                                              if(oAssToImage.getAssociationElementB().getContentType().toString().equals("Alive"))
                                              {
                                                  oAssToImage.setMrLearning(value_tmp);
                                              }
                                          }
                                      }
                                     
                                      value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_low());
                                      if (oLongTermMemoryDM.getQuotaOfAffect_low() > 1)
                                      {
                                          value_tmp = oLongTermMemoryDM.getQuotaOfAffect_low() + Math.floor(value_tmp/oLongTermMemoryDM.getQuotaOfAffect_low());
                                      }    
                                      if(value_tmp > oLongTermMemoryDM.getQuotaOfAffect_low())
                                      {
                                          changes+="Weight_LOW:+" + value_tmp + ";";
                                          oNewLearnedDM.setQuotaOfAffect_low(value_tmp);
                                      }
                                      
                                      value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_mid());
                                      if (oLongTermMemoryDM.getQuotaOfAffect_mid() > 1)
                                      {
                                          value_tmp = oLongTermMemoryDM.getQuotaOfAffect_mid() + Math.floor(value_tmp/oLongTermMemoryDM.getQuotaOfAffect_mid());
                                      }    
                                      if(value_tmp > oLongTermMemoryDM.getQuotaOfAffect_mid())
                                      {
                                          changes+="Weight_MID:+" + value_tmp + ";";
                                          oNewLearnedDM.setQuotaOfAffect_mid(value_tmp);
                                      }
                                     
                                      value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_high());
                                      if (oLongTermMemoryDM.getQuotaOfAffect_high() > 1)
                                      {
                                          value_tmp = oLongTermMemoryDM.getQuotaOfAffect_high() + Math.floor(value_tmp/oLongTermMemoryDM.getQuotaOfAffect_high());
                                      }    
                                      if(value_tmp > oLongTermMemoryDM.getQuotaOfAffect_high())
                                      {
                                          changes+="Weight_HIGH:+" + value_tmp + ";";
                                          oNewLearnedDM.setQuotaOfAffect_high(value_tmp);
                                      }
                                      for(int i=0; i< ArrayChanges.size(); i++)
                                      {
                                          if(ArrayChanges.get(i).compareTo(changes) == 0)
                                          {
                                          //    ArrayChanges.remove(i);
                                          }
                                      }
                                      ArrayChanges.add(changes);
                                      found = true;
                                  }
                              }
                              if (found == false)
                              {
                                  
                                  changes = "|DM:"+ ";";
                                  changes += ":Organ="+oLearningStorageDM.getActualDriveSourceAsENUM()+ ";";
                                  changes += ":DComponent="+oLearningStorageDM.getDriveComponent().toString()+ ";";
                                  changes += ":Action="+(oLearningStorageDM.getActualDriveAim()!=null?oLearningStorageDM.getActualDriveAim().getContent():"no action")+ ";";
                                  changes += ":Object="+(oLearningStorageDM.getActualDriveObject()!=null?oLearningStorageDM.getActualDriveObject().getContent():"no object")+ ";";

                                  oNewLearnedDM = oLearningStorageDM;

                                  double value_tmp;
                                  value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_no());
                                  changes+="Weight_NO:+" + value_tmp + ";";
                                  oNewLearnedDM.setQuotaOfAffect_no(value_tmp);
                                  
                                 
                                  value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_low());
                                  changes+="Weight_LOW:+" + value_tmp + ";";
                                  oNewLearnedDM.setQuotaOfAffect_low(value_tmp);
                                   
                                  value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_mid());
                                  changes+="Weight_MID:+" + value_tmp + ";";
                                  oNewLearnedDM.setQuotaOfAffect_mid(value_tmp);
                                 
                                  value_tmp = Math.floor(oLearningStorageDM.getSatisfactionWeightLearning_high());
                                  changes+="Weight_HIGH:+" + value_tmp + ";";
                                  oNewLearnedDM.setQuotaOfAffect_high(value_tmp);

                                  for(int i=0; i< ArrayChanges.size(); i++)
                                  {
                                      if(ArrayChanges.get(i).compareTo(changes) == 0)
                                      {
                                      //    ArrayChanges.remove(i);
                                      }
                                  }
                                  ArrayChanges.add(changes);
                                  
                                  found = true;
                              }
                              found = false;
                          }
                          if(  (oNewLearnedDM.getQuotaOfAffect_no()==0)
                            && (oNewLearnedDM.getQuotaOfAffect_low()==0)
                            && (oNewLearnedDM.getQuotaOfAffect_mid()==0)
                            && (oNewLearnedDM.getQuotaOfAffect_high()==0)
                            )
                          {
                              oNewLearnedDM = null;    
                          }
                          if(oNewLearnedDM != null)
                          {
                              found=false;
                              moLearningLTM_DM.add(oNewLearnedDM);

                              
                              for(int i=0; i < moLearningLTMred_DM.size() ; i++)
                              {
                                  if (   moLearningLTMred_DM.get(i).getActualDriveSourceAsENUM() == oNewLearnedDM.getActualDriveSourceAsENUM()
                                          && moLearningLTMred_DM.get(i).getPartialDrive() == oNewLearnedDM.getPartialDrive()
                                             // drive component have to be considered to
                                          && moLearningLTMred_DM.get(i).getDriveComponent() == oNewLearnedDM.getDriveComponent()
                                          && moLearningLTMred_DM.get(i).getActualDriveAim() != null
                                          && !moLearningLTMred_DM.get(i).getActualDriveAim().isNullObject()
                                          && moLearningLTMred_DM.get(i).getActualDriveAim().isEquivalentDataStructure(oNewLearnedDM.getActualDriveAim())
                                          && moLearningLTMred_DM.get(i).getActualDriveObject().isEquivalentDataStructure(oNewLearnedDM.getActualDriveObject())
                                         )
                                      {
                                          // Wo sind die Speicherpools WM --> LTM???
                                          moLearningLTMred_DM.get(i).setQuotaOfAffect_no(oNewLearnedDM.getQuotaOfAffect_no());
                                          break;
                                      }
                              }
                              if(!found)
                              {
                                  moLearningLTMred_DM.add(oNewLearnedDM);
                              }
                          }
                      } 
                      //this.getLongTermMemory().writeQoA(oLearningDM);;
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
