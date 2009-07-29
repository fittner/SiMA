/**
 * @author kilic
 * 26.05.2009, 12:36:41
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Diese Klasse beschreibt den einzelnen F�llstand der Nutrition
 * 
 * @author kilic 26.05.2009, 12:36:41
 * 
 */
public class clsSingleFillLevel extends JPanel {

	private static final long serialVersionUID = 6911103242202927797L;
	
	public int id;
	private double mrMinContentFloat;
	private double mrMaxContentFloat;
	private double mrMinFloat;
	private double mrMaxFloat;
	private double mrValueFloat;
	//private float mrMinContentInt;
	//private float mrMaxContentInt;
	private int mrMinInt;
	private int mrMaxInt;
	
	//private int mrValueInt;
	private int x;
	private int y;
	
	//private int mrMinContent;
	private int mrMaxContent;
	private int mrMin;
	private int mrMax;
	private int mrValue;
	private BorderLayout border;
	/** Leere Konstruktor */
	public clsSingleFillLevel() {

	}
	/**
	 * diese Konstruktor empf�ngt die max. und min. und die aktuelle Werte im
	 * Format von Float
	 */
	public clsSingleFillLevel(int id, double min, double max, double minContent, double maxContent, double value, int x, int y) {
		this.mrMinFloat=min;
		this.mrMaxFloat=max;
		this.initValue(value);
		//this.initMinAndMax();
		this.initMinAndMax(min,max,minContent,maxContent);
		this.init(id,x, y);
		//System.out.println("id: "+id+" min: "+min+" max: "+max+" value: "+value+" x: "+x+" y: "+y);
	}
	
	

	/**
	 * diese Konstruktor empf�ngt die max. und min. und die aktuelle Werte im
	 * Format von Integer
	 */
	public clsSingleFillLevel(int id, int min, int max, int value, int x, int y) {
		this.mrMinInt=min;
		this.mrMaxInt=max;
		this.mrValue=value;
		this.init(id,x, y);
	}
	
	private void init(int id, int x, int y){
		this.id=id;
		border = new BorderLayout();
		
		//super.setSize(100, 100);
		//super.setLayout(border);
		//super.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "N"+(id), TitledBorder.CENTER, TitledBorder.BOTTOM,new Font("SansSerif ",Font.BOLD,10),Color.orange));
		//super.add(new JLabel(""+id), BorderLayout.SOUTH);
		
		//this.setSize(100, 100);
		this.setLayout(border);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "N"+(id), TitledBorder.CENTER, TitledBorder.BOTTOM,new Font("SansSerif ",Font.BOLD,10),Color.orange));
		//this.add(new JLabel(""+id), BorderLayout.SOUTH);

		this.x=x;
		this.y=y;
		
	}
	/**************************************************************************
	 * 
	 * Die Werte min, max und Value werden f�r die Aufzeichnung
	 * initalisiert
	 *
	 * @author kilic
	 * 26.05.2009, 13:41:46
	 * @param min ist minimale wert
	 * @param max ist max. Wert
	 * @param minContent ist min. Content-Wert
	 * @param maxContent ist max. Content-Wert
	 **************************************************************************/
	private void initMinAndMax(double min, double max, double minContent, double maxContent){
		this.mrMaxContentFloat=maxContent;
		this.mrMinContentFloat=minContent;
		this.mrMin = (int) (min * 10);
		this.mrMax = (int) (max * 10);
		//this.mrMinContent = (int) (minContent*10);
		this.mrMaxContent = (int) (maxContent*10);
	}
	/*************************************************************************
	 * 
	 * Der Value wird f�r die Aufzeichnung initalisiert
	 *
	 * @author kilic
	 * 26.05.2009, 13:46:20
	 *
	 * @param value
	 *************************************************************************/
	public void initValue(double value){
		this.mrValueFloat=value;
		//this.mrValueFloat=(float)3.35;
		this.mrValue=(int)( this.mrValueFloat * 10);
	}
	/**************************************************************************
	 * 
	 * Der aktuelle Wert in Form von Float wird aktualisiert
	 *
	 * @author kilic
	 * 26.05.2009, 13:43:10
	 *
	 * @param value
	 **************************************************************************/
	public void updateFloat(double value){
		this.initValue(value);
		this.repaint();
	}
	/*************************************************************************
	 * 
	 * Der aktuelle Wert in Form von Integer wird aktualisiert
	 *
	 * @author kilic
	 * 26.05.2009, 13:44:05
	 *
	 * @param value
	 *************************************************************************/
	public void updateInteger(int value){
		this.initValue(value);
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		// HIER ZEICHNEST DU
		this.paintAnalysisOfANutrition(g);
	}

	/********************************************************************************
	 * 
	 * F�r die Auswertung werden die n�tigen Werte und
	 * Koordinaten eingegeben. Die Werte sind in Float. Die K�stschen werden
	 * nach dem Float eingestellt.
	 * 
	 * @author kilic 14.05.2009, 14:05:30
	 * 
	 * @param g
	 * @param valueo
	 *            ist aktueller Wert
	 ********************************************************************************/
	public void paintAnalysisOfANutrition(Graphics g) {
		//this.value=3;
//		System.out.println( "minCont: "+this.mrMinContent+" maxCont: "+this.mrMaxContent+ " value: "+this.mrValueFloat);
		//System.out.println( "min: "+this.mrMinFloat+" max: "+this.mrMaxFloat+ " value: "+this.mrValueFloat);
		g.setColor(Color.black);
		//minContent-Wert
		g.drawString((""+this.mrMinContentFloat).substring(0, 3), this.x - 35, this.y+10);
		//g.drawString("min", this.x - 35, this.y - this.min);
		//minContent-Linie
		g.drawLine((this.x-10), this.y, (this.x + 20), this.y);
		//maxContent-Wert
		
		//minContent-Linie
		int yy=(this.y-this.mrMaxContent);
		g.drawString((""+this.mrMaxContentFloat).substring(0, 3), this.x - 35, yy);
		g.drawLine((this.x-10), yy, (this.x + 20), yy);
		
		g.drawString((""+this.mrMinFloat).substring(0, 3), this.x - 35, this.y - this.mrMin);
		g.drawLine((this.x - 5), (this.y - this.mrMin), (this.x + 13), (this.y - this.mrMin));
		//g.drawString("max", this.x - 35, this.y - this.max);
		g.drawString((""+this.mrMaxFloat).substring(0, 3), this.x - 35, this.y - this.mrMax);
		g.drawLine((this.x - 5), (this.y - this.mrMax), (this.x + 13), (this.y - this.mrMax));
		//g.drawString(""+this.value, this.x+15, this.y-this.value);
		//g.drawString((""+this.mrValueFloat).substring(0, 3), this.x+20, this.y-this.value);
		g.drawString((""+this.mrValueFloat).substring(0, 3), this.x-2, this.y+20);

		// g.fillRect(x, (yy-value), 10, value);

		if (this.mrValue <= this.mrMin) {
			g.setColor(Color.red);
			// g.setColor(Color.yellow);
			g.fillRect(this.x, (this.y - this.mrValue), 10, this.mrValue);
		} else if (this.mrValue > this.mrMin && this.mrValue < this.mrMax) {
			g.setColor(Color.red);
			g.fillRect(this.x, (this.y - this.mrMin), 10, this.mrMin);
			g.setColor(Color.orange);
			g.fillRect((this.x), (this.y - this.mrValue), 10, this.mrValue);
			//g.fillRect((this.x), (this.y - this.value), 10, (this.value - this.min));
		} else if (this.mrValue >= this.mrMax) {
			//g.setColor(Color.red);
			//g.fillRect(this.x, (this.y - this.min), 10, this.min);
			g.setColor(Color.orange);
			g.fillRect((this.x), (this.y - this.mrMax), 10, this.mrMax);
			g.setColor(Color.red);

			g.fillRect((this.x), (this.y - this.mrValue), 10, (this.mrValue - this.mrMax));
		}
		g.drawLine(this.x, this.y, (this.x), (this.y - this.mrMaxContent));
		g.drawLine(this.x+10, this.y, (this.x+10), (this.y - this.mrMaxContent));

	}
	public void paintAnalysisOfANutrition1(Graphics g) {
		//this.value=3;
		System.out.println( "min: "+this.mrMinFloat+" max: "+this.mrMaxFloat+ " value: "+this.mrValueFloat);
		//System.out.println("Neue Content: "+this.value);
		g.setColor(Color.black);
		//g.drawString("min", this.x - 35, this.y - this.min);
		g.drawString((""+this.mrMinFloat).substring(0, 3), this.x - 35, this.y - this.mrMin);
		g.drawLine((this.x - 10), (this.y - this.mrMin), (this.x + 20), (this.y - this.mrMin));
		//g.drawString("max", this.x - 35, this.y - this.max);
		g.drawString((""+this.mrMaxFloat).substring(0, 3), this.x - 35, this.y - this.mrMax);
		g.drawLine((this.x - 10), (this.y - this.mrMax), (this.x + 20), (this.y - this.mrMax));
		//g.drawString(""+this.value, this.x+15, this.y-this.value);
		g.drawString((""+this.mrValueFloat).substring(0, 3), this.x+20, this.y-this.mrValue);

		// g.fillRect(x, (yy-value), 10, value);

		if (this.mrValue <= this.mrMin) {
			g.setColor(Color.red);
			// g.setColor(Color.yellow);
			g.fillRect(this.x, (this.y - this.mrValue), 10, this.mrValue);
		} else if (this.mrValue > this.mrMin && this.mrValue < this.mrMax) {
			g.setColor(Color.red);
			g.fillRect(this.x, (this.y - this.mrMin), 10, this.mrMin);
			g.setColor(Color.orange);
			g.fillRect((this.x), (this.y - this.mrValue), 10, (this.mrValue - this.mrMin));
		} else if (this.mrValue >= this.mrMax) {
			g.setColor(Color.red);
			g.fillRect(this.x, (this.y - this.mrMin), 10, this.mrMin);
			g.setColor(Color.orange);
			g.fillRect((this.x), (this.y - this.mrMax), 10, (this.mrMax - this.mrMin));
			g.setColor(Color.red);

			g.fillRect((this.x), (this.y - this.mrValue), 10, (this.mrValue - this.mrMax));
		}

	}

	/********************************************************************************
	 * 
	 * F�r die Auswertung werden die n�tigen Werte und
	 * Koordinaten eingegeben. Die Werte werden direkt als Integer eingegeben
	 * 
	 * @author kilic 14.05.2009, 14:05:30
	 * 
	 * @param g
	 * @param mrValueInt
	 *            ist aktueller Wert
	 ********************************************************************************/
	public void paintAnalysisOfANutritionReal(Graphics g, int mrValueInt) {
		g.setColor(Color.black);
		g.drawString("min", x - 35, y - mrMinInt);
		g.drawLine((x - 10), (y - mrMinInt), (x + 20), (y - mrMinInt));
		g.drawString("max", x - 35, y - mrMaxInt);
		g.drawLine((x - 10), (y - mrMaxInt), (x + 20), (y - mrMaxInt));

		// g.fillRect(x, (yy-value), 10, value);

		if (mrValueInt <= mrMinInt) {
			g.setColor(Color.red);
			// g.setColor(Color.yellow);
			g.fillRect(x, (y - mrValueInt), 10, mrValueInt);
		} else if (mrValueInt > mrMinInt && mrValueInt < mrMaxInt) {
			g.setColor(Color.red);
			g.fillRect(x, (y - mrMinInt), 10, mrMinInt);
			g.setColor(Color.orange);
			g.fillRect((x), (y - mrValueInt), 10, (mrValueInt - mrMinInt));
		} else if (mrValueInt >= mrMaxInt) {
			g.setColor(Color.red);
			g.fillRect(x, (y - mrMinInt), 10, mrMinInt);
			g.setColor(Color.orange);
			g.fillRect((x), (y - mrMaxInt), 10, (mrMaxInt - mrMinInt));
			g.setColor(Color.red);

			g.fillRect((x), (y - mrValueInt), 10, (mrValueInt - mrMaxInt));
		}

	}

}
