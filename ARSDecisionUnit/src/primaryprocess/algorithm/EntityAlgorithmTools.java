/**
 * CHANGELOG
 *
 * 05.10.2013 wendt - File created
 *
 */
package primaryprocess.algorithm;

import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 05.10.2013, 13:43:59
 * 
 */
public class EntityAlgorithmTools {
    
    private static Logger log = Logger.getLogger("Tools");
    
    /**
     * Drives are merged if they are from the same type, i. e. if the content type is equal, then the mrPleasure is summarized
     *
     * @since 12.07.2011 16:16:34
     *
     * @param oInput
     * @return
     */
    public static void mergeDriveMeshesForObject(clsThingPresentationMesh poInput) {
        /* DMs are added from F35 and F45 to the PDSC. In this function, all values, which are from the same
         * type are summarized, e. g. moContentType is equal
         */
        
        //FIXME someone: All parts of this function are on the same level
        
        //Create a new empty List of associations, in which the modified associations are added
        ArrayList<clsAssociation> oNewAss = new ArrayList<clsAssociation>();
        
        //For each association to a template image in the template image
                
        /* Create an Arraylist with booleans in order to only add the elements, which are set true
         * This is used for avoiding to add an association twice
         */
        ArrayList<clsPair<clsAssociation, Boolean>> oArrAssFirst = new ArrayList<clsPair<clsAssociation, Boolean>>();
        for (int i=0;i<poInput.getExternalAssociatedContent().size();i++) {
            oArrAssFirst.add(new clsPair<clsAssociation, Boolean>(poInput.getExternalAssociatedContent().get(i),false));
        }
        
        ListIterator<clsPair<clsAssociation, Boolean>> liMainList = oArrAssFirst.listIterator();
        ListIterator<clsPair<clsAssociation, Boolean>> liSubList;
        clsAssociation oFirstAss;
        //Go through each element in the first list
        while (liMainList.hasNext()) {
            clsPair<clsAssociation, Boolean> oFirstAssPair = liMainList.next();
            oFirstAss = oFirstAssPair.a;
            
            if ((oFirstAss instanceof clsAssociationDriveMesh) && (oFirstAssPair.b == false)) {
                //Get a DM from the associated content
                clsDriveMesh oFirstDM = (clsDriveMesh)oFirstAss.getLeafElement();
                
                /* Here the new content is set depending on the highest level of total quota of affect
                 * of all equal drive mesh types in the object. If another object has a higher
                 * mrPleasure, its content is taken.
                 */
                double rMaxTotalQuotaOfAffect = oFirstDM.getQuotaOfAffect();    //FIXME Add Unpleasure too
                //String sMaxContent = oFirstDM.getActualBodyOrificeAsENUM().toString();
                
                        
                //Go through all following associations of that object
                //Iterator<clsAssociation> oArrAssSecond = ;
                //Go to the position of the first element
                
                liSubList = oArrAssFirst.listIterator(liMainList.nextIndex());
                //for (int i=0;i<=iStartIndex;i++) {
                //  it2.next();
                //}
                
                while (liSubList.hasNext()) {
                    clsPair<clsAssociation, Boolean> oSecondAssPair = liSubList.next();
                    clsAssociation oSecondAss = oSecondAssPair.a;
                    //Boolean oDelete = oSecondAssPair.b;
                    
                    //If the DM belongs to the same TPM oder TP AND it is a DM and it has not been used yet
                    if (oFirstAss.getRootElement().equals(oSecondAss.getRootElement()) && (oSecondAss instanceof clsAssociationDriveMesh) && (oSecondAssPair.b == false)) {   
                        clsDriveMesh oSecondDM = (clsDriveMesh)oSecondAss.getLeafElement();
                        //firstAssociation is compared with the secondAssociation
                        //If the content type of the DM are equal then
                        if (oFirstDM.getDriveIdentifier().equals(oSecondDM.getDriveIdentifier())==true) {
                            //1. Add mrPleasure from the second to the first DM
                            double mrNewPleasure = setNewQuotaOfAffectValue(oFirstDM.getQuotaOfAffect(), oSecondDM.getQuotaOfAffect());
                            
                            //SSCH 20121002 decommented due to a bug ( continous QoA-increase). if needed, take care to not accumulate the QoA
                            oFirstDM.setQuotaOfAffect(mrNewPleasure);
                            //Set second DM as used (true)
                            oSecondAssPair.b = true;
                            //2. Add Unpleasure from second to first DM
                            //FIXME: AW 20110528: Add unpleasure too?
                            //oFirstDM.setMrPleasure(mrNewUnpleasure);
                            //3. Check if the quota of affect is higher for the second DM and exchange content
                        
                            if (java.lang.Math.abs(oSecondDM.getQuotaOfAffect()) > java.lang.Math.abs(rMaxTotalQuotaOfAffect)) {
                                //FIXME: Corrent the function to consider mrUnpleasure too
                                //sMaxContent = oSecondDM.getMoContent();
                                rMaxTotalQuotaOfAffect = oSecondDM.getQuotaOfAffect();
                            }
                        }
                    }
                }
                //Set new content if different, in order to set the content to the one with the highest mrPleasure
//              if (oFirstDM.getMoContent().equals(sMaxContent)==false) {
//                  oFirstDM.setMoContent(sMaxContent);
//              }
                //No content is set at all
            }
            //Add the association to the list if it has not been used yet
            if (oFirstAssPair.b == false) {
                oNewAss.add(oFirstAss);
            }
        }
        //clsPrimaryDataStructureContainer oMergedResult = new clsPrimaryDataStructureContainer(oInput.getMoDataStructure(), oNewAss);
        
        //Replace the old external associations with the smaller new list
        poInput.setExternalAssociatedContent(oNewAss);
        //return oMergedResult;
    }
    
    /**
     * This function was made in order to be able to set the calculation function of the total 
     * quota of affect separately
     *
     * @since 12.07.2011 16:18:10
     *
     * @param rOriginal
     * @param rAddValue
     * @return
     */
    private static double setNewQuotaOfAffectValue(double rOriginal, double rAddValue) {
        double rResult = (rOriginal + rAddValue);
        // This function was made in order to be able to set the calculation function of the total 
        //  quota of affect separately 
        //
        
        //Averaging is used and normalization of quota of affect, as this quota of affect is only the "erinnerte Besetzung" and does not follow the law
        //of constanct quota of affect
        return  rResult;
    }
}
