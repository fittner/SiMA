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
public class clsSymbolVisionEntryAction implements itfSymbolVisionEntryAction, itfGetDataAccessMethods, itfGetSymbolName, itfIsContainer {

    protected String moName;
    protected clsSymbolVisionEntry moObject;
    
    public clsSymbolVisionEntryAction(clsDataPoint poAction){
       moName = poAction.getAssociation("ACTION_NAME").getValue();
       if(poAction.hasAssociation("CORRESPONDING_ENTITY")) moObject = new clsSymbolVisionEntry(poAction.getAssociation("CORRESPONDING_ENTITY"));
        
        // setActionName(poAction.getActionName());
        //if(poAction.getObjectVisionEntry()!=null)this.setObject(new clsSymbolVisionEntry(poAction.getObjectVisionEntry()));
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
            return  moObject;
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
    
    /**
     * @since 19.11.2013 13:36:08
     * 
     * @return the moName
     */
    public String getActionName() {
        return moName;
    }
    /**
     * @since 19.11.2013 13:36:08
     * 
     * @param moName the moName to set
     */
    public void setActionName(String moName) {
        this.moName = moName;
    }
    /**
     * @since 19.11.2013 13:36:08
     * 
     * @return the moObject
     */
    public clsSymbolVisionEntry getObjectVisionEntry() {
        return moObject;
    }
    /**
     * @since 19.11.2013 13:36:08
     * 
     * @param moObject the moObject to set
     */
    public void setObject(clsSymbolVisionEntry moObject) {
        this.moObject = moObject;
    }
    
    @Override
    public String toString(){
        String oRetVal="";
        oRetVal += moName;
        if(moObject != null){
            oRetVal +=" (" + moObject.mnEntityType.toString() +")";
        }
        return oRetVal;
    }
}
