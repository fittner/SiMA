/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import config.clsProperties;
import bw.entities.base.clsEntity;
import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFood;


/**
 * UNREAL USE ONLY
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * BUT ONLY FOR USE WITH THE UNREAL-ENGINE-BOT IMPLEMENTATION
 * 
 * 
 * @author muchitsch
 * 
 */
public class clsUnrealBody extends clsComplexBody  {
    
    //unreal specific values
 	private double moUnrealArmor;
    private double moUnrealAmmo;
      
    
    /**
     * Constructor for the unreal body, load config parameters here 
     *
     * @since 20.06.2012 14:25:05
     *
     * @param poPrefix
     * @param poProp
     * @param poEntity
     */
    public clsUnrealBody(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:35:37
	 * 
	 * @see bw.body.clsBaseBody#setBodyType()
	 */
	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.UNREAL;		
	}
	

	/**
	 * @since 20.06.2012 14:31:08
	 * 
	 * @return the moUnrealArmor
	 */
	public double getUnrealArmor() {
		return moUnrealArmor;
	}

	/**
	 * @since 20.06.2012 14:31:08
	 * 
	 * @param moUnrealArmor the moUnrealArmor to set
	 */
	public void setUnrealArmor(double moUnrealArmor) {
		this.moUnrealArmor = moUnrealArmor;
	}

	/**
	 * @since 20.06.2012 14:31:08
	 * 
	 * @return the moUnrealAmmo
	 */
	public double getUnrealAmmo() {
		return moUnrealAmmo;
	}

	/**
	 * @since 20.06.2012 14:31:08
	 * 
	 * @param moUnrealAmmo the moUnrealAmmo to set
	 */
	public void setUnrealAmmo(double moUnrealAmmo) {
		this.moUnrealAmmo = moUnrealAmmo;
	}
	
	

	
	/**
	 * Removes all Nutrition's from the StomachSystem and thus removes all Energy.
	 * This creates large hunger. 
	 * This is for TESTING purposes of the model only
	 *
	 * @since 21.06.2012 11:10:30
	 *
	 */
	public void DestroyAllNutritionAndEnergyForModelTesting() {
		super.getInternalSystem().getStomachSystem().DestroyAllEnergyForModelTesting();
	}
	
	public double getInternalHealthValue() {
		return super.getInternalSystem().getHealthSystem().getHealth().getContent();
	}
	
	public void HurtBody(double rDamage) {
		super.getInternalSystem().getHealthSystem().hurt(rDamage);
	}
	
	public void HealBody(double rAmount) {
		super.getInternalSystem().getHealthSystem().heal(rAmount);
	}
	
	public void EatHealthPack(){

		clsFood oFood = new clsFood();
		try {
			oFood.setWeight(6f);
		} catch (exFoodWeightBelowZero e) {
			e.printStackTrace();
		}
		try {
		oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0) );
		oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.MINERAL, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.PROTEIN, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.VITAMIN, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.WATER, new clsMutableDouble(1.0f));
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}

		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}
		
        super.getInterBodyWorldSystem().getConsumeFood().digest(oFood);   
	}
	

	
	
	
	
	
}
