/**
 * CHANGELOG
 *
 * 07.10.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import secondaryprocess.datamanipulation.clsMeshTools;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.10.2013, 21:10:43
 * 
 */
public class TestValues {
    public static void debugCheckDMQoAGrowing(clsThingPresentationMesh poTPM) throws Exception {
        
        
        for (clsThingPresentationMesh oTPM : clsMeshTools.getAllTPMObjects(poTPM, 5)) {
            ArrayList<clsAssociationDriveMesh> oDMList = new ArrayList<clsAssociationDriveMesh>();
            oDMList.addAll(clsMeshTools.getAllDMInMesh(oTPM));
            for (clsAssociationDriveMesh oDM : oDMList) {
                if (oDM.getDM().getQuotaOfAffect()>1.0) {
                    throw new Exception("Error in " + oTPM.getContent() + " in Drive mesh " + oDM.getRootElement() + ". QoA=" + oDM.getDM().getQuotaOfAffect() + " which is higher than allowed, i. e. the reference of the DM is bad");
                }
            }
        }
    }
}
