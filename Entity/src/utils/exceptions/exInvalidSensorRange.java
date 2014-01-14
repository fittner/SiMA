/**
 * @author zeilinger
 * 17.07.2009, 23:42:37
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package utils.exceptions;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 17.07.2009, 23:42:37
 * 
 */
public class exInvalidSensorRange extends exException{

		/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 17.07.2009, 23:48:08
	 */
	private static final long serialVersionUID = 1L;
		private double [] mnValue;
		private double mnSenRange;
		private String moRange; 

		public exInvalidSensorRange(double [] pnValue, double prSenRange) {
			mnValue = pnValue;
			mnSenRange = prSenRange;
			
			for(double element : mnValue){
				moRange += " "+element; 
			}
		}
		
		@Override
		public String toString() {
			return ("InvalidSensorRange: value "+mnSenRange +" is invalid. The sensor range has to match "+moRange + "\n");
		}
		/**
		 * 
		 */
}
