package base.datatypes;

import java.util.ArrayList;




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
	public clsWording() {
			
	} 
	
	/*	*//**
	 * DOCUMENT (hinterleitner) - insert description 
	 *
	 * @since 16.02.2013 14:38:13
	 *
	 *//*
	public clsWording() {
		// TODO (hinterleitner) - Auto-generated constructor stub
	}

		public String getWording(){
		
			String NewEnv = "Eat";
			return NewEnv;
				
		}*/

	
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
