/**
 * F7_SuperEgoReactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author gelbard
 * 02.05.2011, 15:47:53
 */
package pa._v38.modules;

import java.io.*; //muss es mit Sternchen sein :/?
import java.lang.Object;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.regex.PatternSyntaxException;

import pa._v38.modules.eProcessType;
import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.itfGraphInterface;
import pa._v38.interfaces.itfInspectorForRules;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_11_send;
import pa._v38.interfaces.modules.I5_12_receive;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_13_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

import statictools.clsGetARSPath;

/**
 * Checks incoming drives and perceptions according to internalized rules.
 * If one internalized rule fires, a forbidden drive or perception is detected.
 * The forbidden drive or perception is added to the list of forbidden drives or the list of forbidden perceptions, respectively. 
 * The list with forbidden drives is sent to "F06: Defense mechanisms for drives".
 * The list with forbidden perceptions is sent to "F19: Defense mechanisms for perseption".
 * F06 or F19 (Ego) can decide now to defend the forbidden drives or not.
 * 
 * moSuperEgoStrength is a personality parameter which determines the strength of the Super-Ego.
 * Some Super-Ego rules are only affective if the moSuperEgoStrength is above a certain value.
 * 
 * @author zeilinger, gelbard
 * 07.05.2012, 15:47:53
 * 
 */
public class F07_SuperEgoReactive extends clsModuleBase
	implements I5_12_receive, I5_10_receive, I5_11_send, I5_13_send, itfGraphInterface, itfInspectorForRules{

	public static final String P_MODULENUMBER = "07";
	
	public static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
	public static final String P_SUPER_EGO_RULES_FILE = "SUPER_EGO_RULES_FILE";
	public static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
	public static final String P_PSYCHIC_ENERGY_PRIORITY = "PSYCHIC_ENERGY_PRIORITY";
	
	private static int threshold_psychicEnergy;
	private static int msPriorityPsychicEnergy;
	private double moSuperEgoStrength; // personality parameter to adjust the strength of Super-Ego
	
	@SuppressWarnings("unused")
	// Das muss erst noch implementiert werden. Ist jetzt einmal nur vorbereitet.
	private static final int consumed_psychicEnergyPerInteration = 1;
	
	//AW 20110522: New inputs
	private clsThingPresentationMesh moPerceptionalMesh_IN;	
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	private ArrayList<clsEmotion> moEmotions_Input;
	
	@SuppressWarnings("unused")
	private Object moMergedPrimaryInformation;
	private ArrayList<clsDriveMesh> moDrives;
	private ArrayList<clsReadSuperEgoRules> oRegeln;     //die Regeln die das Über-Ich verbietet Ivy
	private ArrayList<clsPair<eDriveComponent, eOrgan>> moForbiddenDrives;
	private ArrayList<clsPair<eContentType, String>> moForbiddenPerceptions;
	private ArrayList<eEmotionType> moForbiddenEmotions;
	
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	private ArrayList<String> Test= new ArrayList<String>() ;
//	Ivy begin ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //
	private ArrayList<String> moSuperEgoOutputRules = new ArrayList <String> (); //für die Ausgabe	
	
	/**
	 * DOCUMENT (Jordakieva) - die Klasse speichert die Über-Ich-Regeln (Perceptions, Drives, Emotions), welche extern aus einer Datei ausgelesen werden
	 * 
	 * @author Jordakieva
	 * 04.10.2013, 12:21:01
	 * 
	 */
	public class clsReadSuperEgoRules {
	    
	    private ArrayList <clsPair <eContentType, String>> oForbiddenPerceptionRule = new ArrayList <clsPair <eContentType, String>> ();
	    private ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> oForbiddenDriveRule = new ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> ();
	    private ArrayList <clsPair <eEmotionType, Double[]>> oForbiddenEmotionRule = new ArrayList <clsPair <eEmotionType, Double[]>> ();
	    
	    private ArrayList <clsPair <eContentType, String>> oForbiddenObject = new ArrayList <clsPair <eContentType, String>> (); //zB: eContentType.ENTITY, "CAKE"
	    private ArrayList <clsPair <eDriveComponent, eOrgan>> oForbiddenDrive = new ArrayList <clsPair <eDriveComponent, eOrgan>> (); //zB: eDriveComponent.AGGRESSIVE, eOrgan STOMACH
	    private ArrayList <eEmotionType> oForbiddenEmotion = new ArrayList <eEmotionType> ();
	    
	    
	    private clsReadSuperEgoRules (eContentType ect, String str) {
            addFRule(ect, str);
        }
	    /**
	     * DOCUMENT (Jordakieva) - Konstruktor 
	     *
	     * @since 04.10.2013 15:45:49
	     *
	     * @param edc - eDriveComponent
	     * @param eorg - eOrgan
	     * @param fl - speichert den QoA ab;
	     *             ist fl [null], wird die Regel für alle QoA angewendet
	     *             ist fl [null, Zahl] wird die Regel nur dann angewendet, wenn der aktuelle QoA niedriger ist als die Zahl hier
	     *             fl [Zahl, null] ist nicht vorgesehen
	     */
	    private clsReadSuperEgoRules (eDriveComponent edc, eOrgan eorg, Double[] fl) {
	        addFRule(edc, eorg, fl); 
        }
	    /**
	     * DOCUMENT (Jordakieva) - Konstruktor
	     *
	     * @since 04.10.2013 15:47:52
	     *
	     * @param em - eEmoitonType
	     * @param fl - speichert den QoA ab;
         *             ist fl [null], wird die Regel für alle QoA angewendet
         *             ist fl [null, Zahl] wird die Regel nur dann angewendet, wenn der aktuelle QoA niedriger ist als die Zahl hier
         *             fl [Zahl, null] ist nicht vorgesehen
	     */
	    private clsReadSuperEgoRules (eEmotionType em, Double [] fl) {
	        addFRule (em, fl);   
	    }
	    
	    
	    
	    	    
	    /**
	     * DOCUMENT (Jordakieva) - erweitert die Regel der Objekte um einen Element
	     *
	     * @since 18.09.2013 18:06:39
	     *
	     * @param ect - eContentType = Objekttyp
	     * @param str - String = Objektart
	     */
	    private void addFRule (eContentType ect, String str) {
	            oForbiddenPerceptionRule.add(new clsPair<eContentType, String>(ect, str));
	    }
	    /**
	     * DOCUMENT (Jordakieva) - erweitert die Regel der Triebe um einen Element
	     *
	     * @since 18.09.2013 18:16:28
	     *
	     * @param edc - eDriveComponent = der Trieb
	     * @param eorg - eOrgan = die Triebart
	     * @param fl - Double = den QoA
	     */
	    private void addFRule (eDriveComponent edc, eOrgan eorg, Double[] fl) {
	        if (fl != null) {
	            if (fl[0] == null) fl[0] = Double.NEGATIVE_INFINITY;
	            oForbiddenDriveRule.add(new clsTriple<eDriveComponent, eOrgan, Double[]>(edc, eorg, fl));
	        } else {
	            Double[] temp = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
	            oForbiddenDriveRule.add(new clsTriple<eDriveComponent, eOrgan, Double[]>(edc, eorg, temp));
	        }
	    }	    
	    /**
	     * DOCUMENT (Jordakieva) - erweitert die Regel der Emotionen um einen Element
	     *
	     * @since 04.10.2013 12:18:01
	     *
	     * @param em - die Emotionsart
	     * @param fl - den QoA
	     */
	    private void addFRule (eEmotionType em, Double[] fl) {
	        if (fl != null) {
	            if (fl[0] == null) fl[0] = Double.NEGATIVE_INFINITY;
	            oForbiddenEmotionRule.add(new clsPair<eEmotionType, Double[]>(em, fl));
	        } else {
	            Double[] temp = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY}; //da clsPair kein null erlaubt (clsTripple hingegen schon, pff fies!!)
	            oForbiddenEmotionRule.add(new clsPair<eEmotionType, Double[]>(em, temp));
	        }
	    }
	    
	    
	    
	    
	    /**
	     * DOCUMENT (Jordakieva) - speichert das zu verbietende Objekt
	     *
	     * @since 18.09.2013 18:09:05
	     *
	     * @param type - eContentType
	     * @param str - String
	     */
	    private void addFObject (eContentType type, String str) {
	        oForbiddenObject.add( new clsPair <eContentType, String> (type, str));
	    }
	    /**
	     * DOCUMENT (Jordakieva) - speichert der zu verbietende Trieb
	     *
	     * @since 04.10.2013 15:50:12
	     *
	     * @param edc - eDriveComponent
	     * @param eor - eOrgan
	     */
	    private void addFObject (eDriveComponent edc, eOrgan eor) {
	        oForbiddenDrive.add(new clsPair <eDriveComponent, eOrgan> (edc, eor));
	    }
	    /**
	     * DOCUMENT (Jordakieva) - speichert die zu verbietende Emotion
	     *
	     * @since 04.10.2013 15:50:41
	     *
	     * @param em - eEmoitonType
	     */
	    private void addFObject (eEmotionType em) {
	        oForbiddenEmotion.add(em);
	    }
	    
	    
	    
	    /**
	     * DOCUMENT (Jordakieva) - liefert die verbotene Wahrehmungs-Regel
	     *
	     * @since 18.09.2013 18:17:06
	     *
	     * @param i - die Stelle
	     * @return die verbotene Wahrnehmungs-Regel
	     */
	    public clsPair <eContentType, String> getPerceptionRule (int i) {
	        if (oForbiddenPerceptionRule.size() < i) return null;
	        return oForbiddenPerceptionRule.get(i);
	    }
	    /**
	     * DOCUMENT (Jordakieva) - liefert die verbotene Trieb-Regel
	     *
	     * @since 18.09.2013 18:18:35
	     *
	     * @param i - die Stelle
	     * @return die verbotene Trieb-Regel
	     */
	    public clsTriple <eDriveComponent, eOrgan, Double[]> getDriveRule (int i) {
	        if (oForbiddenDriveRule.size() < i) return null;
	        return oForbiddenDriveRule.get(i);
	    }
	    /**
	     * DOCUMENT (Jordakieva) - liefert die verbotene Emotions-Regel
	     *
	     * @since 04.10.2013 12:14:20
	     *
	     * @param i - die Stelle
	     * @return die verbotene Emotions-Regel
	     */
	    public clsPair <eEmotionType, Double[]> getEmotionRule (int i) {
	        if (oForbiddenEmotionRule.size() < i) return null;
	        return oForbiddenEmotionRule.get(i);
	    }
	    
	    
	    
	    /**
	     * DOCUMENT (Jordakieva) - liefert das zu verbietende Object in der einen Regel an der Stelle i
	     *
	     * @since 18.09.2013 18:19:36
	     *
	     * @param i - die Stelle
	     * @return liefert das zu verbietende Object in der einen Regel
	     */
	    public clsPair <eContentType, String> getForbiddenObject (int i) {
	        if (oForbiddenObject.size() < i) return null;
	        return oForbiddenObject.get(i);
	    }
	    /**
	     * DOCUMENT (Jordakieva) - liefert der zu verbietende Trieb in der einen Regel an der Stelle i
	     *
	     * @since 04.10.2013 12:07:45
	     *
	     * @param i - die Stelle
	     * @return liefert der zu verbietende Trieb in der einen Regel
	     */
	    public clsPair <eDriveComponent, eOrgan> getForbiddenDrive (int i) {
	        if (oForbiddenDrive.size() < i) return null;
            return oForbiddenDrive.get(i);
        }
	    /**
	     * DOCUMENT (Jordakieva) - liefert die zu verbietende Emotion in der einen Regel an der Stelle i
	     *
	     * @since 04.10.2013 12:07:48
	     *
	     * @param i - die Stelle
	     * @return liefert die zu verbietende Emotion in der einen Regel
	     */
	    public eEmotionType getForbiddenEmotion (int i) {
	        if (oForbiddenEmotion.size() < i) return null;
            return oForbiddenEmotion.get(i);
        }
	    
	    
	    
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der Wahrnehmungen in der einen Regel
	     *
	     * @since 18.09.2013 18:21:04
	     *
	     * @return Anzahl der Wahrnehmungen in der einen Regel
	     */
	    public int perceptionSize () {
	        return oForbiddenPerceptionRule.size();
	    }
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der Triebe in der einen Regel
	     *
	     * @since 18.09.2013 18:21:27
	     *
	     * @return Anzahl der Triebe in der einen Regel
	     */
	    public int driveSize () {
	        return oForbiddenDriveRule.size();
	    }
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der Emotionen in der einen Regel
	     *
	     * @since 04.10.2013 12:04:56
	     *
	     * @return Anzahl der Emotionen in der einen Regel
	     */
	    public int emotionSize () {
	        return oForbiddenEmotionRule.size();
	    }
	    
	    
	    
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der zu verbietende Objekte in der einen Regel
	     *
	     * @since 18.09.2013 18:21:41
	     *
	     * @return Anzahl der zu verbietende Objekte in der einen Regel
	     */
	    public int FObjectSize () {
	        return oForbiddenObject.size();
	    }
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der zu verbietende Triebe in der einen Regel
	     *
	     * @since 04.10.2013 11:58:25
	     *
	     * @return  Anzahl der zu verbietende Triebe in der einen Regel
	     */
	    public int FDriveSize () {
            return oForbiddenDrive.size();
        }
	    /**
	     * DOCUMENT (Jordakieva) - Anzahl der zu verbietende Emotionen in der einen Regel
	     *
	     * @since 04.10.2013 11:57:05
	     *
	     * @return Anzahl der zu verbietende Emotionen in der einen Regel
	     */
	    public int FEmotionSize () {
	        return oForbiddenEmotion.size();
	    }
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description Ivy: liest die SuperEgoRules Datei aus
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:49:49
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poPsychicEnergyStorage 
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F07_SuperEgoReactive(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicEnergyStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
		
		moForbiddenDrives = new ArrayList<clsPair<eDriveComponent, eOrgan>>();
		moForbiddenPerceptions = new ArrayList<clsPair<eContentType,String>>();
		moForbiddenEmotions = new ArrayList<eEmotionType>();
		
		applyProperties(poPrefix, poProp); 
		
		threshold_psychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_THESHOLD).getParameterInt();
		msPriorityPsychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_PRIORITY).getParameterInt();
		moSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_STRENGTH).getParameterDouble();
		
// Ivy begin ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //
		
		BufferedReader oReadIn = null;
        int nZeile = 1; //zeigt bei einer Exception die Zeile im file an wo es passiert ist
        String oFileName = ""; //wird Pfad + Dateinamen des SuperEgoReactiveDrives enthalten
        
        oRegeln = new ArrayList<clsReadSuperEgoRules> ();
        
		try {
		    oFileName = clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath() + System.getProperty("file.separator") + poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_RULES_FILE).getParameter();
		} catch (Exception e) {
		    System.out.println("no SuperEgoReactife-filename in the default.properties");
		}
				
		if (!oFileName.isEmpty()) {
		
		try {
		    oReadIn = new BufferedReader (new FileReader (new File (oFileName))); //öffnet das File mit den Regeln auf
		
		    String oLine = null; //die Zeile vom File
		    
		    for (int nListe = 0, nStelle = 0; (oLine = oReadIn.readLine()) != null; nZeile++ ) { //read line by line from the file
		        
		        if (!oLine.startsWith("--") && !oLine.isEmpty()) { //sieht nach, ob die Zeile ein Kommentar oder leer ist
		            oLine = oLine.replace (',','.'); //falls jmd den QoA mit , getrennt hat, würde es eine Exepiton geben
		            
		            nStelle = nListe;
    		        String[] oLineBan = oLine.split("#"); //Zeilenweise wird der String zuerst nach '#' geteilt, weil mit '#' sind die Verbote definiert und diese behandele ich später
    		        String[] oLinePart = oLineBan[0].split(";"); //der erste Teil ohne die verbotene Objekte wird nach Regelwerken/Syntax bearbeitet
    		        
    		        String[] oSuperEgo = oLinePart[0].split(" ");
    		        Double nSuperEgo = 0.0;
    		        if (oSuperEgo[0].equalsIgnoreCase("SuperEgoStrength")) {
    		            nSuperEgo = Double.valueOf(oSuperEgo[1]);
    		        }
    		        
    		        if (moSuperEgoStrength >= nSuperEgo) { //die Regel wird angenommen und abgespeichert nur wenn das SuperEgo größer ist als das im property-File
    		        
        		        String oeDrive ="", oeOrgan="", oeEmotion="";
        		        boolean schalter = false; //wenn true ist ein eDriveComponent gefunden
        		        boolean beEmotion = false; //true, wenn ein eEmotionType gefunden wurde
        		        
        		        for (int nDriveElements = oLinePart.length, i = 0; i < nDriveElements; i++) { // für alle Tuppeln EINER TriebeRegel
        		            String[] oLineElements = oLinePart[i].split(" "); //Tuppel Unterteilung in Elemente
        		            		            		            
        	                switch (oLineElements.length) {
        	                case 2:
        
        	                    if (oLineElements[0].equalsIgnoreCase("eDriveComponent"))  {
        	                        if (!oeDrive.isEmpty() && schalter) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eDriveComponent und eOrgan 
                                        if (nStelle == nListe) //wenn true, ist es ein erstes Element, deswegen mit new anlegen; ansonsten eben add-en
                                            oRegeln.add(nListe, new clsReadSuperEgoRules (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
                                        else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null);
                                        
                                        nStelle++; //erhöhen, damit das nächste nicht mit new angelegt wird und somit unzusammenhängend wird
                                        oeDrive = "";
                                        schalter = false;
                                    }
        	                        oeDrive = oLineElements[1];
        	                    } else if (oLineElements[0].equalsIgnoreCase("eOrgan"))  {
        	                        oeOrgan = oLineElements[1];
        	                        schalter = true; //man soll den Trieb nicht gleich abspeichern, weil er vlt ein QoA hat
        	                    } else if (oLineElements[0].equalsIgnoreCase("QoA"))  {
        	                        Double[] rTmpQoA = {Double.NEGATIVE_INFINITY, Double.valueOf(oLineElements[1])}; 
        	                        
        	                        if (beEmotion) {
        	                            if (nStelle == nListe) 
                                            oRegeln.add(new clsReadSuperEgoRules (eEmotionType.valueOf(oeEmotion), rTmpQoA) );
                                        else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), rTmpQoA);
        	                            
        	                            nStelle++;
        	                            beEmotion = false;
        	                        } else if (oeDrive.isEmpty() || oeOrgan.isEmpty())
        	                            System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nZeile);
        	                        else {
    
        	                            if (nStelle == nListe) 
        	                                oRegeln.add(new clsReadSuperEgoRules (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA) );
        	                            else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA);
        
        	                            nStelle++;
                                        oeDrive = "";
                                        oeOrgan = "";
        	                            schalter = false;
        	                        }
        	                    } else if (oLineElements[0].equalsIgnoreCase("eEmotionType")) {
        	                        oeEmotion = oLineElements[1];
        	                        beEmotion = true;
        	                    }
                                break;
        	                case 3: 
        	                    if (oLineElements[0].equalsIgnoreCase("QoA")) {
        	                        
        	                        Double[] rTmpQoA = {Double.valueOf(oLineElements[1]), Double.valueOf(oLineElements[2])};
        	                        
        	                        if (beEmotion) {
        	                            if (nStelle == nListe) 
                                            oRegeln.add(new clsReadSuperEgoRules (eEmotionType.valueOf(oeEmotion), rTmpQoA) );
                                        else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), rTmpQoA);
        	                            
        	                            nStelle++;
        	                            beEmotion = false;
        	                        }
        	                        else if (oeDrive.isEmpty() || oeOrgan.isEmpty())
                                        System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nZeile);
                                    else {
                                                                            
                                        if (nStelle == nListe) 
                                            oRegeln.add(new clsReadSuperEgoRules (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA) );
                                        else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA);
                                     
                                        nStelle++;
                                        oeDrive = "";
                                        oeOrgan = "";
                                        schalter = false;
                                    }
        	                    } else if (!oeDrive.isEmpty() && schalter) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eDriveComponent und eOrgan 
        	                        if (nStelle == nListe) 
        	                            oRegeln.add(nListe, new clsReadSuperEgoRules (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
        	                        else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null);
        	                        
        	                        nStelle++;
        	                        oeDrive = "";
        	                        oeOrgan = "";
        	                        schalter = false;    	                        
        	                    } else if (beEmotion) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eEmotionType
        	                        if (nStelle == nListe) 
                                        oRegeln.add(new clsReadSuperEgoRules (eEmotionType.valueOf(oeEmotion), null) );
                                    else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), null);
        	                        
        	                        nStelle++;
        	                        beEmotion = false;
        	                    }
        	                    
        	                    if (oLineElements[0].equalsIgnoreCase("eContentType")) {
        	                        if (nStelle == nListe)
        	                            oRegeln.add(nListe, new clsReadSuperEgoRules (eContentType.valueOf(oLineElements[1]), oLineElements[2]));
        	                        else oRegeln.get(nListe).addFRule(eContentType.valueOf(oLineElements[1]), oLineElements[2]);
        	                        nStelle++;
        	                    }    	                    
        	                    break;
        	                }
        		        }
        		        
        		        if (!oeDrive.isEmpty() && schalter) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eDriveComponent und eOrgan 
                            if (nStelle == nListe) 
                                oRegeln.add(nListe, new clsReadSuperEgoRules (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
                            else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null);
                        }
        		        
        		        if (oLineBan.length > 0)  {
        		            String oBuffer = "";
        		            if (nStelle == nListe)
        		                System.err.println("Verbote ohne Regeln werden einfach nicht beachtet :P!");
        		            else {
            		            for (int i = 1; i < oLineBan.length; i++) { //i=0 sind die Regeln; ab i = 1 sind die verbotene Triebe/Objekte aufgelistet
            		                String[] oSplit = oLineBan[i].split(" ");
            		                
            		                // zu blockierende Triebe und Wahrnehmungen abspeichern
            		                if (oSplit[0].equalsIgnoreCase("eContentType") && oSplit[1].equalsIgnoreCase("ENTITY")) {
            		                    oRegeln.get(nListe).addFObject(eContentType.valueOf(oSplit[1]), oSplit[2]);
            		                } else if (oSplit[0].equalsIgnoreCase("eDriveComponent")) {
            		                    oBuffer = oSplit[1];    		                    
            		                } else if (oSplit[0].equalsIgnoreCase("eOrgan")) {
            		                    if (!oBuffer.isEmpty()) {
            		                        oRegeln.get(nListe).addFObject(eDriveComponent.valueOf(oBuffer), eOrgan.valueOf(oSplit[1]));
            		                        oBuffer = "";
            		                    } else System.err.println("Verbotener Trieb ist Fehlerhaft, Zeile: " + nListe);
            		                } else if (oSplit[0].equalsIgnoreCase("eEmotionType")) {
            		                    oRegeln.get(nListe).addFObject(eEmotionType.valueOf(oSplit[1]));
            		                }
            		            }
            		            nListe++;
            		        }
        		        }
        		    }
		        }
		    }
		} catch (FileNotFoundException e) {
		    System.err.println("Schlampigkeitsfehler: kann die ÜberIchRegel-Datei nicht finden/öffnen!");
        } catch (NullPointerException e) {
            System.err.println("Fehler in der ÜberIchRegel-Datei, Zeile: " + nZeile);
        } catch (IOException e) {  
		    e.printStackTrace(); 
	    } catch (PatternSyntaxException e) {
	        e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	        System.err.println("in der ÜberIchRegel-Datei gibt es einen Fehler in der Rechtschreibung, Zeile: " + nZeile);
	    } finally {
		    if (oReadIn != null)
		        try { oReadIn.close(); }
		                catch (IOException e) { System.err.println("Fehler beim schließen der ÜberIchRegel-Datei"); }
		} } else System.err.println("Simulation gestartet ohne SuperEgoReactive-Regeln");

		//Ivy Sandkiste ->|
// Ivy // ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //

	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * *
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		text += toText.valueToTEXT("moDrives", moDrives);
		text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
		text += toText.listToTEXT("--------------------------------moSuperEgoRules-----------------------------", moSuperEgoOutputRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoEmotionsRules---------------------------", moSuperEgoEmotionsRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoPerceptionsRules------------------------", moSuperEgoPerceptionsRules);
		text += toText.listToTEXT("--------------------------------Test----------------------------------------", Test);
		text += toText.valueToTEXT("moForbiddenDrives", moForbiddenDrives);		
		text += toText.valueToTEXT("moForbiddenPerceptions", moForbiddenPerceptions);
		text += toText.valueToTEXT("moForbiddenEmotions", moForbiddenEmotions);
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_12_receive#receive_I5_12(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_12(ArrayList<clsDriveMesh> poDrives,  ArrayList<clsEmotion> poEmotions) {
		
		moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives); 
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//AW 20110522: Input from perception
		try {
			//moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.cloneGraph();
			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber, threshold_psychicEnergy, msPriorityPsychicEnergy);
		// if there is enough psychic energy
	
		

		if (rReceivedPsychicEnergy > threshold_psychicEnergy
				/* for test purposes only: */ || true)
			checkInternalizedRules();	// check perceptions and drives, and apply internalized rules

	}

	/* (non-Javadoc)
	 *
	 * @author kollmann
	 * 17.09.2013, 14:00
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
//		try {
//			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		
//		//clear the list of forbidden drives, every turn
//		moForbiddenDrives.clear();
//		
//		if(moSuperEgoStrength >= 0.5) //if super ego is strong enough - 0.5 is an arbitrary value
//		{
//			//simple_rule to deal with eating in BODOs vicinity
//
//			ArrayList<String> oEntities = new ArrayList<String> ();
//
//			oEntities.add("BODO");
//
//			simple_rule(eDriveComponent.LIBIDINOUS,
//				eOrgan.STOMACH,
//				0.0, //FIXME Kollmann: The super-rule for divide should only fire above a certain QoA
//				0.1, // Kollmann: When the stomach is full -> LIB/STOM has a QoA ~ 0.04 
//				oEntities);
//		}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
//	
//	/**
//	 * DOCUMENT (Kollmann) - alternative call to simple_rule without any perceivable entities
//	 *
//	 * @since 17.09.2013 14:16:37
//	 *
//	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE)
//	 * @param poOrgan the drive source
//	 * @param pnMinQuota the minimum quota of affect the drive needs to activate the rule
//	 * @param pnMaxQuota the maximum (>=) QoA the drive can have to activate the rule
//	 * @return true if the rule was triggered, false otherwise
//	 */
//	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota)
//	{
//	    return simple_rule(poComponent, poOrgan, pnMinQuota, pnMaxQuota, new ArrayList<String>());
//	}
//	
//	/**
//	 * DOCUMENT (Kollmann) - Checks if the rule, defined by the parameters, is present and, if it is,
//	 *                       adds the corresponding eDriveComponent/eOrgan combination to the list of
//	 *                       forbidden drives
//	 *
//	 * @since 17.09.2013 14:18:15
//	 *
//	 * @param poComponent the drive component (poComponent.LIBIDINOUSE or poComponent.AGGRESIVE) 
//	 * @param poOrgan the drive source
//	 * @param pnMinQuota the minimum QoA the drive needs to activate the rule
//	 * @param pnMaxQuota the maximum QoA the drive can have to activate the rule
//	 * @param poPerceivedEntities - a list of perceivable entities - the rule will only trigger if all
//	 *                              these entities are currently perceived
//	 * @return true if the rule was triggered, false otherwise
//	 */
//	protected boolean simple_rule(eDriveComponent poComponent, eOrgan poOrgan, double pnMinQuota, double pnMaxQuota,
//			ArrayList<String> poPerceivedEntities)
//	{
//		boolean rule_triggered = false;
//		clsPair<eDriveComponent, eOrgan> oDrive;
//		
//		clsQuadruppel<String, eDriveComponent, eOrgan, Double> oForbiddenDrive = null;
//		clsTriple<String, clsQuadruppel<String, eDriveComponent, eOrgan, Double>, ArrayList<String>> oDriveRules=null;
//		ArrayList<String> oContentTypeDrives= new ArrayList<String> ();
//				
//		//check for a fitting drive
//		for(clsDriveMesh oDrives : moDrives){
//			if (oDrives.getDriveComponent().equals(poComponent) &&
//				oDrives.getActualDriveSourceAsENUM().equals(poOrgan) &&
//				oDrives.getQuotaOfAffect() >= pnMinQuota &&
//				oDrives.getQuotaOfAffect() <= pnMaxQuota)
//			{		
//				//logging for inspectors
//				oForbiddenDrive = new clsQuadruppel<String,eDriveComponent, eOrgan,Double>(
//						pnMinQuota + " =< " + poComponent.toString() + "/" + poOrgan.toString() + " >= " + pnMaxQuota,
//						poComponent,
//						poOrgan,
//						pnMinQuota);
//				
//				//check if entities are perceived
//				for(String oEntity : poPerceivedEntities)
//				{
//					if(searchInTPM(eContentType.ENTITY, oEntity))
//					{
//						oContentTypeDrives.add("ENTITY=" + oEntity);
//					}
//				}
//	
//				if(oContentTypeDrives.size() > poPerceivedEntities.size()) //should never happen
//				{
//					System.err.println("PROBLEM: found more perceivide entities than specified entities");
//				}else if(oContentTypeDrives.size() == poPerceivedEntities.size())
//				{
//				  //forbid drive, if not yet forbidden
//					oDrive = new clsPair<eDriveComponent, eOrgan>(poComponent, poOrgan);
//					
//					if (!moForbiddenDrives.contains(oDrive)) // no duplicate entries
//					{
//						moForbiddenDrives.add(oDrive);
//						rule_triggered = true;
//					}
//					
//					//log forbidding of drive
//					oDriveRules= new clsTriple<String,clsQuadruppel<String,eDriveComponent, eOrgan,Double>,ArrayList<String>>("SuperEgoStrength >= 0.5", oForbiddenDrive, oContentTypeDrives);
//					if(!moSuperEgoDrivesRules.contains(oDriveRules))
//					{
//						// add rule info to inspector 
//						moSuperEgoDrivesRules.add(oDriveRules);
//					}
//				}
//			}
//		}
//		
//		
//		return rule_triggered;
//	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:12
	 * 
	 * Super-Ego checks perception and drives
	 * 
	 */
	private void checkInternalizedRules() {
		
		// If no Super-Ego rule fires, the lists with forbidden drives, forbidden perceptions, and forbidden emotions must be empty
		moForbiddenDrives     .clear();
		moForbiddenPerceptions.clear();
		moForbiddenEmotions   .clear();
				
// Ivy ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //

		moSuperEgoOutputRules.clear();
				
        int nRules = oRegeln.size(); //number of rules
        
        if (nRules > 0) {
            
            try {
            
                for (int i = 0; i < nRules; i++) { //für alle Regel
                    
                    int driveSize = oRegeln.get(i).driveSize();
                    int perceptionSize = oRegeln.get(i).perceptionSize();
                    int emotionSize = oRegeln.get(i).emotionSize();
                    int p = 0, d = 0, e = 0;
                    boolean perceptionsGefunden = false; //TPMs brauche ich nur einmal auf vollständigkeit überprüfen
                    
                    if (driveSize > 0) { //ich darf keine TPM ohne DMs verbieten, da TPMs alleine nicht anstoßig sein können
                        
                        boolean bDrive = true;
                        for (d = 0; d < driveSize && bDrive; d++) { //für alle DM in einer Regel
                            
                            bDrive = false;
                            for (clsDriveMesh oDrives : moDrives) { //moDrives ist received_I5_12 moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives);
                                
                                if (oDrives.getDriveComponent().equals(oRegeln.get(i).getDriveRule(d).a) &&
                                        oDrives.getActualDriveSourceAsENUM().equals(oRegeln.get(i).getDriveRule(d).b) &&
                                        oDrives.getQuotaOfAffect() >= oRegeln.get(i).getDriveRule(d).c[0] && 
                                        oDrives.getQuotaOfAffect() <= oRegeln.get(i).getDriveRule(d).c[1]) 
                                {
                                    bDrive = true;
                                    break;
                                } 
                            }
                        }
                        
                        
                        if (bDrive && d == driveSize) { //wurden alle DM von der einen Regel gefunden
                            
                            boolean bPerception = true;                    
                            if (perceptionSize > 0) {
                                
                                ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getMoInternalAssociatedContent();
                            
                                for (p = 0; p < perceptionSize && bPerception; p++) {
                                    
                                    bPerception = false;
                                    for (clsAssociation oAssociation : oInternalAssociations) {
                                        if (oAssociation.getMoAssociationElementB() instanceof clsThingPresentationMesh) {
                                            if( ((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContentType().equals(oRegeln.get(i).getPerceptionRule(p).a) &&
                                                    ((clsThingPresentationMesh)oAssociation.getMoAssociationElementB()).getMoContent().equals(oRegeln.get(i).getPerceptionRule(p).b) ) {
                                                //EINE TPM GEFUNDEN (von vlt vielen) JUHUU! und da gefunden ab zur nächsten in der Schleife
                                                bPerception = true;
                                                break;
                                            }
                                        }
                                    }   
                                }
                                
                                if ((bPerception && p == perceptionSize)) { //true wenn alle TPMs gefunden wurden = abspeichern
                                    perceptionsGefunden = true;
                                }
                                
                            } else { //keine TPMs in meiner Regel, aber die DMs abspeichern
                                perceptionsGefunden = true;
                            }
                            
                            if (perceptionsGefunden) {
                                
                                boolean bEmotionGefunden = false;
                                boolean beGefunden = false;
                                
                                if (emotionSize > 0) {
                                    for (e = 0; e < emotionSize; e++) {
                                        beGefunden = false;
                                        for(clsEmotion oOneEmotion : moEmotions_Input) {
                                            
                                            double rEmotionItensity = oOneEmotion.getMrEmotionIntensity();
                                            
                                            if (oOneEmotion.getMoContent().equals(oRegeln.get(i).getEmotionRule(e).a) &&
                                                    oRegeln.get(i).getEmotionRule(e).b[0] <= rEmotionItensity && 
                                                            oRegeln.get(i).getEmotionRule(e).b[1] >= rEmotionItensity) {
                                                beGefunden = true;
                                                break;
                                            }
                                        }
                                    }
                                    
                                    
                                    if (e == emotionSize && beGefunden) bEmotionGefunden = true;
                                } else bEmotionGefunden = true;
                                
                                 
                                if (bEmotionGefunden) {
                                    moSuperEgoOutputRules.add("FileRule: " + (i+1) + "\nDrives:");
                                    for (int tmp=0; tmp < driveSize; tmp++) {
                                        moSuperEgoOutputRules.add(oRegeln.get(i).oForbiddenDriveRule.get(tmp).a.toString() + " " +
                                                oRegeln.get(i).oForbiddenDriveRule.get(tmp).b.toString() + " " +
                                                        oRegeln.get(i).oForbiddenDriveRule.get(tmp).c[0].toString() + " " +
                                                        oRegeln.get(i).oForbiddenDriveRule.get(tmp).c[1].toString());
                                            
                                    }
                                    moSuperEgoOutputRules.add("\nPerception:");
                                    for (int tmp=0; tmp < perceptionSize; tmp++) 
                                        moSuperEgoOutputRules.add(oRegeln.get(i).oForbiddenPerceptionRule.get(tmp).toString());
                                    moSuperEgoOutputRules.add("\nEmotion:");
                                    for (int tmp=0; tmp < emotionSize; tmp++) 
                                        moSuperEgoOutputRules.add(oRegeln.get(i).oForbiddenEmotionRule.get(tmp).a.toString() + " " +
                                                oRegeln.get(i).oForbiddenEmotionRule.get(tmp).b[0].toString() + " " +
                                                    oRegeln.get(i).oForbiddenEmotionRule.get(tmp).b[1].toString());
                                    moSuperEgoOutputRules.add("----------------------------------------------");
		
                                    
                                    for (int fd = 0; fd < oRegeln.get(i).FDriveSize(); fd++) {
                                        if (!moForbiddenDrives.contains(oRegeln.get(i).getForbiddenDrive(fd))) 
                                            moForbiddenDrives.add(oRegeln.get(i).getForbiddenDrive(fd));
                                    }
                                    for (int fp = 0; fp < oRegeln.get(i).FObjectSize(); fp++) {
                                        if (!moForbiddenPerceptions.contains(oRegeln.get(i).getForbiddenObject(fp))) 
                                            moForbiddenPerceptions.add(oRegeln.get(i).getForbiddenObject(fp));
                                    }
                                    for (int fe = 0; fe < oRegeln.get(i).FEmotionSize(); fe++) {
                                        if (!moForbiddenEmotions.contains(oRegeln.get(i).getForbiddenEmotion(fe))) 
                                            moForbiddenEmotions.add(oRegeln.get(i).getForbiddenEmotion(fe));
                                    }
                                }
                            }
                        } // Ende TPM durchsuchen und in verbotene Liste aufnehmen                
                    } // Ende DM durchsuchen
                } // Ende "für jede Zeile" = für jede Regel
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Index nicht vorhanden");
            }
        }
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_13(moForbiddenDrives, moDrives,moForbiddenEmotions, moEmotions_Input ); 
		send_I5_11(moForbiddenPerceptions, moPerceptionalMesh_OUT, moForbiddenEmotions, moEmotions_Input); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		moDescription = "Based on internalized rules, Super-Ego checks incoming perceptions and drives. If the internalized rules are violated Super-Ego requests from F06 and F19 to activate the defense mechanisms.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_13_send#send_I5_13(java.util.ArrayList)
	 */
	@Override
	public void send_I5_13(ArrayList<clsPair<eDriveComponent, eOrgan>> poForbiddenDrives, ArrayList<clsDriveMesh> poData,ArrayList<eEmotionType> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions) {
		((I5_13_receive)moModuleList.get(6)).receive_I5_13(poForbiddenDrives, poData, poEmotions);
		
		putInterfaceData(I5_13_send.class, poForbiddenDrives, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_11_send#send_I5_11(java.util.ArrayList)
	 */
	@Override
	public void send_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh, ArrayList<eEmotionType> poForbiddenEmotions, ArrayList<clsEmotion> poEmotions) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poPerceptionalMesh, poForbiddenEmotions, poEmotions);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poPerceptionalMesh);
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 6, 2012 11:11:19 AM
	 * 
	 * @see pa._v38.interfaces.itfGraphInterface#getGraphInterfaces()
	 */
	@Override
	public ArrayList<eInterfaces> getGraphInterfaces() {
		return this.getInterfaces();
	}

    /* (non-Javadoc)
     *
     * @since 01.11.2013 16:52:14
     * 
     * @see pa._v38.interfaces.itfInspectorForRules#getDriverules()
     */
    @Override
    public ArrayList<clsReadSuperEgoRules> getDriverules() {
        
        return oRegeln;
    }
}
