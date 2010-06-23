/**
 * clsHomeostaticRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.enums.eHomeostaticSources;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 * 
 */
public abstract class clsHomeostaticRepresentation extends clsPrimaryDataStructure{
	protected String moHomeostaticSource; 	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:57:01
	 *
	 */
	public clsHomeostaticRepresentation(eHomeostaticSources poHomeostaticSource, 
										String poAssociationID, 
										eDataType peAssociationType) {
		
		super(poAssociationID, peAssociationType); 
		moHomeostaticSource = poHomeostaticSource.toString(); 
	}
	
}
