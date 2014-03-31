/**
 * CHANGELOG
 *
 * 03.03.2014 wendt - File created
 *
 */
package base.datatypes.factory;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.03.2014, 11:08:07
 * 
 */
public class DatastructureHelper {
    
    private static DatastructureHelper helper = null;
    
    //Static, in order to use this ID everywhere
    private int id;
    
    
    private DatastructureHelper() {
        id=0;
    }
    
    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
    
    public void incrementID() {
        id++;
    }
    
    public static DatastructureHelper getHelper() {
        DatastructureHelper result = helper;
        if (helper==null) {
            helper = new DatastructureHelper();
        }
        return result;
    }
    
    public clsTriple<Integer, eDataType, eContentType> createTripleStructure(boolean useIncrementedID, eDataType datatype, String contentType) {
        clsTriple<Integer, eDataType, eContentType> result; 
        if (useIncrementedID==true) {
            result = new clsTriple<Integer, eDataType, eContentType>(this.getId(), datatype, eContentType.valueOf(contentType));
            this.incrementID();
        } else {
            result = new clsTriple<Integer, eDataType, eContentType>(-1, datatype, eContentType.valueOf(contentType));
        }
            
        
        return result;
    }
}
