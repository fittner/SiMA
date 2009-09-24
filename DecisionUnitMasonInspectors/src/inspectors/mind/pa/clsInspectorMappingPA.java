/**
 * clsInspectorMappingPA.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 */
package inspectors.mind.pa;

import pa.modules.C00_PsychicApparatus;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 * 
 */
public class clsInspectorMappingPA {

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 01:32:58
	 *
	 * @param moPA
	 * @param poModuleName
	 */
	public static TabbedInspector getPAInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, C00_PsychicApparatus moPA, String poModuleName) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		if(poModuleName.equals("E02NeurosymbolizationOfNeeds")) {
			oRetVal.addInspector( new clsE02InspectorInput(poSuperInspector, poWrapper, poState, moPA.moC01Body.moE02NeurosymbolizationOfNeeds), "Input");
			oRetVal.addInspector( new clsE02InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moC01Body.moE02NeurosymbolizationOfNeeds), "Output");
		}
		else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal.addInspector( new clsPAInspectorFuncModel(poSuperInspector, poWrapper, poState, moPA), "Functional Model");
			oRetVal.addInspector( new clsPAInspectorTopDown(poSuperInspector, poWrapper, poState, moPA), "Top-Down Design");
		}
		else if(poModuleName.equals("E26DecisionMaking")) {
			oRetVal.addInspector( new clsE26DecisionCalculation(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC16Deliberation.moE26DecisionMaking), "Decision Calculation");
		}
		else {
			
		}
		
		return oRetVal;
	}
	
}
