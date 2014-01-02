/**
 * CHANGELOG
 *
 * 15.11.2013 Jordakieva - File created
 *
 */
package inspectors.mind.pa._v38.handcrafted;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsQuadruppel;
import pa._v38.interfaces.itfInspectorModificationDrives;
import pa._v38.modules.F06_DefenseMechanismsForDrives.clsChangedDrives;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 15.11.2013, 14:20:33
 * 
 */
public class clsInspectorImageDrives extends Inspector {
//public class clsImageDrives extends clsGraphWindow {


	private ArrayList<clsQuadruppel<String, String, String, Double>> moChanges;	//DriveAim, DriveObject, ChartsShortString, QoA
	private ArrayList <clsChangedDrives> mDisplayDrives;
	
	private JPanel moTopPanel = new JPanel (); //über drüber Panel
	private JPanel moLeft = new JPanel (); //left oTopPanel
	private JPanel moCenter = new JPanel ();
	private JPanel moRight = new JPanel (); //right oTopPanel
	
	private JPanel oCenterBladder = new JPanel ();
	private JPanel oCenterRectum = new JPanel ();
	private JPanel oCenterStamina = new JPanel ();
	private JPanel oCenterStomach = new JPanel ();
	
	private DefaultPieDataset moPieDatasetAStamina;
	private DefaultPieDataset moPieDatasetLStamina;
	private DefaultPieDataset moPieDatasetAStomach;
	private DefaultPieDataset moPieDatasetLStomach;
	private DefaultPieDataset moPieDatasetABladder;
	private DefaultPieDataset moPieDatasetLBladder;
	private DefaultPieDataset moPieDatasetARectum;
	private DefaultPieDataset moPieDatasetLRectum;
	
	private ArrayList <clsPair <String, Integer>> moLStomach = new ArrayList <clsPair <String, Integer>> (); //Werte für den PieChart Libi-Stomach
	private ArrayList <clsPair <String, Integer>> moAStomach = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moLStamina = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moAStamina = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moLBladder = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moABladder = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moLRectum = new ArrayList <clsPair <String, Integer>> ();
	private ArrayList <clsPair <String, Integer>> moARectum = new ArrayList <clsPair <String, Integer>> ();
	
	private int mnLastDisplayedElement;
	private JScrollPane oScrollPane;
	
	private HashMap <String, String> pictures = new HashMap <String, String> ();

	/**
	 * DOCUMENT (Jordakieva) - insert description 
	 *
	 * @since 20.11.2013 12:31:44
	 *
	 * @param oModule
	 */
	public clsInspectorImageDrives(itfInspectorModificationDrives oModule) { //oModule ist mein Interface zum Modul ( "Zeiger" auf F6)

		////////////////////////////
		pictures.put("CARROT", "C:\\DiplomarbeitIvy\\workspace\\ARSIN_V01\\BaseEntity-Body-ARSIN\\bin\\resources\\images\\carrot_clipart.png");
		pictures.put("DIVIDE", "C:\\DiplomarbeitIvy\\workspace\\ARSIN_V01\\BaseEntity-Body-ARSIN\\bin\\resources\\images\\Action_Divide.png");
		pictures.put("EAT", "C:\\DiplomarbeitIvy\\workspace\\ARSIN_V01\\BaseEntity-Body-ARSIN\\bin\\resources\\images\\Action_Eat.png");
//		pictures.put("CARROT", "C:\\DiplomarbeitIvy\\workspace\\ARSIN_V01\\BaseEntity-Body-ARSIN\\bin\\resources\\images\\");
		pictures.put("erease", "C:\\Users\\Jordakieva\\Desktop\\tmp\\erease1.png");
		pictures.put("decrease", "C:\\Users\\Jordakieva\\Desktop\\tmp\\decrease.png");
		pictures.put("eaqual", "C:\\Users\\Jordakieva\\Desktop\\tmp\\equal.png");
		pictures.put("leer", "C:\\Users\\Jordakieva\\Desktop\\tmp\\leer.png");
		
	
		
		
		mDisplayDrives = oModule.processList();
		mnLastDisplayedElement = 0;
		
		////////////////////////////
		moTopPanel.setLayout(new BorderLayout()); //Layout zugewiesen		
		moLeft.setLayout(new GridLayout (4,1)); //vier Zeilen, eine Spalte
		moCenter.setLayout(new GridLayout(4,1));
		moRight.setLayout(new GridLayout(4,1));
		
		moTopPanel.add(moRight, BorderLayout.EAST);
		moTopPanel.add(moCenter, BorderLayout.CENTER);
		moTopPanel.add(moLeft, BorderLayout.WEST);
		
		oCenterBladder.setLayout(new BoxLayout(oCenterBladder, BoxLayout.X_AXIS));
		oCenterRectum.setLayout(new BoxLayout(oCenterRectum, BoxLayout.X_AXIS));
		oCenterStamina.setLayout(new BoxLayout(oCenterStamina, BoxLayout.X_AXIS));
		oCenterStomach.setLayout(new BoxLayout(oCenterStomach, BoxLayout.X_AXIS));
		
		moCenter.add(oCenterStamina);
		moCenter.add(oCenterStomach);
		moCenter.add(oCenterBladder);
		moCenter.add(oCenterRectum);
		
		moPieDatasetAStamina = new DefaultPieDataset();
		moPieDatasetLStamina = new DefaultPieDataset();
		moPieDatasetAStomach = new DefaultPieDataset();
		moPieDatasetLStomach = new DefaultPieDataset();
		moPieDatasetABladder = new DefaultPieDataset();
		moPieDatasetLBladder = new DefaultPieDataset();
		moPieDatasetARectum = new DefaultPieDataset();
		moPieDatasetLRectum = new DefaultPieDataset();
		
		
		///////////////////////////
		moAStomach.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moLStomach.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moAStamina.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moLStamina.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moABladder.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moLBladder.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moARectum.add (new clsPair <String, Integer> ("noDefenseChanges", 0));
		moLRectum.add (new clsPair <String, Integer> ("noDefenseChanges", 0));		
		///////////////////////////
		
		String s = "C:\\Users\\Jordakieva\\Desktop\\tmp\\";
		ArrayList <String> oNames = new ArrayList <String> ();

		oNames.add(s+"stamina.jpg");
		oNames.add(s+"stomach.jpg");
		oNames.add(s+"bladder.jpg");
		oNames.add(s+"rectum.jpg");
		oNames.add(s+"spaceholder.jpg");
		
		//linke Seite mit den Bilder befüllen
		for (int i = 0; i < 4; i++) { 
			try {
				Image image = ImageIO.read(new File (oNames.get(i)));
				JLabel label = new JLabel (new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_FAST)));
							
				moLeft.add(label);
			} catch (Exception e) {
				System.out.println("kann die icon-Datei nicht finden");
			}
		}		
		///////////////////////////
		moRight.add(piePiePie ("A-Stamina", moPieDatasetAStamina));
		moRight.add(piePiePie ("L-Stamina", moPieDatasetLStamina));
		moRight.add(piePiePie ("A-Stomach", moPieDatasetAStomach));
		moRight.add(piePiePie ("L-Stomach", moPieDatasetLStomach));
		moRight.add(piePiePie ("A-Bladder", moPieDatasetABladder));
		moRight.add(piePiePie ("L-Bladder", moPieDatasetLBladder));
		moRight.add(piePiePie ("A-Rectum", moPieDatasetARectum));
		moRight.add(piePiePie ("L-Rectum", moPieDatasetLRectum));
		///////////////////////////
		
		oScrollPane = new JScrollPane(moTopPanel); //ScrollBar über das über drüber Panel
		this.setLayout(new BorderLayout());		//ohne das Layout wird er nicht checken, dass er scrollen muss
		this.add(oScrollPane, BorderLayout.CENTER);

	}
	
	ChartPanel piePiePie (String name, DefaultPieDataset pPie) {
		JFreeChart oChart = ChartFactory.createPieChart(name, pPie, false, false, false);
		
		//Vereinheitlichung der Abwehmechanismus-Farben. Das coole ist, die Objekte sind noch gar nicht erstellt, aber er macht das =)
		PiePlot plot = (PiePlot) oChart.getPlot();
		plot.setSectionPaint("noDefenseChanges", Color.white);
		plot.setSectionPaint("Replace Drive Aim", Color.blue);
		plot.setSectionPaint("Turn Against Self", Color.cyan);
		plot.setSectionPaint("Projection", Color.green);
		plot.setSectionPaint("Displacement", Color.orange);
		
		ChartPanel A = new ChartPanel (oChart);
		
		A.setPreferredSize(new Dimension (300, 150));
		return A;
	}
	
	/**
	 * DOCUMENT (Jordakieva) - compares a clsChangedDrives-Variable on the position [i]
	 *
	 * @since 28.11.2013 17:50:00
	 *
	 * @param objekt is from the class clsChangedDrives and the function compares if the Pairs inside are equal 
	 * @return true, if there is no difference
	 */
	boolean vergleichen (clsChangedDrives objekt) { 
		boolean ret = false;
		
		if (objekt.getQoA().a.equals(objekt.getQoA().b))
			if (objekt.getDriveAim().a.equals(objekt.getDriveAim().b))
				if (objekt.getDriveObject().a.equals(objekt.getDriveObject().b)) 
					ret = true;
		
		return ret;
	}
	
	/**
	 * DOCUMENT (Jordakieva) - displays the picture in die desired JPanel
	 *
	 * @since 28.11.2013 17:51:51
	 *
	 * @param Bild = the picture to display
	 * @param panel = the panel where the picture will be displayed
	 */
	void unterschiedAusgeben (int Index, JPanel panel, int step) {
		try {
				Integer tStep = step;
			
				Image driveObjectBefore = ImageIO.read(new File (pictures.get(mDisplayDrives.get(Index).getDriveObject().a))); //Drive Object BEFORE
				Image driveObjectAfter = ImageIO.read(new File (pictures.get(mDisplayDrives.get(Index).getDriveObject().b))); //Drive Object AFTER
				
				Image driveAimBefore = ImageIO.read(new File (pictures.get(mDisplayDrives.get(Index).getDriveAim().a))); //drive Aim BEFORE
				Image driveAimAfter = ImageIO.read(new File (pictures.get(mDisplayDrives.get(Index).getDriveAim().b))); //drive Aim AFTER
				
				BufferedImage dOb = (BufferedImage) driveObjectBefore; //picture of the DriveObjectBefore
				BufferedImage dDb = (BufferedImage) driveAimBefore; //picture of the DriveAimBefore
				
				Graphics2D gObject = dOb.createGraphics();
				Graphics2D gDrive = dDb.createGraphics();
				gObject.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, (float) 0.2));
				gDrive.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, (float) 0.2));
				gObject.drawImage(dOb, 0, 0, null);
				gDrive.drawImage(dDb, 0, 0, null);
				
				JLabel labelOBefore = new JLabel (new ImageIcon(dOb.getScaledInstance(50, 50, Image.SCALE_FAST))); //Drive Object before
				JLabel labelOAfter = new JLabel (new ImageIcon(driveObjectAfter.getScaledInstance(50, 50, Image.SCALE_FAST))); //drive object after
				JLabel labelDBefore = new JLabel (new ImageIcon(dDb.getScaledInstance(50, 50, Image.SCALE_FAST)));
				JLabel labelDAfter = new JLabel (new ImageIcon(driveAimAfter.getScaledInstance(50, 50, Image.SCALE_FAST)));
				
				//////////////////////////// QoA Ausgabe
				JLabel theStep = new JLabel ("Step: " + tStep.toString());
				JLabel theDefense = new JLabel (mDisplayDrives.get(Index).getDefense());
				JLabel QoA = new JLabel ("QoA: ");
				
				Image qoaPicture;
				Double bla1 = mDisplayDrives.get(Index).getQoA().a;
				Double bla2 = mDisplayDrives.get(Index).getQoA().b;
				
				if (mDisplayDrives.get(Index).getQoA().a.equals(mDisplayDrives.get(Index).getQoA().b))
					qoaPicture = ImageIO.read(new File (pictures.get("eaqual")));
				else if (bla1 > bla2) qoaPicture = ImageIO.read(new File (pictures.get("decrease")));
				else qoaPicture = ImageIO.read(new File (pictures.get("erease")));
				JLabel qoaL = new JLabel (new ImageIcon(qoaPicture.getScaledInstance(20, 20, Image.SCALE_FAST)));
				/////////////////////////////
				
				JPanel panelObject = new JPanel (); JPanel panelQoA = new JPanel (); JPanel panelDrive = new JPanel ();
				JPanel panelStep = new JPanel (), panelDefense = new JPanel ();
				panelObject.setLayout(new BoxLayout(panelObject, BoxLayout.X_AXIS));
				panelDrive.setLayout(new BoxLayout(panelDrive, BoxLayout.X_AXIS));
				panelQoA.setLayout(new BoxLayout(panelQoA, BoxLayout.X_AXIS));
				
				panelObject.add(labelOBefore); panelObject.add(labelOAfter);
				panelDrive.add(labelDBefore); panelDrive.add(labelDAfter);
				panelQoA.add(QoA); panelQoA.add(qoaL);
				
				panelStep.add(theStep); panelDefense.add(theDefense);
				
				gDrive.dispose(); //gibt die Ressourcen frei
				gObject.dispose();
							
				panel.add(panelStep);
				panel.add(panelObject);
				panel.add(panelDrive);
				panel.add(panelQoA);
				panel.add(panelDefense);
			} catch (Exception e) {
				System.out.println("kann die icon-Datei nicht finden");
		}
	}
	/**
	 * DOCUMENT (Jordakieva) - displays the space-holder in the desired JPanel 
	 *
	 * @since 28.11.2013 17:53:41
	 *
	 * @param panel
	 */
	void leereBoxAusgeben (JPanel panel, int step) {
		try {
			Integer t = step;
			
			Image image = ImageIO.read(new File ("C:\\Users\\Jordakieva\\Desktop\\tmp\\spaceholder.jpg"));
			JLabel label = new JLabel (new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_FAST)));
			JLabel theStep = new JLabel (t.toString());
						
			panel.add(theStep);
			panel.add(label);
		} catch (Exception e) {
			System.out.println("kann die icon-Datei nicht finden");
		}
	}
	
	/**
	 * DOCUMENT (Jordakieva) - increasing the defense Variable needed later for the PieChart
	 *
	 * @since 28.11.2013 17:56:28
	 *
	 * @param moVariable = stomach, bladder, recturm, stamina; from them the aggressive or the libidinous part
	 * @param i = schlechte Programmierung :D
	 * @param defense = if false, then the defense mechanism didnt change any value
	 */
	void PieVariableBefuellen (ArrayList<clsPair<String, Integer>> moVariable, int i, boolean defense) {
		if (defense) {
			
			boolean bla = false;
			for (int j = 0; j < moVariable.size(); j++) {
				if (moVariable.get(j).a.equals(mDisplayDrives.get(i).getDefense())) {
					bla = true;
					moVariable.get(j).b ++;
					break;
				}
			}
			
			if (!bla)
				moVariable.add (new clsPair <String, Integer> (mDisplayDrives.get(i).getDefense(), 1));
			
		} else {
			for (int j = 0; j < moVariable.size(); j++) { //wird eh immer das 0te Element sein, trotzdem sicherheitshalber die Schleife
				if (moVariable.get(j).a.equals("noDefenseChanges")) { 
					moVariable.get(j).b ++; break; 
				}
			}
		}
			
	}
	
	void fillBoarderCenter (ArrayList <String> names) {
		
		ArrayList <String> oNames = names;
		int nElemente = mDisplayDrives.size();
		
		ArrayList <Integer> lo = new ArrayList <Integer> (); //libi Stomach; der Index wo libiStomach vorkommt im mDisplayDrives
		ArrayList <Integer> ao = new ArrayList <Integer> (); //aggr Stomach
		ArrayList <Integer> ls = new ArrayList <Integer> (); //libi Stamina
		ArrayList <Integer> as = new ArrayList <Integer> ();
		ArrayList <Integer> lb = new ArrayList <Integer> (); //libi Bladder
		ArrayList <Integer> ab = new ArrayList <Integer> ();
		ArrayList <Integer> lr = new ArrayList <Integer> (); //libi Rectum
		ArrayList <Integer> ar = new ArrayList <Integer> ();
		ArrayList <Integer> index = new ArrayList <Integer> (); //wird um den Element-Index erhöht wenn min eine der ArrayListen davor zugeschlagen haben
		
		for (int i = mnLastDisplayedElement, step = mDisplayDrives.get(mnLastDisplayedElement).getStep(); i < nElemente; i++) { //damit die update-Funktion wirklich jeden Schritt nur einmal macht 
			
			boolean hmpf = false; //zeigt an ob ein stomach, rectum, bladder, stamina als Drive vorkommt im aktuellen mDisplayDrives.get(i).getMotivation
			//sollte egtl immer sein
			
			if (step == mDisplayDrives.get(i).getStep()) { //wenn immer noch der selbe Step im mDisplayDrives
				if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("STOMACH")) {
					if (vergleichen(mDisplayDrives.get(i))) //wenn die Abwehr nichts verändert hat, dann braucht auch kein Bild zusammengestückelt werden 
						PieVariableBefuellen (moLStomach, i, false);
					else {
						PieVariableBefuellen (moLStomach, i, true);
						lo.add(i);
						hmpf = true;
					}					
				}
				if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("STOMACH")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moAStomach, i, false);
					else {
						PieVariableBefuellen (moAStomach, i, true);
						ao.add(i);
						hmpf = true;
					}					
				}
								
				if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("RECTUM")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moLRectum, i, false);
					else {
						PieVariableBefuellen (moLRectum, i, true);
						lr.add(i);
						hmpf = true;
					}					
				}
				if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("RECTUM")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moARectum, i, false);
					else {
						PieVariableBefuellen (moARectum, i, true);
						ar.add(i);
						hmpf = true;
					}					
				}
				
				if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("BLADDER")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moLBladder, i, false);
					else {
						PieVariableBefuellen (moLBladder, i, true);
						lb.add(i);
						hmpf = true;
					}					
				}
				if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("BLADDER")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moABladder, i, false);
					else {
						PieVariableBefuellen (moABladder, i, true);
						ab.add(i);
						hmpf = true;
					}					
				}
				
				if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("STAMINA")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moLStamina, i, false);
					else {
						PieVariableBefuellen (moLStamina, i, true);
						ls.add(i);
						hmpf = true;
					}					
				}
				if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("STAMINA")) {
					if (vergleichen(mDisplayDrives.get(i))) 
						PieVariableBefuellen (moAStamina, i, false);
					else {
						PieVariableBefuellen (moAStamina, i, true);
						as.add(i);
						hmpf = true;
					}					
				}
				
				if (hmpf) index.add (i);
					
			} else {
				
				double maxStock = Math.max(lo.size(), Math.max (ao.size(), Math.max (lr.size(), Math.max (ar.size(), 
						Math.max (lb.size(), Math.max (ab.size(), Math.max (ls.size(), as.size()))))))); //die Maximale ArraySize von diesen Elementen wird ermittelt

				if (maxStock > 0) { //dh zumindest eines der Arrays hat Elemente
					
					for (int k = 0; k < maxStock; k++) {
						JPanel sto = new JPanel ();
						sto.setLayout(new BoxLayout(sto, BoxLayout.Y_AXIS));
						JPanel sta = new JPanel ();
						sta.setLayout(new BoxLayout(sta, BoxLayout.Y_AXIS));
						JPanel re = new JPanel ();
						re.setLayout(new BoxLayout(re, BoxLayout.Y_AXIS));
						JPanel bl = new JPanel ();
						bl.setLayout(new BoxLayout(bl, BoxLayout.Y_AXIS));
						oCenterStamina.add (sta);
						oCenterBladder.add (bl);
						oCenterRectum.add (re);
						oCenterStomach.add (sto);
						////////////////////////////////////
						
						//da für libi und agg zwar der selbe Step aber verschiedener Index ist, wird das jetzt
						//zusammen gewürfelt um einen Step jeweils nur einmal auszugeben
												
						if (lo.size() > k && lo.contains(index.get(k))) unterschiedAusgeben (index.get(k), sto, step); //(oNames.get(1), sto, step);
						else leereBoxAusgeben (sto, step);
						
						if (ao.size() > k && ao.contains(index.get(k))) unterschiedAusgeben (index.get(k), sto, step); //(oNames.get(1), sto, step);
						else leereBoxAusgeben (sto, step);
						
						if (ls.size() > k && ls.contains(index.get(k))) unterschiedAusgeben (index.get(k), sta, step); //(oNames.get(1), sta, step);
						else leereBoxAusgeben (sta, step);
						
						if (as.size() > k && as.contains(index.get(k))) unterschiedAusgeben (index.get(k), sta, step); //(oNames.get(1), sta, step);
						else leereBoxAusgeben (sta, step);
						
						if (lr.size() > k && lr.contains(index.get(k))) unterschiedAusgeben (index.get(k), re, step); //(oNames.get(1), re, step);
						else leereBoxAusgeben (re, step);
						
						if (ar.size() > k && ar.contains(index.get(k))) unterschiedAusgeben (index.get(k), re, step); //(oNames.get(1), re, step);
						else leereBoxAusgeben (re, step);
						
						if (lb.size() > k && lb.contains(index.get(k))) unterschiedAusgeben (index.get(k), bl, step); //(oNames.get(1), bl, step);
						else leereBoxAusgeben (bl, step);
						
						if (ab.size() > k && ab.contains(index.get(k))) unterschiedAusgeben (index.get(k), bl, step); //(oNames.get(1), bl, step);
						else leereBoxAusgeben (bl, step);
					}

					lo.clear(); ao.clear();
					lr.clear(); ar.clear();
					lb.clear(); ab.clear();
					ls.clear(); as.clear();
					index.clear();
					
					step = mDisplayDrives.get(i).getStep(); //der Step wird aktualisiert
					i--; //wird subtrachiert, sonst verpasst man min einen Schritt
				}
			
			} //Ende vom Else
		} //Ende der for-Schleife
		
		mnLastDisplayedElement = mDisplayDrives.size();		
	}
	

	/* (non-Javadoc)
	 *
	 * @since 20.11.2013 12:21:08
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		
		String s = "C:\\Users\\Jordakieva\\Desktop\\tmp\\";
		ArrayList <String> oNames = new ArrayList <String> ();

		oNames.add(s+"stamina.jpg");
		oNames.add(s+"stomach.jpg");
		oNames.add(s+"bladder.jpg");
		oNames.add(s+"rectum.jpg");
		oNames.add(s+"spaceholder.jpg");
		
		
		//Zentrum mit den Grafiken befüllen
		fillBoarderCenter (oNames);
		
		//rechte Seite mit den Pie-Charts befüllen
		for (int k = moPieDatasetAStamina.getItemCount(); k < moAStamina.size(); k++) {			 ///////////// wieso habe ich hier k = moPieDataSetAStamina? und unten k=0
			moPieDatasetAStamina.insertValue(0, moAStamina.get(k).a, moAStamina.get(k).b);
		}			
		////////////////////////
		
		for (int k = 0; k < moLStamina.size(); k++) {
			moPieDatasetLStamina.insertValue(0, moLStamina.get(k).a, moLStamina.get(k).b);
		}			
		////////////////////////
		
		for (int k = 0; k < moAStomach.size(); k++) {
			moPieDatasetAStomach.insertValue(0, moAStomach.get(k).a, moAStomach.get(k).b);
		}			
		/////////////////////////
		
		for (int k = 0; k < moLStomach.size(); k++) {
			moPieDatasetLStomach.insertValue(0, moLStomach.get(k).a, moLStomach.get(k).b);
		}						
		/////////////////////////
		
		for (int k = 0; k < moABladder.size(); k++) {
			moPieDatasetABladder.insertValue(0, moABladder.get(k).a, moABladder.get(k).b);
		}			
		
		for (int k = 0; k < moLBladder.size(); k++) {
			moPieDatasetLBladder.insertValue(0, moLBladder.get(k).a, moLBladder.get(k).b);
		}			
		////////////////////////
		
		for (int k = 0; k < moARectum.size(); k++) {
			moPieDatasetARectum.insertValue(0, moARectum.get(k).a, moARectum.get(k).b);
		}			
		
		for (int k = 0; k < moLRectum.size(); k++) {
			moPieDatasetLRectum.insertValue(0, moLRectum.get(k).a, moLRectum.get(k).b);
		}			
		
//		moTopPanel.validate();
		oScrollPane.validate();
		
	}
}
