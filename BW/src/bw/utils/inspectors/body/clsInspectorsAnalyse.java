/**
 * @author kilic
 * 15.05.2009, 10:21:47
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (kilic) - insert description 
 * 
 * @author kilic
 * 15.05.2009, 10:21:47
 * 
 */
public class clsInspectorsAnalyse extends JComponent{
	
	private static final long serialVersionUID = -5919029139654004572L;
	
	private JProgressBar energy_progress;
	/**Das ist das Hauptpanel, in dem alle Panels von Analyseanzeigen enthalten sind*/ 
	private JPanel panelOfAnalyse= new JPanel(new BorderLayout());
	/**Die Liste der Nutrition und deren Panele */
	private HashMap<Integer, clsSingleFillLevel> panelOfNutrition = new HashMap<Integer, clsSingleFillLevel>();
	
	/*private int mrX;
	private int mrY;
	private int mrMinValue;
	private int mrMaxValue;
	private int mrValue;
	private int mrLowerBound;
	private int mrUpperBound;*/

	/**
	 * TODO (kilic) - leere Konstruktor 
	 * 
	 * @author kilic
	 * 15.05.2009, 10:21:47
	 *
	 */
	public clsInspectorsAnalyse() {
		// TODO Auto-generated constructor stub
	}
	/****************************************************************
	 * 
	 * TODO (kilic) - Konstruktor mit Creieren vom JProgress 
	 * 
	 * @author kilic
	 * 15.05.2009, 11:26:42
	 *
	 * @param pos
	 * @param min
	 * @param max
	 */
	public clsInspectorsAnalyse(boolean pos, int min, int max) {
		this.creatProgressbarOfEnergy(pos, min, max);
	}

	/*********************************************************************************
	 * 
	 * TODO (kilic) - In dieser Methode wird eine Progressbar f�r Energy erstellt
	 *
	 * @author kilic
	 * 15.05.2009, 11:09:44
	 *
	 * @param pos : die Position von Progressbar: true==> Vertical, false==>Horizontal
	 * @param min ist min Wert von Progressbar
	 * @param max ist max Wert von Progressbar
	 *********************************************************************************/
	public void creatProgressbarOfEnergy(boolean pos, int min, int max){
		if(pos) this.energy_progress=new JProgressBar(JProgressBar.VERTICAL,min,max);
		else  this.energy_progress=new JProgressBar(JProgressBar.HORIZONTAL,min,max);
		this.energy_progress.setStringPainted(true);
		
	}
	/*********************************************************************************
	 * 
	 * TODO (kilic) - In dieser Konstruktor wird eine Progressbar f�r Energy erstellt
	 *
	 * @author kilic
	 * 15.05.2009, 11:09:44
	 *
	 * @param pos : die Position von Progressbar: true==> Vertical, false==>Horizontal
	 * @param min ist min Wert von Progressbar
	 * @param max ist max Wert von Progressbar
	 * @param listOfNutrution ist die Liste von Nutrition
	 *********************************************************************************/
	public clsInspectorsAnalyse(boolean pos, int min, int max, HashMap<Integer, clsNutritionLevel> listOfNutrution){
		this.creatProgressbar(pos, min, max);
		this.createPanelsOfNutritionLevel(listOfNutrution);
				
		
	}	
	/*************************************************************************
	 * 
	 * TODO (kilic) - In dieser Methode wird Progressbar erstellt und in den 
	 * Panel hinzugef�gt
	 *
	 * @author kilic
	 * 25.05.2009, 08:54:31
	 *
	 * @param pos : die Position von Progressbar: true==> Vertical, false==>Horizontal
	 * @param min ist min Wert von Progressbar
	 * @param max ist max Wert von Progressbar
	 *************************************************************************/
	private void creatProgressbar(boolean pos, int min, int max){
		if(pos) this.energy_progress=new JProgressBar(JProgressBar.VERTICAL,min,max);
		else  this.energy_progress=new JProgressBar(JProgressBar.HORIZONTAL,min,max);
		
		this.energy_progress.setStringPainted(true);
		this.panelOfAnalyse.add(this.energy_progress,BorderLayout.WEST);
				
		
	}
	/*******************************************************************
	 * 
	 * TODO (kilic) - F�r jede Natrution wird ein Panel durch die Klasse 
	 * clsSingleFillLevel erstellt
	 *
	 * @author kilic
	 * 25.05.2009, 09:17:52
	 *
	 * @param listOfNatrution
	 */
	private void createPanelsOfNutritionLevel(HashMap<Integer, clsNutritionLevel> listOfNatrution) {
		//JPanel jp = new JPanel();
		Box box= new Box(BoxLayout.X_AXIS); 
			for(int i=0;i<listOfNatrution.size();i++){
				clsNutritionLevel tmp =listOfNatrution.get(i);
				clsSingleFillLevel single = new clsSingleFillLevel(i,tmp.getLowerBound(),tmp.getUpperBound(),0,tmp.getMaxContent(), tmp.getContent(),50,100); 
				//jp.add(new JLabel("N"+(i+1)));
				//single.setSize(200, 200);
				//single.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "N"+(i+1), TitledBorder.CENTER, TitledBorder.BOTTOM,new Font("SansSerif ",Font.BOLD,10),Color.orange));
				//jp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "N"+(i+1), TitledBorder.CENTER, TitledBorder.BOTTOM,new Font("SansSerif ",Font.BOLD,12),Color.orange));
				//jp.add(single);
				box.add(single);
				this.panelOfNutrition.put(i,single);
		}
			this.panelOfAnalyse.add(box,BorderLayout.CENTER);
	}
	
	/******************************************************************************
	 * 
	 * TODO (kilic) - In dieser Methode wird der Wert von Progress aktualisiert
	 * angegeben.
	 *
	 * @author kilic
	 * 15.05.2009, 11:15:55
	 *
	 * @param value
	 ******************************************************************************/
	public void updatingValue(int value){
		this.energy_progress.setValue(value);
	}
	/******************************************************************************
	 * 
	 * TODO (kilic) - Die Methode liefert den Progressbar zur�ck
	 *
	 * @author kilic
	 * 15.05.2009, 11:17:55
	 *
	 * @return JProgressBar f�r Energie von Inspector
	 ******************************************************************************/
	public JProgressBar getProgressOfEnergy(){
		return this.energy_progress;
	}
	/*******************************************************
	 * 
	 * TODO (kilic) - Liefert den Panl von Analyse
	 *
	 * @author kilic
	 * 25.05.2009, 10:28:37
	 *
	 * @return
	 */
	public JPanel getPanelOfAnalyse(){
		return this.panelOfAnalyse;
	}
	/********************************************************************************
	 * 
	 * TODO (kilic) - F�r die Auswertung werden die n�tigen Werte und Koordinaten 
	 * eingegeben. Die Werte sind in Float. Die K�stschen werden nach dem Float
	 * eingestellt.
	 *
	 * @author kilic
	 * 14.05.2009, 14:05:30
	 *
	 * @param g
	 * @param x ist x-Koordinat f�r Ausgabe 
	 * @param y ist y-Koordinat f�r Ausgabe
	 * @param mino ist Min. Wert
	 * @param maxo ist Max. Wert
	 * @param valueo ist aktueller Wert
	 ********************************************************************************/
	public void paintAnalysisOfANutrition (Graphics g, int x, int y, double mino, double maxo, double valueorg) {
		int min=(int)(mino*10);
		int max=(int)(maxo*10);
		int value=(int)(valueorg*10);
		g.setColor(Color.black);
		g.drawString((""+mino).substring(0, 3), x-35, y-min);
		//g.drawString("min", x-35, y-min);
		g.drawLine((x-10), (y-min), (x+20), (y-min));
		g.drawString((""+valueorg).substring(0, 3), x-35, y-max);
		//g.drawString("max", x-35, y-max);
		g.drawLine((x-10), (y-max), (x+20), (y-max));
		g.drawString((""+valueorg).substring(0, 3), x+20, y-value);
		
		
		//g.fillRect(x, (yy-value), 10, value);
		
	    if(value<=min) {
	    	g.setColor(Color.red);
	    	//g.setColor(Color.yellow);
	    	g.fillRect(x, (y-value), 10, value);
	    }
	    else if(value>min&&value<max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-value), 10, (value-min));
	    }
	    else  if(value>=max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-max), 10, (max-min));
	    	g.setColor(Color.red);
	    	
	    	g.fillRect((x), (y-value), 10, (value-max));
	    }

	  }
	/********************************************************************************
	 * 
	 * TODO (kilic) - F�r die Auswertung werden die n�tigen Werte und Koordinaten 
	 * eingegeben. Die Werte  werden direkt als Integer eingegeben
	 *
	 * @author kilic
	 * 14.05.2009, 14:05:30
	 *
	 * @param g
	 * @param x ist x-Koordinat f�r Ausgabe 
	 * @param y ist y-Koordinat f�r Ausgabe
	 * @param min ist Min. Wert
	 * @param max ist Max. Wert
	 * @param value ist aktueller Wert
	 ********************************************************************************/
	public void paintAnalysisOfANutritionReal (Graphics g, int x, int y, int min, int max, int value) {
		g.setColor(Color.black);
		g.drawString("min", x-35, y-min);
		g.drawLine((x-10), (y-min), (x+20), (y-min));
		g.drawString("max", x-35, y-max);
		g.drawLine((x-10), (y-max), (x+20), (y-max));
		
		
		//g.fillRect(x, (yy-value), 10, value);
		
	    if(value<=min) {
	    	g.setColor(Color.red);
	    	//g.setColor(Color.yellow);
	    	g.fillRect(x, (y-value), 10, value);
	    }
	    else if(value>min&&value<max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-value), 10, (value-min));
	    }
	    else  if(value>=max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-max), 10, (max-min));
	    	g.setColor(Color.red);
	    	
	    	g.fillRect((x), (y-value), 10, (value-max));
	    }

	  }
	/**************************************************************************
	 * 
	 * TODO (kilic) - Die Analyse der ganzen Nutritionsliste wird gezeigt
	 * f�r die Werte wird Float angenommen
	 *
	 * @author kilic
	 * 15.05.2009, 11:34:07
	 *
	 * @param g
	 * @param listOfNutrution
	 * @param x ist x-Achse f�r Ausgabeort der Ergebnise von Analysen
	 * @param y ist y-Achse f�r Ausgabeort der Ergebnise von Analysen 
	 **************************************************************************/
	public void paintAnalysisOfSeveralNutrition (Graphics g, HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		for(int i=0;i<listOfNutrution.size();i++){
			clsNutritionLevel tmp =listOfNutrution.get(i);
			this.paintAnalysisOfANutrition(g, x, y, tmp.getLowerBound(), tmp.getUpperBound(), tmp.getContent());
			//this.paintAnalysisOfANutrition(g, x, y, tmp.getLowerBound(), tmp.getUpperBound(),(float)Math.random());
			
			//x=x+100;
		}
	}
	/**************************************************************************
	 * 
	 * TODO (kilic) - Die Analyse der ganzen Nutritionsliste wird gezeigt
	 * f�r die Werte wird Integer angenommen
	 *
	 * @author kilic
	 * 15.05.2009, 11:34:07
	 *
	 * @param g
	 * @param listOfNutrution
	 * @param x ist x-Achse f�r Ausgabeort der Ergebnise von Analysen
	 * @param y ist y-Achse f�r Ausgabeort der Ergebnise von Analysen
	 **************************************************************************/
	public void paintAnalysisOfSeveralNutritionReal (Graphics g, HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		for(int i=0;i<listOfNutrution.size();i++){
			clsNutritionLevel tmp =listOfNutrution.get(i);
			this.paintAnalysisOfANutritionReal(g, x, y, (int)tmp.getLowerBound(), (int)tmp.getUpperBound(), (int)tmp.getContent());
			//tmp.getMaxContent();
			//this.paintAnalysisOFNat(g, x, y,(int) tmp.getLowerBound(), (int)tmp.getMaxContent() ,(int) tmp.getContent());
			x=x+100;
		}
	}
	/**************************************************************************
	 * 
	 * TODO (kilic)  Die Analyse aller Nutrition wird auf eigenen Panel gezeigt
	 * f�r die Werte wird Float angenommen
	 *
	 * @author kilic
	 * 15.05.2009, 11:34:07
	 *
	 * @param g
	 * @param listOfNutrution
	 * @param x ist x-Achse f�r Ausgabeort der Ergebnise von Analysen
	 * @param y ist y-Achse f�r Ausgabeort der Ergebnise von Analysen 
	 **************************************************************************/
	public void paintPanelOfAnalysisOfSeveralNutrition (Graphics g, HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		//public void paintPanelOfAnalysisOfSeveralNutrition (HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		for(int i=0;i<listOfNutrution.size();i++){
			clsNutritionLevel tmp =listOfNutrution.get(i);
			//this.paintAnalysisOfANutrition(this.panelOfNutrition.get(i).getGraphics(), x, y, tmp.getLowerBound(), tmp.getUpperBound(),tmp.getContent());
			this.paintAnalysisOfANutrition(g, x, y, tmp.getLowerBound(), tmp.getUpperBound(), tmp.getContent());
			
			x=x+100;
		}
	}
	/**************************************************************************
	 * 
	 * TODO (kilic) -Die Analyse aller Nutrition wird auf eigenen Panel gezeigt
	 * f�r die Werte wird Integer angenommen
	 *
	 * @author kilic
	 * 15.05.2009, 11:34:07
	 *
	 * @param g
	 * @param listOfNutrution
	 * @param x ist x-Achse f�r Ausgabeort der Ergebnise von Analysen
	 * @param y ist y-Achse f�r Ausgabeort der Ergebnise von Analysen
	 **************************************************************************/
	public void paintPanelOfAnalysisOfSeveralNutritionReal (HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		for(int i=0;i<listOfNutrution.size();i++){
			clsNutritionLevel tmp =listOfNutrution.get(i);
			
			this.paintAnalysisOfANutrition(this.panelOfNutrition.get(i).getGraphics(), x, y,(int)tmp.getLowerBound(), (int)tmp.getUpperBound(),(int)tmp.getContent());
			
			//x=x+100;
		}
	}
	public void update(HashMap<Integer, clsNutritionLevel> listOfNutrution){
		//public void paintPanelOfAnalysisOfSeveralNutrition (HashMap<Integer, clsNutritionLevel> listOfNutrution, int x, int y) {
		for(int i=0;i<listOfNutrution.size();i++){
			clsNutritionLevel tmp =listOfNutrution.get(i);
			this.panelOfNutrition.get(i).updateFloat(tmp.getContent());
			
		}
	}
	
	
	

}
