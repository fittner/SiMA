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
		eXPosition X = null;
		eYPosition Y = null;
		ArrayList<clsAssociation> oDSAssList = poImageContainer.getMoAssociatedDataStructures(poDS);
		for (clsAssociation oAss : oDSAssList) {
			if (oAss instanceof clsAssociationAttribute) {
				//Get content of the association
				String oContent = (String) ((clsThingPresentation)oAss.getLeafElement()).getMoContent();
				if ((X!=null) && (Y!=null)) {
					oRetVal = new clsPair<Double, Double>((double)X.mnPos, (double)Y.mnPos);
					break;
				} else {
					if (X==null) {
						X = eXPosition.valueOf(oContent);
					}
					
					if (Y==null) {
						Y = eYPosition.valueOf(oContent);
					}
				}
			}
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
	public static clsAssociationTime createDistanceAssociation(clsPrimaryDataStructure poElementA, clsPrimaryDataStructure poElementB, double prDistance) {
		double prAssWeight = calculateAssociationWeightFromDistance(prDistance);
		clsAssociationTime oRetVal = new clsAssociationTime(new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONTEMP, "RELATIONASSOCIATION"), poElementA, poElementB);
		oRetVal.setMrWeight(prAssWeight);
		return oRetVal;
	}

}
