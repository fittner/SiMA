/**
 * CHANGELOG
 *
 * 12.12.2013 Jordakieva - File created
 *
 */
package primaryprocess.functionality.superegofunctionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;

import org.slf4j.Logger;

import utils.clsGetARSPath;
import base.datatypes.clsAssociation;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.eOrgan;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (Jordakieva) - die Klasse speichert die Über-Ich-Regeln (Perceptions, Drives, Emotions), welche extern aus einer Datei ausgelesen werden
 * 
 * @author Jordakieva
 * 04.10.2013, 12:21:01
 * 
 */
public class clsSuperEgoRulesCheck {
    //logger
    private static final Logger log = clsLogger.getLog(clsSuperEgoRulesCheck.class.getName());
    
    // 
    // right side of a rule (perceptions, drives, emotions)
    private ArrayList <clsPair <eContentType, String>> oForbiddenPerceptionRule = new ArrayList <clsPair <eContentType, String>> ();
    private ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> oForbiddenDriveRule = new ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> ();
    private ArrayList <clsPair <eEmotionType, Double[]>> oForbiddenEmotionRule = new ArrayList <clsPair <eEmotionType, Double[]>> ();
    
    // right side of a rule (forbidden perceptions, drives, emotions)
    private ArrayList <clsPair <eContentType, String>> oForbiddenObject = new ArrayList <clsPair <eContentType, String>> (); //zB: eContentType.ENTITY, "CAKE"
    private ArrayList <clsPair <eDriveComponent, eOrgan>> oForbiddenDrive = new ArrayList <clsPair <eDriveComponent, eOrgan>> (); //zB: eDriveComponent.AGGRESSIVE, eOrgan STOMACH
    private ArrayList <eEmotionType> oForbiddenEmotion = new ArrayList <eEmotionType> ();
    
    // for internal rool checking
    private ArrayList<clsSuperEgoRulesCheck> moRules;  //   //die Regeln die das Über-Ich verbietet Ivy
    private ArrayList<clsSuperEgoConflictDrive> moForbiddenDrives;
    private ArrayList<clsSuperEgoConflictPerception> moForbiddenPerceptions;
    private ArrayList<clsSuperEgoConflictEmotion> moForbiddenEmotions;
    private ArrayList<String> moSuperEgoOutputRules = new ArrayList <String> (); //für die Ausgabe  

    
    // input parameters for conflicts with drives, emotions and perceptions
    private clsThingPresentationMesh moPerceptionalMeshInput;
    private ArrayList<clsEmotion> moEmotionsInput;
    private ArrayList<clsDriveMesh> moDrivesInput;
    
    private double superEgoRuleStrength; // indicates how important a super-ego rule is
    
    
    public clsSuperEgoRulesCheck (eContentType ect, String str) {
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
    public clsSuperEgoRulesCheck (eDriveComponent edc, eOrgan eorg, Double[] fl) {
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
    public clsSuperEgoRulesCheck (eEmotionType em, Double [] fl) {
        addFRule (em, fl);   
    }
    
    public clsSuperEgoRulesCheck() {
        moForbiddenDrives = new ArrayList<clsSuperEgoConflictDrive>();
        moForbiddenPerceptions = new ArrayList<clsSuperEgoConflictPerception>();
        moForbiddenEmotions = new ArrayList<clsSuperEgoConflictEmotion>();
    }
    
    
    public static List<clsSuperEgoRulesCheck> fromFile(double prSuperEgoStrength, String poFileName) throws IOException {
        BufferedReader oReadIn = null;
        String oFileName = "";
        ArrayList<clsSuperEgoRulesCheck> oRegeln = new ArrayList<>();
        
        if (!poFileName.isEmpty()) {
            oFileName = clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath() + System.getProperty("file.separator") + poFileName;
            
            try {
                oReadIn = new BufferedReader (new FileReader (new File (oFileName)));
            
                oRegeln = (ArrayList<clsSuperEgoRulesCheck>) clsSuperEgoRulesCheck.fromBufferedReader(prSuperEgoStrength, oReadIn);
            } catch (FileNotFoundException e) {
                log.error("\nSchlampigkeitsfehler: kann die ÜberIchRegel-Datei nicht finden/öffnen!\n");
            } catch (NullPointerException e) {
                log.error("\nFehler in der ÜberIchRegel-Datei\n");
            } catch (IllegalArgumentException e) {
                log.error("\nin der ÜberIchRegel-Datei gibt es einen Fehler in der Rechtschreibung\n");
            } finally {
                if (oReadIn != null)
                    try { oReadIn.close(); }
                            catch (IOException e) { System.err.println("Fehler beim schließen der ÜberIchRegel-Datei"); }
            }
        } else log.error("\nThe SuperEgoReactive-RuleFile is empty. Simulation started without any SuperEgoReactive-Rules\n");
        
        return oRegeln;
    }
    
    public static List<clsSuperEgoRulesCheck> fromBufferedReader(double prSuperEgoStrength, BufferedReader oInput) throws IOException {
        List<clsSuperEgoRulesCheck> oRegeln = new ArrayList<clsSuperEgoRulesCheck> ();
        int nFileRow = 1; //marks the aktually row, so when an exception happens, we are showing the row where it happened
        
        String oFileLine = null; //die Zeile vom File
        
        for (int nListe = 0, nStelle = 0; (oFileLine = oInput.readLine()) != null; nFileRow++ ) { //read line by line from the file
            
            if (!oFileLine.startsWith("--") && !oFileLine.isEmpty()) { //sieht nach, ob die Zeile ein Kommentar oder leer ist
                oFileLine = oFileLine.replace (',','.'); //falls jmd den QoA mit , getrennt hat, würde es eine Exepiton geben
                
                nStelle = nListe;
                String[] oSplitFileLine = oFileLine.split("#"); //Zeilenweise wird der String zuerst nach '#' geteilt, weil mit '#' sind die Verbote definiert und diese behandele ich später
                String[] oLineConditions = oSplitFileLine[0].split(";"); //Bis zum ersten '#' = [0] sind Bedingungen und wird nach Regelwerken/Syntax bearbeitet
                
                String[] oSuperEgo = oLineConditions[0].split(" ");
                double nSuperEgo;
                if (oSuperEgo[0].equalsIgnoreCase("SuperEgoStrength")) {
                    nSuperEgo = Double.valueOf(oSuperEgo[1]);
                } else nSuperEgo = 0.0;
                
                if (prSuperEgoStrength >= nSuperEgo) { //die Regel wird angenommen und abgespeichert nur wenn das moSuperEgoStrength größer ist als das im property-File
                
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
                                        oRegeln.add(nListe, new clsSuperEgoRulesCheck (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
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
                                        oRegeln.add(new clsSuperEgoRulesCheck (eEmotionType.valueOf(oeEmotion), rTmpQoA) );
                                    else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), rTmpQoA);
                                    
                                    nStelle++;
                                    beEmotion = false;
                                } else if (oeDrive.isEmpty() || oeOrgan.isEmpty())
                                    System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nFileRow);
                                else {

                                    if (nStelle == nListe) 
                                        oRegeln.add(new clsSuperEgoRulesCheck (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA) );
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
                                        oRegeln.add(new clsSuperEgoRulesCheck (eEmotionType.valueOf(oeEmotion), rTmpQoA) );
                                    else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), rTmpQoA);
                                    
                                    nStelle++;
                                    beEmotion = false;
                                }
                                else if (oeDrive.isEmpty() || oeOrgan.isEmpty())
                                    System.err.println("ein unzusammenhängendes QoA wurde eingetragen, Zeile: " + nFileRow);
                                else {
                                                                        
                                    if (nStelle == nListe) 
                                        oRegeln.add(new clsSuperEgoRulesCheck (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA) );
                                    else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), rTmpQoA);
                                 
                                    nStelle++;
                                    oeDrive = "";
                                    oeOrgan = "";
                                    schalter = false;
                                }
                            } else if (!oeDrive.isEmpty() && schalter) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eDriveComponent und eOrgan 
                                if (nStelle == nListe) 
                                    oRegeln.add(nListe, new clsSuperEgoRulesCheck (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
                                else oRegeln.get(nListe).addFRule(eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null);
                                
                                nStelle++;
                                oeDrive = "";
                                oeOrgan = "";
                                schalter = false;                               
                            } else if (beEmotion) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eEmotionType
                                if (nStelle == nListe) 
                                    oRegeln.add(new clsSuperEgoRulesCheck (eEmotionType.valueOf(oeEmotion), null) );
                                else oRegeln.get(nListe).addFRule(eEmotionType.valueOf(oeEmotion), null);
                                
                                nStelle++;
                                beEmotion = false;
                            }
                            
                            if (oLineElements[0].equalsIgnoreCase("eContentType")) {
                                if (nStelle == nListe)
                                    oRegeln.add(nListe, new clsSuperEgoRulesCheck (eContentType.valueOf(oLineElements[1]), oLineElements[2]));
                                else oRegeln.get(nListe).addFRule(eContentType.valueOf(oLineElements[1]), oLineElements[2]);
                                nStelle++;
                            }                           
                            break;
                        }
                    }
                    
                    if (!oeDrive.isEmpty() && schalter) { //wenn kein QoA angegeben war, jedoch ein nicht abgespeichertes eDriveComponent und eOrgan 
                        if (nStelle == nListe) 
                            oRegeln.add(nListe, new clsSuperEgoRulesCheck (eDriveComponent.valueOf(oeDrive), eOrgan.valueOf(oeOrgan), null) );
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
        
        return oRegeln;
    }
            
    /**
     * DOCUMENT (Jordakieva) - erweitert die Regel der Objekte um einen Element
     *
     * @since 18.09.2013 18:06:39
     *
     * @param ect - eContentType = Objekttyp
     * @param str - String = Objektart
     */
    public void addFRule (eContentType ect, String str) {
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
    public void addFRule (eDriveComponent edc, eOrgan eorg, Double[] fl) {
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
    public void addFRule (eEmotionType em, Double[] fl) {
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
    public void addFObject (eContentType type, String str) {
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
    public void addFObject (eDriveComponent edc, eOrgan eor) {
        oForbiddenDrive.add(new clsPair <eDriveComponent, eOrgan> (edc, eor));
    }
    
    
    /**
     * DOCUMENT (Jordakieva) - speichert die zu verbietende Emotion
     *
     * @since 04.10.2013 15:50:41
     *
     * @param em - eEmoitonType
     */
    public void addFObject (eEmotionType em) {
        oForbiddenEmotion.add(em);
    }
    
    public ArrayList<clsSuperEgoConflictEmotion> getForbiddenEmotions() { 
        return moForbiddenEmotions;
    }

    public ArrayList<clsSuperEgoConflictDrive> getForbiddenDrives() { 
        return moForbiddenDrives;
    }
    
    public ArrayList<clsSuperEgoConflictPerception> getForbiddenPerception() {
        return moForbiddenPerceptions;
    }
    
    public ArrayList<String> getSuperEgoOutputRules() {
        return moSuperEgoOutputRules;
    }
    
    public void setSuperEgoRuleStrength(double newSuperEgoRuleStrength) {
        superEgoRuleStrength = newSuperEgoRuleStrength;
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
    
    
    
    
    public clsPair <eContentType, String> getForbiddenPerceptionRule (int i) {
        if (oForbiddenPerceptionRule.size() < i) return null;
        return oForbiddenPerceptionRule.get(i);
    }
    
    public clsTriple <eDriveComponent, eOrgan, Double[]> getForbiddenDriveRule (int i) {
        if (oForbiddenDriveRule.size() < i) return null;
        return oForbiddenDriveRule.get(i);
    }
    public clsPair <eEmotionType, Double[]> getForbiddenEmotinRule (int i) {
        if (oForbiddenEmotionRule.size() < i) return null;
        return oForbiddenEmotionRule.get(i);
    }
    
    
    
    public double getSuperEgoRuleStrength() {
        return superEgoRuleStrength;
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
    
    
    public void setCheckingSuperEgoRuleParameters(clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsEmotion> poEmotions_Input, ArrayList<clsDriveMesh> poDrivesInput) {
        
        moDrivesInput = poDrivesInput;
        moEmotionsInput = poEmotions_Input;
        moPerceptionalMeshInput = poPerceptionalMesh;
        
    }
    
    
    public ArrayList<clsSuperEgoRulesCheck> readSuperEgoRules(double moSuperEgoStrength, String oFileName )  {
       
        try {
            return (ArrayList<clsSuperEgoRulesCheck>) fromFile(moSuperEgoStrength, oFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public  double  checkInternalizedRules(double prReceivedPsychicEnergy, double moSuperEgoStrength, String oFileName ) throws Exception {
        // For now, its assumed that the consumed psychic intensity equals to the recevied psychic intensity in each period.
           double rConsumedPsychicIntensity = prReceivedPsychicEnergy;
           
           // If no Super-Ego rule fires, the lists with forbidden drives, forbidden perceptions, and forbidden emotions must be empty
           moForbiddenDrives     .clear();
           moForbiddenPerceptions.clear();
           moForbiddenEmotions   .clear();
           moSuperEgoOutputRules .clear();
           
           // reading the rools from File
           moRules =   readSuperEgoRules(moSuperEgoStrength, oFileName );

           
           int nRules = moRules.size(); //number of rules
           
           if (nRules > 0) {
               
               try {
               
                   for (int i = 0; i < nRules; i++) { //für alle Regel
                       
                       int drivesInOneRule = moRules.get(i).driveSize();
                       int perceptionInOneRule = moRules.get(i).perceptionSize();
                       int emotionsInOneRule = moRules.get(i).emotionSize();
                       
                       double quotaOfAffect_of_ForbiddenDrives = checkForDrives (drivesInOneRule, i);
                       double rIntensityOfConflictingEmotions = checkForEmotions (emotionsInOneRule, i);
                           
                       //wurden alle DM, TPM und Emotions von der aktuellen = i Regel gefunden
                       if (quotaOfAffect_of_ForbiddenDrives >= 0 && checkForPerception (perceptionInOneRule, i) && rIntensityOfConflictingEmotions >= 0) { 

                           moSuperEgoOutputRules.add("FileRule: " + (i+1) + "\nDrives:");
                           for (int tmp=0; tmp < drivesInOneRule; tmp++) {
                               moSuperEgoOutputRules.add(moRules.get(i).getForbiddenDriveRule(tmp).a.toString() + " " +
                                       moRules.get(i).getForbiddenDriveRule(tmp).b.toString() + " " +
                                       moRules.get(i).getForbiddenDriveRule(tmp).c[0].toString() + " " +
                                       moRules.get(i).getForbiddenDriveRule(tmp).c[1].toString());
                                   
                           }
                           moSuperEgoOutputRules.add("\nPerception:");
                           for (int tmp=0; tmp < perceptionInOneRule; tmp++) 
                               moSuperEgoOutputRules.add(moRules.get(i).getForbiddenPerceptionRule(tmp).toString());
                           moSuperEgoOutputRules.add("\nEmotion:");
                           for (int tmp=0; tmp < emotionsInOneRule; tmp++) 
                               moSuperEgoOutputRules.add(moRules.get(i).getForbiddenEmotinRule(tmp).a.toString() + " " +
                                       moRules.get(i).getForbiddenEmotinRule(tmp).b[0].toString() + " " +
                                       moRules.get(i).getForbiddenEmotinRule(tmp).b[1].toString());
                           moSuperEgoOutputRules.add("----------------------------------------------");

                           
                           double rConflictTension;
                           
                           for (int fd = 0; fd < moRules.get(i).FDriveSize(); fd++) {
                               rConflictTension = quotaOfAffect_of_ForbiddenDrives + moRules.get(i).getSuperEgoRuleStrength();
                               
                               // hier ist noch ein Fehler drinnen: am 18.03.2014 war
                               // der Datentyp von moForbiddenDrives: ArrayList<clsSuperEgoConflict>
                               // aber
                               // der Datentyp von oRegeln.get(i).getForbiddenDrive(fd): clsPair <eDriveComponent, eOrgan>
                               // Das heißt, die folgende if-Bedingung kann niemals false werden - der Compiler wurde da ausgetrickst.
                               // Das Gleiche gilt auch für die übernächste if-Bedingung.
                               if (!moForbiddenDrives.contains(moRules.get(i).getForbiddenDrive(fd)))
                                   moForbiddenDrives.add(new clsSuperEgoConflictDrive(moRules.get(i).getForbiddenDrive(fd), rConflictTension));
                             
                               // if drive is already in list of forbidden drives: change conflict tension
                               else
                                   moForbiddenDrives.get(moForbiddenDrives.lastIndexOf(moRules.get(i).getForbiddenDrive(fd))).setConflictTension(rConflictTension);
                           }
                           
                           for (int fp = 0; fp < moRules.get(i).FObjectSize(); fp++) {
                               // bei der folgenden Zeile könnte man noch schauen, ob es bei perceptions nicht soetwas gibt wie "Stärke einer Wahrnehmung" - analog dem quota of affect eines Triebes
                               rConflictTension = moRules.get(i).getSuperEgoRuleStrength();
                               
                               if (!moForbiddenPerceptions.contains(moRules.get(i).getForbiddenObject(fp))) 
                                   moForbiddenPerceptions.add(new clsSuperEgoConflictPerception(moRules.get(i).getForbiddenObject(fp), rConflictTension));
                               
                               // if perception is already in list of forbidden perceptions: change conflict tension
                               else
                                   moForbiddenPerceptions.get(moForbiddenPerceptions.lastIndexOf(moRules.get(i).getForbiddenDrive(fp))).setConflictTension(rConflictTension);
                           }
                           
                           for (int fe = 0; fe < moRules.get(i).FEmotionSize(); fe++) {
                               //Kollmann: according to KD, the intensity of the confclicting emotion should have less impact than the super ego rule strength
                               //          (0.5 is a completely arbitrary value)
                               //          also: according to KD, the difference between allowed intensity and actual intensity should be used.
                               rConflictTension = (moRules.get(i).getSuperEgoRuleStrength() * Math.min(Math.pow(prReceivedPsychicEnergy, 0.3), 1)) + (rIntensityOfConflictingEmotions * /*arbitrary->*/0.5);
                               
                               if (!moForbiddenEmotions.contains(moRules.get(i).getForbiddenEmotion(fe))) 
                                   moForbiddenEmotions.add(new clsSuperEgoConflictEmotion(moRules.get(i).getForbiddenEmotion(fe), rConflictTension));
                           }
                           
                       } 
                   }
               } catch (IndexOutOfBoundsException e) {
                   System.err.println("\nIndex nicht vorhanden\n");
               }
           }
           
           return rConsumedPsychicIntensity;
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
    private double checkForEmotions(int emotionSize, int position) {
        double rIntensitySum = 0;
        double rEmotionIntensity = 0;
        double rLowerBound = 0;
        double rUpperBound = 0;
        boolean bFound = false;
        int e;

        for (e = 0; e < emotionSize; e++) {
            for(clsEmotion oOneEmotion : moEmotionsInput.get(0).generateExtendedEmotions()) {
                rEmotionIntensity = oOneEmotion.getEmotionIntensity();
                rLowerBound = moRules.get(position).getEmotionRule(e).b[0];
                rUpperBound = moRules.get(position).getEmotionRule(e).b[1];
                
                if (oOneEmotion.getContent().equals(moRules.get(position).getEmotionRule(e).a) &&
                        rLowerBound <= rEmotionIntensity && 
                        rUpperBound >= rEmotionIntensity) {
                    bFound = true;
                    //Kollmann: according to KD we should consider the difference between the allowed range and the actual intensity.
                    //          But currently the rule supplies the conflicting range - what if the rule says: must be nothing but moderately
                    //          angry (for example), I decided to use the minimal difference to the bound, e.g. ANGER between 0.5 and 1.0 with
                    //          actual intensity 0.6 --> rIntensitySum = 0.1 
                    rIntensitySum += Math.min(rUpperBound - rEmotionIntensity, rEmotionIntensity - rLowerBound);
                    break;
                }
            }
        }
        
        if(!bFound) {
            rIntensitySum = 0;
        }
        
        return rIntensitySum;
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
        
        ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMeshInput).getInternalAssociatedContent();
    
        for (p = 0, bPerception = true; p < perceptionSize && bPerception; p++) {
            
            bPerception = false;
            for (clsAssociation oAssociation : oInternalAssociations) {
                if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh) {
                    if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(moRules.get(position).getPerceptionRule(p).a) &&
                            ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals(moRules.get(position).getPerceptionRule(p).b) ) {
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
            for (clsDriveMesh oDrive : moDrivesInput) { //moDrives ist received_I5_12 moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives);
                
                if (oDrive.getDriveComponent().equals(moRules.get(position).getDriveRule(d).a) &&
                        oDrive.getActualDriveSourceAsENUM().equals(moRules.get(position).getDriveRule(d).b) &&
                        oDrive.getQuotaOfAffect() >= moRules.get(position).getDriveRule(d).c[0] && 
                        oDrive.getQuotaOfAffect() <= moRules.get(position).getDriveRule(d).c[1]) 
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

    

}
