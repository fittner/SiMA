
/**
 * CHANGELOG
 *
 * Jun 29, 2018 fittner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

import base.datatypes.helpstructures.clsPair;
import memorymgmt.interfaces.itfModuleMemoryAccess;


/**
 * DOCUMENT (fittner) - insert description 
 * 
 * @author fittner
 * Jun 29, 2018 - File created
 * 
 */
public class clsShortTermMemoryMF {
	
    private double QoAactivation;
    private ArrayList<clsDriveMesh> DMs;
    public static ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>> snapshots = new ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>>();;
    private static int Steps;
    private static int Moment;
    private static int actMoment=0;    
    private static int pevMoment=0;
    private static boolean changeMoment=false;
    
    private static ArrayList<clsDriveMesh> LearningDMs;
    private static ArrayList<clsDriveMesh> MemoryDMs;
    private static ArrayList<clsDriveMesh> ChangedDMs;
    private static boolean learning=false;
    
	/**
	 * DOCUMENT (fittner)
	 * 
	 * Constructor of clsShortTermMemory:
	 * 
	 * @param TODO
	 *
	 * @since  Jun 29, 2018 - File created
	 * 
	 */
	public clsShortTermMemoryMF(itfModuleMemoryAccess poLongTermMemory	) {
	    QoAactivation = 0.0;
	}
	
    public double getActualQoAactivation()
    {
        return QoAactivation;
    }
    
    public void setActualSnapShot(ArrayList<clsDriveMesh> DM)
    {
        if(!(this.getChangedMoment()))
        {
            snapshots.remove(snapshots.size()-1);
        }
        snapshots.add(new clsPair<Integer, ArrayList<clsDriveMesh>>(actMoment, DM));
    }
    
    public ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>> getActualSnapShot()
    {
        return snapshots;
    }
    
    public static void setActualStep(int step)
    {
        Steps = step;
        actMoment = step/10;
        if(actMoment!=pevMoment)
        {
            changeMoment = true;    
        }
        else
        {
            changeMoment = false;
        }
        pevMoment=actMoment;
    }
    
    public void setLearningDMs(clsDriveMesh DMs)
    {
//        ArrayList<clsDriveMesh> poLerningCandidates = clsShortTermMemoryMF.getLearningDMs();
//        if(  poLerningCandidates != null
//          && (!poLerningCandidates.isEmpty())
//          && clsShortTermMemoryMF.getLearning())
//        {
//            for (clsDriveMesh oLearningDM : poLerningCandidates) {
//                
//                ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
//                        new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
//                
//                ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
//                poSearchPattern.add(oLearningDM);
//                
//                // search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
//                oSearchResult = this.getLongTermMemory().searchEntity(eDataType.TPM, poSearchPattern);
//                clsDriveMesh oMemoryDM = null;
//                
//                // get rSumSimilarDMsQoA to calculate cathexis (see below)
//                for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
//                    // for results of similar memory-DMs (should be various similar DMs)
//                    for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList) {
//                        oMemoryDM = ((clsDriveMesh)oSearchPair.b.getMoDataStructure());
//                        if (oMemoryDM.getContentType().equals(eContentType.MEMORIZEDDRIVEREPRESENTATION)){
//                            rSumSimilarDMsQoA += oMemoryDM.getQuotaOfAffect();
//                        }
//                    }
//                }
//            }
//        }
//
//        
//        if(moAllDrivesXSteps!=null && !moAllDrivesXSteps.isEmpty() )
//        {
//            double nNewPleasureValue = 0.0;
//            //go through the list of drives from last step, and calculate the pleasure out of the reduction
//            int i=0;
//            
//            for( clsDriveMesh oOldDMEntry : moAllDrivesXSteps)
//            {
//                //find the drive from the list from last step
//                for( clsDriveMesh oNewDMEntry : moAllDrivesActualStep)
//                {
//                    if(    oOldDMEntry.getActualDriveSourceAsENUM() == oNewDMEntry.getActualDriveSourceAsENUM()
//                        && oOldDMEntry.getContentType() == oNewDMEntry.getContentType()
//                        && oOldDMEntry.getPartialDrive() == oNewDMEntry.getPartialDrive()
//                           // drive component have to be considered to
//                        && oOldDMEntry.getDriveComponent() == oNewDMEntry.getDriveComponent()
//                      )
//                    {
//                        //old drive is the same as the new one, found a match... calculate pleasure
//                    
//                        double mrQuotaOfAffect = oOldDMEntry.getQuotaOfAffect();
//                        double tmpCalc = mrQuotaOfAffect - oNewDMEntry.getQuotaOfAffect();
//                        
//                        
//                        //Pleasure cannot be negative
//                        // If Pleasure is negativ --> No pleasure any more
//                        if(tmpCalc < 0)
//                        {
//                            tmpCalc = 0;
//                            moAllDrivesXSteps.get(i).setQuotaOfAffect(mrQuotaOfAffect);
//                            moPleasure = moAllDrivesXSteps.get(i).getPleasureSumMax();
//                            moPleasures.add(moAllDrivesXSteps.get(i).getPleasureSum());
//                            if(!moAllDrivesXSteps.get(i).getRisingQoA())
//                            {
//                                moAllDrivesXSteps.get(i).setLearning();
//                            }
//                            else
//                            {
//                                moAllDrivesXSteps.get(i).resetLearning();
//                            }
//                            moAllDrivesXSteps.get(i).setRisingQoA();
//                        }
//                        
//                        if(tmpCalc == 0)
//                        {
//                            moAllDrivesXSteps.get(i).setLearningCnt(moAllDrivesXSteps.get(i).getLearningCnt()+1);
//                        }
//                        else
//                        {
//                            moAllDrivesXSteps.get(i).resetLearning();
//                            moAllDrivesXSteps.get(i).resetRisingQoA();
//                            if(moAllDrivesXSteps.get(i).getLearningCnt() > 0)
//                            {
//                                moAllDrivesXSteps.get(i).setQuotaOfAffect(mrQuotaOfAffect);
//                            }
//                            moAllDrivesXSteps.get(i).setLearningCnt(0);
//                            moAllDrivesXSteps.get(i).setPleasureSum(tmpCalc);
//                        }
//                        
//                        if( moAllDrivesXSteps.get(i).getPleasureSum() > moAllDrivesXSteps.get(i).getPleasureSumMax())
//                        {
//                            moAllDrivesXSteps.get(i).setPleasureSumMax(moAllDrivesXSteps.get(i).getPleasureSum());
//                        }
//                        else if( moAllDrivesXSteps.get(i).getPleasureSum() < moAllDrivesXSteps.get(i).getPleasureSumMax())
//                        {
//                            tmpCalc = 0;
//                        }
//                        
//                        //if (oDMEntryPleasure.getLearningCnt() > 5)
//                        //{
//                        //    
//                        //}
//                    }
//                }
//                moAllDrivesActualStep.get(i).setPleasureSumMax(moAllDrivesXSteps.get(i).getPleasureSumMax());
//                i++;
//            }
//        }

    }
    
    public static void setLearning()
    {
        learning = true;
    }
    
    public static boolean getLearning()
    {
        return learning;
    }
    
    public static void resetLearning()
    {
        learning = false;
    }
    
    public static ArrayList<clsDriveMesh> getLearningDMs()
    {
        if(!snapshots.isEmpty())
        {
            LearningDMs = snapshots.get(snapshots.size()-1).b;
        }

        
        return LearningDMs;
    }
    
    public boolean getChangedMoment()
    {
        return changeMoment;
    }
	
}



