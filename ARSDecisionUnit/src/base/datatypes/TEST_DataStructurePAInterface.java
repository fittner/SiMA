/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package base.datatypes;

import memorymgmt.enums.eContentType;

/**
 * Top interface
 * 
 * @author wendt
 * 08.10.2013, 09:20:24
 * 
 */
public interface TEST_DataStructurePAInterface {
    public int getID();
    public eContentType getContentType();
    public String getContent();
}
