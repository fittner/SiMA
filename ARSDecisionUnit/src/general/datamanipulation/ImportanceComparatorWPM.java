/**
 * CHANGELOG
 *
 * 13.05.2014 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.Comparator;

import base.datatypes.clsWordPresentationMeshGoal;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 13.05.2014, 21:22:49
 * 
 */
public class ImportanceComparatorWPM implements Comparator<clsWordPresentationMeshGoal> {

    /* (non-Javadoc)
     *
     * @since 13.05.2014 21:25:02
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(clsWordPresentationMeshGoal o1, clsWordPresentationMeshGoal o2) {        
        return Double.compare(o1.getTotalImportance(), o2.getTotalImportance());
    }
}
