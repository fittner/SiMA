/**
 * clsHomeostaticMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 */
package pa.knowledgebasehandler.datatypes;

import pa.knowledgebasehandler.enums.eHomeostaticSources;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 * 
 */
public class clsHomeostaticMesh extends clsHomeostaticRepresentation{
	clsDriveDemand moDriveDemand; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:21:21
	 *
	 * @param poHomeostaticSource
	 */
	public clsHomeostaticMesh(eHomeostaticSources poHomeostaticSource, double pnDriveDemandIntensity) {
		super(poHomeostaticSource);
		moDriveDemand = new clsDriveDemand(pnDriveDemandIntensity); 
	}

}
