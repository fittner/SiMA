/**
 * @author deutsch
 * 15.07.2009, 15:44:38
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.config;

import java.util.Random;
import bw.factories.clsSingletonMasonGetter;
import ec.util.MersenneTwisterFast;

/**
 * random number generator for property files. 
 * 
 * each time the value of some.key is read, a new random value is returned. four different types of random number generators are available: linear, gaussian, gaussian tail, and bounded gaussian.
 * 
 * 
 * @author deutsch
 * 15.07.2009, 15:44:38
 * 
 */
public class clsRandomProperties {
	/**
	 * the string poParams is returned without poIdentifier at its start
	 *
	 * @author deutsch
	 * 22.07.2009, 09:40:35
	 *
	 * @param poParams
	 * @param poIdentifier
	 * @return
	 */
	private static String removeIdentifier(String poParams, String poIdentifier) {
		return poParams.substring(poIdentifier.length());
	}
	
	/**
	 * converts a property string to a matching random value.
	 *
	 * usage: 
	 * 		$L34.55;99.0  				// L ... linear distribution // lower bound // upper bound
	 * 		$G1.0;20.0					// G ... gaussian distribution // mean // std
	 *      $BG1.0;20.0;-10.0;100.0     // BG ... bounded gaussian distribution // mean // std // lower bound // upper bound
	 *      $TG1.0;20.0					// TG ... gaussian tail distribution // mean // std
	 *      
	 * @author deutsch
	 * 22.07.2009, 09:41:30
	 *
	 * @param poParams
	 * @return
	 * @throws java.lang.UnsupportedOperationException
	 * @throws java.lang.NullPointerException
	 */
	public static double getRandom(String poParams) throws java.lang.UnsupportedOperationException, java.lang.NullPointerException {
		abstract class RandomGenerator {
			protected String[] oParams;
			protected MersenneTwisterFast moRandomTwister;
			protected Random moRandomJava;
			
			public RandomGenerator(String poParams) {
				moRandomTwister = null;
				moRandomJava = null;
				
				try {
					moRandomTwister = clsSingletonMasonGetter.getSimState().random;
				} catch (java.lang.NullPointerException e) {
					moRandomJava = new Random();
				}
				
				oParams = poParams.split(";");
			}
			
			public abstract double getValue(); 
		}
		
		class LinearRandom extends RandomGenerator {
			public static final String P_IDENTIFIER = "L"; // important: should be unique 
			protected double mrFrom;
			protected double mrTo;
			
			public LinearRandom(String poParams) {
				super(poParams);
				
				mrFrom = Double.parseDouble(oParams[0]);
				mrTo = Double.parseDouble(oParams[1]);			
			}

			@Override
			public double getValue() {
				double r = 0;
				
				if (moRandomTwister != null) {
					r = moRandomTwister.nextDouble();
				} else {
				    r = moRandomJava.nextDouble();
				}
				
				r = mrFrom + r*(mrTo-mrFrom);
				return r;
			}
		}
		
		class GaussianRandom extends RandomGenerator {
			public static final String P_IDENTIFIER = "G"; // important: should be unique 
			protected double mrMean;
			protected double mrSigma;
			
			public GaussianRandom(String poParams) {
				super(poParams);
				
				mrMean = Double.parseDouble(oParams[0]);
				mrSigma = Double.parseDouble(oParams[1]);			
			}

			@Override
			public double getValue() {
				double r = 0;
				
				if (moRandomTwister != null) {
					r = moRandomTwister.nextGaussian();
				} else {
				    r = moRandomJava.nextGaussian();
				}				

				r = mrMean + r*mrSigma;
				return r;
			}
		}	
		
		class BoundedGaussianRandom extends GaussianRandom {
			public static final String P_IDENTIFIER = "BG"; // important: should be unique e.g. "GB" would lead to never calling bounded gaussian due to the fact that gaussian has "G" as identifier!
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
		
		class GaussianTailRandom extends GaussianRandom {
			public static final String P_IDENTIFIER = "TG"; // important: should be unique e.g. "GT" would lead to never calling bounded gaussian due to the fact that gaussian has "G" as identifier!
			
			public GaussianTailRandom(String poParams) {
				super(poParams);
			}
			
			@Override
			public double getValue() {
				double r = 0;
				
				if (moRandomTwister != null) {
					r = moRandomTwister.nextGaussian();
				} else {
				    r = moRandomJava.nextGaussian();
				}			
				
				r = Math.abs(r);

				r = mrMean + r*mrSigma;
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
		} else if (poParams.startsWith(GaussianTailRandom.P_IDENTIFIER)) {
			oRandom = new GaussianTailRandom( clsRandomProperties.removeIdentifier(poParams, GaussianTailRandom.P_IDENTIFIER) );
		} else {
			throw new java.lang.UnsupportedOperationException();
		}
		
		if (oRandom != null) {
			result = oRandom.getValue();
		} else {
			throw new java.lang.NullPointerException();
		}
		
		return result;
	}
	
}
