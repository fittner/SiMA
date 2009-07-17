/**
 * @author zeilinger
 * 17.07.2009, 23:42:37
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 17.07.2009, 23:42:37
 * 
 */
public class exInvalidSensorRange extends exException{

		/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 17.07.2009, 23:48:08
	 */
	private static final long serialVersionUID = 1L;
		private double [] mnValue;
		private double mnSenRange;

		public exInvalidSensorRange(double [] pnValue, double prSenRange) {
			mnValue = pnValue;
			mnSenRange = prSenRange;
		}
		
		@Override
		public String toString() {
			return ("InvalidSensorRange: value "+mnSenRange +" is invalid. The sensor range has to match "+mnValue.toString() + "\n");
		}
		/**
		 * 
		 */
}
