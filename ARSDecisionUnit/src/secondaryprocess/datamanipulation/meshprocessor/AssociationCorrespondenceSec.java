/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package secondaryprocess.datamanipulation.meshprocessor;

import java.util.ArrayList;

import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 08.10.2013, 09:45:20
 * 
 */
public class AssociationCorrespondenceSec {

    private final clsAssociationSecondary fromAss;
    private final clsSecondaryDataStructure fromRootElement;
    private final clsSecondaryDataStructure fromLeafElement;
    
    private clsSecondaryDataStructure toRootElement;
    private clsSecondaryDataStructure toLeafElement;
    
    private int score;
    
    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 08.10.2013 09:54:14
     *
     * @param fromAss
     */
    public AssociationCorrespondenceSec(clsAssociationSecondary fromAss) {
        this.fromAss = fromAss;
        this.fromRootElement = (clsSecondaryDataStructure) this.fromAss.getRootElement();
        this.fromLeafElement = (clsSecondaryDataStructure) this.fromAss.getLeafElement();
        
        //this.toLeafElement = findWPMInList((clsSecondaryDataStructure) fromLeafElement, correspondigMeshList); 
        //this.toRootElement = findWPMInList((clsSecondaryDataStructure) fromRootElement, correspondigMeshList); 
        
//        setScore(0);
//        if (toLeafElement!=null) {
//            setScore(getScore() + 1);
//        }
        
//        if (toRootElement!=null) {
//            setScore(getScore() + 1);
//        }
        
    }
    
    private clsSecondaryDataStructure findWPMInList(clsSecondaryDataStructure searchWPM, ArrayList<clsSecondaryDataStructure> correspondigMeshList) {
        clsSecondaryDataStructure result = null;
        
        for (clsSecondaryDataStructure wpm :  correspondigMeshList) {
            if (searchWPM.isEquivalentDataStructure(wpm)==true) {
                result = wpm;
                break;
            }
        }
        
        return result;
    }

    /**
     * @since 08.10.2013 10:10:21
     * 
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public void transferElements(ArrayList<clsSecondaryDataStructure> correspondigMeshList) {
        //ArrayList<clsSecondaryDataStructure> newCorrespondingList = new ArrayList<clsSecondaryDataStructure>(correspondigMeshList);
        
        //Renew corresponding list
        this.toLeafElement = findWPMInList((clsSecondaryDataStructure) fromLeafElement, correspondigMeshList);
        if (this.toLeafElement==null) {
            correspondigMeshList.add(fromLeafElement);
        } else {
            if (toLeafElement instanceof clsWordPresentationMesh && fromLeafElement instanceof clsWordPresentationMesh) {
                clsMeshTools.moveAssociation((clsWordPresentationMesh)toLeafElement, (clsWordPresentationMesh)fromLeafElement, this.fromAss, true);
            }
        }
        
        this.toRootElement = findWPMInList((clsSecondaryDataStructure) fromRootElement, correspondigMeshList);
        if (toRootElement==null) {
            correspondigMeshList.add(fromRootElement);
        } else {
            if (toRootElement instanceof clsWordPresentationMesh && fromRootElement instanceof clsWordPresentationMesh) {
                clsMeshTools.moveAssociation((clsWordPresentationMesh)toRootElement, (clsWordPresentationMesh)fromRootElement, this.fromAss, true);
            }
        }
        
        //return newCorrespondingList;
    }
    
    
    
    
}
