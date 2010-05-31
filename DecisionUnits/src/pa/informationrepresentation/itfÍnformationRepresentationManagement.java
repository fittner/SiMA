/**
 * itfÍnformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa.informationrepresentation;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureContainer;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * 
 */
public interface itfÍnformationRepresentationManagement {
	public ArrayList<clsDataStructureContainer> searchDataStructure(ArrayList<clsDataStructureContainer> poSearchPatternContainer);
}
