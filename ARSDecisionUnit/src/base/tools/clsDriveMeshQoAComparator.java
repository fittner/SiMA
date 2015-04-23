/**
 * CHANGELOG
 *
 * 15.10.2013 herret - File created
 *
 */
package base.tools;

import java.util.Comparator;

import base.datatypes.clsDriveMesh;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 15.10.2013, 10:19:43
 * 
 */
public class clsDriveMeshQoAComparator implements Comparator<clsDriveMesh> {



    /**
     * Returns >1 if the QoA o1>o2
     * 
     */
    @Override
    public int compare(clsDriveMesh o1, clsDriveMesh o2) {
        if(o1.getQuotaOfAffect()>o2.getQuotaOfAffect()) return -1;
        else if(o1.getQuotaOfAffect() == o2.getQuotaOfAffect()) return 0;
        else if(o1.getQuotaOfAffect() < o2.getQuotaOfAffect()) return 1;
        return 0;
    }

    
}
