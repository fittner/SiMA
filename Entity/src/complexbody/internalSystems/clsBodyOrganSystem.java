
package complexbody.internalSystems;

import java.util.ArrayList;

import org.jfree.util.Log;

import properties.clsProperties;
import body.itfStepUpdateInternalState;
import body.attributes.clsBodyOrganArms;
import body.attributes.clsBodyOrganHeart;
import body.attributes.clsBodyOrganLegs;
import body.attributes.clsBodyOrganStomach;
import body.attributes.clsBodyOrganSweatGlands;

import complexbody.expressionVariables.clsExpressionVariable;
import complexbody.expressionVariables.clsExpressionVariableCheeksRedning;
import complexbody.expressionVariables.clsExpressionVariableFacialEyeBrows;
import complexbody.expressionVariables.clsExpressionVariableFacialEyes;
import complexbody.expressionVariables.clsExpressionVariableFacialMouth;
import complexbody.expressionVariables.clsExpressionVariableGeneralSweat;
import complexbody.expressionVariables.clsExpressionVariablePartialSweat;
import complexbody.expressionVariables.clsExpressionVariableShake;



public class clsBodyOrganSystem implements itfStepUpdateInternalState {

	public static final String P_BODYORGANARMS = "bodyorganarms";
	public static final String P_BODYORGANHEART = "bodyorganheart";
	public static final String P_BODYORGANLEGS = "bodyorganlegs";
	public static final String P_BODYORGANSWEATGLANDS = "bodyorgansweatglands";
	
	public static final String P_BODYORGANEXPRESSIONS = "bodyorganexpressions";
	public static final String P_BODYORGANEXPRESSION_SHAKE = "shake";
	public static final String P_BODYORGANEXPRESSION_CHEEKSREDNING = "cheeksredning";
	public static final String P_BODYORGANEXPRESSION_FACIALEYEBROWS = "facialeyebrows";
	public static final String P_BODYORGANEXPRESSION_FACIALEYES = "facialeyes";
	public static final String P_BODYORGANEXPRESSION_FACIALMOUTH = "facialmouth";
	public static final String P_BODYORGANEXPRESSION_GENERALSWEAT = "generalsweat";
	public static final String P_BODYORGANEXPRESSION_PARTIALSWEAT = "partialsweat";
	
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
		//initializeExpressionList();
		
		
//		moPersonalityParameterContainer = poPersonalityParameterContainer;
		
		applyProperties(poPrefix, poProp);

	}


	/**
 * DOCUMENT (herret) - insert description
 *
 * @since 25.09.2014 14:07:46
 *
 */
/*private void initializeExpressionList() {
	moExpressionsList.add( new clsExpressionVariableShake() );
	moExpressionsList.add( new clsExpressionVariableCheeksRedning() );
	moExpressionsList.add( new clsExpressionVariableFacialEyeBrows() );
	moExpressionsList.add( new clsExpressionVariableFacialEyes() );
	moExpressionsList.add( new clsExpressionVariableFacialMouth() );
	moExpressionsList.add( new clsExpressionVariableGeneralSweat() );
	moExpressionsList.add( new clsExpressionVariablePartialSweat() );
	//moExpressionsList.add( new clsExpressionVariableShake() );
	
	
}*/


	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll( clsBodyOrganArms.getDefaultProperties(pre+P_BODYORGANARMS) );
		oProp.putAll( clsBodyOrganHeart.getDefaultProperties(pre+P_BODYORGANHEART) );
		oProp.putAll( clsBodyOrganLegs.getDefaultProperties(pre+P_BODYORGANLEGS) );
		oProp.putAll( clsBodyOrganSweatGlands.getDefaultProperties(pre+P_BODYORGANSWEATGLANDS) );

		//kollmann: now extend the pre string for the expression varaibles
		pre = clsProperties.addDot(pre + P_BODYORGANEXPRESSIONS);
		
		oProp.putAll( clsExpressionVariableShake.getDefaultProperties(pre + P_BODYORGANEXPRESSION_SHAKE));
		oProp.putAll( clsExpressionVariableCheeksRedning.getDefaultProperties(pre + P_BODYORGANEXPRESSION_CHEEKSREDNING));
		oProp.putAll( clsExpressionVariableFacialEyeBrows.getDefaultProperties(pre + P_BODYORGANEXPRESSION_FACIALEYEBROWS));
		oProp.putAll( clsExpressionVariableFacialEyes.getDefaultProperties(pre + P_BODYORGANEXPRESSION_FACIALEYES));
		oProp.putAll( clsExpressionVariableFacialMouth.getDefaultProperties(pre + P_BODYORGANEXPRESSION_FACIALMOUTH));
		oProp.putAll( clsExpressionVariableGeneralSweat.getDefaultProperties(pre + P_BODYORGANEXPRESSION_GENERALSWEAT));
		oProp.putAll( clsExpressionVariablePartialSweat.getDefaultProperties(pre + P_BODYORGANEXPRESSION_PARTIALSWEAT));
		
		return oProp;

	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);

	    moBOArms = new clsBodyOrganArms(pre+P_BODYORGANARMS, poProp);
	    moBOHeart = new clsBodyOrganHeart(pre+P_BODYORGANHEART, poProp);
	    moBOLegs = new clsBodyOrganLegs(pre+P_BODYORGANLEGS, poProp);
	    moBOSweatGlands = new clsBodyOrganSweatGlands(pre+P_BODYORGANSWEATGLANDS, poProp);

	    //kollmann: now extend the pre string for the expression varaibles
	    pre = clsProperties.addDot(pre + P_BODYORGANEXPRESSIONS);
	    try {
		    moExpressionsList.add( new clsExpressionVariableShake(pre + P_BODYORGANEXPRESSION_SHAKE, poProp) );
			moExpressionsList.add( new clsExpressionVariableCheeksRedning(pre + P_BODYORGANEXPRESSION_CHEEKSREDNING, poProp) );
			moExpressionsList.add( new clsExpressionVariableFacialEyeBrows(pre + P_BODYORGANEXPRESSION_FACIALEYEBROWS, poProp) );
			moExpressionsList.add( new clsExpressionVariableFacialEyes(pre + P_BODYORGANEXPRESSION_FACIALEYES, poProp) );
			moExpressionsList.add( new clsExpressionVariableFacialMouth(pre + P_BODYORGANEXPRESSION_FACIALMOUTH, poProp) );
			moExpressionsList.add( new clsExpressionVariableGeneralSweat(pre + P_BODYORGANEXPRESSION_GENERALSWEAT, poProp) );
			moExpressionsList.add( new clsExpressionVariablePartialSweat(pre + P_BODYORGANEXPRESSION_PARTIALSWEAT, poProp) );
	    } catch(NumberFormatException e) {
	    	Log.error("Number format exception in when applying expression properties in body organ system");
	    }
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
