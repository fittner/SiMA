/**
 * CHANGELOG
 *
 * 20.11.2013 herret - File created
 *
 */
package prementalapparatus.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 20.11.2013, 10:00:55
 * 
 */
public class clsSymbolVisionEntryExpression implements itfGetDataAccessMethods, itfGetSymbolName, itfIsContainer, itfSymbolExpression {

    protected String moName;
    protected String moValue;
    
    public clsSymbolVisionEntryExpression(clsDataPoint poExpression){
       moName = poExpression.getType();
       moValue =poExpression.getValue();
    }



    /* (non-Javadoc)
     *
     * @since 20.11.2013 12:14:31
     * 
     * @see pa._v38.symbolization.representationsymbol.itfGetDataAccessMethods#getDataAccessMethods()
     */
    @Override
    public Method[] getDataAccessMethods() {
        return itfSymbolExpression.class.getMethods();
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 12:16:23
     * 
     * @see pa._v38.symbolization.representationsymbol.itfGetSymbolName#getSymbolName()
     */
    @Override
    public String getSymbolName() {
        return moName;
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 12:16:23
     * 
     * @see pa._v38.symbolization.representationsymbol.itfGetSymbolName#getSymbolType()
     */
    @Override
    public String getSymbolType() {
        return moName;
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 12:18:59
     * 
     * @see pa._v38.symbolization.representationsymbol.itfIsContainer#getSymbolMeshContent()
     */
    @Override
    public Object getSymbolMeshContent() {
        return moName;
    }
    
   
    
    @Override
    public String toString(){
        String oRetVal="";
        oRetVal += moName +"  ";
        oRetVal += moValue;

        return oRetVal;
    }






    /* (non-Javadoc)
     *
     * @since 24.09.2014 13:36:17
     * 
     * @see prementalapparatus.symbolization.representationsymbol.itfSymbolExpression#getExpressionValue()
     */
    @Override
    public String getExpressionValue() {
        return moValue;
    }



    /* (non-Javadoc)
     *
     * @since 24.09.2014 13:37:10
     * 
     * @see prementalapparatus.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
     */
    @Override
    public ArrayList<itfSymbol> getSymbolObjects() {
        // TODO (herret) - Auto-generated method stub
        return null;
    }

}
