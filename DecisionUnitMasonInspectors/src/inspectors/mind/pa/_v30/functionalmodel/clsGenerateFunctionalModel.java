/**
 * clsGenerateFunctionalModel.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 15:08:13
 */
package inspectors.mind.pa._v30.functionalmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa._v30.tools.clsPair;
import pa._v30.interfaces.eInterfaces;
import pa._v30.modules.clsModuleBase;
import pa._v30.modules.clsPsychicApparatus;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 15:08:13
 * 
 */
public class clsGenerateFunctionalModel {
	public static ArrayList<clsNode> getRootNodes(clsPsychicApparatus moPA) {
		ArrayList<clsNode> oRootNodes = new ArrayList<clsNode>();
		HashMap<Integer, clsNode> oNodes = new HashMap<Integer, clsNode>();
		
		int x = 0;
		for (Map.Entry<Integer, clsModuleBase> e:moPA.moModules.entrySet()) {
			Integer oKey = e.getKey();
			clsModuleBase oModule = e.getValue();
			
			int col = x % 12;
			int row = (int) Math.floor( x/12 );
			x++;
			
			clsNode oNode = new clsNode(oKey, col, row, oModule);
			oNodes.put(oKey, oNode);
		}
		
		clsGenerateFunctionalModel.beautify(oNodes);
		
		for (Map.Entry<Integer, clsNode> e:oNodes.entrySet()) {
			clsNode oNode = e.getValue();
			Integer oKey = e.getKey();
			
			for (clsPair<eInterfaces,Integer> oPair:moPA.moInterfaceMesh.get(oKey)) {
				oNode.addNextModule( new clsConnection( oPair.a, oNodes.get(oPair.b) ) );
			}
		}
		
		oRootNodes.add( oNodes.get(1) );
		oRootNodes.add( oNodes.get(10) );
		oRootNodes.add( oNodes.get(12) );
		oRootNodes.add( oNodes.get(39) );
		
		return oRootNodes;
	}
	
	private static void beautify(HashMap<Integer, clsNode> oNodes) {
		((clsNode)(oNodes.get(39))).setCoords(0, 0);		
		((clsNode)(oNodes.get(40))).setCoords(1, 0);
		((clsNode)(oNodes.get(41))).setCoords(2, 0);
		((clsNode)(oNodes.get(42))).setCoords(4, 0);
		((clsNode)(oNodes.get(43))).setCoords(3, 0);
		((clsNode)(oNodes.get(44))).setCoords(5, 0);
		
		((clsNode)(oNodes.get(1))).setCoords(0, 1);
		((clsNode)(oNodes.get(2))).setCoords(1, 1);
		((clsNode)(oNodes.get(3))).setCoords(2, 1);
		((clsNode)(oNodes.get(4))).setCoords(3, 1);
		((clsNode)(oNodes.get(5))).setCoords(4, 1);
		((clsNode)(oNodes.get(38))).setCoords(5, 1);

		((clsNode)(oNodes.get(9))).setCoords(6, 1);
		((clsNode)(oNodes.get(7))).setCoords(6, 2);
		
		((clsNode)(oNodes.get(10))).setCoords(0, 2);
		((clsNode)(oNodes.get(11))).setCoords(1, 2);
		((clsNode)(oNodes.get(12))).setCoords(0, 3);
		((clsNode)(oNodes.get(13))).setCoords(1, 3);
		
		((clsNode)(oNodes.get(14))).setCoords(2, 3);		
		((clsNode)(oNodes.get(46))).setCoords(3, 3);		
		((clsNode)(oNodes.get(37))).setCoords(4, 3);
		((clsNode)(oNodes.get(35))).setCoords(5, 3);
		((clsNode)(oNodes.get(45))).setCoords(5, 2);
		((clsNode)(oNodes.get(18))).setCoords(6, 3);
		
		((clsNode)(oNodes.get(6))).setCoords(7, 0);
		((clsNode)(oNodes.get(36))).setCoords(7, 1);
		((clsNode)(oNodes.get(19))).setCoords(7, 2);
		
		((clsNode)(oNodes.get(8))).setCoords(8, 0);
		((clsNode)(oNodes.get(20))).setCoords(8, 1);
		((clsNode)(oNodes.get(21))).setCoords(8, 2);
		((clsNode)(oNodes.get(47))).setCoords(8, 4);
			
		((clsNode)(oNodes.get(23))).setCoords(9, 0);
		((clsNode)(oNodes.get(25))).setCoords(10, 0);
		((clsNode)(oNodes.get(24))).setCoords(10, 1);
		
		((clsNode)(oNodes.get(22))).setCoords(9, 2);
		((clsNode)(oNodes.get(26))).setCoords(10, 2);
		
		((clsNode)(oNodes.get(27))).setCoords(11, 2);
		((clsNode)(oNodes.get(28))).setCoords(11, 1);

		((clsNode)(oNodes.get(33))).setCoords(12, 1);
		((clsNode)(oNodes.get(34))).setCoords(12, 0);
		
		((clsNode)(oNodes.get(29))).setCoords(12, 2);		
		
		((clsNode)(oNodes.get(30))).setCoords(13, 2);
		((clsNode)(oNodes.get(31))).setCoords(14, 2);
		((clsNode)(oNodes.get(32))).setCoords(15, 2);
		
	}
}
