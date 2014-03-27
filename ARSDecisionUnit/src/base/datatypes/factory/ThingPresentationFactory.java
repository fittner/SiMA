/**
 * CHANGELOG
 *
 * 03.03.2014 wendt - File created
 *
 */
package base.datatypes.factory;

import memorymgmt.enums.eDataType;
import base.datatypes.clsThingPresentation;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.03.2014, 11:05:32
 * 
 */
public class ThingPresentationFactory extends BaseFactory {
    
    DatastructureHelper helper = DatastructureHelper.getHelper();
    
    public clsThingPresentation createDataStructure(String ContentType, String content, boolean useIncrementedID) {
        clsThingPresentation result = new clsThingPresentation(helper.createTripleStructure(useIncrementedID, eDataType.TP, ContentType), content);
        return result;
    }
    
}
