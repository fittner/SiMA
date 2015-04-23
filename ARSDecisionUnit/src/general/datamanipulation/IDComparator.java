/**
 * CHANGELOG
 *
 * 14.05.2014 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.Comparator;

import base.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 14.05.2014, 13:24:37
 * 
 */
public class IDComparator implements Comparator<clsDataStructurePA> {

    /* (non-Javadoc)
     *
     * @since 14.05.2014 13:25:06
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(clsDataStructurePA arg0, clsDataStructurePA arg1) {
        return Integer.compare(arg0.getDS_ID(), arg1.getDS_ID());
    }

}
