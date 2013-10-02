/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.ArrayList;

import datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 21:16:02
 * 
 */
public class GeneralSortingTools {

    
    /**
     * Sort and filter any list with rates in double format.
     * 
     * This sort is used to sort images, which are rated for importance. The biggest value first
     * 
     * (wendt)
     *
     * @since 09.07.2012 15:02:22
     * 
     * @param pnNumberOfAllowedObjects: -1 for no filtering, any positive value for filtering
     * @param poInput
     * @return
     */
    
    public static <E extends Object> ArrayList<E> sortAndFilterRatedStructures(ArrayList<clsPair<Double, E>> poInput, int pnNumberOfAllowedObjects) {
        ArrayList<clsPair<Double, E>> oResult = new ArrayList<clsPair<Double, E>>();
        
        for (clsPair<Double, E> oP : poInput) {
            int nIndex = 0;
            //Increase index if the list is not empty
            while((oResult.isEmpty()==false) && 
                    (nIndex<oResult.size()) &&
                    (oResult.get(nIndex).a > oP.a)) {
                nIndex++;
            }
            
            oResult.add(nIndex, oP);
            
            if (pnNumberOfAllowedObjects!=-1 && oResult.size()==pnNumberOfAllowedObjects) {
                break;
            }
        }
        
        ArrayList<E> reducedResult = convertSortListToList(oResult);
        
        return reducedResult;
    }
    
    /**
     * Convert a sortlist for goals to goallist 
     *
     * MANIPULATION
     * 
     * @author wendt
     * @since 30.09.2013 21:26:29
     *
     * @param sortList
     * @return
     */
    private static <E extends Object> ArrayList<E> convertSortListToList(ArrayList<clsPair<Double, E>> sortList) {
        ArrayList<E> result = new ArrayList<E>();
        
        //Add results to the new list
        for (int i=0; i<sortList.size();i++) {
            result.add(i, sortList.get(i).b);
        }
        
        return result;
    }
}
