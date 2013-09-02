/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package pa._v38.symbolization.representationsymbol;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;

import bfg.utils.enums.ePercievedActionType;

import du.enums.eAntennaPositions;
import du.enums.eDistance;
import du.enums.eSensorExtType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolAcousticEntry extends du.itf.sensors.clsAcousticEntry implements itfIsContainer, itfGetSymbolName, itfSymbolAcousticEntry, itfGetDataAccessMethods  {
    public clsSymbolAcousticEntry(du.itf.sensors.clsAcousticEntry poSensor) {
        super();
        
        moSensorType = poSensor.getSensorType();
        
        moPolarcoordinate = poSensor.getPolarcoordinate();
        mnEntityType = poSensor.getEntityType();
        mnShapeType = poSensor.getShapeType();
        moEntityId = poSensor.getEntityId();
        moObjectPosition = poSensor.getObjectPosition(); 
        moExactDebugX = poSensor.getExactDebugX();
        moExactDebugY = poSensor.getExactDebugY();
        moExactDebugAngle = poSensor.getExactDebugAngle();
        moBrightness = poSensor.getBrightness();
        moDebugSensorArousal = poSensor.getDebugSensorArousal();
        
        moActions = poSensor.getActions();
        
        
    }
    /* (non-Javadoc)
     *
     * @author deutsch
     * 19.10.2009, 20:16:15
     * 
     * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
     */
    @Override
    public String getSymbolName() {
        return mnEntityType.name();
    }
    /* (non-Javadoc)
     *
     * @author deutsch
     * 21.10.2009, 15:09:46
     * 
     * @see pa.symbolization.representationsymbol.itfGetSymbolName#getSymbolType()
     */
    @Override
    public String getSymbolType() {
        return "ENTITY";
    }
    @Override
    public Method[] getDataAccessMethods() {
        return itfSymbolVisionEntry.class.getMethods();
    }
    
    @Override
    public ArrayList<ePercievedActionType> getPerceivedAction(){
        return moActions;
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * 21.10.2009, 12:34:45
     * 
     * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
     */
    @Override
    public ArrayList<itfSymbol> getSymbolObjects() {
        ArrayList<itfSymbol> oRetVal = new ArrayList<itfSymbol>();
        oRetVal.add(this);
        return oRetVal;
    }
    /* (non-Javadoc)
     *
     * @author langr
     * 21.10.2009, 21:36:06
     * 
     * @see pa.symbolization.representationsymbol.itfIsContainer#getSymbolMeshContent()
     */
    @Override
    public Object getSymbolMeshContent() {
        return mnEntityType;
    }
    /* (non-Javadoc)
     *
     * @author langr
     * 30.10.2009, 12:32:29
     * 
     * @see pa.symbolization.representationsymbol.itfSymbolVisionEntry#getDistance()
     */
    @Override
    public eDistance getDistance() {

        eDistance oRetVal = eDistance.UNDEFINED;
        
        if(moSensorType == eSensorExtType.ACOUSTIC_FAR) {
            oRetVal = eDistance.FAR;
        } 
        else if(moSensorType == eSensorExtType.ACOUSTIC_MEDIUM) {
            oRetVal = eDistance.MEDIUM;
        } 
        else if(moSensorType == eSensorExtType.ACOUSTIC_NEAR) {
            oRetVal = eDistance.NEAR;
        }
        
        return oRetVal;
    }
    /* (non-Javadoc)
     *
     * @since 25.08.2013 10:08:44
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcousticEntry#getAlive()
     */
    @Override
    public boolean getAlive() {
        // TODO (hinterleitner) - Auto-generated method stub
        return false;
    }
    /* (non-Javadoc)
     *
     * @since 25.08.2013 10:08:44
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcousticEntry#getColor()
     */
    @Override
    public Color getColor() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     *
     * @since 25.08.2013 10:08:44
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcousticEntry#getAntennaPositionLeft()
     */
    @Override
    public eAntennaPositions getAntennaPositionLeft() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     *
     * @since 25.08.2013 10:08:44
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolAcousticEntry#getAntennaPositionRight()
     */
    @Override
    public eAntennaPositions getAntennaPositionRight() {
        // TODO (hinterleitner) - Auto-generated method stub
        return null;
    }
}
