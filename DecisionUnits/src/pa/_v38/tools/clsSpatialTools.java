/**
 * CHANGELOG
 *
 * 01.10.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eXPosition;
import pa._v38.memorymgmt.enums.eYPosition;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2011, 09:16:51
 * 
 */
public class clsSpatialTools {
	
	
	
	/**
	 * Get the position coordinates in X, Y intergers from thing presentations of a data structure in a container
	 * (wendt)
	 *
	 * @since 01.10.2011 09:50:49
	 *
	 * @param poDS
	 * @param poImageContainer
	 * @return
	 */
	public static clsPair<Double, Double> getPosition(clsDataStructurePA poDS, clsPrimaryDataStructureContainer poImageContainer) {
		clsPair<Double, Double> oRetVal = null;
		
		//Search for xy compontents
		int X = -10;	//default error value
		int Y = -10;
		ArrayList<clsAssociation> oDSAssList = poImageContainer.getMoAssociatedDataStructures(poDS);
		for (clsAssociation oAss : oDSAssList) {
			if (oAss instanceof clsAssociationAttribute && oAss.getLeafElement().getMoContentType().equals("LOCATION")) {
				//Get content of the association
				String oContent = (String) ((clsThingPresentation)oAss.getLeafElement()).getMoContent();
				if (X==-10) {
					X = eXPosition.getValue(oContent);
					continue;
				}
				
				if (Y==-10) {
					Y = eYPosition.getValue(oContent);
					continue;
				}
			}
		}
		
		if ((X!=-10) && (Y!=-10)) {
			oRetVal = new clsPair<Double, Double>((double)X, (double)Y);
		}
		
		return oRetVal;
	}
	
	/**
	 * Return the distance between tow coordinates (x1, y1) - (x2, y2)
	 * (wendt)
	 *
	 * @since 01.10.2011 09:57:36
	 *
	 * @param prX
	 * @param prY
	 * @return
	 */
	public static double getDistance(double prX1, double prY1, double prX2, double prY2) {
		return Math.sqrt(Math.pow(prX1-prX2, 2) + Math.pow(prY1-prY2, 2));
	}
	
	/**
	 * Calculate a normalized association weight from the distance. The higher the distance, the lower the weight
	 *
	 * WENDT
	 * @since 01.10.2011 10:00:49
	 *
	 * @param prDistance
	 * @return
	 */
	private static double calculateAssociationWeightFromDistance(double prDistance) {
		return 1/(1+prDistance);
	}
	
	/**
	 * Create a temporal association between the objects in the image
	 * (wendt)
	 *
	 * @since 01.10.2011 10:07:38
	 *
	 * @param poElementA
	 * @param poElementB
	 * @param prDistance
	 * @return
	 */
	private static clsAssociationTime createDistanceAssociation(clsPrimaryDataStructure poElementA, clsPrimaryDataStructure poElementB, double prDistance) {
		double prAssWeight = calculateAssociationWeightFromDistance(prDistance);
		clsAssociationTime oRetVal = new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "RELATIONASSOCIATION"), poElementA, poElementB);
		oRetVal.setMrWeight(prAssWeight);
		return oRetVal;
	}
	
	/**
	 * Add associations between all objects in the image to the image. The distance between the objects is used as association
	 * weights. In this way, it is possible to identify patterns and to recognize images, with similar patterns
	 * (wendt)
	 *
	 * @since 01.10.2011 22:41:58
	 *
	 * @param poInputContainer
	 * @param pbTemplateSetting
	 */
	public static void addRelationAssociations(clsPrimaryDataStructureContainer poInputContainer, boolean pbTemplateSetting) {
		//Compare all elements in the container with each other and add distance associations
		if ((poInputContainer.getMoDataStructure() instanceof clsTemplateImage)==false) {
			try {
				throw new Exception("Error in clsSpatialTools, addRelationAssociations: Input data type not allowed. Only TI allowed");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<clsAssociation> oTimeAssList = ((clsTemplateImage)poInputContainer.getMoDataStructure()).getMoAssociatedContent();
		//Go through all elements in the image and calculate their positions
		ArrayList<clsPair<clsPrimaryDataStructure , clsPair<Double, Double>>> oPositionList = new ArrayList<clsPair<clsPrimaryDataStructure , clsPair<Double, Double>>>();
		for (clsAssociation oAss : oTimeAssList) {
			//Get no Empty space objects
			clsPrimaryDataStructure  oElement = (clsPrimaryDataStructure) oAss.getLeafElement();
			clsPair<Double, Double> oElementPosition = clsSpatialTools.getPosition(oElement, poInputContainer);
			if (oElementPosition!=null) {
				oPositionList.add(new clsPair<clsPrimaryDataStructure , clsPair<Double, Double>>(oElement, oElementPosition));
			}
		}
		
		//Get all distances between those positions
		for (int i=0;i<oPositionList.size();i++) {
			clsPrimaryDataStructure oElementA = oPositionList.get(i).a;
			for(int j=i+1;j<oPositionList.size();j++) {
				clsPrimaryDataStructure oElementB = (clsPrimaryDataStructure) oPositionList.get(j).a;
				//get distance
				double rDistance = getDistance(oPositionList.get(i).b.a, oPositionList.get(i).b.b, oPositionList.get(j).b.a, oPositionList.get(j).b.b);
				//create association
				clsAssociationTime oNewDistanceAss = createDistanceAssociation(oElementA, oElementB, rDistance);
				//Add this association to the container
				poInputContainer.addMoAssociatedDataStructure(oNewDistanceAss);
			}
		}
	}

}
