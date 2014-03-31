/**
 * @author MW
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   Loch Ness
 * $Author::                   deutsch Deutsch
 * $Date::                     $: Date of last commit
 */
package complexbody.io.speech;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.lang.Double;

import complexbody.io.speech.itfSpeakable;
import complexbody.io.speech.clsAbstractSpeech.clsGrainLevel.clsGrain;

import properties.clsProperties;


/**
 * 
 * @author MW
 * 
 */
/**
 * DOCUMENT (Owner) - insert description 
 * 
 * @author Owner
 * 26.02.2013, 14:56:41
 * 
 */
public class clsAbstractSpeech implements Cloneable{
	public static final String P_ABSTRACTSPEECH = "abstractspeech";
	public static final String P_THINKINGSPEED = "thinkingspeed";
	public static final String P_TALKINGSPEED = "talkingspeed";
	public static final String P_MAXLENGTH = "maxlength";
	public static final String P_MAXLEVEL= "maxlevel";
	
	public class clsGrainLevel {
		public class clsGrain<T extends itfSpeakable> {
			private double mdLength;
			private double mdCommitLength;
			private T moData;
			
			@SuppressWarnings("unchecked")
			private clsGrain(clsGrain<T> oGrain) {
				mdLength = oGrain.mdLength;
				if (oGrain.mdCommitLength == oGrain.mdLength)
				mdCommitLength = oGrain.mdCommitLength;
				moData = (T) oGrain.moData.speak();
			}

			private clsGrain() {
			}
		};
		
		private LinkedList<clsGrain<itfSpeakable>> moGrains;
		private double mdMaxLength;
		private double mdProcessedLengthMind;
		private double mdProcessedLengthBody;
		
		private clsGrainLevel(double dMaxLength) {
			moGrains = new LinkedList<clsGrain<itfSpeakable>>();
			mdProcessedLengthMind = 0.0;
			mdProcessedLengthBody = 0.0;
			mdMaxLength = dMaxLength;
		}

		private clsGrainLevel(clsGrainLevel oGrainLevel, double dCloneLength) {
			mdProcessedLengthMind = oGrainLevel.mdProcessedLengthMind;
			mdProcessedLengthBody = oGrainLevel.mdProcessedLengthBody;
			mdMaxLength = oGrainLevel.mdMaxLength;
			
			if (moProceedings.compareTo("S2") == 0 && dCloneLength > mdProcessedLengthMind)
				dCloneLength = mdProcessedLengthMind;
			else if (moProceedings.compareTo("S3") == 0 && dCloneLength > mdProcessedLengthBody)
				dCloneLength = mdProcessedLengthBody;
			else if (moProceedings.compareTo("S4") == 0)
				dCloneLength = mdProcessedLengthBody;
			
			moGrains = cloneGrains(oGrainLevel, dCloneLength);
			
			if (moProceedings.compareTo("S2") == 0) {
				mdProcessedLengthBody = oGrainLevel.mdProcessedLengthBody;
				mdProcessedLengthMind = 0;
				oGrainLevel.mdProcessedLengthMind -= oGrainLevel.mdProcessedLengthBody;
				oGrainLevel.mdProcessedLengthBody = 0;
			} else if (moProceedings.compareTo("S3") == 0){
				mdProcessedLengthMind = 0;
				// mdProcessedLengthBody = 0; // we do not set this to zero. mdProcessedLengthbody is the same for sender and receiver
			} else if (moProceedings.compareTo("S4") == 0){
				mdProcessedLengthMind = 0;
				mdProcessedLengthBody = oGrainLevel.mdProcessedLengthBody;
			}
		}

		/**
		 * DOCUMENT (MW) - insert description
		 *
		 * @since 06.03.2013 13:35:18
		 *
		 * @param oData
		 * @param dLength
		 * @param dCommitLength must be > 0 or it will not be processed by DU
		 */
		private void addGrain(itfSpeakable oData, Double dLength, Double dCommitLength) {
			clsGrain<itfSpeakable> oGrain = new clsGrain<itfSpeakable>();
			oGrain.mdLength = dLength;
			oGrain.mdCommitLength = dCommitLength;
			oGrain.moData = oData;
			
			if (mdProcessedLengthMind + dLength > mdMaxLength) {
				throw new IndexOutOfBoundsException();
			}
			moGrains.addLast(oGrain);
			mdProcessedLengthMind += dLength;
		}
		 
		private LinkedList<clsGrain<itfSpeakable>> cloneGrains(clsGrainLevel oGrainLevel, double dCloneLength) {
			LinkedList<clsGrain<itfSpeakable>> oGrains = new LinkedList<clsGrain<itfSpeakable>>();
			Double dCountLength = 0.0;
			clsGrain<itfSpeakable> oGrainClone;
			
			for (Iterator<clsGrain<itfSpeakable>> oIter = oGrainLevel.moGrains.iterator(); oIter.hasNext(); ) {
				clsGrain<itfSpeakable> oGrain = oIter.next();
				if (dCloneLength <= dCountLength) 
					break;
				if (dCloneLength < dCountLength + oGrain.mdLength) {
					oGrainClone = new clsGrain<itfSpeakable>(oGrain);
					oGrainClone.mdCommitLength = 0.0;
					oGrainClone.mdLength = dCloneLength - dCountLength;
					oGrain.mdLength -= oGrainClone.mdLength;
					oGrain.mdCommitLength -= oGrainClone.mdLength;
				} else {
					oGrainClone = new clsGrain<itfSpeakable>(oGrain);
					if (moProceedings.equals("S2"))
						oIter.remove();
				}
				oGrains.addLast(oGrainClone);
				dCountLength += oGrainClone.mdLength;
				oGrainLevel.mdProcessedLengthBody = dCountLength;
			}
			return oGrains;
		}
	}; 
	
	class clsGrainLevelKey{
		String moLabel;
		private Double mdHierarchy;
		
		private clsGrainLevelKey(String oLabel, Double dHierarchy) {
			moLabel = oLabel;
			mdHierarchy = dHierarchy;
		}

		private clsGrainLevelKey(clsGrainLevelKey oKey) {
			moLabel = oKey.moLabel;
			mdHierarchy = oKey.mdHierarchy;
		}

		@Override
		public int hashCode(){
		  	return(moLabel + mdHierarchy.toString()).hashCode();
	    }
		
		@Override
		public boolean equals(Object oObject){
			return moLabel.equals(((clsGrainLevelKey) oObject).moLabel) && mdHierarchy.equals(((clsGrainLevelKey) oObject).mdHierarchy);
		}
	}
	
	private String moProceedings; 
	private double mdMaxPufferLength;
	private double mdTalkingSpeed; // Length per Cycle
	private double mdThinkingSpeed; // Length per Cycle
	private double mdMaxLevel;
	
	private HashMap<clsGrainLevelKey, clsGrainLevel> moGrainLevels;
	
	public clsAbstractSpeech(String poPrefix, clsProperties poProp) {
		moGrainLevels = new HashMap<clsGrainLevelKey, clsGrainLevel>();
		moProceedings = "Initialize";
		applyProperties(poPrefix, poProp);
	}
	
	public clsAbstractSpeech(clsAbstractSpeech oAbstractSpeech) {
		mdMaxPufferLength = oAbstractSpeech.mdMaxPufferLength;
		mdTalkingSpeed = oAbstractSpeech.mdTalkingSpeed;
		mdThinkingSpeed = oAbstractSpeech.mdThinkingSpeed;
		mdMaxLevel = oAbstractSpeech.mdMaxLevel;
		moProceedings = oAbstractSpeech.moProceedings;
		moGrainLevels = oAbstractSpeech.cloneGrainLevels(mdMaxPufferLength);  
	}
	
	/**
	 * clsAbstractSpeech(clsAbstractSpeech oAbstractSpeech, double dTalkingSpeed) (MW) - Modifies the parameter Object! 
	 *
	 * @since 27.02.2013 10:17:17
	 *
	 * @param oAbstractSpeech
	 * @param dTalkingSpeed
	 */
	public clsAbstractSpeech(clsAbstractSpeech oAbstractSpeech, double dCloneLength) {
		mdMaxPufferLength = oAbstractSpeech.mdMaxPufferLength;
		mdTalkingSpeed = oAbstractSpeech.mdTalkingSpeed;
		mdThinkingSpeed = oAbstractSpeech.mdThinkingSpeed;
		moProceedings = oAbstractSpeech.moProceedings;
		moGrainLevels = oAbstractSpeech.cloneGrainLevels(dCloneLength); 
	}
	
	/**
	 * public static clsProperties getDefaultProperties(String poPrefix) (MW) - Is called from F31
	 *
	 * @since 26.02.2013 01:18:31
	 *
	 * @param poPrefix Is the same as for F31
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(clsProperties.addDot(poPrefix)+P_ABSTRACTSPEECH);

		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_MAXLENGTH, 10.0);
		oProp.setProperty(pre+P_MAXLEVEL, 1.1);
		oProp.setProperty(pre+P_TALKINGSPEED, 1.0);
		oProp.setProperty(pre+P_THINKINGSPEED, 1.0);
				
		return oProp;
	}	

	public void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(clsProperties.addDot(poPrefix)+P_ABSTRACTSPEECH);

		mdMaxPufferLength = poProp.getPropertyDouble(pre+P_MAXLENGTH);
		mdMaxLevel = poProp.getPropertyDouble(pre+P_MAXLEVEL);
		mdTalkingSpeed = poProp.getPropertyDouble(pre+P_TALKINGSPEED);
		mdThinkingSpeed = poProp.getPropertyDouble(pre+P_THINKINGSPEED);
	}	

	public boolean isEmpty() {
		Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
		while (oEntries.hasNext()) {
			Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
			
			clsGrainLevel oGrainLevel = getGrainLevel(oEntry.getKey());
			if (oGrainLevel.mdProcessedLengthMind != 0)
				return false;
		}
		return true;
	}
	
	private void add(clsAbstractSpeech oAbstractSpeechBody) {
		if (oAbstractSpeechBody != null && oAbstractSpeechBody.moGrainLevels != null){
			Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = oAbstractSpeechBody.moGrainLevels.entrySet().iterator();
			while (oEntries.hasNext()) {
				Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
				
				clsGrainLevel oGrainLevel = this.getGrainLevel(oEntry.getKey());
				if (oGrainLevel == null){
					this.addGrainLevel(oEntry.getKey().moLabel, oEntry.getKey().mdHierarchy);
					oGrainLevel = this.getGrainLevel(oEntry.getKey());
				}
				
				for (clsGrain<itfSpeakable> oGrain : oEntry.getValue().moGrains){
					if (oGrainLevel.mdMaxLength < oGrainLevel.mdProcessedLengthBody + oGrain.mdLength)
						throw new IndexOutOfBoundsException();
					oGrainLevel.addGrain(oGrain.moData, oGrain.mdLength, oGrain.mdCommitLength);	
					oGrainLevel.mdProcessedLengthBody += oGrain.mdLength;
				}
				oGrainLevel.mdProcessedLengthMind = 0.0; //do set this at symbolization automatically but only after finishing F31
			}
		}
		
	}
	
	private clsGrainLevel addGrainLevel(String moLabel, Double mdHierarchy) {
		clsGrainLevel oGrainLevel = new clsGrainLevel(mdMaxPufferLength);
		clsGrainLevelKey oKey = new clsGrainLevelKey(moLabel, mdHierarchy);
		
		moGrainLevels.put(oKey, oGrainLevel);
		return oGrainLevel;
	}
	
	private clsGrainLevel getGrainLevel(String moLabel, Double mdHierarchy) {
		clsGrainLevelKey oKey = new clsGrainLevelKey(moLabel, mdHierarchy);
		
		return getGrainLevel(oKey);
	}
	
	private clsGrainLevel getGrainLevel(clsGrainLevelKey oKey) {	
		return moGrainLevels.get(oKey);
	}
	
	public void flushPuffer() {
		moGrainLevels.clear();
	}
	
	protected HashMap<clsGrainLevelKey, clsGrainLevel> cloneGrainLevels(double dCloneLength) {
		HashMap<clsGrainLevelKey, clsGrainLevel> oGrainLevels = new HashMap<clsGrainLevelKey, clsGrainLevel>();
		
		if (moGrainLevels != null){
			Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
			while (oEntries.hasNext()) {
				Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
				
				clsGrainLevel oGrainLevel = new clsGrainLevel(getGrainLevel(oEntry.getKey()), dCloneLength);
				clsGrainLevelKey oKey = new clsGrainLevelKey(oEntry.getKey());
				oGrainLevels.put(oKey, oGrainLevel);
			}
		}
		return oGrainLevels;	
	}
	
	public void addLiteralSentence(String oString, double dLength) {
		clsGrainLevel oGrainLevelS;
		clsGrainLevel oGrainLevelW;
		clsGrainLevel oGrainLevelL;
		double dsum = 0;
		
		oGrainLevelS = addGrainLevel("Sentence", 1.0);
		oGrainLevelS.addGrain(new clsSpeakableString(oString), dLength, dLength);
		oGrainLevelS.mdProcessedLengthMind = dLength;
		
		
		//LetterLevel
		oGrainLevelL = addGrainLevel("Letter", 3.0);
		
		double d = dLength / oString.length(); // Länge pro Buchstaben
		d = (double) Math.round(d * 1000) / 1000;	 // Smallest resolution
		for (int j = 0; j < oString.length(); j++){
			if (j + 1 == oString.length())
				d = dLength - dsum;
			oGrainLevelL.addGrain(new clsSpeakableCharacter(oString.charAt(j)), d, d);
			dsum += d;
		}
		oGrainLevelL.mdProcessedLengthMind = dsum;
		
		//WordLevel
		oGrainLevelW = addGrainLevel("Word", 2.0);
		
		String oWords[] = oString.split("\\s+");
		d = 0;
		for(String oWord : oWords)
			d += oWord.length();
		d = dLength / d;
		d = (double) Math.round(d * 1000) / 1000;	 // Smallest resolution
		int i = 0;
		dsum = 0;
		for(String oWord : oWords) {
			i++;
			if (i == oWords.length){
				d = dLength - dsum;
				//oGrainLevelW.addGrain(new clsSpeakableString(oWord), d, d);
				oGrainLevelW.addGrain(new clsSpeakableStringOrdered(oWord, i), d, d);
				dsum += d;
			} else {
				//oGrainLevelW.addGrain(new clsSpeakableString(oWord), d * oWord.length(), d * oWord.length());
				oGrainLevelW.addGrain(new clsSpeakableStringOrdered(oWord, i), d * oWord.length(), d * oWord.length());
				dsum += (d * oWord.length());
			}
			
		}
		oGrainLevelW.mdProcessedLengthMind = dsum;
		
	}
	
	public void pronounceLiteralSentence(double dValue) {
		String oLevel; 
		double dLevel;
		
		oLevel = new String("Sentence");
		dLevel = 1.0;
		
		pronounceLiteral(oLevel, dLevel, dValue);
		pronounceLiteralWords(dValue);
	}
	
	private void pronounceLiteralWords(double dValue) {
		String oLevel; 
		double dLevel;
		
		oLevel = new String("Word");
		dLevel = 2.0;
		
		pronounceLiteral(oLevel, dLevel, dValue);
		pronounceLiteralLetters(dValue);
	}
	
	private void pronounceLiteralLetters(double dValue) {
		String oLevel; 
		double dLevel;
		
		oLevel = new String("Letter");
		dLevel = 3.0;
		
		pronounceLiteral(oLevel, dLevel, dValue);
	}
	
	private void pronounceLiteral(String oLevel, double dLevel, double dValue)
	{
		double dValues[];
		double dLengths[]; 
		clsGrainLevel oGrainLevel;
		oGrainLevel = getGrainLevel(oLevel, dLevel);
		int i = 0;
		
		if (oGrainLevel != null){
			dLengths = new double [oGrainLevel.moGrains.size()]; // Dummy Value
			dValues = new double [oGrainLevel.moGrains.size()]; // Dummy Value
			for (clsGrain<itfSpeakable> oGrain : oGrainLevel.moGrains){
				dLengths[i] = oGrain.mdLength;
				dValues[i] = dValue;
				i++;
			}
			addProsody(dValues, dLengths, oLevel, dLevel + 0.1);
		}
	}
	
	public void addProsody(double dValues[], double dLengths[], String oLevel, double dLevel){
		clsGrainLevel oGrainLevel = addGrainLevel(oLevel, dLevel);
		
		for (int i = 0; i< dValues.length; i++)
		{
			Double d = dValues[i];
			oGrainLevel.addGrain(new clsSpeakableDouble(d), dLengths[i], dLengths[i]);
		}
	}
	
	protected clsAbstractSpeech cloneBySpeed(double dSpeed) {
		clsAbstractSpeech oAbstractSpeechBody = new clsAbstractSpeech(this, dSpeed);
		
		return oAbstractSpeechBody;
	}

	/**
	 * clone() (MW) - Functionality depends on moProceding
	 *
	 * @since 06.03.2013 17:44:57
	 *
	 * @return
	 */
	@Override
	protected clsAbstractSpeech clone() {
		clsAbstractSpeech oAbstractSpeechBody = new clsAbstractSpeech(this);
		
		return oAbstractSpeechBody;
	}
	
	
	/**
	 * changeProceedingsToS1() (MW) - Normalize length by thinking speed
	 * @return 
	 *
	 * @since 27.02.2013 10:38:20
	 *
	 */
	public clsAbstractSpeech changeProceedingsToS1(){
		moProceedings = "S1";
		
		Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
		while (oEntries.hasNext()) {
			Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
			clsGrainLevel oGrainLevel = getGrainLevel(oEntry.getKey());
			
			oGrainLevel.mdProcessedLengthMind = 0;
			for (Iterator<clsGrain<itfSpeakable>> oIter = oGrainLevel.moGrains.iterator(); oIter.hasNext(); ) {
				oGrainLevel.mdProcessedLengthMind += oIter.next().mdLength;
			}
			
			if (oGrainLevel.mdProcessedLengthMind >= mdThinkingSpeed)
				oGrainLevel.mdProcessedLengthMind = mdThinkingSpeed;
		}
		return this;
	}; 
	
	/**
	 * changeProceedingsToS2 (MW) - Splits clsAbstractSpeech in two by length according to talking speed, normalizes length values
	 *
	 * @since 27.02.2013 10:39:45
	 *
	 * @return First of the two resulting clsAbstractSpeech normalized by talking speed
	 */
	public clsAbstractSpeech changeProceedingsToS2(){
		if (!moProceedings.equals("S1"))
			throw new IndexOutOfBoundsException();
		moProceedings = "S2";
		clsAbstractSpeech oRet = this.cloneBySpeed(mdTalkingSpeed);
		oRet.moProceedings = "S2";
		moProceedings = "S1";
		
		return oRet;
	};
	
	//here we could change loudness by distance
	public clsAbstractSpeech changeProceedingsToS3(){
		if (!moProceedings.equals("S2"))
			throw new IndexOutOfBoundsException();
		moProceedings = "S3";
		clsAbstractSpeech oRet =  this.clone();
		oRet.moProceedings = "S3";
		moProceedings = "S2";
		return oRet;
	} 
	
	//here we could change loudness by factors of the recipients ears
	//comitlength > lenth puffern!
	public clsAbstractSpeech changeProceedingsToS4(){
		String oldProceedings = moProceedings;
		if (!(moProceedings.equals("S3") || 
			  moProceedings.equals("S4")))			// This happens because the Object is cloned for the Inspector also, and the copy constructor calls changeProceedingsToS4() It was a poor design choice not to implement "clone" and "clone by range" separately.
			throw new IndexOutOfBoundsException();
		if (moProceedings.equals("S3"))
			moProceedings = "S4";
		else
			moProceedings = "S4_Inspector";
		clsAbstractSpeech oRet = this.clone();
		oRet.moProceedings = "S4";
		moProceedings = oldProceedings;
		return oRet;
	} 
	
	//puffer the incoming signal. 
	public void changeProceedingsToS5(clsAbstractSpeech oAbstractSpeech){
		if (!moProceedings.equals("S5"))
			throw new IndexOutOfBoundsException();
		moProceedings = "S5";
		this.add(oAbstractSpeech);
	}
	public void changeProceedingsToS5(){
		if (!moProceedings.equals("S4"))
				throw new IndexOutOfBoundsException();	
		moProceedings = "S5";
	}
	public void finishS5() {
		if (moGrainLevels != null){
			Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
			while (oEntries.hasNext()) {
				Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
				
				oEntry.getValue().mdProcessedLengthMind = Math.max(0.0, oEntry.getValue().mdProcessedLengthMind - mdThinkingSpeed);
				oEntry.getValue().mdProcessedLengthBody = Math.max(0.0, oEntry.getValue().mdProcessedLengthBody - mdThinkingSpeed);
			}
		}
	}
	
	public String getProceedings() {
		return moProceedings;
	}

	public boolean hasNextGrainTP() {
		Double dBodyLength = 0.0;
		Double dMindLength = 0.0;
		if (moGrainLevels == null)
			return false;
		Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
		while (oEntries.hasNext()) {
			Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
			
			clsGrainLevel oGrainLevel = getGrainLevel(oEntry.getKey());
			dBodyLength += oGrainLevel.mdProcessedLengthBody;
			dMindLength += oGrainLevel.mdProcessedLengthMind;
		}
		if (dMindLength < dBodyLength)
			return true;
		return false;
	}

	public String[] getNextGrainTP() {
		if (moGrainLevels == null)
			return null;
		Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
		while (oEntries.hasNext()) {
			Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
			
			clsGrainLevel oGrainLevel = getGrainLevel(oEntry.getKey());
			
			if (oGrainLevel.moGrains != null && !oGrainLevel.moGrains.isEmpty()) {
				for (Iterator<clsGrain<itfSpeakable>> oIter = oGrainLevel.moGrains.iterator(); oIter.hasNext() && oGrainLevel.mdProcessedLengthBody > 0 && oGrainLevel.mdProcessedLengthMind <= mdThinkingSpeed; ) {
					clsGrain<itfSpeakable> oGrain = oIter.next();
					
					if (oGrain.mdCommitLength > 0){
						if (oGrainLevel.mdProcessedLengthMind + oGrain.mdCommitLength <= mdThinkingSpeed){
							oGrainLevel.mdProcessedLengthMind += oGrain.mdLength;
							
							String oData = oGrain.moData.toString();
							String oKeyLevel = oEntry.getKey().mdHierarchy.toString();
							String oKeyTag = oEntry.getKey().moLabel;
							oIter.remove();
							return new String[] {oData, oKeyLevel, oKeyTag};
						} else {
							oGrainLevel.mdProcessedLengthMind += mdThinkingSpeed - oGrainLevel.mdProcessedLengthMind;
							oGrain.mdLength -= mdThinkingSpeed - oGrainLevel.mdProcessedLengthMind;
							oGrain.mdCommitLength = mdThinkingSpeed - oGrainLevel.mdProcessedLengthMind;
							
							if (oGrain.mdCommitLength <= 0){
								String oData = oGrain.moData.toString();
								String oKeyLevel = oEntry.getKey().mdHierarchy.toString();
								String oKeyTag = oEntry.getKey().moLabel;
								return new String[] {oData, oKeyLevel, oKeyTag};
							}
						}
					} else {
						oGrainLevel.mdProcessedLengthMind += Math.min(mdThinkingSpeed, oGrain.mdLength);
						oGrain.mdLength -= Math.min(mdThinkingSpeed, oGrain.mdLength);
						if (oGrain.mdLength <= 0)
							oIter.remove();
					}
				}
			}
		}
		return null;
	}


	public String getMood() {
		String oLevel; 
		double dLevel;
		
		oLevel = new String("Sentence");
		dLevel = 1.1;
		
		clsGrainLevel oGrainLevel = getGrainLevel(oLevel, dLevel);
		
		if (oGrainLevel != null){
			for (clsGrain<itfSpeakable> oGrain : oGrainLevel.moGrains){
				return oGrain.moData.toString();
			}
		}
		return "";
	}
	
	
	public String getInspectorString(){
		String oRet = new String();
		
		Iterator<Entry<clsGrainLevelKey, clsGrainLevel>> oEntries = moGrainLevels.entrySet().iterator();
		while (oEntries.hasNext()) {
			Entry<clsGrainLevelKey, clsGrainLevel> oEntry = oEntries.next();
			oRet = oRet + oEntry.getKey().moLabel + ": ";
			clsGrainLevel oGrainLevel = getGrainLevel(oEntry.getKey());
			
			for (Iterator<clsGrain<itfSpeakable>> oIter = oGrainLevel.moGrains.iterator(); oIter.hasNext(); ) {
				clsGrain<itfSpeakable> temp = oIter.next();
				oRet = oRet + temp.moData.speak();
				if (oIter.hasNext())
					oRet = oRet + "-";
			}
			oRet = oRet + "<br> \n";
		}
		oRet = oRet + "<br> \n Last Cues possibly not committed";
		return oRet;
	}
}