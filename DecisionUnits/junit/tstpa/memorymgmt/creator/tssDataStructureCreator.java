/**
 * tssDataStructureCreator.java: DecisionUnits - tstpa.memorymgmt.creator
 * 
 * @author zeilinger
 * 12.08.2010, 09:27:57
 */
package tstpa.memorymgmt.creator;

import java.util.ArrayList;

import org.junit.Test;

import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 12.08.2010, 09:27:57
 * 
 */
public class tssDataStructureCreator {
	@Test
	public void tssCreate(){
		clsDataStructureGenerator.generateDataStructure(eDataType.DM, new clsTripple<String, Object, Object>("TEST", new ArrayList<clsThingPresentation>(), "test1"));
	}
}
