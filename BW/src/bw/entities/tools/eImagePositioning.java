/**
 * @author tobias
 * Jul 24, 2009, 10:55:43 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities.tools;

/**
 * DOCUMENT (tobias) - insert description 
 * 
 * @author tobias
 * Jul 24, 2009, 10:55:43 PM
 * 
 */
public enum eImagePositioning {
	DEFAULT,
	CENTER, //the image is centered on shape and not scaled 1)
	TOPLEFT,
	TOPRIGHT,
	BOTTOMLEFT,
	BOTTOMRIGHT,
	TILEING,
	STRETCHING, //the image is scaled and streched in x and y to meet the underlying physical shape
}

/*
 * 1) if the image is taller than it is wide, then the width of the image will be info.draw.width * scale and the height
   will stay in proportion; else the height of the image will be info.draw.height * scale and the width will stay in proportion.
 * 
 * 2) 
 */

