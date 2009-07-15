/**
 * @author deutsch
 * 15.07.2009, 15:44:38
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.config;

import ec.util.MersenneTwisterFast;
import bw.factories.clsSingletonMasonGetter;

/**
 * usage: 
 * 		$L34.55;99.0  				// L ... linear distribution // lower bound // upper bound
 * 		$G1.0;20.0					// G ... gaussian distribution // mean // std
 *      $BG1.0;20.0;-10.0;100.0     // BG ... bounded gaussian distribution // mean // std // lower bound // upper bound
 * 
 * @author deutsch
 * 15.07.2009, 15:44:38
 * 
 */
public class clsRandomProperties {
	private static String removeIdentifier(String poParams, String poIdentifier) {
		return poParams.substring(poIdentifier.length());
	}
	
	public static double getRandom(String poParams) {
		abstract class RandomGenerator {
			protected String[] oParams;
			protected MersenneTwisterFast moRandom;
			
			public RandomGenerator(String poParams) {
				moRandom = clsSingletonMasonGetter.getSimState().random;		
				oParams = poParams.split(";");
			}
			
			public abstract double getValue(); 
		}
		
		class LinearRandom extends RandomGenerator {
			public static final String P_IDENTIFIER = "L";
			protected double mrFrom;
			protected double mrTo;
			
			public LinearRandom(String poParams) {
				super(poParams);
				
				mrFrom = Double.parseDouble(oParams[0]);
				mrTo = Double.parseDouble(oParams[1]);			
			}

			@Override
			public double getValue() {
				double r = moRandom.nextDouble();
				r = mrFrom + r*(mrTo-mrFrom);
				return r;
			}
		}
		
		class GaussianRandom extends RandomGenerator {
			public static final String P_IDENTIFIER = "G";
			protected double mrMean;
			protected double mrSigma;
			
			public GaussianRandom(String poParams) {
				super(poParams);
				
				mrMean = Double.parseDouble(oParams[0]);
				mrSigma = Double.parseDouble(oParams[1]);			
			}

			@Override
			public double getValue() {
				double r = moRandom.nextDouble();
				r = mrMean + r*mrSigma;
				return r;
			}
		}	
		
		class BoundedGaussianRandom extends GaussianRandom {
			public static final String P_IDENTIFIER = "BG";
			protected double mrLowerBound;
			protected double mrUpperBound;			
			
			public BoundedGaussianRandom(String poParams) {
				super(poParams);
				mrLowerBound = Double.parseDouble(oParams[2]);
				mrUpperBound = Double.parseDouble(oParams[3]);					
			}
			
			@Override
			public double getValue() {
				double r=super.getValue();
				if (r<mrLowerBound) {
					r=mrLowerBound;
				} else if (r>mrUpperBound) {
					r=mrUpperBound;
				}
				return r;
			}
		}
		
		double result = 0.0;
		RandomGenerator oRandom = null;
		
		if (poParams.startsWith(LinearRandom.P_IDENTIFIER)) {
			oRandom = new LinearRandom( clsRandomProperties.removeIdentifier(poParams, LinearRandom.P_IDENTIFIER) );
		} else if (poParams.startsWith(GaussianRandom.P_IDENTIFIER)) {
			oRandom = new GaussianRandom( clsRandomProperties.removeIdentifier(poParams, GaussianRandom.P_IDENTIFIER) );
		} else if (poParams.startsWith(BoundedGaussianRandom.P_IDENTIFIER)) {
			oRandom = new BoundedGaussianRandom( clsRandomProperties.removeIdentifier(poParams, BoundedGaussianRandom.P_IDENTIFIER) );
		}  
		
		if (oRandom != null) {
			result = oRandom.getValue();
		}
		
		return result;
	}
}
