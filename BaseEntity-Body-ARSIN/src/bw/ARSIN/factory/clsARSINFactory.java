/**
 * CHANGELOG
 *
 * Dec 13, 2012 herret - File created
 *
 */
package bw.ARSIN.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import bw.ARSIN.clsARSIN;

import bw.entities.base.clsAnimal;
import bw.entities.base.clsBase;
import bw.entities.base.clsEntity;
import bw.entities.base.clsRemoteBot;
import bw.entities.base.clsSmartExcrement;
import bw.entities.base.clsUraniumOre;
import config.clsProperties;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;


/**
 * This class provides methodes to get the default parameter of all entities and to create a single entity
 * 
 * @author herret
 * Dec 13, 2012, 10:12:15 AM
 * 
 */
public class clsARSINFactory {

	/*
	 * holds a mapping from eEntityType to the entity classes 
	 */
	public static HashMap<eEntityType,Class> moEntities=null;
	
	private static void fillEntityMap(){
		moEntities = new HashMap<eEntityType,Class>();
		
		moEntities.put(eEntityType.ARSIN, clsARSIN.class);
		moEntities.put(eEntityType.BASE, clsBase.class);
		moEntities.put(eEntityType.REMOTEBOT, clsRemoteBot.class);
		moEntities.put(eEntityType.ANIMAL, clsAnimal.class);
		moEntities.put(eEntityType.URANIUM, clsUraniumOre.class);
		moEntities.put(eEntityType.SMARTEXCREMENT, clsSmartExcrement.class);
		
	}
	
	public static HashMap<eEntityType,Class> getEntities(){
		if(moEntities==null) clsARSINFactory.fillEntityMap();
		return moEntities;
	}
	public static clsProperties getEntityDefaultProperties(Class poEntity, String poPrefix){
		String oPrefix= clsProperties.addDot(poPrefix);
		clsProperties oProp= new clsProperties();
		Method[] oMethodes = poEntity.getMethods();
		
		for(int i=0; i<oMethodes.length; i++){
			if(oMethodes[i].getName().equals("getDefaultProperties")) {
				try {
					oProp.putAll((clsProperties)oMethodes[i].invoke(null, oPrefix));
					break;
				} catch (Exception e) {
					System.out.println("Default Properties from"+poEntity.getName() +" couldn't be loaded: There is no getDefaultProperty(String) methode");
					e.printStackTrace();
				}
			}
		}
		return oProp;
	}
	
	public static clsProperties getEntityDefaults(String poPrefix){
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		if(moEntities==null){
			fillEntityMap();
		}
		
		for(eEntityType oType : moEntities.keySet()){
			oProp.putAll(getEntityDefaultProperties(moEntities.get(oType),pre+oType.name()));
		}
		
		return oProp;
	}
	
	public static clsEntity createEntity(clsProperties poPropEntity, eEntityType pnEntityType,itfDecisionUnit poDU, int uid){
		if(moEntities==null){
			fillEntityMap();
		}
		
		String pre = clsProperties.addDot("");
		clsEntity oEntity=null;
		try {
			if(poDU==null){
				@SuppressWarnings("unchecked")
				Constructor<clsEntity> oContructor=moEntities.get(pnEntityType).getConstructor(String.class, clsProperties.class, int.class);
				oEntity = oContructor.newInstance(pre,poPropEntity,uid);
			}
			else{
				@SuppressWarnings("unchecked")
				Constructor<clsEntity> oContructor=moEntities.get(pnEntityType).getConstructor(itfDecisionUnit.class, String.class, clsProperties.class, int.class);
				oEntity = oContructor.newInstance(poDU,pre,poPropEntity,uid);
			}
			oEntity.registerEntity();

		} catch (Exception e) {
			System.out.println("The entity "+pnEntityType.name() +" doesn't have the right constructor");
			e.printStackTrace();
		}
		return oEntity;
	}
}
