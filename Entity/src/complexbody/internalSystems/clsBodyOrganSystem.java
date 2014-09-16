
package complexbody.internalSystems;

import java.util.ArrayList;

import properties.clsProperties;

import complexbody.expressionVariables.clsExpressionVariable;
import complexbody.expressionVariables.clsExpressionVariableCheeksRedning;
import complexbody.expressionVariables.clsExpressionVariablePartialSweat;
import complexbody.expressionVariables.clsExpressionVariableShake;

import body.itfStepUpdateInternalState;
import body.attributes.clsBodyOrganArms;
import body.attributes.clsBodyOrganHeart;
import body.attributes.clsBodyOrganLegs;
import body.attributes.clsBodyOrganStomach;
import body.attributes.clsBodyOrganSweatGlands;



public class clsBodyOrganSystem implements itfStepUpdateInternalState {

	public static final String P_BODYORGANARMS = "bodyorganarms";
	public static final String P_BODYORGANHEART = "bodyorganheart";
	public static final String P_BODYORGANLEGS = "bodyorganlegs";
	public static final String P_BODYORGANSWEATGLANDS = "bodyorgansweatglands";
	
    // body parts
	private ArrayList<clsExpressionVariable> moExpressionsList = null;
    private clsBodyOrganArms moBOArms;
    private clsBodyOrganHeart moBOHeart;
    private clsBodyOrganLegs moBOLegs;
    private clsBodyOrganSweatGlands moBOSweatGlands;
    private clsBodyOrganStomach moBOStomach;
	
//	private clsPersonalityParameterContainer moPersonalityParameterContainer;
	
    public clsBodyOrganSystem(String poPrefix, clsProperties poProp) {

		moExpressionsList = new ArrayList<clsExpressionVariable>();
		
		
//		moPersonalityParameterContainer = poPersonalityParameterContainer;
		
		applyProperties(poPrefix, poProp);

	}


	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll( clsBodyOrganArms.getDefaultProperties(pre+P_BODYORGANARMS) );
		oProp.putAll( clsBodyOrganHeart.getDefaultProperties(pre+P_BODYORGANHEART) );
		oProp.putAll( clsBodyOrganLegs.getDefaultProperties(pre+P_BODYORGANLEGS) );
		oProp.putAll( clsBodyOrganSweatGlands.getDefaultProperties(pre+P_BODYORGANSWEATGLANDS) );

		return oProp;

	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);

	    moBOArms = new clsBodyOrganArms(pre+P_BODYORGANARMS, poProp);
	    moBOHeart = new clsBodyOrganHeart(pre+P_BODYORGANHEART, poProp);
	    moBOLegs = new clsBodyOrganLegs(pre+P_BODYORGANLEGS, poProp);
	    moBOSweatGlands = new clsBodyOrganSweatGlands(pre+P_BODYORGANSWEATGLANDS, poProp);

	}


	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	@Override
	public void stepUpdateInternalState() {
		// drawing for the expressions will be on ComplexBody.stepUpdateInternalState()
		
		// Set the ExpressionsVariable's in consideration of BodyOrgan variables
		if ( this.getBOSweatGlands().getStressSweatIntensity() > 0.01) 
		{
			int positionOfEVPartialSweat = -1;
			for(clsExpressionVariable ev : moExpressionsList){
				if( ev instanceof clsExpressionVariablePartialSweat ){
					positionOfEVPartialSweat = moExpressionsList.indexOf(ev);
				}
			}
			
			if(positionOfEVPartialSweat != -1){
				// EV found! no need to create a new one
				moExpressionsList.get(positionOfEVPartialSweat).triggerExpression( this.getBOSweatGlands().getStressSweatIntensity( ) ); // ( intensityOfOrganVariable / 2.25) = (intensityOfExpression / 1.0)
			}
			else{
				// EV not found! create a new one
				moExpressionsList.add( new clsExpressionVariablePartialSweat() );
				moExpressionsList.get( (moExpressionsList.size() - 1) ).triggerExpression( this.getBOSweatGlands().getStressSweatIntensity( ) ); // ( intensityOfOrganVariable / 2.25) = (intensityOfExpression / 1.0)
			}
		}
		if ( this.getBOArms().getTensionIntensity() > 0.01)
		{
			int positionOfEVShake = -1;
			for(clsExpressionVariable ev : moExpressionsList){
				if( ev instanceof clsExpressionVariableShake ){
					positionOfEVShake = moExpressionsList.indexOf(ev);
				}
			}
			
			if(positionOfEVShake != -1){
				// EV found! no need to create a new one
				moExpressionsList.get(positionOfEVShake).triggerExpression( this.getBOArms().getTensionIntensity() ); // ( intensityOfOrganVariable / 2.25) = (intensityOfExpression / 1.0)
			}
			else{
				// EV not found! create a new one
				moExpressionsList.add( new clsExpressionVariableShake() );
				moExpressionsList.get( (moExpressionsList.size() - 1) ).triggerExpression( this.getBOArms().getTensionIntensity() ); // ( intensityOfOrganVariable / 2.25) = (intensityOfExpression / 1.0)
			}
		}
		if ( this.getBOHeart().getBloodPressureIntensity() >= 0.0 )
		{
			int positionOfEVCheeksRedning = -1;
			double nCheekRedningIntensity = this.getBOHeart().getBloodPressureIntensity();
			
			for(clsExpressionVariable ev : moExpressionsList){
				if( ev instanceof clsExpressionVariableCheeksRedning ){
					positionOfEVCheeksRedning = moExpressionsList.indexOf(ev);
				}
			}
			
			if(positionOfEVCheeksRedning != -1){
				// EV found! no need to create a new one
				moExpressionsList.get(positionOfEVCheeksRedning).triggerExpression( nCheekRedningIntensity );
			}
			else{
				// EV not found! create a new one
				moExpressionsList.add( new clsExpressionVariableCheeksRedning() );
				moExpressionsList.get( (moExpressionsList.size() - 1) ).triggerExpression( nCheekRedningIntensity );
			}
		}
	} // end stepUpdateInternalState
	
	// getter and setter's for body parts

	public clsBodyOrganArms getBOArms() {
		return moBOArms;
	}
	public void setBOArms(clsBodyOrganArms poBOArms) {
		this.moBOArms = poBOArms;
	}

	public clsBodyOrganHeart getBOHeart() {
		return moBOHeart;
	}
	public void setBOHeart(clsBodyOrganHeart poBOHeart) {
		this.moBOHeart = poBOHeart;
	}

	public clsBodyOrganLegs getBOLegs() {
		return moBOLegs;
	}
	public void setBOLegs(clsBodyOrganLegs poBOLegs) {
		this.moBOLegs = poBOLegs;
	}

	public clsBodyOrganSweatGlands getBOSweatGlands() {
		return moBOSweatGlands;
	}
	public void setBOSweatGlands(clsBodyOrganSweatGlands poBOSweatGlands) {
		this.moBOSweatGlands = poBOSweatGlands;
	}

	public clsBodyOrganStomach getBOStomach() {
		return moBOStomach;
	}
	public void setBOStomach(clsBodyOrganStomach poBOStomach) {
		this.moBOStomach = poBOStomach;
	}


	/**
	 * @since May 13, 2014 7:06:01 PM
	 * 
	 * @return the moExpressionsList
	 */
	public ArrayList<clsExpressionVariable> getExpressionsList() {
		return moExpressionsList;
	}	

}
