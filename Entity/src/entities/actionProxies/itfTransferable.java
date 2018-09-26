/**
 * CHANGELOG
 *
 * Oct 31, 2015 zhukova - File created
 *
 */
package entities.actionProxies;

import entities.abstractEntities.clsMobile;

/**
 * DOCUMENT (zhukova) - insert description 
 * 
 * @author zhukova
 * Oct 31, 2015, 7:46:05 PM
 * 
 */
public interface itfTransferable {

	/*
	 *  Tries to get entity to which we are trying to tranfer
	 */
	
	clsMobile getTransferedEntity();
		
	
}
