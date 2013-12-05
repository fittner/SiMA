/**
 * eImages.java: BW - bw.factories
 * 
 * @author hinterleitner*/
package bw.factories;

/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * 
 */
public enum eStrings {
	
	Nourish("Nourish"),
	Share("Share"), 
	Welcome("Welcome"), 
    Cake("Cake"),
	ARSINBodo("Bodo"),
	ARSINAdam("Adam"),
	ARSINBella("Bella"),
	Plant("Plant"),
	Rock01("Rock"),
	SmartExcrement("Smartexcrement");
	
	private String moString;
	
	private eStrings(String poString) {
		moString = poString;
	}
	
	public String getFilename() {
		return moString;
	}
	
	public String getString() {
		return moString;
	}
	
	
}
