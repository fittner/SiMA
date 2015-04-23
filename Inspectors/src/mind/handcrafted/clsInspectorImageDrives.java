/**
 * CHANGELOG
 *
 * 15.11.2013 Jordakieva - File created
 *
 */
package mind.handcrafted;

import inspector.interfaces.itfInspectorModificationDrives;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

import primaryprocess.modules.F06_DefenseMechanismsForDrives.clsChangedDrives;
import sim.portrayal.Inspector;
import utils.clsGetARSPath;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 15.11.2013, 14:20:33
 * 
 */
public class clsInspectorImageDrives extends Inspector {
//public class clsImageDrives extends clsGraphWindow {


	/** DOCUMENT (Jordakieva) - insert description; @since 03.01.2014 17:52:15 */
	private static final long serialVersionUID = -7680684180814877754L;

	private ArrayList <clsChangedDrives> mDisplayDrives;
	
	private JPanel moTopPanel = new JPanel (); //über drüber Panel
	private JPanel moLeft = new JPanel (); //left oTopPanel
	private JPanel moCenter = new JPanel ();
	private JPanel moRight = new JPanel (); //right oTopPanel
	
	private JPanel moCenterBladder = new JPanel ();
	private JPanel moCenterRectum = new JPanel ();
	private JPanel moCenterStamina = new JPanel ();
	private JPanel moCenterStomach = new JPanel ();
	
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
	private JScrollPane moScrollPane;
	
	private HashMap <String, String> moImageNames = new HashMap <String, String> ();
	private HashMap <String, Image> moConceptionalImages = new HashMap <String, Image> ();

	/**
	 * DOCUMENT (Jordakieva) - der Inspector gehört noch extremst geschliffen und die Bilder durch nicht copyrighte ersetzt. Großes TODO 
	 *
	 * @since 20.11.2013 12:31:44
	 *
	 * @param oModule
	 */
	public clsInspectorImageDrives(itfInspectorModificationDrives oModule) { //oModule ist mein Interface zum Modul ( "Zeiger" auf F6)

		//////////////TODO TO ÄNDERN der Bilder wegen COPYRIGHT//////////////
		moImageNames.put("CARROT", clsGetARSPath.getImagePath() + "carrot_clipart.png");
		moImageNames.put("DIVIDE", clsGetARSPath.getImagePath() + "Action_Divide.png");
		moImageNames.put("EAT", clsGetARSPath.getImagePath() + "Action_Eat.png");
		moImageNames.put("CAKE", clsGetARSPath.getImagePath() + "cake.png");
		
		try {
			moConceptionalImages.put("erease", ImageIO.read(new File (clsGetARSPath.getImagePath() + "erease1.png")));
			moConceptionalImages.put("decrease", ImageIO.read(new File (clsGetARSPath.getImagePath()+ "decrease.png")));
			moConceptionalImages.put("eaqual", ImageIO.read(new File (clsGetARSPath.getImagePath() + "equal.png")));
			moConceptionalImages.put("leer", ImageIO.read(new File (clsGetARSPath.getImagePath() + "leer.png")));
		} catch (Exception e) {
			System.err.println("Images not found");
		}
		
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
		
		moCenterBladder.setLayout(new BoxLayout(moCenterBladder, BoxLayout.X_AXIS));
		moCenterRectum.setLayout(new BoxLayout(moCenterRectum, BoxLayout.X_AXIS));
		moCenterStamina.setLayout(new BoxLayout(moCenterStamina, BoxLayout.X_AXIS));
		moCenterStomach.setLayout(new BoxLayout(moCenterStomach, BoxLayout.X_AXIS));
		
		moCenter.add(moCenterStamina);
		moCenter.add(moCenterStomach);
		moCenter.add(moCenterBladder);
		moCenter.add(moCenterRectum);
		
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
		
		ArrayList <String> oNames = new ArrayList <String> ();

		oNames.add(clsGetARSPath.getImagePath()+"stamina.jpg");
		oNames.add(clsGetARSPath.getImagePath()+"stomach.jpg");
		oNames.add(clsGetARSPath.getImagePath()+"bladder.jpg");
		oNames.add(clsGetARSPath.getImagePath()+"rectum.jpg");
		oNames.add(clsGetARSPath.getImagePath()+"spaceholder.jpg");
		
		//linke Seite mit den Bilder befüllen: (moLeft, BorderLayout.WEST);
		for (int i = 0; i < 4; i++) { 
			try {
				Image image = ImageIO.read(new File (oNames.get(i)));
				JLabel label = new JLabel (new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_FAST)));
							
				moLeft.add(label);
			} catch (Exception e) {
				System.out.println("kann die icon-Datei nicht finden");
			}
		}		
		/////////////////////////// (moRight, BorderLayout.EAST);
		moRight.add(piePiePie ("A-Stamina", moPieDatasetAStamina));
		moRight.add(piePiePie ("L-Stamina", moPieDatasetLStamina));
		moRight.add(piePiePie ("A-Stomach", moPieDatasetAStomach));
		moRight.add(piePiePie ("L-Stomach", moPieDatasetLStomach));
		moRight.add(piePiePie ("A-Bladder", moPieDatasetABladder));
		moRight.add(piePiePie ("L-Bladder", moPieDatasetLBladder));
		moRight.add(piePiePie ("A-Rectum", moPieDatasetARectum));
		moRight.add(piePiePie ("L-Rectum", moPieDatasetLRectum));
		///////////////////////////
		
		moScrollPane = new JScrollPane(moTopPanel); //ScrollBar über das über drüber Panel
		this.setLayout(new BorderLayout());		//ohne das Layout wird er nicht checken, dass er scrollen muss
		this.add(moScrollPane, BorderLayout.CENTER);

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
	boolean compareObject (clsChangedDrives objekt) { 
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
	void displayTheChangedDrive (int Index, JPanel panel, int step) {

		try {
			Integer tStep = step;
			
			Image driveObjectBefore;
			Image driveObjectAfter;
			Image driveAimBefore;
			Image driveAimAfter;
			
			if (moImageNames.containsKey(mDisplayDrives.get(Index).getDriveObject().a))
				driveObjectBefore = ImageIO.read(new File (moImageNames.get(mDisplayDrives.get(Index).getDriveObject().a))); //Drive Object BEFORE
			else driveObjectBefore = ImageIO.read(new File (moImageNames.get("notFound"))); //Drive Object not found in the hash map
			
			if (moImageNames.containsKey(mDisplayDrives.get(Index).getDriveObject().b))
				driveObjectAfter = ImageIO.read(new File (moImageNames.get(mDisplayDrives.get(Index).getDriveObject().b))); //Drive Object AFTER
			else driveObjectAfter = ImageIO.read(new File (moImageNames.get("notFound"))); //Drive Object not found in the hash map
			
			if (moImageNames.containsKey(mDisplayDrives.get(Index).getDriveAim().a))
				driveAimBefore = ImageIO.read(new File (moImageNames.get(mDisplayDrives.get(Index).getDriveAim().a))); //drive Aim BEFORE
			else driveAimBefore = ImageIO.read(new File (moImageNames.get("notFound"))); //Drive Object not found in the hash map
			
			if (moImageNames.containsKey(mDisplayDrives.get(Index).getDriveAim().b))
				driveAimAfter = ImageIO.read(new File (moImageNames.get(mDisplayDrives.get(Index).getDriveAim().b))); //drive Aim AFTER
			else driveAimAfter = ImageIO.read(new File (moImageNames.get("notFound"))); //Drive Object not found in the hash map

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
				qoaPicture = moConceptionalImages.get("eaqual");
			else if (bla1 > bla2) qoaPicture = moConceptionalImages.get("decrease");
			else qoaPicture = moConceptionalImages.get("erease");
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
		} catch (IllegalArgumentException e) {
			System.out.println("Fehler beim Icon-Filenamen");
		} catch (IOException e) {
			System.out.println("Fehler beim lesen der Datei");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBound in der Fkt UnterschiedAusgeben im File clsInspectorImageDrives.java");
		} catch (NullPointerException e) {
			System.out.println("NullPointerException in der Fkt UnterschiedAusgeben im File clsInspectorImageDrives.java");
		} catch (Exception e) {
			System.out.println("Fehler in Fkt: UnterschiedAusgeben im File clsInspectorImageDrives.java");
		}
	}
	/**
	 * DOCUMENT (Jordakieva) - displays the space-holder in the desired JPanel 
	 *
	 * @since 28.11.2013 17:53:41
	 *
	 * @param panel
	 */
	void displayEmptyBox (JPanel panel, int step) {
		try {
			Integer t = step;
			
			Image image = moConceptionalImages.get("leer");
			JLabel label = new JLabel (new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_FAST)));
			JLabel theStep = new JLabel ("Step: " + t.toString());
						
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
	void besetThePieVariables (ArrayList<clsPair<String, Integer>> moVariable, int i, boolean defense) {
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
	
	void fillBoarderCenter () {
		
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
		boolean befuellenAktiv = false;
		
		
		if (!mDisplayDrives.isEmpty()) { 
//			for (int i = 0, step = mDisplayDrives.get(0).getStep(); i < nElemente; i++) { //damit die update-Funktion wirklich jeden Schritt nur einmal macht
			for (int i = mnLastDisplayedElement, step = mDisplayDrives.get(mnLastDisplayedElement).getStep(); i < nElemente; i++) { //damit die update-Funktion wirklich jeden Schritt nur einmal macht
				
				boolean hmpf = false; //zeigt an ob ein stomach, rectum, bladder, stamina als Drive vorkommt im aktuellen mDisplayDrives.get(i).getMotivation
				//sollte egtl immer sein
				
				if (step == mDisplayDrives.get(i).getStep()) { //wenn immer noch der selbe Step im mDisplayDrives
					if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("STOMACH")) {
						if (compareObject(mDisplayDrives.get(i))) //wenn die Abwehr nichts verändert hat, dann braucht auch kein Bild zusammengestückelt werden 
							besetThePieVariables (moLStomach, i, false);
						else {
							besetThePieVariables (moLStomach, i, true);
							lo.add(i);
							hmpf = true;
						}					
					}
					if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("STOMACH")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moAStomach, i, false);
						else {
							besetThePieVariables (moAStomach, i, true);
							ao.add(i);
							hmpf = true;
						}					
					}
									
					if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("RECTUM")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moLRectum, i, false);
						else {
							besetThePieVariables (moLRectum, i, true);
							lr.add(i);
							hmpf = true;
						}					
					}
					if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("RECTUM")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moARectum, i, false);
						else {
							besetThePieVariables (moARectum, i, true);
							ar.add(i);
							hmpf = true;
						}					
					}
					
					if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("BLADDER")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moLBladder, i, false);
						else {
							besetThePieVariables (moLBladder, i, true);
							lb.add(i);
							hmpf = true;
						}					
					}
					if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("BLADDER")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moABladder, i, false);
						else {
							besetThePieVariables (moABladder, i, true);
							ab.add(i);
							hmpf = true;
						}					
					}
					
					if (mDisplayDrives.get(i).getMotivation().startsWith("L.") && mDisplayDrives.get(i).getMotivation().endsWith("STAMINA")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moLStamina, i, false);
						else {
							besetThePieVariables (moLStamina, i, true);
							ls.add(i);
							hmpf = true;
						}					
					}
					if (mDisplayDrives.get(i).getMotivation().startsWith("A.") && mDisplayDrives.get(i).getMotivation().endsWith("STAMINA")) {
						if (compareObject(mDisplayDrives.get(i))) 
							besetThePieVariables (moAStamina, i, false);
						else {
							besetThePieVariables (moAStamina, i, true);
							as.add(i);
							hmpf = true;
						}					
					}
					
					if (hmpf) {
						index.add (i);
						befuellenAktiv = true;
					}
						
				} else {
					
					if (befuellenAktiv)
						manageCenterOutput (lo, ao, ls, as, lr, ar, lb, ab, index, step-1);
					befuellenAktiv = false;
					step = mDisplayDrives.get(i).getStep(); //der Step wird aktualisiert
					i--; //wird subtrachiert, sonst verpasst man min einen Schritt
				} //Ende vom Else
				mnLastDisplayedElement = i;
			} //Ende der for-Schleife
			
			if (befuellenAktiv)	manageCenterOutput (lo, ao, ls, as, lr, ar, lb, ab, index, mDisplayDrives.get(mnLastDisplayedElement).getStep()-1);
			mnLastDisplayedElement++; //da ich das i-te Element bereits ausgegeben habe; sonst wäre es doppelt
		} //Ende vom if
	}
	

	/**
	 * DOCUMENT (Jordakieva) - insert description
	 *
	 * @since 06.01.2014 14:30:01
	 *
	 * @param lo
	 * @param ao
	 * @param ls
	 * @param as
	 * @param lr
	 * @param ar
	 * @param lb
	 * @param ab
	 * @param index 
	 */
	private void manageCenterOutput(ArrayList<Integer> lo, ArrayList<Integer> ao,
			ArrayList<Integer> ls, ArrayList<Integer> as,
			ArrayList<Integer> lr, ArrayList<Integer> ar,
			ArrayList<Integer> lb, ArrayList<Integer> ab, ArrayList<Integer> index, int step) {
		
//		double maxStock = Math.max(lo.size(), Math.max (ao.size(), Math.max (lr.size(), Math.max (ar.size(), 
//				Math.max (lb.size(), Math.max (ab.size(), Math.max (ls.size(), as.size()))))))); //die Maximale ArraySize von diesen Elementen wird ermittelt

//		if (maxStock > 0) { //dh zumindest eines der Arrays hat Elemente
		if (!index.isEmpty()) {

			
			for (int k = 0; k < index.size(); k++) {
				JPanel sto = new JPanel ();
				sto.setLayout(new BoxLayout(sto, BoxLayout.Y_AXIS));
				JPanel sta = new JPanel ();
				sta.setLayout(new BoxLayout(sta, BoxLayout.Y_AXIS));
				JPanel re = new JPanel ();
				re.setLayout(new BoxLayout(re, BoxLayout.Y_AXIS));
				JPanel bl = new JPanel ();
				bl.setLayout(new BoxLayout(bl, BoxLayout.Y_AXIS));
				moCenterStamina.add (sta);
				moCenterBladder.add (bl);
				moCenterRectum.add (re);
				moCenterStomach.add (sto);
				////////////////////////////////////
				
				//da für libi und agg zwar der selbe Step aber verschiedener Index ist, wird das jetzt
				//zusammen gewürfelt um einen Step jeweils nur einmal auszugeben
										
				if (lo.contains(index.get(k))) displayTheChangedDrive (index.get(k), sto, step); //(oNames.get(1), sto, step);
				else displayEmptyBox (sto, step);
				
				if (ao.contains(index.get(k))) displayTheChangedDrive (index.get(k), sto, step); //(oNames.get(1), sto, step);
				else displayEmptyBox (sto, step);
				
				if (ls.contains(index.get(k))) displayTheChangedDrive (index.get(k), sta, step); //(oNames.get(1), sta, step);
				else displayEmptyBox (sta, step);
				
				if (as.contains(index.get(k))) displayTheChangedDrive (index.get(k), sta, step); //(oNames.get(1), sta, step);
				else displayEmptyBox (sta, step);
				
				if (lr.contains(index.get(k))) displayTheChangedDrive (index.get(k), re, step); //(oNames.get(1), re, step);
				else displayEmptyBox (re, step);
				
				if (ar.contains(index.get(k))) displayTheChangedDrive (index.get(k), re, step); //(oNames.get(1), re, step);
				else displayEmptyBox (re, step);
				
				if (lb.contains(index.get(k))) displayTheChangedDrive (index.get(k), bl, step); //(oNames.get(1), bl, step);
				else displayEmptyBox (bl, step);
				
				if (ab.contains(index.get(k))) displayTheChangedDrive (index.get(k), bl, step); //(oNames.get(1), bl, step);
				else displayEmptyBox (bl, step);
			}

			lo.clear(); ao.clear();
			lr.clear(); ar.clear();
			lb.clear(); ab.clear();
			ls.clear(); as.clear();
			index.clear();
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 20.11.2013 12:21:08
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		

		//Zentrum mit den Grafiken befüllen
		fillBoarderCenter ();
		
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
		
		moScrollPane.validate();
		
	}
}
