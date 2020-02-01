
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
import java.util.HashMap;

import base.datatypes.helpstructures.clsPair;
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
    private ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> LearningObjects = new ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>>();
    private ArrayList<clsThingPresentationMesh> LearningImages = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningAction = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningIntention = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningBodypart = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningLTMImage = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsEmotion> LearningEmotion = new ArrayList<clsEmotion>();
    private ArrayList<clsDriveMesh> LearningDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> MemoryDMs = new ArrayList<clsDriveMesh>();
    private ArrayList<clsDriveMesh> ChangedDMsv = new ArrayList<clsDriveMesh>();
    private ArrayList<clsEmotion> Emotions = new ArrayList<clsEmotion>();
    private ArrayList<String> socialRules = new ArrayList<String>();
    private boolean learning=false;
    private int step=0;
    private HashMap<String, clsThingPresentationMesh> LearningSTMStoreHM = new HashMap<String, clsThingPresentationMesh>();
    private HashMap<String, clsThingPresentationMesh> LearningSTMObjectStoreHM = new HashMap<String, clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningSTMStore = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningSTMStoreRemove = new ArrayList<clsThingPresentationMesh>();
    private ArrayList<clsThingPresentationMesh> LearningSTMObjectStore = new ArrayList<clsThingPresentationMesh>();
    
    
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
    
    public void setLearningObjects(clsPair<clsThingPresentationMesh, clsThingPresentationMesh> Pair)
    {
        LearningObjects.add(Pair);
    }
    
    public ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> getLearningObjects()
    {
        return LearningObjects;
    }
    public String getLearningObjectsString()
    {
        String out="";
        for(clsPair<clsThingPresentationMesh, clsThingPresentationMesh> LearningObject : LearningObjects)
        {
            out += LearningObject.b.getContent()+"::Act:"+LearningObject.b.getActiveTime()+"::Foc:"+LearningObject.b.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION)+"\n";
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
        LearningImages.add(TPM_Object);
        LearningLTMImage.clear();
        
        clsThingPresentationMesh LearningImageOld = null;
        String out="";
        clsThingPresentationMesh LearningImageTemp = null;
        for(clsThingPresentationMesh LearningImage : LearningImages)
        {
            if(LearningImageOld == null)
            {
                LearningImageOld = LearningImage;
            }
            else
            {
                if(LearningImage.compareTo(LearningImageOld) == 0)
                {
                    LearningLTMImage.add(LearningImageOld);               
                }
                LearningImageOld = LearningImage;
            }
            LearningImageTemp = LearningImage;
        }
        LearningLTMImage.add(LearningImageTemp);
    }
    
    public void setLearningLTMStorage(clsThingPresentationMesh TPM_Image)
    {
//        clsThingPresentationMesh LI1, LI2, LI3, LI4, LI5;
//        LI1 = LearningImage.get(LearningImage.size()-1);
//        LI2 = LearningImage.get(LearningImage.size()-2);
//        LI3 = LearningImage.get(LearningImage.size()-3);
//        LI4 = LearningImage.get(LearningImage.size()-4);
//        LI5 = LearningImage.get(LearningImage.size()-5);
        double LearningIntensity=0;
        double LearningIntensity0=0;
        double LearningIntensity1=0;
        double LearningIntensity2=0;
        double LearningIntensityOld=0;
        double LearningIntensityOld0=0;
        double LearningIntensityOld1=0;
        double LearningIntensityOld2=0;
        clsThingPresentationMesh LearningImageOld = null;
        clsThingPresentationMesh LearningImageTemp = null;
        if(LearningImages.size() > 0)
        {   clsThingPresentationMesh Image = TPM_Image;
            for(int i=LearningImages.size()-1; i > LearningImages.size()-6 && i > 0; i--)
            {
                Image = LearningImages.get(i);
                if(Image.compareTo(TPM_Image) == 1.0)
                {
                    LearningIntensity += (Image.getLearningWeight() * 0.2 * (i - (LearningImages.size()-6)));
                }
                else
                {
                    LearningIntensityOld += (Image.getLearningWeight() * 0.2 * (i - (LearningImages.size()-6)));
                    Image.setLearningWeightSum(LearningIntensityOld);
                }
                if(Image.compareTo(TPM_Image) == 1.0)
                {
                    LearningIntensity1 += (Image.getLearningWeight1() * 0.2 * (i - (LearningImages.size()-6)));
                }
                else
                {
                    LearningIntensityOld1 += (Image.getLearningWeight1() * 0.2 * (i - (LearningImages.size()-6)));
                    Image.setLearningWeightSum1(LearningIntensityOld1);
                }
                if(Image.compareTo(TPM_Image) == 1.0)
                {
                    LearningIntensity2 += (Image.getLearningWeight2() * 0.2 * (i - (LearningImages.size()-6)));
                }
                else
                {
                    LearningIntensityOld2 += (Image.getLearningWeight2() * 0.2 * (i - (LearningImages.size()-6)));
                    Image.setLearningWeightSum2(LearningIntensityOld2);
                }
            }
            Image.setLearningWeightSum(LearningIntensityOld);
            TPM_Image.setLearningWeightSum(LearningIntensity);
            Image.setLearningWeightSum1(LearningIntensityOld1);
            TPM_Image.setLearningWeightSum1(LearningIntensity1);
            Image.setLearningWeightSum2(LearningIntensityOld2);
            TPM_Image.setLearningWeightSum2(LearningIntensity2);
            for(int i=LearningImages.size()-6; i >= 0 ; i--)
            {
                Image = LearningImages.get(i);
                Image.setLearningWeightSum(0);
            }
            if(LearningIntensity > 1.0)
            {
//                LearningSTMStoreRemove.clear();
//                for(clsThingPresentationMesh LearningImage : LearningImages)
//                {
//                    if(LearningImageOld == null)
//                    {
//                        LearningImageOld = LearningImage;
//                    }
//                    else
//                    {
//                        if(LearningImage.compareTo(LearningImageOld) == 0)
//                        {
//                            LearningSTMStoreRemove.add(LearningImageOld);               
//                        }
//                        LearningImageOld = LearningImage;
//                    }
//                    LearningImageTemp = LearningImage;
//                }
                LearningSTMStore.add(TPM_Image);
                LearningSTMStoreHM.remove(TPM_Image.getContent());
                LearningSTMStoreHM.put(TPM_Image.getContent(),TPM_Image);
                if(LearningSTMStoreRemove.size()>0)
                {
                    if(TPM_Image.getContent() == LearningSTMStoreRemove.get(LearningSTMStoreRemove.size()-1).getContent())
                    {
                        LearningSTMStoreRemove.remove(LearningSTMStoreRemove.size()-1);
                    }
                    LearningSTMStoreRemove.add(TPM_Image);
                }
                else
                {
                    LearningSTMStoreRemove.add(TPM_Image);
                }
            }
        }
    }
    
    public void setLearningLTMObjectStorage(clsThingPresentationMesh TPM_Object)
    {
//        clsThingPresentationMesh LI1, LI2, LI3, LI4, LI5;
//        LI1 = LearningImage.get(LearningImage.size()-1);
//        LI2 = LearningImage.get(LearningImage.size()-2);
//        LI3 = LearningImage.get(LearningImage.size()-3);
//        LI4 = LearningImage.get(LearningImage.size()-4);
//        LI5 = LearningImage.get(LearningImage.size()-5);
        double LearningIntensity=0;
        if(LearningObjects.size() > 0)
        {
            for(int i=LearningObjects.size()-1; i > LearningObjects.size()-6 && i > 0; i--)
            {
                clsThingPresentationMesh Object = LearningObjects.get(i).b;
                if(Object.compareTo(TPM_Object) == 1.0)
                {
                    LearningIntensity += (Object.getLearningWeight() * 0.2 * (i - (LearningImages.size()-6)));
                }
                Object.setLearningWeightSum(LearningIntensity);
            }
            if(LearningIntensity > 1.0)
            {
                LearningSTMObjectStore.add(TPM_Object);
                LearningSTMObjectStoreHM.remove(TPM_Object.getContent());
                LearningSTMObjectStoreHM.put(TPM_Object.getContent(),TPM_Object);
            }
        }
    }
    
    public ArrayList<clsThingPresentationMesh> getLearningImage()
    {
        return LearningImages;
    }
    public String getLearningImagesString()
    {
        String out="";
        for(clsThingPresentationMesh LearningImage : LearningImages)
        {
            out += LearningImage.getContent()+"::Act:"+LearningImage.getActiveTime()+"::Foc:"+LearningImage.getCriterionActivationValue(eActivationType.MOMENT_ACTIVATION)+"::PI_Weight:"+LearningImage.getMrWeightPI()+"\n";
        }
        return out;
    }
    public ArrayList<clsThingPresentationMesh> getLTMLearningImages()
    {
                return LearningLTMImage;
    }
    public String getLearningContent()
    {
        String out;
        out= "Learning Content  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
        out+="\n--> STM ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
        out+="\n--------> Images:";
        Formatter oDoubleFormatter;

        for(clsThingPresentationMesh LearningImage : LearningLTMImage)
        {   oDoubleFormatter = new Formatter();
            if(LearningImage.getLearningWeightSum()>0)
            {
            out+="\n"+LearningImage.getContent() + ": Learning intensity: ";
            out += oDoubleFormatter.format("%.3f",LearningImage.getLearningWeightSum());
            oDoubleFormatter = new Formatter();
//            out+="\n"+LearningImage.getContent() + ": Learning intensity1: ";
//            out += oDoubleFormatter.format("%.3f",LearningImage.getLearningWeightSum1());
            oDoubleFormatter = new Formatter();
//            out+="\n"+LearningImage.getContent() + ": Learning intensity2: ";
//            out += oDoubleFormatter.format("%.3f",LearningImage.getLearningWeightSum2());
            oDoubleFormatter = new Formatter();
            }
        }
        out+="\n--> LTM ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
        out+="\n-------->                                                            New learned <EMOTION>         for          <IMAGE>";
//        for(String key : LearningSTMStoreHM.keySet())
//        {
//
//            clsEmotion EmotionRI = null;
//            for(clsAssociation Ass:LearningSTMStoreHM.get(key).getExternalAssociatedContent())
//            {
//                if (Ass instanceof clsAssociationEmotion)
//                {
//                    EmotionRI = (clsEmotion) Ass.getTheOtherElement(LearningSTMStoreHM.get(key));
//                }    
//            }
//            out += EmotionRI + " ----- ";
//            out += LearningSTMStoreHM.get(key).getContent();
//        }
//        out+="\n--> LTM ++++++";
        for(clsThingPresentationMesh LearningSTMStoreRem : LearningSTMStoreRemove)
        {

            clsEmotion EmotionRI = null;
            for(clsAssociation Ass:LearningSTMStoreRem.getExternalAssociatedContent())
            {
                if (Ass instanceof clsAssociationEmotion)
                {
                    EmotionRI = (clsEmotion) Ass.getTheOtherElement(LearningSTMStoreRem);
                }    
            }
            out += EmotionRI + " ----- ";
            out += LearningSTMStoreRem.getContent();
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
        text += "|---Emotion:\n";
        text += this.getEmotions();
        
        return text;
    }
    

    public String PrintImageStorage()
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
        text += "|---Emotion:\n";
        text += this.getEmotions();
        
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

    /**
     * @since 02.07.2019 07:41:25
     * 
     * @return the learningAction
     */
    public ArrayList<clsThingPresentationMesh> getLearningAction() {
        return LearningAction;
    }

    /**
     * @since 02.07.2019 07:41:25
     * 
     * @param learningAction the learningAction to set
     */
    public void setLearningAction(clsThingPresentationMesh learningAction) {
        LearningAction.add(learningAction);
    }

    /**
     * @since 02.07.2019 07:41:37
     * 
     * @return the learningIntention
     */
    public ArrayList<clsThingPresentationMesh> getLearningIntention() {
        return LearningIntention;
    }

    /**
     * @since 02.07.2019 07:41:37
     * 
     * @param learningIntention the learningIntention to set
     */
    public void setLearningIntention(clsThingPresentationMesh learningIntention) {
        LearningIntention.add(learningIntention);
    }

    /**
     * @since 02.07.2019 08:42:05
     * 
     * @return the learningEmotion
     */
    public ArrayList<clsEmotion> getLearningEmotion() {
        return LearningEmotion;
    }

    /**
     * @since 02.07.2019 08:42:05
     * 
     * @param learningEmotion the learningEmotion to set
     */
    public void setLearningEmotion(clsEmotion learningEmotion) {
        LearningEmotion.add(learningEmotion);
    }

    /**
     * @since 02.07.2019 08:49:35
     * 
     * @return the learningBodypart
     */
    public ArrayList<clsThingPresentationMesh> getLearningBodypart() {
        return LearningBodypart;
    }

    /**
     * @since 02.07.2019 08:49:35
     * 
     * @param learningBodypart the learningBodypart to set
     */
    public void setLearningBodypart(clsThingPresentationMesh learningBodypart) {
        LearningBodypart.add(learningBodypart);
    }
}



