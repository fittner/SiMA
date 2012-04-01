/**
 * CHANGELOG
 *
 * 29.03.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.psychicspreadactivation;

import java.util.ArrayList;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;

import pa._v38.memorymgmt.psychicspreadactivation.clsPsychicSpreadActivationNode;
import pa._v38.tools.clsAffectTools;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 29.03.2012, 21:56:28
 * 
 */
public class clsPsychicSpreadActivation {


	public static clsThingPresentationMesh startSpreadActivation(clsThingPresentationMesh poImage, double prPsychicEnergyIn, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages) {
		clsThingPresentationMesh oRetVal = poImage;
		
		//1. Get level 1 of the image associations
		if (poImage.getMoContentType().equals(eContentType.RI.toString())==true) {
			oRetVal = getAssociatedImagesMemory(poImage);
			//TODO
		} else if (poImage.getMoContentType().equals(eContentType.PI.toString())==true) {
			oRetVal = getAssociatedImagesPercption(poImage);
			//TODO
		}
		
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oPossibleActivationList = activateAssociatedImages(poImage, prPsychicEnergyIn, poAlreadyActivatedImages);
		
		deleteInactivatedAssociations(poImage, oPossibleActivationList);
		
		
		
		
		return oRetVal;
	}
	
//	private static ArrayList<clsPair<clsThingPresentationMesh, Double>> processImage(clsThingPresentationMesh poEnhancedOriginImage, double prPsychicEnergyIn, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages) {
//		ArrayList<clsPair<clsThingPresentationMesh, Double>>
//	}
	
	/**
	 * XXXX
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 22:56:58
	 *
	 * @param poOriginImage
	 * @return
	 */
	public static clsThingPresentationMesh getAssociatedImagesPercption(clsThingPresentationMesh poOriginImage) {
		
		//TODO: load images
		
		clsThingPresentationMesh oRetVal = poOriginImage;
		
		return oRetVal;
	}
	
	public static clsThingPresentationMesh getAssociatedImagesMemory(clsThingPresentationMesh poOriginImage) {
		clsThingPresentationMesh oRetVal = poOriginImage;
		
		return oRetVal;
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
	 * @return
	 */
	public static ArrayList<clsPair<clsThingPresentationMesh, Double>> activateAssociatedImages(clsThingPresentationMesh poEnhancedOriginImage, double prPsychicEnergyIn, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages) {
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, Double>>();
		
		//1. Get all unprocessed images. Only they will be used in the calculation
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oAssociatedUnprocessedImages = getUnprocessedImages(poEnhancedOriginImage, poAlreadyActivatedImages);
		
		ArrayList<clsPsychicSpreadActivationNode> oNodeTable = new ArrayList<clsPsychicSpreadActivationNode>();
		
		//2. go through each of them
		for (clsPair<clsThingPresentationMesh, Double> oPair : oAssociatedUnprocessedImages) {
			//Get weight
			double oAssWeight = oPair.b;
			//Get the Image itself
			clsThingPresentationMesh oImage = oPair.a;
			//Get the average affect of the image
			double oAffect = clsAffectTools.calculateAbsoluteAffect(oImage, new ArrayList<clsDriveMesh>());
			//Calculate the psychic potential 
			double rPsychicPotential = calculatePsychicPotential(oAssWeight, oAffect);
			//Get consume value
			double rConsumption = getEnergyConsumptionValue(oImage);
			//Calculate P
			double rP = calculateP(prPsychicEnergyIn, rConsumption, rPsychicPotential);
			//Calculate P + PsychicPotential
			double rDivider = rP + rPsychicPotential;
			
			//Add to the list, sort after psychic potential
			clsPsychicSpreadActivationNode oNode = new clsPsychicSpreadActivationNode();
			oNode.setMoBaseImage(oImage);
			oNode.setMrPsychicPotential(rPsychicPotential);
			oNode.setMrP(rP);
			oNode.setMrDivider(rDivider);
			
			int i=0;
			while((oNodeTable.isEmpty()==false) && 
					(i<oNodeTable.size()) &&
					(oNodeTable.get(i).getMrPsychicPotential() < rPsychicPotential)) {
				i++;
			}
			oNodeTable.add(i, oNode);
		}
		
		//Go through the list a second time and activate
		double rAccumulatedSum = 0.0;
		int nBreakIndex = 0;
		for (int i=0;i<oNodeTable.size();i++) {
			clsPsychicSpreadActivationNode oNode = oNodeTable.get(i);
			
			//Calculate the accumulated sum
			rAccumulatedSum += oNode.getMrPsychicPotential();
			//oNode.setMrAccumulatedSum(rAccumulatedSum);
			
			//Check if P >=  accumulated sum
			if (oNode.getMrP() >= rAccumulatedSum) {
				//oNode.setMbActivateable(true);
				nBreakIndex++;
			} else {
				break;
			}
		}
		
		//calculate the energy distribution. Go through only the first ones, which will be activated
		for (int i=0;i<oNodeTable.size();i++) {
			clsPsychicSpreadActivationNode oNode = oNodeTable.get(i);
		
			if (i<=nBreakIndex) {
				//Calculate how much energy the activated images shall get
				double rEnergyQuote = oNode.getMrPsychicPotential()/rAccumulatedSum;
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
	 */
	private static void deleteInactivatedAssociations(clsThingPresentationMesh poOriginImage, ArrayList<clsPair<clsThingPresentationMesh, Double>> poActivationsList) {
		ArrayList<clsThingPresentationMesh> oRemoveStructues = new ArrayList<clsThingPresentationMesh>();
		
		for (clsPair<clsThingPresentationMesh, Double> oPair : poActivationsList) {
			//Delete all associations where no psychic energy is assigned
			if (oPair.b==0.0) {
				clsDataStructureTools.deleteAssociationInObject(poOriginImage, oPair.a);
			}
		}
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
	static double calculatePsychicPotential(double prWeight, double prAffect) {
		return prWeight * (1 + Math.abs(prAffect));
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
	static double getEnergyConsumptionValue(clsThingPresentationMesh poEnhancedOriginImage) {
		return 0.2;
	}
	
	/**
	 * Calculate P (acc. theory)
	 * 
	 * (wendt)
	 *
	 * @since 30.03.2012 21:41:24
	 *
	 * @param prPsychicEnergyIn
	 * @param prConsumption
	 * @param prPsychicPotential
	 * @return
	 */
	static double calculateP(double prPsychicEnergyIn, double prConsumption, double prPsychicPotential) {
		return prPsychicPotential * (prPsychicEnergyIn/prConsumption - 1);
	}
	
	static ArrayList<clsPair<clsThingPresentationMesh, Double>> getUnprocessedImages(clsThingPresentationMesh poEnhancedOriginImage, ArrayList<clsThingPresentationMesh> poAlreadyActivatedImages) {
		ArrayList<clsPair<clsThingPresentationMesh, Double>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, Double>>();
		
		for (clsAssociation oAss : poEnhancedOriginImage.getExternalMoAssociatedContent()) {
			if (oAss instanceof clsAssociationPrimary) {
				//Get the other image, i. e. the leaf image of all association primary
				clsThingPresentationMesh oLeafImage = (clsThingPresentationMesh) oAss.getTheOtherElement(poEnhancedOriginImage);
				
				//Check if the association is not already activated
				if (poAlreadyActivatedImages.contains(oLeafImage)==false) {
					double rAssociationWeight = oAss.getMrWeight();
					
					oRetVal.add(new clsPair<clsThingPresentationMesh, Double>(oLeafImage, rAssociationWeight));
				}
			}
		}
		
		return oRetVal;
	}

}
