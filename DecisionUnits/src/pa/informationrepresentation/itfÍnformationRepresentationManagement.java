/**
 * itf�nformationRepresentationManagement.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 */
package pa.informationrepresentation;

import java.util.List;

import pa.informationrepresentation.datatypes.clsDataStructureContainer;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 30.05.2010, 12:47:34
 * 
 */
public interface itf�nformationRepresentationManagement {
	public List<clsDataStructureContainer> searchDataStructure(String poReturnType, List<clsDataStructureContainer> poSearchPatternContainer);
}
