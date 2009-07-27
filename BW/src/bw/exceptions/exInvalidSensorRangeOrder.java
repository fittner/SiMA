/**
 * @author zeilinger
 * 27.07.2009, 12:04:58
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
 * 27.07.2009, 12:04:58
 * 
 */
public class exInvalidSensorRangeOrder {
	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 17.07.2009, 23:48:08
	 */
	private static final long serialVersionUID = 1L;
		private double mnValue;
		private double mnSenRange;
		
		public exInvalidSensorRangeOrder(double [] pnValue, double prSenRange) {
			mnValue = pnValue[1];
			mnSenRange = prSenRange;
		}
		
		@Override
		public String toString() {
			return ("InvalidSensorRange: value "+mnSenRange +" is invalid. The minimum sensor" +
					"range exceeds another range limit "+mnValue + ". The entity's" +
					"dimensions exceeds the sensor range.\n");
		}
		/**
		 * 
		 */

}
