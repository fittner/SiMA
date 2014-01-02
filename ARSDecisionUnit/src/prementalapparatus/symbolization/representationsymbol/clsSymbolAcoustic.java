package prementalapparatus.symbolization.representationsymbol;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;

import bfg.utils.enums.eCount;
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
            moEntries.add(oE);
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
    
    public double getDebugSensorArousal() {
        // TODO (hinterleitner) - Auto-generated method stub
        return 0;
    }



 
}
