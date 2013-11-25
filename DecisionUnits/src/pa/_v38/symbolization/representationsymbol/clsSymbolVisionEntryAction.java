/**
 * CHANGELOG
 *
 * 20.11.2013 herret - File created
 *
 */
package pa._v38.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import du.itf.sensors.clsVisionEntryAction;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 20.11.2013, 10:00:55
 * 
 */
public class clsSymbolVisionEntryAction extends clsVisionEntryAction implements itfSymbolVisionEntryAction, itfGetDataAccessMethods, itfGetSymbolName, itfIsContainer {


    
    public clsSymbolVisionEntryAction(clsVisionEntryAction poAction){
        setActionName(poAction.getActionName());
        if(poAction.getObjectVisionEntry()!=null)this.setObject(new clsSymbolVisionEntry(poAction.getObjectVisionEntry()));
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 10:00:55
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
     */
    @Override
    public ArrayList<itfSymbol> getSymbolObjects() {
        // TODO (herret) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 10:00:55
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolVisionEntryAction#getName()
     */
    @Override
    public String getName() {
        return moName;
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 10:00:55
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolVisionEntryAction#getObject()
     */
    @Override
    public itfSymbolVisionEntry getObjectSymbolVisionEntry() {
        if(moObject!=null){
            return  new clsSymbolVisionEntry (moObject);
        }
        else{
            return null;
        }
    }

    /* (non-Javadoc)
     *
     * @since 20.11.2013 12:14:31
     * 
     * @see pa._v38.symbolization.representationsymbol.itfGetDataAccessMethods#getDataAccessMethods()
     */
    @Override
    public Method[] getDataAccessMethods() {
        return itfSymbolVisionEntryAction.class.getMethods();
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
        return "ACTION";
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
}
