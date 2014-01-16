/**
 * clsAttributes.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:39:56
 */
package body.attributes;

import java.util.HashMap;

import properties.clsProperties;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 14:39:56
 * 
 */
public class clsAttributes {
	public static final String P_NUMATTRIBUTES = "numattributes";
	public static final String P_ATTRIBUTETYPE = "attributetype";
	public static final String P_ATTRIBUTEACTIVE = "attributeactive";
	
	protected HashMap<eBodyAttributes, clsBaseAttribute> moAttributes;

    public clsAttributes(String poPrefix, clsProperties poProp, clsEntity poEntity) {
    	moAttributes = new HashMap<eBodyAttributes, clsBaseAttribute>();
		applyProperties(poPrefix, poProp, poEntity);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		int num = poProp.getPropertyInt(pre+P_NUMATTRIBUTES);
		for (int i=0; i<num; i++) {
			String tmp_pre = pre+i+".";
			
			boolean nActive = poProp.getPropertyBoolean(tmp_pre+P_ATTRIBUTEACTIVE);
			if (nActive) {
				eBodyAttributes eType = eBodyAttributes.valueOf( poProp.getPropertyString(tmp_pre+P_ATTRIBUTETYPE) );
				switch(eType) {
					case COLOR:
						moAttributes.put(eType, new clsAttributeColor(tmp_pre, poProp, poEntity));
						break;
					case ANTENNA_LEFT:
						moAttributes.put(eType, new clsAttributeAntenna(tmp_pre, poProp, eBodyAttributes.ANTENNA_LEFT));
						break;
					case ANTENNA_RIGHT:
						moAttributes.put(eType, new clsAttributeAntenna(tmp_pre, poProp, eBodyAttributes.ANTENNA_RIGHT));
						break;
					case EYE:
						moAttributes.put(eType, new clsAttributeEye(tmp_pre, poProp));
						break;
					case SHAPE:
						moAttributes.put(eType, new clsAttributeShape(tmp_pre, poProp, poEntity));
						break;
					case HEIGHT:
						moAttributes.put(eType, new clsAttributeHeight(tmp_pre, poProp));
						break;
					case ALIVE:
						moAttributes.put(eType, new clsAttributeAlive(tmp_pre, poProp, poEntity));
						break;
					default:
						throw new java.lang.IllegalArgumentException("don't know how to handle attribute type "+eType);
				}
			}
		}
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();

		int i = 0;
		
		oProp.putAll( clsAttributeShape.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_ATTRIBUTETYPE, eBodyAttributes.SHAPE.name());
		oProp.setProperty(pre+i+"."+P_ATTRIBUTEACTIVE, true);
		i++;

		oProp.putAll( clsAttributeColor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_ATTRIBUTETYPE, eBodyAttributes.COLOR.name());
		oProp.setProperty(pre+i+"."+P_ATTRIBUTEACTIVE, true);
		i++;

		oProp.putAll( clsAttributeHeight.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_ATTRIBUTETYPE, eBodyAttributes.HEIGHT.name());
		oProp.setProperty(pre+i+"."+P_ATTRIBUTEACTIVE, true);
		i++;
		
		oProp.putAll( clsAttributeHeight.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_ATTRIBUTETYPE, eBodyAttributes.ALIVE.name());
		oProp.setProperty(pre+i+"."+P_ATTRIBUTEACTIVE, true);
		i++;		

		oProp.setProperty(pre+P_NUMATTRIBUTES, i);
		
		return oProp;
	}	
	
	public clsBaseAttribute getAttribute(eBodyAttributes pnAttributeType) {
		return moAttributes.get(pnAttributeType);
	}
	
	public void putAttribute(eBodyAttributes pnAttributeType, clsBaseAttribute poAttribute) {
		moAttributes.put(pnAttributeType, poAttribute);
	}
	
	public HashMap<eBodyAttributes, clsBaseAttribute> getAttributeList() {
		return moAttributes;
	}
}
