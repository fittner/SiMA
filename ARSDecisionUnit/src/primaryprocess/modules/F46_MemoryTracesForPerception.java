
/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package primaryprocess.modules;

import general.datamanipulation.PrintTools;
import inspector.interfaces.Singleton;
import inspector.interfaces.itfGraphInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import memorymgmt.enums.PsychicSpreadingActivationMode;
import memorymgmt.enums.eContent;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import modules.interfaces.I2_6_receive;
import modules.interfaces.I5_6_receive;
import modules.interfaces.I5_6_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsConcept;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import primaryprocess.datamanipulation.clsPrimarySpatialTools;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import secondaryprocess.datamanipulation.clsEntityTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.clsTester;

/**
 * Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). 
 * In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from 
 * the TPMs to the thing presentations 
 * 
 * @author deutsch
 * 07.05.2012, 16:16:45
 * 
 */ 
public class F46_MemoryTracesForPerception extends clsModuleBaseKB implements I2_6_receive, I5_6_send, itfGraphInterface {
    public static final String P_MODULENUMBER = "46";
    public static final String P_MATCH_THRESHOLD = "MATCH_THRESHOLD";
     
    //FIXME: Connect to neutral drive energy
    private static final double PSYCHICINTENSITYFORSPREADINGACTIVATION = 30.0;
    private static final int MAXDIRECTACTIVATIONFORSPREADINGACTIVATION = 30;
    private static final double RECOGNIZEDIMAGEMULTIPLICATIONFACTOR = 2;
    
    
    /* Inputs */
    /** Here the associated memory from the planning is put on the input to this module */
    private ArrayList<clsThingPresentationMesh> moReturnedPhantasy_IN; 
    private PsychicSpreadingActivationMode psychicSpreadingActivationMode;
    /** Input from perception */
    private ArrayList<clsThingPresentationMesh> moEnvironmentalPerception_IN;

    private ArrayList<clsDriveMesh> moDrives_IN;
    
    /* Output */
    /** A Perceived image incl. DMs */
    private clsThingPresentationMesh moPerceptionalMesh_OUT; 
    private clsWordPresentationMesh moWordingToContext;
    private clsWordPresentationMesh moWordingToContextNew;
    private clsConcept moConcept;
    
    ///* Internal */
    //private clsThingPresentationMesh moEnhancedPerception;
    
    /** Threshold for matching for associated images */
    private double mrMatchThreshold;
    
    /** (wendt) Localitzation of things for the primary process. With the localization, memories can be triggered; @since 15.11.2011 16:23:43 */
    private clsEnvironmentalImageMemory moTempLocalizationStorage;
    // This array holds references to the clones created from the localization storage entities. These clones will be added to the perceived image
    // so they can be used during spreading activation. After spreading activation, they need to be removed from the perceived image again - therefore
    // we hold the references.
    private ArrayList<clsThingPresentationMesh> moTempEntities = new ArrayList<>();

    /* Module-Parameters */
    
    /**
     * Association of TPMs (TP + Emotion, fantasies) with thing presentations 
     * raw data (from external perception). In a first step these are attached with a value to get a meaning. 
     * Secondly the fantasies are added from the TPMs to the thing presentations
     * 
     * @author deutsch
     * 03.03.2011, 16:16:50
     *
     * @param poPrefix
     * @param poProp
     * @param poModuleList
     * @throws Exception
     */
    public F46_MemoryTracesForPerception(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory,
            clsEnvironmentalImageMemory poTempLocalizationStorage, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);
        
        applyProperties(poPrefix, poProp);
        
        mrMatchThreshold= poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MATCH_THRESHOLD).getParameterDouble();
        moTempLocalizationStorage = poTempLocalizationStorage;
        moReturnedPhantasy_IN = new ArrayList<clsThingPresentationMesh>();      //Set Input!=null
        this.psychicSpreadingActivationMode=psychicSpreadingActivationMode.NONE;
    }
    
    /* (non-Javadoc)
     *
     * @author deutsch
     * 14.04.2011, 17:36:19
     * 
     * @see pa.modules._v38.clsModuleBase#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        String text ="\n";
        
        //text += toText.listToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
        //text += toText.valueToTEXT("moReturnedPhantasy_IN", moReturnedPhantasy_IN);
        //text += toText.listToTEXT("Internal associations of moPerceptionalMesh_OUT (entities)", moPerceptionalMesh_OUT.getInternalAssociatedContent());
        //text += toText.listToTEXT("Directly activated associations of moPerceptionalMesh_OUT", moPerceptionalMesh_OUT.getExternalAssociatedContent());
        text += toText.valueToTEXT("PI Match Analysis", PrintTools.printActivatedMeshWithPIMatch(moPerceptionalMesh_OUT));
        //text += toText.valueToTEXT("Activated images", PrintTools.printActivatedMeshWithPIMatchOriginal(moPerceptionalMesh_OUT));
        
       // text += toText.valueToTEXT("moEnhancedPerception", moEnhancedPerception);
        //text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
        //text += toText.valueToTEXT("mrMatchThreshold", mrMatchThreshold);
        
        //text += toText.listToTEXT("moTempLocalizationStorage", moTempLocalizationStorage.getMoShortTimeMemory());
        
        return text;
    }   
    
    
    
    public static clsProperties getDefaultProperties(String poPrefix) {
        String pre = clsProperties.addDot(poPrefix);
        
        clsProperties oProp = new clsProperties();
        oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
                
        return oProp;
    }
    
    private void applyProperties(String poPrefix, clsProperties poProp) {
        //String pre = clsProperties.addDot(poPrefix);
    
        //nothing to do
    }   
    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#process_basic()
     */
    @Override
    protected void process_basic() {
        if (moWordingToContext == null){
            moConcept = new clsConcept();
            
            moWordingToContextNew = moConcept.moWording;
            moWordingToContext = moWordingToContextNew;
            
        }
        
        
        Logger datalogger = LogManager.getLogger("GetImageMatch");
       
        
        //Set new instance IDs
        //clsDataStructureTools.createInstanceFromTypeList(oContainerWithTypes, true);
        //Convert LOCATION to DISTANCE and POSITION
        //FIXME AW: Remove this when CM has implemented it in his modules
        //TEMPconvertLOCATIONtoPOSITIONandDISTANCE(oContainerWithTypes);

        clsThingPresentationMesh oPerceivedImage = clsMeshTools.createTPMImage(moEnvironmentalPerception_IN, eContentType.PI, eContent.PI.toString());
        
        
        //=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignment(oPerceivedImage);
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
        
        // Deprecated, MERGED WITH SPREADACT. Compare PI with similar Images from Memory(RIs). Result = PI associated with similar TIs
        // lsThingPresentationMesh oPIWithAssociatedRIs =  compareRIsWithPI(oPerceivedImage);
                
                
        //Create EMPTYSPACE objects
        ArrayList<clsThingPresentationMesh> oEmptySpaceList = createEmptySpaceObjects(oPerceivedImage);
        //Add those to the PI
        clsMeshTools.addTPMToTPMImage(oPerceivedImage, oEmptySpaceList);
        //=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            log.warn("Systemtester activated");
            try {
                clsTester.getTester().exeTestAssociationAssignment(oPerceivedImage);
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
        
        //--- Activation of associated memories ---//
        //Get the phantasy input
        //clsThingPresentationMesh oBestPhantasyInput = this.processPhantasyInput(moReturnedPhantasy_IN);
        
        if (clsTester.getTester().isActivated()) {
            log.warn("Systemtester activated");
            try {
                for (clsThingPresentationMesh tpm : moReturnedPhantasy_IN) {
                    clsTester.getTester().exeTestAssociationAssignment(tpm);
                }
                
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
        
        //Associate current emotions (currently associated to SELF entity) to the perceived image via ASSOCIATIONEMOTION
        // (this will later be used for the emotion aspect of the image match)
        
        //Start by finding the SELF entity
        clsThingPresentationMesh oSelf = clsMeshTools.getSELF(oPerceivedImage);
        
        if(oSelf != null && !oSelf.isNullObject()) {
            for(clsAssociationEmotion oAssEmotion : clsAssociation.filterListByType(oSelf.getInternalAssociatedContent(), clsAssociationEmotion.class)) {
                if(oAssEmotion.getTheOtherElement(oSelf) instanceof clsEmotion) {
                    clsDataStructureGenerator.generateASSOCIATIONEMOTION(eContentType.ASSOCIATIONEMOTION, (clsEmotion)oAssEmotion.getTheOtherElement(oSelf), oPerceivedImage, false, 1.0);
                    //clsDataStructureGenerator.generateASSOCIATIONEMOTION(eContentType.ASSOCIATIONEMOTION, (clsEmotion)oAssEmotion.getTheOtherElement(oSelf), Singleton.PIList.get(Singleton.stepGlobalPIMatch), false, 1.0);
                    //datalogger.debug("External Associated Emotions from PI: " + Singleton.PIList.get(Singleton.stepGlobalPIMatch).getExternalAssociatedContent());
                
                }
            }
        }
        
      /*  clsEmotion PIEmotion = null;
        for(clsAssociation AssEmotion: Singleton.PIList.get(Singleton.stepGlobalPIMatch).getExternalAssociatedContent())
        {                
            if(AssEmotion instanceof clsAssociationEmotion) {
                PIEmotion = (clsEmotion) AssEmotion.getTheOtherElement(Singleton.PIList.get(Singleton.stepGlobalPIMatch-1));
                
            }                    
               
        }
        */
        Singleton.PIList.add(oPerceivedImage);
        //Activate memories (Spread activation)
        try {
            activateMemories(oPerceivedImage, moReturnedPhantasy_IN, this.psychicSpreadingActivationMode);
            Singleton.stepGlobalPIMatch++;
            Singleton PIMatch = Singleton.getInstance();
            //PIMatch.addToPIMatchList();
          //delacruz: add Perceived image to Singleton PI List
            
           
            if((Singleton.stepGlobalPIMatch % 2)==0) {
                Singleton.PIMatchList.clear(); 
                Singleton.RIList.clear();
                //Singleton.PIList.clear();
                //Singleton.PIEmotionList.clear();
                
                //Singleton.oRIPIMatchList.clear();
                //set to -1 since step will be then inside method addToPIMatchList incremented
                Singleton.stepPIMatch=-1;
                Singleton.numberImagesPIMatch = 0;
                PIMatch.addToPIMatchList();
                Singleton.RIList.add(null);
                
                
            }
            //Singleton.clearPIMatchList = true;
            datalogger.debug("number of PI Match calculation rounds: " + Singleton.stepGlobalPIMatch);
            
        } catch (Exception e1) {
            log.error("", e1);
            datalogger.error(e1);
        }
        
//      Remove the emotion from the perceived image
        for(clsAssociationEmotion oAss : clsAssociation.filterListByType(oPerceivedImage.getExternalAssociatedContent(), clsAssociationEmotion.class)) {
            //retrieve emotion from PI Image and set to global Singleton
            //clsAssociation oAssTmp = null;
            //Singleton.PIEmotionList.add(oAssTmp);
            Singleton.PIEmotionList.add(oAss.getAssociationElementA());
            //datalogger.debug("External Associated Emotions from PI: " + Singleton.PIList.get(Singleton.stepGlobalPIMatch));
            
            oPerceivedImage.getExternalAssociatedContent().remove(oAss);
            
        }
        
        log.debug("PI: " + oPerceivedImage);
        log.info("Activated images: {}", PrintTools.printActivatedMeshWithPIMatch(oPerceivedImage));
        moPerceptionalMesh_OUT = oPerceivedImage;
        
        //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestNullPointer(oPerceivedImage);
                clsTester.getTester().exeTestAssociationAssignment(oPerceivedImage);
                clsTester.getTester().exeTestDMReference(oPerceivedImage);
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#process_draft()
     */
    @Override
    protected void process_draft() {
        
        }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#process_final()
     */
    @Override
    protected void process_final() {
    

    }
    
//  /**
//   * HACK by AW: This function converts all locations to either DISTANCE or POSITION
//   * 
//   * (wendt)
//   *
//   * @since 01.12.2011 13:36:22
//   *
//   * @param poEnvironmentalPerception
//   */
//  private void TEMPconvertLOCATIONtoPOSITIONandDISTANCE(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception) {
//      ArrayList<String> oDistance = new ArrayList<String>();
//      oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
//      ArrayList<String> oPosition = new ArrayList<String>();
//      oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
//      
//      for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception) {
//          for (clsAssociation oAss : oContainer.getMoAssociatedDataStructures()) {
//              if (oAss.getLeafElement() instanceof clsThingPresentation) {
//                  if ((oAss.getLeafElement().getMoContentType().equals("LOCATION")==true) && (oDistance.contains(((clsThingPresentation)oAss.getLeafElement()).getMoContent())==true)) {
//                      oAss.getLeafElement().setMoContentType(eContentType.DISTANCE.toString());
//                  } else if ((oAss.getLeafElement().getMoContentType().equals("LOCATION")==true) && (oPosition.contains(((clsThingPresentation)oAss.getLeafElement()).getMoContent())==true)) {
//                      oAss.getLeafElement().setMoContentType(eContentType.POSITION.toString());
//                  }
//              }
//          }
//      }
//  }
    
    
    /**
     * The PI is enhanced with all objects from the localization, which cannot be seen in the image.
     * 
     * (wendt)
     *
     * @since 15.11.2011 16:42:41
     *
     * @param poPI
     * @param poTempLocalizationStorage
     * @return
     * @throws CloneNotSupportedException 
     */
    private void enhancePerceptionWithEnhancedEnvironmentalImage(clsThingPresentationMesh poPI, clsEnvironmentalImageMemory poEnvironmentalImageStorage) {
        
        //Get all objects from the localization
        ArrayList<clsThingPresentationMesh> oPTPMList = poEnvironmentalImageStorage.getAllTPMFromEnhancedEnvironmentalImage();
        ArrayList<clsThingPresentationMesh> oExtendEntityList = new ArrayList<clsThingPresentationMesh>();
        
        for(clsThingPresentationMesh oTPM : oPTPMList) {
            //Check if the PI contains this type of object
            clsThingPresentationMesh oFoundTPM = clsMeshTools.searchFirstDataStructureInstanceInTPM(poPI, oTPM, 3, true);
            
            //if not then add to 
            if (oFoundTPM.isNullObject()==true) {
                clsThingPresentationMesh oNewTPM = null;
                try {
                    oNewTPM = (clsThingPresentationMesh) oTPM.clone();
                    String distance = clsMeshTools.getUniqueTP(oNewTPM, eContentType.DISTANCE);
                    
                    if (distance.equals(eRadius.OUTOFSIGHT.toString())==false) {
                        log.warn("The TPM in the environmental image {}", oNewTPM);
                        clsEntityTools.removeDistanceAndPosition(oNewTPM);
                    }
                    
                    //Store reference to the cloned TPM so it can be removed later
                    moTempEntities.add(oNewTPM);
                    
                    oExtendEntityList.add(oNewTPM);
                } catch (CloneNotSupportedException e) {
                    // TODO (wendt) - Auto-generated catch block
                    log.warn("Could enchance perception with envionmental TPM {} because TPM could not be cloned", oTPM);
                    e.printStackTrace();
                }
            }
        }
        
        //Add the containerlist to the PI
        clsMeshTools.addTPMToTPMImage(poPI, oExtendEntityList); 
    }
    
    /**
     * Remove all entites with no position, i. e. the entities from the enhanced environmental image
     * 
     * (wendt)
     *
     * @since 10.10.2012 12:40:28
     *
     * @param poPI
     */
    private void removeEnhancedEnvironmentalImageFromPerception(clsThingPresentationMesh poPI) {
        
        for(clsThingPresentationMesh oTempTPM : moTempEntities) {
            clsMeshTools.deleteAssociationInObject(poPI, oTempTPM);
        }
        
        //clear the array - we don't need it anymore
        moTempEntities.clear();
    }
    
    /**
     * Either the perceived image or the input image from the secondary process are put on the input for searching for experiences (type IMAGE)
     * in the storage. The total amount of mrPleasure decides which image is put on the input. In that way content from the secondary process
     * can activate phantasies, if the perception is not so important (subjective). The function returns a list of activated images, which are
     * not perception.
     *
     * @since 14.07.2011 15:15:31
     *
     * @param oPerceptionInput
     * @param oReturnedMemory
     * @return
     * @throws Exception 
     */
    private void activateMemories(clsThingPresentationMesh perceivedImage, ArrayList<clsThingPresentationMesh> returnedPhantasyImageList, PsychicSpreadingActivationMode mode) throws Exception {
        //Check if phantasyimages are valid and mode is correctly set
        boolean hasError=false;
        if (returnedPhantasyImageList.isEmpty() && mode.equals(PsychicSpreadingActivationMode.NONE)==false) {
            hasError=true;
            log.warn("Erroneous input to psychic spreading activation. No phantasyimage has arrived, but phantasy shall be used.");
        } else if (returnedPhantasyImageList.size()!=1 && (mode.equals(PsychicSpreadingActivationMode.COMPLETE_DIRECT_ACTIVATION)==true || mode.equals(PsychicSpreadingActivationMode.COMPLETE_INDIRECT_ACTIVATION)==true)) {
            hasError=true;
            log.warn("Erroneous input to psychic spreading activation. No or more than one images are set as source for phantasy activation.");
        }
        
        log.debug("Start Spreading Activation with mode {}", mode);
        
        //If no errors, perform the psychic spreading activation
        if (hasError==false) {
            //Execute spreading activation depending on set mode. Default mode is ENHANCED_ACTIVATION
            if (mode.equals(PsychicSpreadingActivationMode.ENHANCED_ACTIVATION)==true || mode.equals(PsychicSpreadingActivationMode.NONE)) {
                //Activate with the PI match as source mesh and only enhanced use of other images from phantasy
                //Default case + if moment and exception is sent
                //delacruz: here will be the pi match calculated
                activateWithPerceivedImageAsSource(perceivedImage, returnedPhantasyImageList);
                
                
            } else if (mode.equals(PsychicSpreadingActivationMode.COMPLETE_DIRECT_ACTIVATION)==true || mode.equals(PsychicSpreadingActivationMode.COMPLETE_INDIRECT_ACTIVATION)==true) {
                activateWithPhantasyImageAsSource(perceivedImage, returnedPhantasyImageList, mode);  
            } else {
                log.error("Mode {} does not exist.", mode);
                throw new Exception("mode not found " + mode);
            }
        } else {
            log.warn("Phantasyinputs were supposed to be used, but because of errors normal activation will be used.");
            activateWithPerceivedImageAsSource(perceivedImage, returnedPhantasyImageList);
        }               
    }

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 07.12.2013 14:15:18
     *
     * @param perceivedImage
     * @param returnedPhantasyImageList
     * @param mode
     */
    private void activateWithPhantasyImageAsSource(clsThingPresentationMesh perceivedImage,
            ArrayList<clsThingPresentationMesh> returnedPhantasyImageList, PsychicSpreadingActivationMode mode) {
        clsThingPresentationMesh sourceImagePhantasy = clsMeshTools.getNullObjectTPM();
        //Get the first phantasyimage
        if (returnedPhantasyImageList.isEmpty()==false) {
            sourceImagePhantasy = returnedPhantasyImageList.get(0);
        }
        
        //Add SELF to the image if it does not exist
        if (clsMeshTools.getSELF(sourceImagePhantasy).isNullObject()==true) {
            //FIXME AW SELF should be loaded somewhere else.
            clsThingPresentationMesh oSELF = this.getLongTermMemory().searchExactEntityFromInternalAttributes("SELF", "CIRCLE", "#33FF33");
            ArrayList<clsThingPresentationMesh> oSELFList = new ArrayList<clsThingPresentationMesh>();
            oSELFList.add(oSELF);
            clsMeshTools.addTPMToTPMImage(sourceImagePhantasy, oSELFList);
        }
        
        if (mode.equals(PsychicSpreadingActivationMode.COMPLETE_DIRECT_ACTIVATION)==true) {
            //Activate with the first phantasyimage as source and use direct activation for it
            //Used if drive images are sent
            this.getLongTermMemory().executePsychicSpreadActivation(sourceImagePhantasy, moDrives_IN, PSYCHICINTENSITYFORSPREADINGACTIVATION/2, MAXDIRECTACTIVATIONFORSPREADINGACTIVATION, true, 1.0, new ArrayList<clsThingPresentationMesh>());
        } else {
            //Activate with the first phantasyimage as source and use indirect activation for it
            //Used if intentions are sent by SEND_TO_PHANTASY
            this.getLongTermMemory().executePsychicSpreadActivation(sourceImagePhantasy, moDrives_IN, PSYCHICINTENSITYFORSPREADINGACTIVATION/2, MAXDIRECTACTIVATIONFORSPREADINGACTIVATION, false, 1.0, new ArrayList<clsThingPresentationMesh>());
        }
        
        clsMeshTools.createAssociationPrimary(perceivedImage, sourceImagePhantasy, 1.0);
    }

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 07.12.2013 14:14:33
     *
     * @param perceivedImage
     * @param returnedPhantasyImageList
     */
    private void activateWithPerceivedImageAsSource(clsThingPresentationMesh perceivedImage, ArrayList<clsThingPresentationMesh> returnedPhantasyImageList) {
        //--- Enhance perception with environmental image ---//
        enhancePerceptionWithEnhancedEnvironmentalImage(perceivedImage, moTempLocalizationStorage);
        
        //=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignment(perceivedImage);
            } catch (Exception e) {
                log.error("Systemtester has an error in activateMemories in" + this.getClass().getSimpleName(), e);
            }
        }
        
         this.getLongTermMemory().executePsychicSpreadActivation(perceivedImage, moDrives_IN, PSYCHICINTENSITYFORSPREADINGACTIVATION, MAXDIRECTACTIVATIONFORSPREADINGACTIVATION, true, RECOGNIZEDIMAGEMULTIPLICATIONFACTOR, returnedPhantasyImageList);
        
        //=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignment(perceivedImage);
            } catch (Exception e) {
                log.error("Systemtester has an error in activateMemories in" + this.getClass().getSimpleName(), e);
            }
        }
        
        //--- Remove enhanced perception from PI as these were only there to activate memories
        removeEnhancedEnvironmentalImageFromPerception(perceivedImage);
    }
    
    
    /**
     * Get the phantasy from the input
     * 
     * (wendt)
     *
     * @since 16.07.2012 15:34:16
     *
     * @param poPhantasyInputList
     * @return
     */
    private clsThingPresentationMesh processPhantasyInput(ArrayList<clsThingPresentationMesh> poPhantasyInputList) {
        clsThingPresentationMesh oResult = clsMeshTools.getNullObjectTPM();
        
        if (moReturnedPhantasy_IN.isEmpty()==false) {
            
            //Get the first phantasy image
            oResult = moReturnedPhantasy_IN.get(0);
        }
        
        return oResult;
    }

    
    
    
    

    
    
    /**
     * Add Empty-Space-Objects to perception for all floor elements, which are not visible
     * 
     * (wendt)
     *
     * @since 28.11.2011 14:35:09
     *
     * @param poImage
     * @return
     */
    private ArrayList<clsThingPresentationMesh> createEmptySpaceObjects(clsThingPresentationMesh poImage) {
        ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
        
        //Get all positions in the image
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oExistingPositions = clsPrimarySpatialTools.getImageEntityPositions(poImage);
        
        //Generate a matrix of all possible positions
        //ArrayList<String> oDistance = new ArrayList<String>();
        //oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
        //oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR"));   //Use only the things, which are relevant
        //ArrayList<String> oPosition = new ArrayList<String>();
        //oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
        
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oAllPositions = new ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>>();
        for (int i=1; i< eRadius.values().length;i++) {
            for (int j=0; j< ePhiPosition.values().length;j++) {
                oAllPositions.add(new clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>(null, ePhiPosition.values()[j], eRadius.values()[i]));
            }
        }
        
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oNewPositions = new ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>>();
         
        //Find all Objects in the oAllPositions and add them to oRemovePositions
        for (clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oAllPosPair: oAllPositions) {
            boolean bFound = false;
            for (clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oExistPosPair : oExistingPositions) {
                try { 
                    if ((oExistPosPair.b.equals(oAllPosPair.b)) && (oExistPosPair.c.equals(oAllPosPair.c))) {
                        bFound = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error beacuse some values are NULL");
                /*  String a="";
                    a+= "Entity oExistPosPair: ";
                    if(oExistPosPair.a!=null)a+= oExistPosPair.a.toString();
                    else a+="null";
                    a+=", position: ";
                    if(oExistPosPair.b!=null)a+= oExistPosPair.b.toString();
                    else a+="null";
                    a+= ", distance: ";
                    if(oExistPosPair.c!=null)a+= oExistPosPair.c.toString();
                    else a+="null";
                    System.out.println(a);
                    */
                    //System.out.println("Entity oExistPosPair: " + oExistPosPair.a.toString() + ", position: " + oExistPosPair.b.toString() + ", distance: " + oExistPosPair.c.toString());
                    //System.out.println("Entity: oAllPosPair" + oAllPosPair.a.toString() + ", position: " + oAllPosPair.b.toString() + ", distance: " + oAllPosPair.c.toString());
                }
            }
            if (bFound==false) {
                oNewPositions.add(oAllPosPair);
            }
        }   

        
        
        //Search for one "Nothingobject"
        //Create the TP
        clsThingPresentationMesh oGeneratedTPM = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>
            (eContentType.ENTITY, new ArrayList<clsThingPresentation>(),"EMPTYSPACE"));
        
        ArrayList<clsPrimaryDataStructureContainer> oSearchStructure = new ArrayList<clsPrimaryDataStructureContainer>();
        oSearchStructure.add(new clsPrimaryDataStructureContainer(oGeneratedTPM, new ArrayList<clsAssociation>()));
        
        ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
            new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
        
        oSearchResult = this.getLongTermMemory().searchEntity(eDataType.TPM, oSearchStructure); 
        //If nothing is found, cancel
        if (oSearchResult.get(0).isEmpty()==true) {
            return oRetVal;
        }
        //Create "Nothing"-objects for each position
        clsPrimaryDataStructureContainer oEmptySpaceContainer = (clsPrimaryDataStructureContainer) oSearchResult.get(0).get(0).b;
        ArrayList<clsPrimaryDataStructureContainer> oEmptySpaceContainerList = new ArrayList<clsPrimaryDataStructureContainer>();
        oEmptySpaceContainerList.add(oEmptySpaceContainer);
        //assignDriveMeshes(oEmptySpaceContainerList);
        
        //for each position, fill it with a container
        clsThingPresentationMesh oEmptySpaceTPM;
        for (clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPosPair : oNewPositions) {
            //Create a new TP-Container
            try {
                ((clsThingPresentationMesh)oEmptySpaceContainer.getMoDataStructure()).setExternalAssociatedContent(oEmptySpaceContainer.getMoAssociatedDataStructures());
                oEmptySpaceTPM = (clsThingPresentationMesh) ((clsThingPresentationMesh) oEmptySpaceContainer.getMoDataStructure()).clone();
                
            
                clsThingPresentation oPositionTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.POSITION, oPosPair.b.toString()));
                clsThingPresentation oDistanceTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.DISTANCE, oPosPair.c.toString()));
            
                //clsTriple<Integer, eDataType, String> poDataStructureIdentifier,
                //clsPrimaryDataStructure poAssociationElementA, 
                //clsPrimaryDataStructure poAssociationElementB)
                clsTriple<Integer, eDataType, eContentType> poIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE);
                clsAssociationAttribute oPositionAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oPositionTP);
                clsAssociationAttribute oDistanceAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oDistanceTP);
            
                oEmptySpaceTPM.getExternalAssociatedContent().add(oPositionAss);
                oEmptySpaceTPM.getExternalAssociatedContent().add(oDistanceAss);
            
            
                oRetVal.add(oEmptySpaceTPM);
            
            } catch (CloneNotSupportedException e) {
                // TODO (wendt) - Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        //Set new instance IDs
        //clsDataStructureTools.createInstanceFromTypeList(oRetVal, true);
        
        return oRetVal;
    }
    
//  /**
//   * Extract the n first drives from a list of drive meshes and drive objects
//   * (wendt)
//   *
//   * @since 10.05.2012 11:07:00
//   *
//   * @param poDriveList
//   * @param pnNumberOfDriveMeshes
//   * @return
//   */
//  private ArrayList<clsDriveMesh> extractDriveMeshes(ArrayList<clsDriveMesh> poDriveList, int pnNumberOfDriveMeshes) {
//      ArrayList<clsDriveMesh> oRetVal = new ArrayList<clsDriveMesh>();
//      
//      int nCounter = 0;
//      if (poDriveList.isEmpty()==false) {
//          for (int i=0; i<poDriveList.size();i++) {
//              clsDriveMesh oDM = poDriveList.get(i);
//              oRetVal.add(oDM);
//              
//              if (nCounter>=pnNumberOfDriveMeshes-1) {
//                  break;
//              }
//              
//              nCounter++;
//                  
//          }
//      }
//      
//      
//      
//      return oRetVal;
//  }
    

    
    
    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#send()
     */
    @Override
    protected void send() {
        send_I5_6(moPerceptionalMesh_OUT, moWordingToContext);
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#setProcessType()
     */
    @Override
    protected void setProcessType() {
        mnProcessType = eProcessType.PRIMARY;

    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
     */
    @Override
    protected void setPsychicInstances() {
        mnPsychicInstances = ePsychicInstances.EGO;

    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:16:45
     * 
     * @see pa.modules._v38.clsModuleBase#setModuleNumber()
     */
    @Override
    protected void setModuleNumber() {
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:20:28
     * 
     * @see pa.interfaces.send._v38.I2_20_send#receive_I2_20(java.util.ArrayList)
     */
    @Override
    public void send_I5_6(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
        /* The inputs and outputs can be changed if the following parameters are changed:
         * clsModuleBase.deepcopy
         * this function
         * the receive function in the following module
         * I5_6_send.java
         * I5_6_receive.java
         */
        //Give output to input of F37
        ((I5_6_receive)moModuleList.get(37)).receive_I5_6(poPerceptionalMesh, moWordingToContext2);
        //Give output to input of F57
        //v38g has no interface between F46 and F57 ((I5_6_receive)moModuleList.get(57)).receive_I5_6(poPerceptionalMesh);
        putInterfaceData(I5_6_send.class, poPerceptionalMesh, moWordingToContext2);
    }
    
    /* (non-Javadoc)
     *
     * @author deutsch
     * 03.03.2011, 16:20:28
     * 
     * @see pa.interfaces.receive._v38.I2_5_receive#receive_I2_5(java.util.ArrayList)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I2_6(ArrayList<clsThingPresentationMesh> poEnvironmentalPerception, ArrayList<clsDriveMesh> poDrives_IN,
            ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode poMode, clsWordPresentationMesh moWordingToContext2) {
        
        moWordingToContext = moWordingToContext2;
        moReturnedPhantasy_IN = (ArrayList<clsThingPresentationMesh>)deepCopy(poReturnedMemory);
        psychicSpreadingActivationMode = poMode;
        
        moEnvironmentalPerception_IN = (ArrayList<clsThingPresentationMesh>)deepCopy(poEnvironmentalPerception); 
        
          //=== Perform system tests ===//
        boolean status = clsTester.getTester().isActivated();
        clsTester.getTester().setActivated(true);
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignmentTPMArray(moEnvironmentalPerception_IN);
                clsTester.getTester().exeTestAssociationAssignmentTPMArray(poEnvironmentalPerception);
            } catch (Exception e) {
                log.error("Systemtester has an error in activateMemories in" + this.getClass().getSimpleName(), e);
            }
        }
        clsTester.getTester().setActivated(status);
        
        moDrives_IN = poDrives_IN;
    }
    
    /* (non-Javadoc)
     *
     * @author deutsch
     * 15.04.2011, 13:52:57
     * 
     * @see pa.modules._v38.clsModuleBase#setDescription()
     */
    @Override
    public void setDescription() {
        moDescription = "Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from the TPMs to the thing presentations";
    }

    /* (non-Javadoc)
     *
     * @since Oct 4, 2012 11:15:51 AM
     * 
     * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesSend()
     */
    @Override
    public ArrayList<eInterfaces> getGraphInterfaces() {
        return this.getInterfacesSend();
    }       
}
