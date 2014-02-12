/**
 * CHANGELOG
 *
 * 12.12.2013 Jordakieva - File created
 *
 */
package primaryprocess.functionality.superegofunctionality;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

/**
 * DOCUMENT (Jordakieva) - die Klasse speichert die Über-Ich-Regeln (Perceptions, Drives, Emotions), welche extern aus einer Datei ausgelesen werden
 * 
 * @author Jordakieva
 * 04.10.2013, 12:21:01
 * 
 */
public class clsReadSuperEgoRules {
    
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
