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

import datatypes.clsPolarcoordinate;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolVisionEntry implements itfIsContainer, itfGetSymbolName, itfSymbolVisionEntry, itfGetDataAccessMethods  {
    protected String moSensorType;

    
    protected clsPolarcoordinate moPolarcoordinate = new clsPolarcoordinate();
    protected String mnEntityType = "UNDEFINED";
    protected String mnShapeType = "UNDEFINED";
    protected String moEntityId = "";
    protected String mnNumEntitiesPresent = "UNDEFINED"; 
    
    protected boolean mnAlive = false;
    protected Color moColor = new Color(0,0,0);
    protected String moBrightness = "UNDEFINED";
    protected String moObjectPosition = "UNDEFINED"; 
    protected String moAntennaPositionLeft = "UNDEFINED"; 
    protected String moAntennaPositionRight = "UNDEFINED";
    protected double moExactDebugX;
    protected double moExactDebugY;
    protected double moExactDebugAngle;
    protected double moDebugSensorArousal;
    protected double moObjectBodyIntegrity;
    protected clsSymbolVisionEntryAction moAction;
    protected String moDistance;
    
    protected ArrayList<clsSymbolVisionEntryExpression> moExpressions;
    
    public clsSymbolVisionEntry(clsDataPoint poEntry) {
        super();
        
        //moSensorType = eSensorExtType.valueOf(poEntry.getAssociation("SENSOR_TYPE").getValue());
        if(poEntry.hasAssociation("DISTANCE")) moDistance = convertDistance(poEntry.getAssociation("DISTANCE").getValue());//convertDistance(poEntry.getAssociation("DISTANCE").getValue());
        if(poEntry.hasAssociation("POLARCOORDINATE"))  moPolarcoordinate = new clsPolarcoordinate(Double.parseDouble(poEntry.getAssociation("POLARCOORDINATE").getAssociation("LENGTH").getValue()),Double.parseDouble(poEntry.getAssociation("POLARCOORDINATE").getAssociation("RADIANS").getValue()));
        mnEntityType = poEntry.getValue();//eEntityType.valueOf(poEntry.getValue());

        if(poEntry.hasAssociation("SHAPE"))  mnShapeType = poEntry.getAssociation("SHAPE").getValue();// eShapeType.valueOf(poEntry.getAssociation("SHAPE").getValue());

        if(poEntry.hasAssociation("ENTITYID"))  moEntityId = poEntry.getAssociation("ENTITYID").getValue();
        
        if(poEntry.hasAssociation("ALIVE"))  mnAlive = Boolean.parseBoolean(poEntry.getAssociation("ALIVE").getValue());
        
        if(poEntry.hasAssociation("COLOR"))  moColor = new Color(Integer.parseInt(poEntry.getAssociation("COLOR").getValue()));
        if(poEntry.hasAssociation("OBJECT_POSITION"))  moObjectPosition =poEntry.getAssociation("OBJECT_POSITION").getValue();//eSide.valueOf( poEntry.getAssociation("OBJECT_POSITION").getValue()); 
        if(poEntry.hasAssociation("DEBUG_POSITION"))  moExactDebugX = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("X").getValue());
        if(poEntry.hasAssociation("DEBUG_POSITION"))   moExactDebugY = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("Y").getValue());
        if(poEntry.hasAssociation("DEBUG_POSITION"))  moExactDebugAngle = Double.parseDouble(poEntry.getAssociation("DEBUG_POSITION").getAssociation("ANGLE").getValue());
        if(poEntry.hasAssociation("BRIGHTNESS"))  moBrightness = poEntry.getAssociation("BRIGHTNESS").getValue();//eSaliency.valueOf( poEntry.getAssociation("BRIGHTNESS").getValue()); 
        
        moExpressions = new ArrayList<clsSymbolVisionEntryExpression>();
        
        if(poEntry.hasAssociation("EXPRESSIONS")){
            clsDataPoint oExpressions = poEntry.getAssociation("EXPRESSIONS");
            for(clsDataPoint oExpr: oExpressions.getAssociatedDataPoints()){
                moExpressions.add(new clsSymbolVisionEntryExpression(oExpr));
            }
            
        }
        
        
        
        if(poEntry.hasAssociation("ACTIONS")) {
            moAction = new clsSymbolVisionEntryAction(poEntry.getAssociation("ACTIONS").getAssociation("ACTION"));
        }
        //TODO: Set new Action types
        //moActions = poSensor.getActions();
        
        
    }
    

   /* public eDistance convertDistance(String poDistance) {

        eDistance oRetVal = eDistance.UNDEFINED;
        if(poDistance.equals("SELF")) oRetVal = eDistance.NODISTANCE;
        else if(poDistance.equals("NEAR")) oRetVal = eDistance.NEAR;
        else if(poDistance.equals("MEDIUM")) oRetVal = eDistance.MEDIUM;
        else if(poDistance.equals("FAR")) oRetVal = eDistance.FAR;

        return oRetVal;
    }*/
    
    private String convertDistance(String poDist){
        if(poDist.equals("SELF")) return "NODISTANCE";
        else return poDist;
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
		return mnEntityType;
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
	public String getDistance() {

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
        public String getBrightness() {
	        return moBrightness;
	    }
	    public void setBrightness(String poBrightness) {
	        moBrightness = poBrightness;
	    }
	    
	    @Override
        public String getObjectPosition() {
	        return moObjectPosition;
	    }
	    public void setObjectPosition(String poObjectPosition) {
	        moObjectPosition = poObjectPosition;
	    }
	    
	    @Override
        public String getAntennaPositionLeft() {
	        return moAntennaPositionLeft;
	    }
	    public void setAntennaPositionLeft(String poAntennaPositionLeft) {
	        moAntennaPositionLeft = poAntennaPositionLeft;
	    }
	    
	    @Override
        public String getAntennaPositionRight() {
	        return moAntennaPositionRight;
	    }
	    public void setAntennaPositionRight(String poAntennaPositionRight) {
	        moAntennaPositionRight = poAntennaPositionRight;
	    }
	    
	    
	    public clsPolarcoordinate getPolarcoordinate() {
	        return moPolarcoordinate;
	    }
	    public void setPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
	        moPolarcoordinate = poPolarcoordinate;
	    }
	    
	    public String getEntityType() {
	        return mnEntityType;
	    }
	    public void setEntityType(String pnEntityType) {
	        mnEntityType = pnEntityType;
	    }
	    
	    @Override
        public String getShapeType() {
	        return mnShapeType;
	    }
	    public void setShapeType(String pnShapeType) {
	        mnShapeType = pnShapeType;
	    }
	    
	    public String getEntityId() {
	        return moEntityId;
	    }
	    public void setEntityId(String pnEntityId) {
	        moEntityId = pnEntityId;
	    }
	    
	    @Override
        public String getNumEntitiesPresent() {
	        return mnNumEntitiesPresent;
	    }
	    public void setNumEntitiesPresent(String pnNumEntitiesPresent) {
	        mnNumEntitiesPresent = pnNumEntitiesPresent;
	    }
	    
	    
	    public String getSensorType() {
	        return moSensorType;
	    }
	    public void setSensorType(String poSensorType) {
	        moSensorType = poSensorType;
	    }


        /* (non-Javadoc)
         *
         * @since 25.09.2014 09:09:00
         * 
         * @see prementalapparatus.symbolization.representationsymbol.itfSymbolVisionEntry#getExpressions()
         */
        @Override
        public ArrayList<clsSymbolVisionEntryExpression> getExpressions() {
            return moExpressions;
        }
	    
}
