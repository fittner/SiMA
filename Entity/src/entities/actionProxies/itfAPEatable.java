package entities.actionProxies;

import body.utils.clsFood;

/**
 * Proxy-Interface for action eat
 * Method tryEat: will be called before eating 
 * Method Eat: eat the entity 
 *  
 * @author Benny Doenz
 * 20.06.2009, 15:38:41
 * 
 */
public interface itfAPEatable {

	/*
	 * returns 0 if ok, otherwise a value for the damage inflicted
	 */
	double tryEat();
	
	
	/*
	 * Inform the entity it has been eaten and get food as a result. 
	 * Will only be executed if tryEat returns 0 as result
	 * Parameters
	 * 	prBiteSize=Size of the bite (default=1)
	 */
	clsFood Eat(double prBiteSize);
}
