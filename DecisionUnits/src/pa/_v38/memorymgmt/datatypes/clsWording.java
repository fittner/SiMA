package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;




/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * @author hinterleitner
 * 26.01.2013, 21:45:47
 * 
 */
public class clsWording {

	private static final String high = null;
	private String getmoWording = "Eat"; 
	private String getWordMelody = high;
	private ArrayList<clsThingPresentationMesh> getAssociation;
	ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP;
    

	/**
	 * DOCUMENT (hinterleitner) - insert description 
	 * @param poContent1 
	 *
	 * @since 28.10.2012 22:25:48
	 *
	 */
	public clsWording(HashMap<eSensorExtType,clsSensorExtern> poContent1) {
			
	} 
	
		/**
	 * DOCUMENT (hinterleitner) - insert description 
	 *
	 * @since 16.02.2013 14:38:13
	 *
	 */
	public clsWording() {
		// TODO (hinterleitner) - Auto-generated constructor stub
	}

		public String getWording(){
		
			String NewEnv = "Eat";
			return NewEnv;
				
		}

	
		public String SetWording(String getmoWording){
			
			return this.getmoWording = getmoWording;
				
		}
		
		public Object deleteWording(){
			
			return getmoWording;
				
		}
		
		public Object getWordMelody(){
			
			return getWordMelody;
				
		}
		
		public ArrayList<clsThingPresentationMesh> getAssociation(ArrayList<clsThingPresentationMesh> moEnvironmentalPerception_IN){
			
			getAssociation = moEnvironmentalPerception_IN;
			return getAssociation;
				
		}

		/**
		 * DOCUMENT (hinterleitner) - insert description
		 *
		 * @since 16.02.2013 14:11:05
		 *
		 * @return
		 */
		public Object getExternalAssociatedContent() {
			// TODO (hinterleitner) - Auto-generated method stub
			return null;
		}


}
