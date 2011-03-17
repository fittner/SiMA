/**
 * clsGenerateFunctionalModel.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 15:08:13
 */
package inspectors.mind.pa._v30.functionalmodel;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 15:08:13
 * 
 */
public class clsGenerateFunctionalModel {
	public static ArrayList<clsNode> getRootNodes() {
		ArrayList<clsNode> oRootNodes = new ArrayList<clsNode>();
		
		clsNode E1 = new clsNode(1, "Homeostases", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 0, 1, "");
		clsNode E2 = new clsNode(2, "Neurosymbolization of needs", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 1, 1, "");
		clsNode E3 = new clsNode(3, "Generation of drives", ePsychicInstance.ID, eInformationProcessingType.PRIMARY, 2, 1, "");
		clsNode E4 = new clsNode(4, "Fusion of drives", ePsychicInstance.ID, eInformationProcessingType.PRIMARY, 3, 1, "");
		clsNode E5 = new clsNode(5, "Generation of affects for drives", ePsychicInstance.ID, eInformationProcessingType.PRIMARY, 4, 1, "");
		clsNode E6 = new clsNode(6, "Defens mechanisms for drive contents", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 5, 1, "");
		clsNode E7 = new clsNode(7, "Super-Ego (unconscious)", ePsychicInstance.SUPEREGO, eInformationProcessingType.PRIMARY, 5, 2, "");
		clsNode E8 = new clsNode(8, "Conversion to secondary process", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 6, 1, "");
		clsNode E9 = new clsNode(9, "Knowledge about reality (unconscious)", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 4, 0, "");
		clsNode E10 = new clsNode(10, "Sensors environment", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 0, 3, "");
		clsNode E11 = new clsNode(11, "Neurosymbols environment", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 1, 3, "");
		clsNode E12 = new clsNode(12, "Sensors body", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 0, 5, "");
		clsNode E13 = new clsNode(13, "Neurosymbols body", ePsychicInstance.BODY, eInformationProcessingType.PRIMARY, 1, 5, "");
		clsNode E14 = new clsNode(14, "Preliminary external perception", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 2, 4, "");
		clsNode E15 = new clsNode(15, "Management of repressed contents", ePsychicInstance.ID, eInformationProcessingType.PRIMARY, 3, 4, "");
		clsNode E16 = new clsNode(16, "Management of memory traces", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 2, 2, "");
		clsNode E17 = new clsNode(17, "Fusion of external perception and memory traces", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 3, 2, "");
		clsNode E18 = new clsNode(18, "Generation of affects for perception", ePsychicInstance.ID, eInformationProcessingType.PRIMARY, 4, 2, "");
		clsNode E19 = new clsNode(19, "Defense mechanisms for perception", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 5, 3, "");
		clsNode E20 = new clsNode(20, "Inner perception (affects)", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 6, 3, "");
		clsNode E21 = new clsNode(21, "Conversion to secondary process", ePsychicInstance.EGO, eInformationProcessingType.PRIMARY, 6, 5, "");
		clsNode E22 = new clsNode(22, "Super-Ego (preconscious)", ePsychicInstance.SUPEREGO, eInformationProcessingType.SECONDARY, 7, 0, "");
		clsNode E23 = new clsNode(23, "External perception focues", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 7, 5, "");
		clsNode E24 = new clsNode(24, "Reality check", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 8, 4, "");
		clsNode E25 = new clsNode(25, "Knowledge about reality", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 8, 5, "");
		clsNode E26 = new clsNode(26, "Decision making", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 8, 2, "");
		clsNode E27 = new clsNode(27, "Generation of imaginary actions", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 9, 2, "");
		clsNode E28 = new clsNode(28, "Knowledge base (stored scenarios)", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 9, 1, "");
		clsNode E29 = new clsNode(29, "Evaluation of imaginary actions", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 9, 3, "");
		clsNode E30 = new clsNode(30, "Motility control", ePsychicInstance.EGO, eInformationProcessingType.SECONDARY, 10, 3, "");
		clsNode E31 = new clsNode(31, "Neurodesymbolization", ePsychicInstance.BODY, eInformationProcessingType.SECONDARY, 11, 3, "");
		clsNode E32 = new clsNode(32, "Actuators", ePsychicInstance.BODY, eInformationProcessingType.SECONDARY, 12, 3, "");

		E1.addNextModule(new clsConnection(1, 1, E2));
		E2.addNextModule(new clsConnection(1, 2, E3));
		E3.addNextModule(new clsConnection(1, 3, E4));
		E4.addNextModule(new clsConnection(1, 4, E5));
		E5.addNextModule(new clsConnection(1, 5, E6));
		E5.addNextModule(new clsConnection(1, 5, E7));
		E5.addNextModule(new clsConnection(1, 5, E9));
		E6.addNextModule(new clsConnection(1, 6, E8));
		E6.addNextModule(new clsConnection(4, 1, E15));
		E6.addNextModule(new clsConnection(5, 1, E20));
		E7.addNextModule(new clsConnection(3, 1, E6));
		E7.addNextModule(new clsConnection(3, 2, E19));
		E8.addNextModule(new clsConnection(1, 7, E22));
		E8.addNextModule(new clsConnection(1, 7, E23));
		E8.addNextModule(new clsConnection(1, 7, E26));
		E8.addNextModule(new clsConnection(5, 3, E20));
		E9.addNextModule(new clsConnection(6, 1, E6));
		E10.addNextModule(new clsConnection(2, 1, E11));
		E11.addNextModule(new clsConnection(2, 2, E14));
		E12.addNextModule(new clsConnection(2, 3, E13));
		E13.addNextModule(new clsConnection(2, 4, E14));
		E14.addNextModule(new clsConnection(2, 5, E15));
		E15.addNextModule(new clsConnection(2, 6, E16));
		E15.addNextModule(new clsConnection(4, 3, E6));
		E16.addNextModule(new clsConnection(2, 7, E17));
		E17.addNextModule(new clsConnection(2, 8, E18));
		E18.addNextModule(new clsConnection(2, 9, E7));
		E18.addNextModule(new clsConnection(2, 9, E19));
		E19.addNextModule(new clsConnection(2, 10, E21));
		E19.addNextModule(new clsConnection(4, 2, E15));
		E19.addNextModule(new clsConnection(5, 2, E20));
		E20.addNextModule(new clsConnection(5, 5, E26));
		E20.addNextModule(new clsConnection(5, 5, E29));
		E21.addNextModule(new clsConnection(2, 11, E22));
		E21.addNextModule(new clsConnection(2, 11, E23));
		E21.addNextModule(new clsConnection(5, 4, E20));
		E22.addNextModule(new clsConnection(3, 3, E26));
		E23.addNextModule(new clsConnection(2, 12, E24));
		E23.addNextModule(new clsConnection(2, 12, E25));
		E24.addNextModule(new clsConnection(2, 13, E26));
		E25.addNextModule(new clsConnection(6, 1, E24));
		E26.addNextModule(new clsConnection(7, 2, E28));
		E26.addNextModule(new clsConnection(7, 1, E27));
		E27.addNextModule(new clsConnection(7, 3, E29));
		E28.addNextModule(new clsConnection(6, 2, E27));
		E29.addNextModule(new clsConnection(7, 4, E30));
		E30.addNextModule(new clsConnection(8, 1, E31));
		E31.addNextModule(new clsConnection(8, 2, E32));
		
		oRootNodes.add(E1);
		oRootNodes.add(E10);
		oRootNodes.add(E12);
		
		return oRootNodes;
	}
}
