
/**
 * CHANGELOG
 *
 * Jun 29, 2018 fittner - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;

import memorymgmt.enums.eActivationType;


/**
 * DOCUMENT (fittner) - insert description 
 * 
 * @author fittner
 * Jun 29, 2018 - File created
 * 
 */
public class clsShortTermMemoryEntry {
	
    private double QoAactivation;
    private ArrayList<clsDriveMesh> DMs;
    
    private ArrayList<clsDriveMesh> LearningPartDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsThingPresentationMesh> LearningObjects = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningImage = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsDriveMesh> LearningDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> MemoryDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> ChangedDMsv = new ArrayList<clsDriveMesh>();
    private ArrayList<clsEmotion> Emotions = new ArrayList<clsEmotion>();
    private ArrayList<String> socialRules = new ArrayList<String>();
    private boolean learning=false;
    private int step=0;
    
    
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
	public clsShortTermMemoryEntry() {
	    QoAactivation = 0.0;
	}
	
    public double getActualQoAactivation()
    {
        return QoAactivation;
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
            out += LearningObject.getContent()+"::Act:"+LearningObject.getActiveTime()+"::Foc:"+LearningObject.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"\n";
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
            out += "::QoA:"+oDoubleFormatter.format("%.3f",LearningObject.getQuotaOfAffect());
            oDoubleFormatter = new Formatter();
            out += "::QoAchange:"+oDoubleFormatter.format("%.3f",LearningObject.getQoAchange());
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
            out += LearningImage.getContent()+"::Act:"+LearningImage.getActiveTime()+"::Foc:"+LearningImage.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"::PI_Weight:"+LearningImage.getMrWeightPI()+"\n";
        }
        return out;
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
    
    

    /**
     * @since 26.04.2019 10:32:00
     * 
     * @return the step
     */
    public int getStep() {
        return step;
    }

    /**
     * @since 26.04.2019 10:32:00
     * 
     * @param step the step to set
     */
    public void setStep(int step) {
        this.step = step;
    }
	
    @Override
    public String toString()
    {
        String text;
        text =  "+ Element ";
        text += "---- Step:"+ this.step+"\n";
        text += "|---DMParts:\n";
        text += this.getLearningDMsString();
        text += "|---Objects:\n";
        text += this.getLearningObjectsString();
        text += "|---Images:\n";
        text += this.getLearningImagesString();
        
        return text;
    }

    private String GetQuotaOfAffectAsMyString(double rQoA){
        DecimalFormat threeDec = new DecimalFormat("0.000");
        String shortString = (threeDec.format(rQoA));
        return shortString;
    }

    /**
     * @since 09.05.2019 08:39:52
     * 
     * @return the socialRules
     */
    public ArrayList<String> getSocialRules() {
        return socialRules;
    }

    /**
     * @since 09.05.2019 08:39:52
     * 
     * @param socialRules the socialRules to set
     */
    public void setSocialRules(String socialRules) {
        this.socialRules.add(socialRules);
    }
}



