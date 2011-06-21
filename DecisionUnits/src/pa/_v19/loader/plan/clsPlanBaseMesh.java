/**
 * clsScenarioBaseMesh.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 */
package pa._v19.loader.plan;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.datatypes.clsAssociation;
import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsSecondaryInformationMesh;
import pa._v19.datatypes.clsWordPresentation;
import pa._v19.tools.clsPair;

/**
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 * 
 */
@Deprecated
public class clsPlanBaseMesh extends clsSecondaryInformationMesh{

	public HashMap<Integer, clsPlanStateMesh> moStates;
	public int mnCurrentState = 0;

	public clsPlanBaseMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);

		moStates = new HashMap<Integer, clsPlanStateMesh>(); 

	}

	/**
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
		
		if(mnCurrentState < 0) {	//reset the plan state in case of already finished plan
			mnCurrentState = 0;
		}
		
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
