/**
 * clsScenarioBaseMesh.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 13:06:33
 */
package pa._v19.loader.scenario;

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
public class clsScenarioBaseMesh extends clsSecondaryInformationMesh{

	public HashMap<Integer, clsScenarioStateMesh> moStates;
	public int mnCurrentState = 0;

	public clsScenarioBaseMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);

		moStates = new HashMap<Integer, clsScenarioStateMesh>(); 

	}

	/**
	 *
	 * @author langr
	 * 25.10.2009, 13:08:40
	 *
	 * @param scenarioMesh
	 */
	public void addState(clsScenarioStateMesh poScenarioMesh) {

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
	public double process(
			HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateImageResult) {

		double oRetVal = 0;
		
		clsScenarioStateMesh oCurrentState = moStates.get(mnCurrentState);
		for( clsAssociation<clsSecondaryInformation> oAssoc : oCurrentState.moAssociations) {
			if( oAssoc.moElementB instanceof clsScenarioTransition ){
				clsScenarioTransition oTrans = (clsScenarioTransition)oAssoc.moElementB;
				if( poTemplateImageResult.containsKey( oTrans.moWP.moContent ) &&
					poTemplateImageResult.get(oTrans.moWP.moContent).b	>= oTrans.mrMatch) {
					mnCurrentState = oTrans.mnTargetId;
					break;
				}
			}
		}
		if(mnCurrentState < 0) {
			oRetVal = 1;
			mnCurrentState++;
		}
		else if(moStates.size() > 0) {
			oRetVal = (double)(mnCurrentState+1) / (double)moStates.size();
		}
		return oRetVal;
	}
}
