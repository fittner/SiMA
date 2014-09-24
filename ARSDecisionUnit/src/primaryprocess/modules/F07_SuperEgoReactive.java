/**
 * F7_SuperEgoReactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author gelbard
 * 02.05.2011, 15:47:53
 */
package primaryprocess.modules;

import inspector.interfaces.itfGraphInterface;
import inspector.interfaces.itfInspectorForRules;

import java.io.*; //muss es mit Sternchen sein :/?
import java.lang.Object;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.regex.PatternSyntaxException;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_10_receive;
import modules.interfaces.I5_11_receive;
import modules.interfaces.I5_11_send;
import modules.interfaces.I5_12_receive;
import modules.interfaces.I5_13_receive;
import modules.interfaces.I5_13_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsAssociation;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import primaryprocess.functionality.superegofunctionality.clsReadSuperEgoRules;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflict;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;
import utils.clsGetARSPath;

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
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
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
	private ArrayList<clsSuperEgoConflict> moForbiddenDrives;
	private ArrayList<clsSuperEgoConflictPerception> moForbiddenPerceptions;
	private ArrayList<eEmotionType> moForbiddenEmotions;
	
	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	//private ArrayList<String> Test= new ArrayList<String>() ;
//	Ivy begin ~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~.~*~ //
	private ArrayList<String> moSuperEgoOutputRules = new ArrayList <String> (); //für die Ausgabe	

	private clsWordPresentationMesh moWordingToContext;
    
	
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
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicIntensityStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F07", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F07", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		
		moForbiddenDrives = new ArrayList<clsSuperEgoConflict>();
		moForbiddenPerceptions = new ArrayList<clsSuperEgoConflictPerception>();
		moForbiddenEmotions = new ArrayList<eEmotionType>();
		
		applyProperties(poPrefix, poProp); 
		
		threshold_psychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_THESHOLD).getParameterInt();
		msPriorityPsychicEnergy = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PSYCHIC_ENERGY_PRIORITY).getParameterInt();
		moSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_STRENGTH).getParameterDouble();
	
		oRegeln = new ArrayList<clsReadSuperEgoRules> (); //Ivy
		readTheRuleFileAndStoreIt (poPersonalityParameterContainer); //Ivy
	}
	
	/**
     * DOCUMENT - insert description
     *
     * @author Jordakieva
     * @since 11.12.2013 13:36:32
     *
     */
    private void readTheRuleFileAndStoreIt (clsPersonalityParameterContainer poPersonalityParameterContainer) {
        
        BufferedReader oReadIn = null;
        int nFileRow = 1; //marks the aktually row, so when an exception happens, we are showing the row where it happened
        String oFileName = ""; //wird Pfad + Dateinamen des SuperEgoReactiveDrives enthalten
        
        try {
            // the file is stored in \ARSIN_V01\Simulation\config\personality_parameters\decision_unit
            oFileName = clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath() + System.getProperty("file.separator") + poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SUPER_EGO_RULES_FILE).getParameter();
        } catch (Exception e) {
            System.err.println("\nno SuperEgoReactife-filename in the default.properties\n");
        }
                
        if (!oFileName.isEmpty()) {
        
        try {
            oReadIn = new BufferedReader (new FileReader (new File (oFileName))); //öffnet das File mit den Regeln auf
        
            String oFileLine = null; //die Zeile vom File
            
            for (int nListe = 0, nStelle = 0; (oFileLine = oReadIn.readLine()) != null; nFileRow++ ) { //read line by line from the file
                
                if (!oFileLine.startsWith("--") && !oFileLine.isEmpty()) { //sieht nach, ob die Zeile ein Kommentar oder leer ist
                    oFileLine = oFileLine.replace (',','.'); //falls jmd den QoA mit , getrennt hat, würde es eine Exepiton geben
                    
                    nStelle = nListe;
                    String[] oSplitFileLine = oFileLine.split("#"); //Zeilenweise wird der String zuerst nach '#' geteilt, weil mit '#' sind die Verbote definiert und diese behandele ich später
                    String[] oLineConditions = oSplitFileLine[0].split(";"); //Bis zum ersten '#' = [0] sind Bedingungen und wird nach Regelwerken/Syntax bearbeitet
                    
                    String[] oSuperEgo = oLineConditions[0].split(" ");
                    Double nSuperEgo;
                    if (oSuperEgo[0].equalsIgnoreCase("SuperEgoStrength")) {
                        nSuperEgo = Double.valueOf(oSuperEgo[1]);
                    } else nSuperEgo = 0.0;
                    
                    if (moSuperEgoStrength >= nSuperEgo) { //die Regel wird angenommen und abgespeichert nur wenn das moSuperEgoStrength größer ist als das im property-File
                    
                        String oeDrive ="", oeOrgan="", oeEmotion="";
                        boolean schalter = false; //wenn true ist ein eDriveComponent gefunden
                        boolean beEmotion = false; //true, wenn ein eEmotionType gefunden wurde
                        
                        for (int nDriveElements = oLineConditions.length, i = 0; i < nDriveElements; i++) { // für alle Tuppeln EINER TriebeRegel
                            String[] oLineElements = oLineConditions[i].split(" "); //Tuppel Unterteilung in Elemente
                                                                    
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
                                        System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nFileRow);
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
                                        System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nFileRow);
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
                        
                        if (oSplitFileLine.length > 0)  {
                            String oBuffer = "";
                            if (nStelle == nListe)
                                System.err.println("\nVerbote ohne Regeln werden einfach nicht beachtet :P!\n");
                            else {
                                for (int i = 1; i < oSplitFileLine.length; i++) { //i=0 sind die Regeln; ab i = 1 sind die verbotene Triebe/Objekte aufgelistet
                                    String[] oSplit = oSplitFileLine[i].split(" ");
                                    
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
                                
                                // superEgoStrength abspeichern
                                oRegeln.get(nListe).setSuperEgoRuleStrength(nSuperEgo);
                                
                                nListe++;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("\nSchlampigkeitsfehler: kann die ÜberIchRegel-Datei nicht finden/öffnen!\n");
        } catch (NullPointerException e) {
            System.err.println("\nFehler in der ÜberIchRegel-Datei, Zeile: " + nFileRow + "\n");
        } catch (IOException e) {  
            e.printStackTrace(); 
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("\nin der ÜberIchRegel-Datei gibt es einen Fehler in der Rechtschreibung, Zeile: " + nFileRow + "\n");
        } finally {
            if (oReadIn != null)
                try { oReadIn.close(); }
                        catch (IOException e) { System.err.println("Fehler beim schließen der ÜberIchRegel-Datei"); }
        } } else System.err.println("\nThe SuperEgoReactive-RuleFile is empty. Simulation started without any SuperEgoReactive-Rules\n");
        
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
		text += toText.listToTEXT("--------------------------------moSuperEgoRules Activated-----------------------------", moSuperEgoOutputRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoEmotionsRules---------------------------", moSuperEgoEmotionsRules);
//		text += toText.listToTEXT("--------------------------------moSuperEgoPerceptionsRules------------------------", moSuperEgoPerceptionsRules);
//		text += toText.listToTEXT("--------------------------------Test----------------------------------------", Test);
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
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
	    
	    moWordingToContext =  moWordingToContext2;
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
	public void receive_I5_12(ArrayList<clsDriveMesh> poDrives,  ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
	    
	    moWordingToContext = moWordingToContext2;
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
		
		
		double rRequestedPsychicIntensity = threshold_psychicEnergy;
		
		//AMP F07 receives the assigned psychic intensity.
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
		
		// if there is enough psychic energy, then the module check the internalied rules.

		if (rReceivedPsychicEnergy > threshold_psychicEnergy
				/* for test purposes only: */ || true)
			checkInternalizedRules();	// check perceptions and drives, and apply internalized rules
		
		
		// it is assumed that the consumed psyhic intensity equals to the recevied psychic intensity in each period.
		double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
		
	   // F07 informs its intensity values to the psychic intensity storage.
		moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
				
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
		moSuperEgoOutputRules .clear();
				
        int nRules = oRegeln.size(); //number of rules
        
        if (nRules > 0) {
            
            try {
            
                for (int i = 0; i < nRules; i++) { //für alle Regel
                    
                    int drivesInOneRule = oRegeln.get(i).driveSize();
                    int perceptionInOneRule = oRegeln.get(i).perceptionSize();
                    int emotionsInOneRule = oRegeln.get(i).emotionSize();
                    
                    double quotaOfAffect_of_ForbiddenDrives = checkForDrives (drivesInOneRule, i);
                        
                    //wurden alle DM, TPM und Emotions von der aktuellen = i Regel gefunden
                    if (quotaOfAffect_of_ForbiddenDrives >= 0 && checkForPerception (perceptionInOneRule, i) && checkForEmotions (emotionsInOneRule, i)) { 

                        moSuperEgoOutputRules.add("FileRule: " + (i+1) + "\nDrives:");
                        for (int tmp=0; tmp < drivesInOneRule; tmp++) {
                            moSuperEgoOutputRules.add(oRegeln.get(i).getForbiddenDriveRule(tmp).a.toString() + " " +
                                    oRegeln.get(i).getForbiddenDriveRule(tmp).b.toString() + " " +
                                            oRegeln.get(i).getForbiddenDriveRule(tmp).c[0].toString() + " " +
                                            oRegeln.get(i).getForbiddenDriveRule(tmp).c[1].toString());
                                
                        }
                        moSuperEgoOutputRules.add("\nPerception:");
                        for (int tmp=0; tmp < perceptionInOneRule; tmp++) 
                            moSuperEgoOutputRules.add(oRegeln.get(i).getForbiddenPerceptionRule(tmp).toString());
                        moSuperEgoOutputRules.add("\nEmotion:");
                        for (int tmp=0; tmp < emotionsInOneRule; tmp++) 
                            moSuperEgoOutputRules.add(oRegeln.get(i).getForbiddenEmotinRule(tmp).a.toString() + " " +
                                    oRegeln.get(i).getForbiddenEmotinRule(tmp).b[0].toString() + " " +
                                        oRegeln.get(i).getForbiddenEmotinRule(tmp).b[1].toString());
                        moSuperEgoOutputRules.add("----------------------------------------------");

                        
                        double conflictTension;
                        
                        for (int fd = 0; fd < oRegeln.get(i).FDriveSize(); fd++) {
                            conflictTension = quotaOfAffect_of_ForbiddenDrives + oRegeln.get(i).getSuperEgoRuleStrength();
                            
                            // hier ist noch ein Fehler drinnen: am 18.03.2014 war
                            // der Datentyp von moForbiddenDrives: ArrayList<clsSuperEgoConflict>
                            // aber
                            // der Datentyp von oRegeln.get(i).getForbiddenDrive(fd): clsPair <eDriveComponent, eOrgan>
                            // Das heißt, die folgende if-Bedingung kann niemals false werden - der Compiler wurde da ausgetrickst.
                            // Das Gleiche gilt auch für die übernächste if-Bedingung.
                            if (!moForbiddenDrives.contains(oRegeln.get(i).getForbiddenDrive(fd)))
                                moForbiddenDrives.add(new clsSuperEgoConflict(oRegeln.get(i).getForbiddenDrive(fd), conflictTension));
                          
                            // if drive is already in list of forbidden drives: change conflict tension
                            else
                                moForbiddenDrives.get(moForbiddenDrives.lastIndexOf(oRegeln.get(i).getForbiddenDrive(fd))).setConflictTension(conflictTension);
                        }
                        
                        for (int fp = 0; fp < oRegeln.get(i).FObjectSize(); fp++) {
                            // bei der folgenden Zeile könnte man noch schauen, ob es bei perceptions nicht soetwas gibt wie "Stärke einer Wahrnehmung" - analog dem quota of affect eines Triebes
                            conflictTension = oRegeln.get(i).getSuperEgoRuleStrength();
                            
                            if (!moForbiddenPerceptions.contains(oRegeln.get(i).getForbiddenObject(fp))) 
                                moForbiddenPerceptions.add(new clsSuperEgoConflictPerception(oRegeln.get(i).getForbiddenObject(fp), conflictTension));
                            
                            // if perception is already in list of forbidden perceptions: change conflict tension
                            else
                                moForbiddenPerceptions.get(moForbiddenPerceptions.lastIndexOf(oRegeln.get(i).getForbiddenDrive(fp))).setConflictTension(conflictTension);
                        }
                        
                        for (int fe = 0; fe < oRegeln.get(i).FEmotionSize(); fe++) {
                            if (!moForbiddenEmotions.contains(oRegeln.get(i).getForbiddenEmotion(fe))) 
                                moForbiddenEmotions.add(oRegeln.get(i).getForbiddenEmotion(fe));
                        }
                        
                    } 
                } // Ende vom for -> "für jede Zeile" = für jede Regel
            } catch (IndexOutOfBoundsException e) {
                System.err.println("\nIndex nicht vorhanden\n");
            }
        }
	}

	/**
     * DOCUMENT - insert description
     *
     * @author Jordakieva
     * @since 11.12.2013 16:14:28
     *
     * @param emotionSize
     * @param i
     * @return
     */
    private boolean checkForEmotions(int emotionSize, int position) {

        boolean beGefunden;
        int e;

        for (e = 0, beGefunden = true; e < emotionSize && beGefunden; e++) {
            
            beGefunden = false;
            for(clsEmotion oOneEmotion : moEmotions_Input) {
                
                double rEmotionItensity = oOneEmotion.getEmotionIntensity();
                
                if (oOneEmotion.getContent().equals(oRegeln.get(position).getEmotionRule(e).a) &&
                        oRegeln.get(position).getEmotionRule(e).b[0] <= rEmotionItensity && 
                                oRegeln.get(position).getEmotionRule(e).b[1] >= rEmotionItensity) {
                    beGefunden = true;
                    break;
                }
            }
        }
        
        
        if (e == emotionSize && beGefunden) return true;
        else return false;
    }

    /**
     * DOCUMENT - insert description
     *
     * @author Jordakieva
     * @since 11.12.2013 16:01:13
     *
     * @param perceptionSize
     * @param i
     * @return
     */
    private boolean checkForPerception(int perceptionSize, int position) {

        boolean bPerception;
        int p;
        
        ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_OUT).getInternalAssociatedContent();
    
        for (p = 0, bPerception = true; p < perceptionSize && bPerception; p++) {
            
            bPerception = false;
            for (clsAssociation oAssociation : oInternalAssociations) {
                if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh) {
                    if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(oRegeln.get(position).getPerceptionRule(p).a) &&
                            ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals(oRegeln.get(position).getPerceptionRule(p).b) ) {
                        //EINE TPM GEFUNDEN (von vlt vielen) JUHUU! und da gefunden ab zur nächsten in der Schleife
                        bPerception = true;
                        break;
                    }
                }
            }   
        }
        
        if ((bPerception && p == perceptionSize)) { //true wenn alle TPMs gefunden wurden = abspeichern
            return true;
        } else return false;
    }

    /* (non-Javadoc)
     * 
     * checks whether all conflicting drive meshes of one super-ego rule are contained in the array list of incoming drive meshes
     * 
     * returns the sum of quotas of affect of all forbidden drives (if all forbidden drives are in the array list of incoming drives)
     * returns -1 if not all forbidden drives are in the array list of incoming drives
     *
     * @author Jordakieva
     * @since 11.12.2013 15:17:59
     *
     * @param driveSize
     * @return
     */
    private double checkForDrives(int driveSize, int position) {

        boolean match;
        int d;
        double sum_of_QuoataOfAffect_of_MatchingDrives = 0;
        
        for (d = 0, match = true; d < driveSize && match; d++) { //für alle DM in einer Regel
            
            match = false;
            for (clsDriveMesh oDrive : moDrives) { //moDrives ist received_I5_12 moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives);
                
                if (oDrive.getDriveComponent().equals(oRegeln.get(position).getDriveRule(d).a) &&
                        oDrive.getActualDriveSourceAsENUM().equals(oRegeln.get(position).getDriveRule(d).b) &&
                        oDrive.getQuotaOfAffect() >= oRegeln.get(position).getDriveRule(d).c[0] && 
                        oDrive.getQuotaOfAffect() <= oRegeln.get(position).getDriveRule(d).c[1]) 
                {
                    match = true;
                    sum_of_QuoataOfAffect_of_MatchingDrives += oDrive.getQuotaOfAffect();
                    break;
                } 
            }

        }
        
        if (d == driveSize && match)        
            return sum_of_QuoataOfAffect_of_MatchingDrives;

        else return -1.0; // no match found or not all drive mashes of the super-ego-rule match
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
		send_I5_11(moForbiddenPerceptions, moPerceptionalMesh_OUT, moForbiddenEmotions, moEmotions_Input, moWordingToContext); 
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
	public void send_I5_13(ArrayList<clsSuperEgoConflict> poForbiddenDrives, ArrayList<clsDriveMesh> poData,ArrayList<eEmotionType> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions) {
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
	public void send_I5_11(ArrayList<clsSuperEgoConflictPerception> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh, ArrayList<eEmotionType> poForbiddenEmotions, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poPerceptionalMesh, poForbiddenEmotions, poEmotions, moWordingToContext2);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poPerceptionalMesh, moWordingToContext2);
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
