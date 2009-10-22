/**
 * clsDriveMesh.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 22.10.2009, 17:19:57
 */
package pa.datatypes;

import pa.enums.eDriveType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 22.10.2009, 17:19:57
 * 
 */
public class clsDriveMesh extends clsPrimaryInformationMesh {

	public eDriveType meDriveType;

	public eDriveType getDriveType() {
		return meDriveType;
	}

	public void setDriveType(eDriveType peDriveType) {
		this.meDriveType = peDriveType;
	}

	public clsDriveMesh(clsThingPresentationSingle poThingPresentationSingle) {
		super(poThingPresentationSingle);
	}

}
