/**
 * clsScenarioBaseMesh.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 */
package pa.loader.plan;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.datatypes.clsWordPresentation;
import pa.tools.clsPair;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 * 
 */
public class clsPlanBaseMesh extends clsSecondaryInformationMesh{

	public HashMap<Integer, clsPlanStateMesh> moStates;
	public int mnCurrentState = 0;

	public clsPlanBaseMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);

		moStates = new HashMap<Integer, clsPlanStateMesh>(); 

	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 13:08:40
	 *
	 * @param scenarioMesh
	 */
	public void addState(clsPlanStateMesh poScenarioMesh) {

		moStates.put(poScenarioMesh.mnId, poScenarioMesh);
		moAssociations.add(new clsAssociation<clsSecondaryInformation>(this, poScenarioMesh));

	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 15:19:55
	 *
	 * @param poTemplateImageResult
	 * @return
	 */
	public ArrayList<clsPlanAction> process(
			HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateImageResult) {

		ArrayList<clsPlanAction> oRetVal = new ArrayList<clsPlanAction>();
		
		clsPlanStateMesh oCurrentState = moStates.get(mnCurrentState);
		for( clsAssociation<clsSecondaryInformation> oAssoc : oCurrentState.moAssociations) {
			if( oAssoc.moElementB instanceof clsPlanTransition ){
				clsPlanTransition oTrans = (clsPlanTransition)oAssoc.moElementB;
				if( poTemplateImageResult.containsKey( oTrans.moWP.moContent ) &&
					poTemplateImageResult.get(oTrans.moWP.moContent).b	>= oTrans.mrMatch) {
					mnCurrentState = oTrans.mnTargetId;
					break;
				}
			}
		}
		
		for( clsAssociation<clsSecondaryInformation> oAssoc : moStates.get(mnCurrentState).moAssociations ) {
			if(oAssoc.moElementB instanceof clsPlanAction) {
				oRetVal.add((clsPlanAction)oAssoc.moElementB);
			}
		}

		return oRetVal;
	}
}
