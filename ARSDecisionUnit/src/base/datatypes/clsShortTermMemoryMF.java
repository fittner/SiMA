
/**
 * CHANGELOG
 *
 * Jun 29, 2018 fittner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;
import java.util.Formatter;

import base.datatypes.helpstructures.clsPair;
import memorymgmt.enums.eActivationType;
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
    private static int Steps=0;
    private static int Moment;
    private static int actMoment=0;
    private static int MomentCnt=0;
    private static int prevMoment=0;
    private static boolean changeMoment=false;
    private static int moSTMMaxSize=5;
    
    private ArrayList<clsDriveMesh> LearningPartDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsThingPresentationMesh> LearningObjects = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningImage = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsDriveMesh> LearningDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> MemoryDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> ChangedDMsv = new ArrayList<clsDriveMesh>();
    private ArrayList<clsEmotion> Emotions = new ArrayList<clsEmotion>();
    private boolean learning=false;
    
    public static ArrayList<clsPair<Integer, clsShortTermMemoryMF>> moShortTimeMemoryMF1;
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
	    moShortTimeMemoryMF1 = new ArrayList<clsPair<Integer, clsShortTermMemoryMF>>();
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
        actMoment = step%10;
        if(actMoment==0)
        {
            changeMoment = true;
            if(step==0)
            {
                MomentCnt++;
                changeMoment();
            }
        }
        else
        {
            changeMoment = false;
        }

    }
    public static int getActualStep()
    {
        return Steps;
    }
    
    public static void changeMoment()
    {
        // remove last Object
        if(moShortTimeMemoryMF1.size()>=moSTMMaxSize)
        {
            int index=moSTMMaxSize-1;
            
            moShortTimeMemoryMF1.remove(index);
            
            for(; index > 0; index--)
            {
                moShortTimeMemoryMF1.add(index, moShortTimeMemoryMF1.get(index-1));
            }
        }
    }
    
    public static void addNewMoment(clsShortTermMemoryMF Moment)
    {
        moShortTimeMemoryMF1.add(new clsPair<Integer, clsShortTermMemoryMF>(0, Moment));
    }
    
    public void setLearningObjects(clsThingPresentationMesh TPM_Object)
    {
        LearningObjects.add(TPM_Object);
    }
    
    public ArrayList<clsThingPresentationMesh> getLearningObjects()
    {
        return LearningObjects;
    }
    public String getLearningObjectsString()
    {
        String out="";
        for(clsThingPresentationMesh LearningObject : LearningObjects)
        {
            out += LearningObject.getContent()+"::Act:"+LearningObject.getmnActiveTime()+"::Foc:"+LearningObject.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"\n";
        }
        return out;
    }
    
    public void setLearningPartDMs(clsDriveMesh PartDMs)
    {
        LearningPartDMs.add(PartDMs);
    }
    
    public void setLearningDMs(clsDriveMesh DMs)
    {
        LearningDMs.add(DMs);
    }
    
    public ArrayList<clsDriveMesh> getLearningPartDMs()
    {
        return LearningPartDMs;
    }
    
    public ArrayList<clsDriveMesh> getLearningDMs()
    {
        return LearningDMs;
    }
    
    public String getLearningDMsString()
    {
        String out="";
        for(clsDriveMesh LearningObject : LearningPartDMs)
        {
//          out += LearningObject.getContent()+"::Act:"+LearningObject.getmnActiveTime()+"::Foc:"+LearningObject.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"\n";
            out += LearningObject.getChartString();
            Formatter oDoubleFormatter = new Formatter();
            out += "::QoA:"+oDoubleFormatter.format("%.2f",LearningObject.getQuotaOfAffect());
            out += "::ActTime:"+LearningObject.getActiveTime()+"\n";
        }
        return out;
    }
    
    public void setLearning()
    {
        learning = true;
    }
    
    public boolean getLearning()
    {
        return learning;
    }
    
    public void resetLearning()
    {
        learning = false;
    }
    
    public void setLearningImage(clsThingPresentationMesh TPM_Object)
    {
        LearningImage.add(TPM_Object);
    }
    
    public ArrayList<clsThingPresentationMesh> getLearningImage()
    {
        return LearningImage;
    }
    public String getLearningImagesString()
    {
        String out="";
        for(clsThingPresentationMesh LearningImage : LearningImage)
        {
            out += LearningImage.getContent()+"::Act:"+LearningImage.getmnActiveTime()+"::Foc:"+LearningImage.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"::Foc:"+LearningImage.getMrWeightPI()+"\n";
        }
        return out;
    }
    
  
    public boolean getChangedMoment()
    {
        return changeMoment;
    }

    /**
     * @since 25.04.2019 09:43:16
     * 
     * @return the emotions
     */
    public ArrayList<clsEmotion> getEmotions() {
        return Emotions;
    }

    /**
     * @since 25.04.2019 09:43:16
     * 
     * @param emotions the emotions to set
     */
    public void setEmotions(clsEmotion emotions) {
        Emotions.add(emotions);
    }
	
}



