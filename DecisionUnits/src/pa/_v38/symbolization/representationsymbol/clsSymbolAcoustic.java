package pa._v38.symbolization.representationsymbol;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;

import bfg.utils.enums.eCount;
import bfg.utils.enums.ePercievedActionType;
import bfg.utils.enums.eSide;

import du.enums.eAntennaPositions;
import du.enums.eDistance;
import du.enums.eSaliency;
import du.enums.eShapeType;
import du.itf.sensors.clsAcousticEntry;
import du.itf.sensors.clsSensorExtern;

public class clsSymbolAcoustic extends du.itf.sensors.clsAcoustic implements itfGetDataAccessMethods, itfSymbolAcoustic {
    
    public clsSymbolAcoustic(du.itf.sensors.clsAcoustic poSensor) {
        super();
        
        moSensorType = poSensor.getSensorType();
        
        for (du.itf.sensors.clsSensorExtern oEntry:poSensor.getDataObjects()) {
            clsSymbolAcousticEntry oE = new clsSymbolAcousticEntry( (du.itf.sensors.clsAcousticEntry)oEntry);
            //moEntries.add(oE);
        }       
    }

  

    @Override
    public Method[] getDataAccessMethods() {
        return itfSymbolAcoustic.class.getMethods();
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 21.10.2009, 12:43:37
     * 
     * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
     */
    @Override
    public ArrayList<itfSymbol> getSymbolObjects() {
        ArrayList<clsSensorExtern> oSE = getDataObjects();
        ArrayList<itfSymbol> oResult =  new ArrayList<itfSymbol>();
        
        for (clsSensorExtern oEntry:oSE) {
            oResult.add( (itfSymbol) new clsSymbolAcousticEntry( (clsAcousticEntry)oEntry) );
        }
        
        return oResult;
    }   
    
    /* (non-Javadoc)
     *
     * @author deutsch
     * 21.10.2009, 13:41:44
     * 
     * @see pa.symbolization.representationsymbol.itfSymbolEatableArea#getEntries()
     */
    @Override
    public ArrayList<itfSymbolAcousticEntry> getEntries() {
        ArrayList<clsSensorExtern> oSE = getDataObjects();
        ArrayList<itfSymbolAcousticEntry> oResult =  new ArrayList<itfSymbolAcousticEntry>();
        
        for (clsSensorExtern oEntry:oSE) {
            oResult.add( new clsSymbolAcousticEntry( (clsAcousticEntry)oEntry) );
        }
        
        return oResult;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getShapeType()
     */
    @Override
    public eShapeType getShapeType() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getNumEntitiesPresent()
     */
    @Override
    public eCount getNumEntitiesPresent() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getAlive()
     */
    @Override
    public boolean getAlive() {
        // TODO (hinterleitner) - Auto-generated method stub
        return false;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getColor()
     */
    @Override
    public Color getColor() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getBrightness()
     */
    @Override
    public eSaliency getBrightness() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getObjectPosition()
     */
    @Override
    public eSide getObjectPosition() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getAntennaPositionLeft()
     */
    @Override
    public eAntennaPositions getAntennaPositionLeft() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getAntennaPositionRight()
     */
    @Override
    public eAntennaPositions getAntennaPositionRight() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getDistance()
     */
    @Override
    public eDistance getDistance() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getExactDebugX()
     */
    @Override
    public double getExactDebugX() {
        // TODO (hinterleitner) - Auto-generated method stub
        return 0;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getExactDebugY()
     */
    @Override
    public double getExactDebugY() {
        // TODO (hinterleitner) - Auto-generated method stub
        return 0;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getDebugSensorArousal()
     */
    @Override
    public double getDebugSensorArousal() {
        // TODO (hinterleitner) - Auto-generated method stub
        return 0;
    }



    /* (non-Javadoc)
     *
     * @since 25.08.2013 09:45:21
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcoustic#getPerceivedAction()
     */
    @Override
    public ArrayList<ePercievedActionType> getPerceivedAction() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }   
}
