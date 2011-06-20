/**
 * clsDriveMesh.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 22.10.2009, 17:19:57
 */
package pa._v19.datatypes;

import pa._v19.enums.eDriveType;

/**
 * 
 * @author langr
 * 22.10.2009, 17:19:57
 * 
 */
@Deprecated
public class clsDriveMesh extends clsPrimaryInformationMesh {

	public eDriveType meDriveType;
	public clsDriveMesh moCounterDrive;

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
