/**
 * CHANGELOG
 *
 * 07.10.2013 wendt - File created
 *
 */
package secondaryprocess.datamanipulation.meshprocessor;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.TestException;
import testfunctions.meshtester.clsTestDataStructureConsistency;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.10.2013, 20:33:01
 * 
 */
public class MeshProcessor implements MeshProcessorInterface {
    
    private boolean isSafeMode = true;
    private static final Logger log = clsLogger.getLog("Tools");
    
    
    /**
     * Merge 2 meshes. Only WPM are allowed. Move all associations from the new mesh to the source mesh
     * 
     * (wendt)
     *
     * @since 31.01.2012 20:06:35
     *
     * @param poSourceTPM
     * @param poNewMesh
     */
    @Override
    public void complementMesh(clsWordPresentationMesh poSourceMesh, clsWordPresentationMesh poNewMesh) {
       
        //Get all associations from both meshes
        ArrayList<clsAssociationSecondary> toMeshAssociations = clsMeshTools.getAllAssociationSecondaryInMesh(poSourceMesh, 10);
        ArrayList<clsAssociationSecondary> fromMeshAssociations = clsMeshTools.getAllAssociationSecondaryInMesh(poNewMesh, 10);
        
        if (this.isSafeMode==true) {
            //Check if -1 in one of the meshes
            //Check if the same ID occurs twice in the meshes
            //Check that every association is existent at both elements
            
            try {
                this.executeSetupTests(poNewMesh);
            } catch (Exception e) {
               log.error("", e);
            }
        }
       
        //Find the Datatype ID in the meshes
        //Create process pairs Source and New
        //Check if ElementA of the fromMesh in the toMesh exists
        //Check if ElementB of the fromMesh in the toMesh exists
        //If ID exists, do nothing
        ArrayList<AssociationCorrespondence> evaluatedUnsortedAssocitations;
        try {
            evaluatedUnsortedAssocitations = evaluateAllAssociations(fromMeshAssociations, toMeshAssociations);
            
            //If A && B exists, transfer both elements
            //If A || B exists but not A && B => transfer A, leave B, add B to list of existing elements and the same if A
            //If !A && !B => then add A and B to list of toMesh. This should never be the case, therefore this case throws exception
            processAssociations(evaluatedUnsortedAssocitations, poSourceMesh);
        } catch (Exception e) {
            log.error("", e);
        }
        
        
        //If ID does not exists

        //Sort the fromMesh list for number of occurences 0, 1 or 2
        
       
       if (this.isSafeMode==true) {
           //Check if in the toMesh every element only occurs once, never 2 times the same ID
           try {
            this.executeTearDownTests(poSourceMesh);
        } catch (Exception e) {
            log.error("", e);
        }
           
       }
    }
    
    private void executeSetupTests(clsWordPresentationMesh poMesh) throws Exception {
        try {
            clsTestDataStructureConsistency.debugFindAllErroneousLinksInDataStructure(poMesh);
            clsTestDataStructureConsistency.testNoLooseAssociations(poMesh);
            clsTestDataStructureConsistency.testCheckIfMeshHasErroneousID(poMesh);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    private void executeTearDownTests(clsWordPresentationMesh poMesh) throws Exception {
        try {
            clsTestDataStructureConsistency.testCheckIfMultipleIDsLoaded(poMesh);
            clsTestDataStructureConsistency.testNoLooseAssociations(poMesh);
            ArrayList<clsAssociationSecondary> toMeshAssociations = clsMeshTools.getAllAssociationSecondaryInMesh(poMesh, 10);
        } catch (TestException e) {
            throw new Exception(e.getMessage());
        }
        
       
    }
    
    private clsAssociationSecondary getEquivalentAssociation(clsAssociationSecondary ass, ArrayList<clsAssociationSecondary> list) {
        clsAssociationSecondary result = null;
        for (clsAssociationSecondary compareass : list) {
            if (ass.isEquivalentDataStructure(compareass)==true) {
                result=ass;
                break;
            }
        }
        
        return result;
    }
    
    private void processAssociations(ArrayList<AssociationCorrespondence> evaluatedUnsortedAssocitations, clsWordPresentationMesh poSourceMesh) {
        //Get all existsing datastructures
        ArrayList<clsSecondaryDataStructure> toMeshSecondaryDataStructure = clsMeshTools.getAllSecondaryDataStructureObjects(poSourceMesh, 10);
        
        for (AssociationCorrespondence assCorr : evaluatedUnsortedAssocitations) {
            assCorr.transferElements(toMeshSecondaryDataStructure);
        }
        
    }
    
    /**
     * Evaluate all associations of the fromMesh
     *
     * @author wendt
     * @since 08.10.2013 11:23:20
     *
     * @param fromAssociations
     * @param toAssociations
     * @param toSecondaryDataStructureList
     * @return
     * @throws Exception
     */
    private ArrayList<AssociationCorrespondence> evaluateAllAssociations(ArrayList<clsAssociationSecondary> fromAssociations, ArrayList<clsAssociationSecondary> toAssociations) throws Exception {
        ArrayList<AssociationCorrespondence> result = new ArrayList<AssociationCorrespondence>();
        
        for (clsAssociationSecondary fromAss : fromAssociations) {
            try {
                AssociationCorrespondence assCorrespondence = evaluateAssociation(fromAss, toAssociations);
                if (assCorrespondence!=null) {
                    result.add(assCorrespondence);
                }
                
                
            } catch (Exception e) {
                throw new Exception("Error in the assignment of associations");
            }
        }
        
        return result;
    }
    
    /**
     * Evaluate single association
     *
     * @author wendt
     * @since 08.10.2013 11:21:50
     *
     * @param ass
     * @param compareAssociation
     * @param secondaryDataStructureList
     * @return
     * @throws Exception
     */
    private AssociationCorrespondence evaluateAssociation(clsAssociationSecondary ass, ArrayList<clsAssociationSecondary> compareAssociation) throws Exception {
        AssociationCorrespondence result = null;
        
        clsAssociationSecondary foundAss = getEquivalentAssociation(ass, compareAssociation);
        
        if (foundAss==null) {
            //Ass not found and therefore something should be done with it
            //Create evaluation
            AssociationCorrespondence newAssCorrespondace = new AssociationCorrespondence(ass);
            result = newAssCorrespondace;
            
        }
        return result; 
        
    }
    

    /* (non-Javadoc)
     *
     * @since 07.10.2013 20:36:05
     * 
     * @see secondaryprocess.datamanipulation.MeshProcessorInterface#setSafeControlMode(boolean)
     */
    @Override
    public void setSafeControlMode(boolean safeMode) {
        this.isSafeMode = safeMode;
        
    }

}
