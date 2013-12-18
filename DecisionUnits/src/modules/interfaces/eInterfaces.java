/**
 * eInterfaces.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 */
package modules.interfaces;

import java.util.ArrayList;

/**
 * Contains the list of all interfaces of the functional model. Additionally the purpose of the interface is stored. 
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 * 
 */
public enum eInterfaces {
	D1_1("Write access to libido storage (from F41)."),
	D1_2("Read access to libido storage (from F41)."),
	D1_3("Write access to libido storage (from F39)."),
	D1_4("Read access to libido storage (from F39)."),
	D2_1("Write access to blocked content (from F35)."),
	D2_2("Read access to blocked content (from F35)."),
	D2_3("Write access to blocked content (from F36)."),
	D2_4("Read access to blocked content (from F36)."),
	D3_1("n/a"),
	D3_2("n/a"),
	D3_3("n/a"),
	D3_4("n/a"),
	D3_5("n/a"),
	D4_1("write actual pleasure to DT4"),
	I0_1("The inner somatic stimulation source which produces a constant flow of libido is represented by this interface. The circular loop in the figure defines that this source has a not identifiable bodily source and that the amount of libido produced cannot be influenced and stays constant. I0.1 connects the physical and chemical body with F39. "),	
	I0_2("The second incoming connection to F39 originates in the erogenous zones. "),	
	I0_3("This input connected to Module F1 represents the outputs of the sensors which measure the homeostasis."),	
	I0_4("Environment sensors signals are fed into F10."),	
	I0_5("The body sensors (see above) provide their information to F12 via this interface."),	
	I0_6("Electrophysical signals are transmitted from F32 to the actuators."),
	I1_1("Connection of a bodily module to its neurosymbolization module. F39->F40"),
	I1_2("Connection of a bodily module to its neurosymbolization module. F1->F2"),
	I1_3("Connection of a bodily module to its neurosymbolization module. F10->F11"),
	I1_4("Connection of a bodily module to its neurosymbolization module. F12->F13"),
	I1_5("Connection of a neurosymbolization module with its body module. F31->F32"),
	I2_1("Neurosymbols representing libido are transmitted from F40 to F41."),
	I2_2("This interface transports the neurosymbolized bodily needs from F2 to F3."),
	I2_3("Neurosymbols derived from external sensors are transmitted from F11 to F14."),
	I2_4("Similar to I2.2, I2.4 transports neurosymbols to F14. This time, they originate from F12."),
	I2_5("Connects the last psychic module in the chain F30 to the neurodesymbolization of the actions F31.	"),
	I2_6("Memory traces representing perceived environment and body information are forwarded to F46."),
//	I2_15("Self-preservation drives represented by thing presentations and their quota of affects are sent from F5 to F38."), //TD 2011/07/12 - deprecated and unused
//	I2_18("Sexual drives in the form of thing presentations and their quota of affects is sent from F42 to F44."), //TD 2011/07/12 - deprecated and unused
//	I2_19("Thing presentations and their quota of affects are transported from F44 to F6, F7, and F9."), //TD 2011/07/12 - deprecated and unused
	I3_1("The total amount of libido tension as well as the pair of opposites are transmitted from F41 to F43."),
	I3_2("Libidinous and aggressive drives represented by more or less complex associated thing presentations containing at least drive source, aim of drive, and drive object together with the tensions at the various drive sources are forwarded from F3 to F4."),
	I3_3("The eight drives - the four partial sexual drives divided into libidinous and aggressive components - as well as the total amount of libido tension are transmitted from F43 to F42."),
	I3_4("Pair of opposites in form of thing presentations and the tensions at the various drive source are transmitted from F4 to F48."),
	I4_1("Drive Candidates - vector of quota of affects are forwarded from F48 to F57"),
	I5_1("Remembered drive content (emotions + quota of affects) are forwarded from F57 to F54"),
	I5_2("Remembered drive content with associated primal repressed content are forwarded F49 to F54"),
	I5_3("Drives (emotions + quota of affects) are forwarded from F54 to F56"),
	I5_4("Drives (emotions + quota of affects) are forwarded from F56 to F55"),
	I5_5("Selected drive content is forwarded from F55 to F6"),
	I5_6("Similarly to I2.6, thing presentations are transported from F46 to F37. If quota of affects were retrieved from memory, these values are transported too."),
	I5_7("Thing presentations and their quota of affects are transported from F37 to F35 and F57."),
	I5_8("From F35, thing presentations and quotas of affects are transported to F45."),
	I5_9("Quantified quotas of affect and thing presentations are transported from F45 to F18."),
	I5_10("Thing presentations of the perception (enriched with data from memory and feed back thing presentations) and their attached quota of affects are forwarded from F18 to F7."),
	I5_11("Superego bans and rules are transported from F7 to F19."),
	I5_12("Drive content/Drives F55 to F7"),
	I5_13("Superego bans and rules are transported from F7 to F6."),
	I5_14("Superego bans and rules are transported from F55 to F19."),
	I5_15("Transports (unchanged or adapted) perceptions which passed the defense mechanisms from F19 to the conversion module F21."),
	I5_16("Analogous to I5.17, I5.16 transports quota of affects which have formerly been attached to thing presentations representing perceived contents. They are forwarded from F19 to F20.	"),
	I5_17("Transports quota of affects which have originally been attached to thing presentations representing drive contents from F6 to F20. The splitting apart is a result of the defense mechanisms."),
	I5_18("Transports (unchanged or adapted) drive contents which passed the defense mechanisms from F6 to the conversion module F8."),
	I5_19("Word presentations originating in F27 are reduced to thing presentations in F47. These are forwarded together with their attached quota of affects to F46."),
	I5_21("F63 to F55."),
	I6_1("The perception contents consisting of word presentations, thing presentations, and affects are sent from F21 to F61 and F66 for further processing."),
	I6_2("Differentiated experienced moods (Feelings, Thing Presentations, Symbol Presentations) are transported from F20 to F26, F52, and F29."),
	I6_3("This interface distributes the drive wishes produced by module F8 to the modules F23 and F26."),
	I6_4("Analogous to 6.5, this interface transports the perceptions in the form of word presentations, thing presentations, and affects from F21 to F20."),
	I6_5("Drive wishes are transported from F8 to F20. The contents are in the form of word presentations, thing presentations, and affects."),
	I6_6("The list - the package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importance - is forwarded by the interface I6.6 to F51."),
	I6_7("The results of the first reality check performed by module F51 is forwarded to F26."),
	I6_8("Three action goals are passed on to F52. Word and thing presentations representing the result of module F26 are distributed to F52."),
	I6_9("The various imaginated action plans are distributed from F52 to F47, F29, and F53."),
	I6_10("Reality checked action plans are passed on from F53 to F29."),
	I6_11("The final action plan is transported from F29 to F30."),
	I6_12("The perception contents consisting of word presentations, thing presentations, and affects are sent from F61 to F23."),
	I6_13("The speech production contents consisting of word presentations, thing presentations, and affects are sent from F66 to F21."),
	I6_14("Emotions are sent as a signal from F20 to F67. Bodily Reactions are resulted.");
	
	/** Description of the purpose of the interface; @since 12.07.2011 13:16:00 */
	private String moDescription;
	
    /**
     * Creates an enum entry together with the provided description attached.
     *
     * @since 12.07.2011 13:16:13
     *
     * @param poDescription
     */
    private eInterfaces(String poDescription) {
    	this.moDescription = poDescription;
    }


	/**
	 * Extract the interface enum entry from the provided interface name in form of a string.
	 *
	 * @since 12.07.2011 13:16:36
	 *
	 * @param poName
	 * @return
	 * @throws java.lang.IllegalArgumentException
	 */
	public static eInterfaces getEnum(String poName) throws java.lang.IllegalArgumentException {
		String[] temp = poName.split("_");
		poName = temp[0]+"_"+temp[1];
		return eInterfaces.valueOf(poName);
	}
	
	/**
	 * Convert an array of classes of interfaces into an array that contains the corresponding enums.
	 *
	 * @since 12.07.2011 13:19:36
	 *
	 * @param poInterfaces
	 * @return
	 */
	public static ArrayList<eInterfaces> getInterfaces(@SuppressWarnings("rawtypes") Class[] poInterfaces) {
		ArrayList<eInterfaces> oResult = new ArrayList<eInterfaces>();
		
		for (@SuppressWarnings("rawtypes") Class i:poInterfaces) {
			try {
				eInterfaces t = getEnum(i.getSimpleName());
				oResult.add(t);
			} catch (java.lang.IllegalArgumentException e) {
				// do nothing
			}
		}
		
		return oResult;
	}

	/**
	 * Return the description of this enum.
	 *
	 * @since 12.07.2011 13:20:28
	 *
	 * @return
	 */
	public String getDescription() {
		return moDescription;
	}
}
