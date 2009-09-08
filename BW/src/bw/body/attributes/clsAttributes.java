/**
 * clsAttributes.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:39:56
 */
package bw.body.attributes;

import java.util.HashMap;

import bw.body.itfget.itfGetBotHand;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyAttributes;
import config.clsBWProperties;

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

    public clsAttributes(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
    	moAttributes = new HashMap<eBodyAttributes, clsBaseAttribute>();
		applyProperties(poPrefix, poProp, poEntity);
	}
    
	private void applyProperties(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		String pre = clsBWProperties.addDot(poPrefix);

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
					case HAND_LEFT:
						if (poEntity instanceof itfGetBotHand) {
							moAttributes.put(eType, new clsAttributeHand(tmp_pre, poProp, (itfGetBotHand) poEntity, eBodyAttributes.HAND_LEFT));
						} else {
							throw new java.lang.IllegalArgumentException("itfGetBotHand not implemented");
						}
						
						break;
					case HAND_RIGHT:
						if (poEntity instanceof itfGetBotHand) {
							moAttributes.put(eType, new clsAttributeHand(tmp_pre, poProp, (itfGetBotHand) poEntity, eBodyAttributes.HAND_RIGHT));
						} else {
							throw new java.lang.IllegalArgumentException("itfGetBotHand not implemented");
						}
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
					default:
						throw new java.lang.IllegalArgumentException("don't know how to handle attribute type "+eType);
				}
			}
		}
	}	    

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NUMATTRIBUTES, 3);
		
		oProp.putAll( clsAttributeShape.getDefaultProperties( pre+"0") );
		oProp.setProperty(pre+"0."+P_ATTRIBUTETYPE, eBodyAttributes.SHAPE.name());
		oProp.setProperty(pre+"0."+P_ATTRIBUTEACTIVE, true);

		oProp.putAll( clsAttributeColor.getDefaultProperties( pre+"1") );
		oProp.setProperty(pre+"1."+P_ATTRIBUTETYPE, eBodyAttributes.COLOR.name());
		oProp.setProperty(pre+"1."+P_ATTRIBUTEACTIVE, true);		

		oProp.putAll( clsAttributeHeight.getDefaultProperties( pre+"2") );
		oProp.setProperty(pre+"2."+P_ATTRIBUTETYPE, eBodyAttributes.HEIGHT.name());
		oProp.setProperty(pre+"2."+P_ATTRIBUTEACTIVE, true);		
		
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
