
package pa._v38.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import bfg.utils.enums.eCount;

import du.enums.eDistance;
import du.enums.eOdor;
import du.enums.eSaliency;
import du.enums.eShapeType;
import du.itf.sensors.clsOlfactoricEntry;



/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.09.2009, 13:09:00
 * 
 */
public class clsSymbolOlfactoricEntry extends du.itf.sensors.clsOlfactoricEntry 
											implements itfIsContainer, itfGetSymbolName, itfSymbolOlfactoricEntry, itfGetDataAccessMethods  {

	public clsSymbolOlfactoricEntry(clsOlfactoricEntry oEntry) {
		super();
		
		moSensorType = oEntry.getSensorType();
		
		moPolarcoordinate = oEntry.getPolarcoordinate();
		mnEntityType = oEntry.getEntityType();
		mnShapeType = oEntry.getShapeType();
		moEntityId = oEntry.getEntityId();
		mnNumEntitiesPresent = oEntry.getNumEntitiesPresent(); 
		
		moOdor = oEntry.getOdor();
		moIntensity = oEntry.getIntensity();
		moObjectPosition = oEntry.getObjectPosition(); 
			
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
		return itfSymbolOlfactoricEntry.class.getMethods();
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
	 * 21.10.2009, 21:35:01
	 * 
	 * @see pa.symbolization.representationsymbol.itfIsContainer#getSymbolMeshContent()
	 */
	@Override
	public Object getSymbolMeshContent() {
		return mnEntityType;
	}


	@Override
	public eDistance getDistance() {
		return eDistance.FAR;
	}

    /* (non-Javadoc)
     *
     * @since 15.08.2013 14:00:29
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolOlfactoricEntry#getShapeType()
     */
    @Override
    public eShapeType getShapeType() {
        // TODO (muchitsch) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 15.08.2013 14:00:29
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolOlfactoricEntry#getNumEntitiesPresent()
     */
    @Override
    public eCount getNumEntitiesPresent() {
        // TODO (muchitsch) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 15.08.2013 14:00:29
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolOlfactoricEntry#getIntensity()
     */
    @Override
    public eSaliency getIntensity() {
        // TODO (muchitsch) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 15.08.2013 14:00:29
     * 
     * @see pa._v38.symbolization.representationsymbol.itfSymbolOlfactoricEntry#getOdor()
     */
    @Override
    public eOdor getOdor() {
        // TODO (muchitsch) - Auto-generated method stub
        return null;
    }
}
