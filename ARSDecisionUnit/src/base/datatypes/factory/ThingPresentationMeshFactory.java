/**
 * CHANGELOG
 *
 * 03.03.2014 wendt - File created
 *
 */
package base.datatypes.factory;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.03.2014, 11:11:38
 * 
 */
public class ThingPresentationMeshFactory extends BaseFactory {
    
    DatastructureHelper helper = DatastructureHelper.getHelper();
    ThingPresentationFactory tpfactory = new ThingPresentationFactory();
    
    boolean useIncrementalId = true;
    
    public boolean isUseIncrementalId() {
        return useIncrementalId;
    }

    public void setUseIncrementalId(boolean useIncrementalId) {
        this.useIncrementalId = useIncrementalId;
    }
    
    
    
    public clsThingPresentationMesh createEntity(String content, String position, String distance, ArrayList<clsThingPresentation> internalProperties, ArrayList<clsDriveMesh> driveMeshes, boolean useIncrementedid) {
        
        clsThingPresentationMesh result = new clsThingPresentationMesh(helper.createTripleStructure(useIncrementedid, eDataType.TPM, eContentType.ENTITY.toString()), new ArrayList<clsAssociation>(), content);

        //Add internal associations
        ArrayList<clsAssociation> internalAssociations = new ArrayList<clsAssociation>();
        
        
        //Add external associations
        ArrayList<clsAssociation> externalAssociations = new ArrayList<clsAssociation>();
        
        clsAssociationAttribute oPositionAss = new clsAssociationAttribute(helper.createTripleStructure(useIncrementedid, eDataType.ASSOCIATIONATTRIBUTE, eContentType.DISTANCEASSOCIATION.toString()), result, tpfactory.createDataStructure(eContentType.DISTANCE.toString(), distance, useIncrementedid));
        clsAssociationAttribute oDistanceAss = new clsAssociationAttribute(helper.createTripleStructure(useIncrementedid, eDataType.ASSOCIATIONATTRIBUTE, eContentType.POSITIONASSOCIATION.toString()), result, tpfactory.createDataStructure(eContentType.POSITION.toString(), position, useIncrementedid));
       
        externalAssociations.add(oDistanceAss);
        externalAssociations.add(oPositionAss);
        
        
        return result;
       

       
        
//        //Search for one "Nothingobject"
//        //Create the TP
//        clsThingPresentationMesh oGeneratedTPM = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>
//            (eContentType.ENTITY, new ArrayList<clsThingPresentation>(),"EMPTYSPACE"));
//        
//        ArrayList<clsPrimaryDataStructureContainer> oSearchStructure = new ArrayList<clsPrimaryDataStructureContainer>();
//        oSearchStructure.add(new clsPrimaryDataStructureContainer(oGeneratedTPM, new ArrayList<clsAssociation>()));
//        
//        ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
//            new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
//        
//        oSearchResult = this.getLongTermMemory().searchEntity(eDataType.TPM, oSearchStructure); 
//        //If nothing is found, cancel
//        if (oSearchResult.get(0).isEmpty()==true) {
//            return oRetVal;
//        }
//        //Create "Nothing"-objects for each position
//        clsPrimaryDataStructureContainer oEmptySpaceContainer = (clsPrimaryDataStructureContainer) oSearchResult.get(0).get(0).b;
//        ArrayList<clsPrimaryDataStructureContainer> oEmptySpaceContainerList = new ArrayList<clsPrimaryDataStructureContainer>();
//        oEmptySpaceContainerList.add(oEmptySpaceContainer);
//        //assignDriveMeshes(oEmptySpaceContainerList);
//        
//        //for each position, fill it with a container
//        clsThingPresentationMesh oEmptySpaceTPM;
//        for (clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPosPair : oNewPositions) {
//            //Create a new TP-Container
//            try {
//                ((clsThingPresentationMesh)oEmptySpaceContainer.getMoDataStructure()).setExternalAssociatedContent(oEmptySpaceContainer.getMoAssociatedDataStructures());
//                oEmptySpaceTPM = (clsThingPresentationMesh) ((clsThingPresentationMesh) oEmptySpaceContainer.getMoDataStructure()).clone();
//                
//            
//                clsThingPresentation oPositionTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.POSITION, oPosPair.b.toString()));
//                clsThingPresentation oDistanceTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.DISTANCE, oPosPair.c.toString()));
//            
//                //clsTriple<Integer, eDataType, String> poDataStructureIdentifier,
//                //clsPrimaryDataStructure poAssociationElementA, 
//                //clsPrimaryDataStructure poAssociationElementB)
//                clsTriple<Integer, eDataType, eContentType> poIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE);
//                clsAssociationAttribute oPositionAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oPositionTP);
//                clsAssociationAttribute oDistanceAss = new clsAssociationAttribute(poIdentifier, oEmptySpaceTPM, oDistanceTP);
//            
//                oEmptySpaceTPM.getExternalAssociatedContent().add(oPositionAss);
//                oEmptySpaceTPM.getExternalAssociatedContent().add(oDistanceAss);
//            
//            
//                oRetVal.add(oEmptySpaceTPM);
//            
//            } catch (CloneNotSupportedException e) {
//                // TODO (wendt) - Auto-generated catch block
//                e.printStackTrace();
//            }
//            
//        }
        
    }
    
    public clsThingPresentationMesh createImage() {
        //TODO
        return null;
        
    }
    
}
