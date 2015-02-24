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

import org.slf4j.Logger;

import utils.clsGetARSPath;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
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
public class clsReadSuperEgoRules {
    //logger
    private static final Logger log = clsLogger.getLog(clsReadSuperEgoRules.class.getName());
    
    // right side of a rule (perceptions, drives, emotions)
    private ArrayList <clsPair <eContentType, String>> oForbiddenPerceptionRule = new ArrayList <clsPair <eContentType, String>> ();
    private ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> oForbiddenDriveRule = new ArrayList <clsTriple <eDriveComponent, eOrgan, Double[]>> ();
    private ArrayList <clsPair <eEmotionType, Double[]>> oForbiddenEmotionRule = new ArrayList <clsPair <eEmotionType, Double[]>> ();
    
    // right side of a rule (forbidden perceptions, drives, emotions)
    private ArrayList <clsPair <eContentType, String>> oForbiddenObject = new ArrayList <clsPair <eContentType, String>> (); //zB: eContentType.ENTITY, "CAKE"
    private ArrayList <clsPair <eDriveComponent, eOrgan>> oForbiddenDrive = new ArrayList <clsPair <eDriveComponent, eOrgan>> (); //zB: eDriveComponent.AGGRESSIVE, eOrgan STOMACH
    private ArrayList <eEmotionType> oForbiddenEmotion = new ArrayList <eEmotionType> ();
    
    private double superEgoRuleStrength; // indicates how important a super-ego rule is
    
    
    public clsReadSuperEgoRules (eContentType ect, String str) {
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
    public clsReadSuperEgoRules (eDriveComponent edc, eOrgan eorg, Double[] fl) {
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
    public clsReadSuperEgoRules (eEmotionType em, Double [] fl) {
        addFRule (em, fl);   
    }
    
    public static List<clsReadSuperEgoRules> fromFile(double prSuperEgoStrength, String poFileName) throws IOException {
        BufferedReader oReadIn = null;
        String oFileName = "";
        ArrayList<clsReadSuperEgoRules> oRegeln = new ArrayList<>();
        
        if (!poFileName.isEmpty()) {
            oFileName = clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath() + System.getProperty("file.separator") + poFileName;
            
            try {
                oReadIn = new BufferedReader (new FileReader (new File (oFileName)));
            
                oRegeln = (ArrayList<clsReadSuperEgoRules>) clsReadSuperEgoRules.fromBufferedReader(prSuperEgoStrength, oReadIn);
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
    
    public static List<clsReadSuperEgoRules> fromBufferedReader(double prSuperEgoStrength, BufferedReader oInput) throws IOException {
        List<clsReadSuperEgoRules> oRegeln = new ArrayList<clsReadSuperEgoRules> ();
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
}
