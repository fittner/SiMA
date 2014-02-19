/**
 * CHANGELOG
 *
 * 29.03.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.longtermmemory.psychicspreadactivation;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.Collections;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.interfaces.itfSearchSpaceAccess;

import org.slf4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationTime;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.longtermmemory.psychicspreadactivation.PsychicSpreadActivationNode;
import secondaryprocess.datamanipulation.clsImportanceTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.HackMethods;
import testfunctions.clsTester;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 29.03.2012, 21:56:28
 * 
 */
public class PsychicSpreadingActivation implements PsychicSpreadingActivationInterface {
	
	private final double moDefaultConsumeValue;
	private final double mrActivationThreshold;
	private itfSearchSpaceAccess moModuleBase;
	
	private Logger log = clsLogger.getLog("memory");
	
	public PsychicSpreadingActivation(itfSearchSpaceAccess poModuleBase, double prConsumeValue, double prActivationThreshold) {
		moDefaultConsumeValue = prConsumeValue;
		mrActivationThreshold=prActivationThreshold;
		moModuleBase = poModuleBase;
	}


	/**
	 * Start spreading activation, the first method for each node
	 * 
	 * @param poImage
	 * @param prPsychicEnergyIn
	 * @param pnMaximumDirectActivationValue
	 * @param poDrivesForFilteringList
	 * @param poAlreadyActivatedImages
	 */
	@Override
	public void startSpreadActivation(clsThingPresentationMesh poImage, double prPsychicEnergyIn, int pnMaximumDirectActivationValue, boolean useDirectActivation, ArrayList<clsDriveMesh> poDrivesForFilteringList, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages) {
		//Activate this mesh, i. e. consume this energy
		double rAvailablePsychicEnergy = prPsychicEnergyIn - getEnergyConsumptionValue(poImage);
		log.info("Start spread activation from image {} with psychic energy {}, max activations/node={}, useDirectActivation={}, ", poImage.getContent(), prPsychicEnergyIn, pnMaximumDirectActivationValue, useDirectActivation);
		log.info("Images to use from phantasy for enhancement. Factor={}, images: {}", recognizedImageMultiplyFactor, preferredImages);
		log.debug("Inputs: Psychic Energy In=" + prPsychicEnergyIn + ", Available psychic energy=" + rAvailablePsychicEnergy + ", InputImage=" + poImage.getContent());
		log.trace("Already activated images: " + clsMeshTools.toString(poAlreadyActivatedImages));
		
		
		//1. Get level 1 of the image associations
		if (useDirectActivation==true) {
			log.trace("Image {}, get DIRECT associations", poImage.getContent());
			getAssociatedImagesPerception(poImage, mrActivationThreshold);
		} else {
			log.trace("Image {}, get INDIRECT associations", poImage.getContent());
			getAssociatedImagesMemory(poImage);
		}
		
		//2. Consolidate mesh
		
		//3. Calculate activation
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oPossibleActivationList = activateAssociatedImages(poImage, rAvailablePsychicEnergy, pnMaximumDirectActivationValue, poDrivesForFilteringList, recognizedImageMultiplyFactor, preferredImages, poAlreadyActivatedImages, useDirectActivation);
		log.info("Possible images, which can be activated: {}", PrintTools.printArrayListWithLineBreaks(oPossibleActivationList));
		
		//4. Delete non activated images		
		ArrayList<clsPair<clsThingPresentationMesh,Double>> oActivatedImageList = deleteInactivatedAssociations(poImage, oPossibleActivationList);
		log.info("Activated images: {}", PrintTools.printArrayListImageNamesWithLineBreaks(oActivatedImageList));
		
		//5. Go through each of the previously activated images
		for (clsPair<clsThingPresentationMesh,Double> oPair : oActivatedImageList) {
			int mnTotalNumberOfAllowedActivations = poAlreadyActivatedImages.size() + pnMaximumDirectActivationValue;
			//useDirectActivation is set false here and for all following activation. This option is only applied for the first activation stage, the direct activations
			startSpreadActivation(oPair.a, oPair.b, mnTotalNumberOfAllowedActivations, false, poDrivesForFilteringList, recognizedImageMultiplyFactor, preferredImages, poAlreadyActivatedImages);
		}
	}
	
	/**
	 * For a perceived image, get the direct associations to stored memories. Enhance the perceived image with the associations to the memories
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 22:56:58
	 *
	 * @param poOriginImage
	 * @return 
	 * @return
	 */
	private void getAssociatedImagesPerception(clsThingPresentationMesh poOriginImage, double prThreshold) {
		ArrayList<clsPair<Double,clsDataStructurePA>> oSearchResultMesh = new ArrayList<clsPair<Double,clsDataStructurePA>>();
		
		//moModuleBase.searchMesh(poOriginImage, oSearchResultMesh, eContentType.RI, prThreshold, 1);
		oSearchResultMesh = moModuleBase.searchMesh(poOriginImage, eContentType.RI, prThreshold, 1);
		
		//=== Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				clsTester.getTester().exeTestAssociationAssignment(poOriginImage);
			} catch (Exception e) {
				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
			}
		}
		
		//This is a hack method that can be used to focus the list of activate able entries 
//		HackMethods.filterTPMSearchResultList(Collections.singleton("A10"), oSearchResultMesh);
		
		log.debug("Create direct associations between " + poOriginImage.getContent() + " and ");
		if (poOriginImage.getContentType().equals(eContentType.PI) || poOriginImage.getContentType().equals(eContentType.PHI)) {
			for (clsPair<Double,clsDataStructurePA> oPair : oSearchResultMesh) {
				clsMeshTools.createAssociationPrimary(poOriginImage, (clsThingPresentationMesh) oPair.b, oPair.a);
				//Now all matched images are linked with the PI
				log.debug(((clsThingPresentationMesh)oPair.b).getContent() + ", matching value: " + oPair.a + ", ");
			}
		}
	}
	
	/**
	 * For a stored image, content type RI, search all direct associated memories
	 * 
	 * (wendt)
	 *
	 * @since 01.04.2012 16:24:44
	 *
	 * @param poOriginImage
	 */
	private void getAssociatedImagesMemory(clsThingPresentationMesh poOriginImage) {
//		poOriginImage = (clsThingPresentationMesh) moModuleBase.getCompleteMesh(poOriginImage, 10);
	    clsThingPresentationMesh oTPM = null;
	    
	    //Kollmann: complete the meshes for all entities in the image
	    for(clsAssociation oAssociation : poOriginImage.getInternalAssociatedContent()) {
	        if(oAssociation instanceof clsAssociationTime) {
	            if(oAssociation.getLeafElement() instanceof clsThingPresentationMesh) {
	                oTPM = (clsThingPresentationMesh) oAssociation.getLeafElement();
	                
	                if(oTPM == poOriginImage || oTPM.getDS_ID() == poOriginImage.getDS_ID()) {
	                    log.warn("The following internal association seems to have root and leaf mixed up:\n" + oAssociation.toString());
	                    //switch root and leaf
	                    oTPM = (clsThingPresentationMesh) oAssociation.getRootElement();
	                }
	                
	                moModuleBase.complementMesh(poOriginImage, 5); // 5 is an arbitrary value ... change as needed (lower values means that less is loaded) 
	            }
	        }
	    }
	    
		//=== Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				clsTester.getTester().exeTestAssociationAssignment(poOriginImage);
			} catch (Exception e) {
				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
			}
		}
	}
	
	/**
	 * After an image and its immediate neighbors have been loaded, check which images shall be activated
	 * 
	 * This is a recursive function
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 22:22:06
	 *
	 * @param poEnhancedOriginImage
	 * @param prPsychicEnergyIn
	 * @param poAlreadyActivatedImages
	 * @param pbDirectActivation 
	 * @return
	 */
	private ArrayList<clsPair<clsThingPresentationMesh, Double>> activateAssociatedImages(clsThingPresentationMesh poEnhancedOriginImage, double prPsychicEnergyIn, int pnMaximumDirectActivationValue, ArrayList<clsDriveMesh> poDrivesForFilteringList, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages, boolean pbDirectActivation) {
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, Double>>();
		log.trace("Calculate activation for " + poEnhancedOriginImage.getContent());
		
		//1. Get all unprocessed images. Only they will be used in the calculation
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oAssociatedUnprocessedImages = getUnprocessedImages(poEnhancedOriginImage, poAlreadyActivatedImages, pbDirectActivation);
		log.trace("Get unprocessed images" + oAssociatedUnprocessedImages.toString());
		
		ArrayList<PsychicSpreadActivationNode> oNodeTable = new ArrayList<PsychicSpreadActivationNode>();
		
		log.trace("Start activation of nodes");
		//2. go through each of them
		for (clsPair<clsThingPresentationMesh, Double> oPair : oAssociatedUnprocessedImages) {
			//Get weight
			double oAssWeight = oPair.b;
			//Get the Image itself
			clsThingPresentationMesh oImage = oPair.a;
			//Get the average affect of the image
			double oAffect = clsImportanceTools.calculateImageImportance(oImage, poDrivesForFilteringList);
			if (oAffect==0) {
				log.warn("WARN: An image has no importance: " + oImage.getContent() +  "QoA=" + oAffect);
			}
			
			//Calculate the psychic potential 
			double rPsychicPotential = calculatePsychicPotential(oPair.a, oAssWeight, oAffect, recognizedImageMultiplyFactor, preferredImages);
			//Get consume value
			double rConsumption = getEnergyConsumptionValue(oImage);
			//Calculate P
			double rP = calculateP(prPsychicEnergyIn, rConsumption, rPsychicPotential);
			//Calculate P + PsychicPotential
			double rDivider = rP + rPsychicPotential;
			
			//Add to the list, sort after psychic potential
			PsychicSpreadActivationNode oNode = new PsychicSpreadActivationNode();
			oNode.setBaseImage(oImage);
			oNode.setPsychicPotential(rPsychicPotential);
			oNode.setP(rP);
			oNode.setDivider(rDivider);
			oNode.setConsumptionValue(rConsumption);
			
			int i=0;
			while((oNodeTable.isEmpty()==false) && 
					(i<oNodeTable.size()) &&
					(oNodeTable.get(i).getPsychicPotential() > rPsychicPotential)) {
				i++;
			}
			oNodeTable.add(i, oNode);
			
			log.trace("Node: " + oPair.a.getContent() + "; Ass wght=" + oAssWeight + "; importance=" + oAffect + "; PsyPot=" + rPsychicPotential + "; consumption=" + rConsumption + "; tablepos=" + i);
		}
		
		//Go through the list a second time and activate
		double rAccumulatedSum = 0.0;
		int nBreakIndex = 0;
		for (int i=0;i<oNodeTable.size();i++) {
			PsychicSpreadActivationNode oNode = oNodeTable.get(i);
			
			//Calculate the accumulated sum
			rAccumulatedSum += oNode.getPsychicPotential();
			//oNode.setMrAccumulatedSum(rAccumulatedSum);
			
			
			
			
			//Check if P >=  accumulated sum
			if ((oNode.getP() >= rAccumulatedSum || (oNodeTable.size()==1 && oNode.getConsumptionValue()<=prPsychicEnergyIn)) && i < pnMaximumDirectActivationValue) {
				log.trace(oNode.toString() + " activated");
				nBreakIndex++;
			} else {
				log.trace("Stop activation");
				break;
			}
		}
		
		//calculate the energy distribution. Go through only the first ones, which will be activated
		for (int i=0;i<oNodeTable.size();i++) {
			PsychicSpreadActivationNode oNode = oNodeTable.get(i);
		
			if (i<nBreakIndex) {
				//Calculate how much energy the activated images shall get
				double rEnergyQuote = oNode.getPsychicPotential()/rAccumulatedSum;
				oNode.setMrEnergyQuote(rEnergyQuote);
				double rReceivedEnergy = rEnergyQuote * prPsychicEnergyIn;
				oNode.setMrAssignedPsychicEnergy(rReceivedEnergy);
				
				//Add the activated image to the already processed list
				poAlreadyActivatedImages.add(oNode.getMoBaseImage());
				
				//Put the activated images in the result
				oRetVal.add(new clsPair<clsThingPresentationMesh, Double>(oNode.getMoBaseImage(), oNode.getMrAssignedPsychicEnergy()));
			} else {
				oRetVal.add(new clsPair<clsThingPresentationMesh, Double>(oNode.getMoBaseImage(), 0.0));
			}	
		}
		
		return oRetVal;
	}
	
	/**
	 * Delete all inactivated associations from the origin image
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 22:52:11
	 *
	 * @param poOriginImage
	 * @param poActivationsList
	 * @return 
	 */
	private ArrayList<clsPair<clsThingPresentationMesh,Double>> deleteInactivatedAssociations(clsThingPresentationMesh poOriginImage, ArrayList<clsPair<clsThingPresentationMesh, Double>> poActivationsList) {
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, Double>>();
		
		for (clsPair<clsThingPresentationMesh, Double> oPair : poActivationsList) {
			//Delete all associations where no psychic energy is assigned
			if (oPair.b==0.0) {
				clsMeshTools.deleteAssociationInObject(poOriginImage, oPair.a);
				log.trace("Delete association " + oPair.a);
			} else {
				oRetVal.add(oPair);
			}
		}
		
		return oRetVal;
	}
	
	
	
	/**
	 * Formula for the psychic postential of an image
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 21:18:24
	 *
	 * @param prWeight
	 * @param prAffect
	 * @return
	 */
	private double calculatePsychicPotential(clsThingPresentationMesh sourceImage, double prWeight, double prAffect, double recognizedImageMultiplyFactor, ArrayList<clsThingPresentationMesh> preferredImages) {
		double result = 0;
		
		double reinforcementFactor = 1.0;
		//Check if this image is one of the preferred images
		for (clsThingPresentationMesh preferredImage :preferredImages) {
			if (sourceImage.getDS_ID()==preferredImage.getDS_ID()) {
				reinforcementFactor = recognizedImageMultiplyFactor;
				break;
			}
		}
		
		
		result = prWeight * (1 + Math.abs(prAffect)) * reinforcementFactor;
		
		return result;
	}
	
	/**
	 * Get the energy consumption of a certain image
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 21:37:22
	 *
	 * @param poEnhancedOriginImage
	 * @return
	 */
	private double getEnergyConsumptionValue(clsThingPresentationMesh poEnhancedOriginImage) {
		//TODO: This is a parameterizable value and not constant
		return moDefaultConsumeValue;
	}
	
	/**
	 * Calculate P (acc. theory)
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 21:41:24
	 *
	 * @param prPsychicEnergyIn
	 * @param prConsumptionEnergy
	 * @param prPsychicPotential
	 * @return
	 */
	private double calculateP(double prPsychicEnergyIn, double prConsumptionEnergy, double prPsychicPotential) {
		return prPsychicPotential * (prPsychicEnergyIn/prConsumptionEnergy - 1);
	}
	
	private ArrayList<clsPair<clsThingPresentationMesh, Double>> getUnprocessedImages(clsThingPresentationMesh poEnhancedOriginImage, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages, boolean pbDirectActivation) {
        ArrayList<clsPair<clsThingPresentationMesh, Double>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, Double>>();
        
        for (clsAssociation oAss : poEnhancedOriginImage.getExternalAssociatedContent()) {
            if (oAss instanceof clsAssociationPrimary) {
                //Get the other image, i. e. the leaf image of all association primary
                clsThingPresentationMesh oLeafImage = (clsThingPresentationMesh) oAss.getTheOtherElement(poEnhancedOriginImage);
                
                boolean bFound = false;
                //Check if the association is not already activated, check only the IDs
                for (clsThingPresentationMesh oAlreadyActivatedImages : poAlreadyActivatedImages) {
                    if (oAlreadyActivatedImages.getDS_ID()==oLeafImage.getDS_ID()) {
                        bFound=true;
                        break;

                    }
                }
                
                if (bFound==false) {
                    double rAssociationWeight = oAss.getMrWeight();
                    oRetVal.add(new clsPair<clsThingPresentationMesh, Double>(oLeafImage, rAssociationWeight));
                }
            }
        }
        
        return oRetVal;
    }

}
