/**
 * clsCreateSpeech.java: BW - bw.body.interBodyWorldSystems
 * 
 * @author MW
 * 
 */
package complexbody.interBodyWorldSystems;

import complexbody.internalSystems.clsSpeechSystem;

import statictools.clsUniqueIdGenerator;
import tools.clsPolarcoordinate;
import tools.clsPose;
import config.clsProperties;
import du.itf.tools.clsAbstractSpeech;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsSpeech;

/**
 * DOCUMENT (MW) - This class can be used by the body to change the message according to bodily factors. 
 * 
 * @author MW
 * 25.02.2013, 13:08:48
 * 
 */
public class clsCreateSpeech {
	public static final String P_SPEECH = "speech";
	public static final String P_PLACEMENTDISTANCE = "placement_distance";
	
	private clsEntity moEntity; // reference to this entity. necessary to determine the position
	private clsProperties moSpeechProps;
	private double mdPlacementDistance;
	
	public clsCreateSpeech(String poPrefix, clsProperties poProp, clsSpeechSystem SpeechSystem, clsEntity poEntity) {
		moEntity = poEntity;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll( clsSpeech.getDefaultProperties(pre+P_SPEECH));
		oProp.setProperty(pre+P_PLACEMENTDISTANCE, 12);
					
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		moSpeechProps = poProp.getSubset( pre+P_SPEECH );
		mdPlacementDistance = poProp.getPropertyDouble(pre+P_PLACEMENTDISTANCE);
	
	}
	
	private clsPose getPose() {
		clsPose oPose =  moEntity.getPose();
		
		double rDir = oPose.getAngle().radians;
		double rLength = mdPlacementDistance;
		clsPolarcoordinate oP = new clsPolarcoordinate(rLength, rDir);
		oPose.setPosition( oPose.getPosition().add( oP.toDouble2D() ) );
		
		return oPose;
	}
	
	public clsSpeech getSpeech(clsAbstractSpeech oAbstractSpeech, int iEcho) {
		clsSpeech oSpeech = new clsSpeech("", moSpeechProps, 
				clsUniqueIdGenerator.getUniqueId(), oAbstractSpeech, this, iEcho);
		
			oSpeech.setPose( getPose() );
		
		return oSpeech;
	}
}

