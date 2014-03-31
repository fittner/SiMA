/**
 * clsVisionEntries.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 */
package prementalapparatus.symbolization.representationsymbol;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

import bfg.tools.shapes.clsPolarcoordinate;
import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;


import du.enums.eAntennaPositions;
import du.enums.eDistance;
import du.enums.eEntityType;
import du.enums.eSaliency;
import du.enums.eSensorExtType;
import du.enums.eShapeType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolVisionEntry implements itfIsContainer, itfGetSymbolName, itfSymbolVisionEntry, itfGetDataAccessMethods  {
    protected eSensorExtType moSensorType;

    
    protected clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
    protected eEntityType mnEntityType = eEntityType.UNDEFINED;
    protected eShapeType mnShapeType = eShapeType.UNDEFINED;
    protected String moEntityId = "";
    protected eCount mnNumEntitiesPresent = eCount.UNDEFINED; 
    
    protected boolean mnAlive = false;
    protected Color moColor = new Color(0,0,0);
    protected eSaliency moBrightness = eSaliency.UNDEFINED;
    protected eSide moObjectPosition = eSide.UNDEFINED; 
    protected eAntennaPositions moAntennaPositionLeft = eAntennaPositions.UNDEFINED; 
    protected eAntennaPositions moAntennaPositionRight = eAntennaPositions.UNDEFINED;
    protected double moExactDebugX;
    protected double moExactDebugY;
    protected double moExactDebugAngle;
    protected double moDebugSensorArousal;
    protected double moObjectBodyIntegrity;
    protected clsSymbolVisionEntryAction moAction;
    protected eDistance moDistance;

    
    public clsSymbolVisionEntry(du.itf.sensors.clsVisionEntry poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		moPolarcoordinate = poSensor.getPolarcoordinate();
		mnEntityType = poSensor.getEntityType();
		mnShapeType = poSensor.getShapeType();
		moEntityId = poSensor.getEntityId();
		mnNumEntitiesPresent = poSensor.getNumEntitiesPresent(); 
		
		mnAlive = poSensor.getAlive();
		moColor = poSensor.getColor();
		moObjectPosition = poSensor.getObjectPosition(); 
		moAntennaPositionLeft = poSensor.getAntennaPositionLeft(); 
		moAntennaPositionRight = poSensor.getAntennaPositionRight();	
		moExactDebugX = poSensor.getExactDebugX();
		moExactDebugY = poSensor.getExactDebugY();
		moExactDebugAngle = poSensor.getExactDebugAngle();
		moBrightness = poSensor.getBrightness();
		moDebugSensorArousal = poSensor.getDebugSensorArousal();
		
		if(poSensor.getAction() != null){
		    moAction=new clsSymbolVisionEntryAction(poSensor.getAction());
		}
	}
    
    public clsSymbolVisionEntry(clsDataPoint poEntry) {
        super();
        
        //moSensorType = eSensorExtType.valueOf(poEntry.getAssociation("SENSOR_TYPE").getValue());
        moDistance = convertDistance(poEntry.getAssociation("DISTANCE").getValue());
        moPolarcoordinate = new clsPolarcoordinate(Double.parseDouble(poEntry.getAssociation("POLARCOORDINATE").getAssociation("LENGTH").getValue()),Double.parseDouble(poEntry.getAssociation("POLARCOORDINATE").getAssociation("RADIANS").getValue()));
        mnEntityType = eEntityType.valueOf(poEntry.getValue());

        mnShapeType = eShapeType.valueOf(poEntry.getAssociation("SHAPE").getValue());

        moEntityId = poEntry.getAssociation("ENTITYID").getValue();
        
        mnAlive = Boolean.parseBoolean(poEntry.getAssociation("ALIVE").getValue());
        
        moColor = new Color(Integer.parseInt(poEntry.getAssociation("COLOR").getValue()));
        moObjectPosition =eSide.valueOf( poEntry.getAssociation("OBJECT_POSITION").getValue()); 
        moExactDebugX = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("X").getValue());
        moExactDebugY = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("Y").getValue());
        moExactDebugAngle = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("ANGLE").getValue());
        moBrightness = eSaliency.valueOf( poEntry.getAssociation("BRIGHTNESS").getValue()); 
        
        //TODO: Set new Action types
        //moActions = poSensor.getActions();
        
        
    }
    

    public eDistance convertDistance(String poDistance) {

        eDistance oRetVal = eDistance.UNDEFINED;
        if(poDistance.equals("SELF")) oRetVal = eDistance.NODISTANCE;
        else if(poDistance.equals("NEAR")) oRetVal = eDistance.NEAR;
        else if(poDistance.equals("MEDIUM")) oRetVal = eDistance.MEDIUM;
        else if(poDistance.equals("FAR")) oRetVal = eDistance.FAR;

        return oRetVal;
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
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 12:34:45
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
	 */
    
    @Override
    public itfSymbolVisionEntryAction getSymbolAction(){
        if(moAction!= null){
            return (moAction);
        }
        else{
            return null;
        }
    }
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

		return moDistance;
	}
	   public void setAction(clsSymbolVisionEntryAction poAction) {
	        this.moAction = poAction;
	    }

	    
	    public double getObjectBodyIntegrity() {
	        return moObjectBodyIntegrity;
	    }

	    public void setObjectBodyIntegrity(double poObjectBodyIntegrity) {
	        this.moObjectBodyIntegrity = poObjectBodyIntegrity;
	    }

	    @Override
        public double getExactDebugX() {
	        return moExactDebugX;
	    }

	    @Override
        public double getExactDebugY() {
	        return moExactDebugY;
	    }

	    public double getExactDebugAngle() {
	        return moExactDebugAngle;
	    }
	    
	    @Override
        public double getDebugSensorArousal() {
	        return moDebugSensorArousal;
	    }
	    
	    public void setDebugSensorArousal(double poDebugSensorArousal) {
	        moDebugSensorArousal = poDebugSensorArousal;
	    }
	    
	    public void setExactDebugPosition(double poExactX, double poExactY, double poExactAngle) {
	        moExactDebugX = poExactX;
	        moExactDebugY = poExactY;
	        moExactDebugAngle = poExactAngle;
	    }
	    
	    @Override
        public boolean getAlive() {
	        return mnAlive;
	    }
	    public void setAlive(boolean pnAlive) {
	        mnAlive=pnAlive;
	    }
	    
	    @Override
        public Color getColor() {
	        return moColor;
	    }
	    public void setColor(Color poColor) {
	        moColor = poColor;
	    }
	    
	    @Override
        public eSaliency getBrightness() {
	        return moBrightness;
	    }
	    public void setBrightness(eSaliency poBrightness) {
	        moBrightness = poBrightness;
	    }
	    
	    @Override
        public eSide getObjectPosition() {
	        return moObjectPosition;
	    }
	    public void setObjectPosition(eSide poObjectPosition) {
	        moObjectPosition = poObjectPosition;
	    }
	    
	    @Override
        public eAntennaPositions getAntennaPositionLeft() {
	        return moAntennaPositionLeft;
	    }
	    public void setAntennaPositionLeft(eAntennaPositions poAntennaPositionLeft) {
	        moAntennaPositionLeft = poAntennaPositionLeft;
	    }
	    
	    @Override
        public eAntennaPositions getAntennaPositionRight() {
	        return moAntennaPositionRight;
	    }
	    public void setAntennaPositionRight(eAntennaPositions poAntennaPositionRight) {
	        moAntennaPositionRight = poAntennaPositionRight;
	    }
	    
	    
	    public clsPolarcoordinate getPolarcoordinate() {
	        return moPolarcoordinate;
	    }
	    public void setPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
	        moPolarcoordinate = poPolarcoordinate;
	    }
	    
	    public eEntityType getEntityType() {
	        return mnEntityType;
	    }
	    public void setEntityType(eEntityType pnEntityType) {
	        mnEntityType = pnEntityType;
	    }
	    
	    @Override
        public eShapeType getShapeType() {
	        return mnShapeType;
	    }
	    public void setShapeType(eShapeType pnShapeType) {
	        mnShapeType = pnShapeType;
	    }
	    
	    public String getEntityId() {
	        return moEntityId;
	    }
	    public void setEntityId(String pnEntityId) {
	        moEntityId = pnEntityId;
	    }
	    
	    @Override
        public eCount getNumEntitiesPresent() {
	        return mnNumEntitiesPresent;
	    }
	    public void setNumEntitiesPresent(eCount pnNumEntitiesPresent) {
	        mnNumEntitiesPresent = pnNumEntitiesPresent;
	    }
	    
	    
	    public eSensorExtType getSensorType() {
	        return moSensorType;
	    }
	    public void setSensorType(eSensorExtType poSensorType) {
	        moSensorType = poSensorType;
	    }
	    
}
