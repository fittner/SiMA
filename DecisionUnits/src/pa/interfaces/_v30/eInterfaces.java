/**
 * eInterfaces.java: DecisionUnits - pa.interfaces._v30
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 */
package pa.interfaces._v30;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 16:00:09
 * 
 */
public enum eInterfaces {
	D1_1("Write access to libido storage (from E41)."),
	D1_2("Read access to libido storage (from E41)."),
	D1_3("Write access to libido storage (from E39)."),
	D1_4("Read access to libido storage (from E39)."),
	D2_1("Write access to blocked content (from E35)."),
	D2_2("Read access to blocked content (from E35)."),
	D2_3("Write access to blocked content (from E36)."),
	D2_4("Read access to blocked content (from E36)."),
	I0_1("The inner somatic stimulation source which produces a constant flow of libido is represented by this interface. The circular loop in the figure defines that this source has a not identifiable bodily source and that the amount of libido produced cannot be influenced and stays constant. I0.1 connects the physical and chemical body with E39. "),	
	I0_2("The second incoming connection to E39 originates in the erogenous zones. "),	
	I0_3("This input connected to Module E1 represents the outputs of the sensors which measure the homeostasis."),	
	I0_4("Environment sensors signals are fed into E10."),	
	I0_5("The body sensors (see above) provide their information to E12 via this interface."),	
	I0_6("Electrophysical signals are transmitted from E32 to the actuators."),
	I1_1("Connection of a bodily module to its neurosymbolization module. E1->E2"),
	I1_2("This interface transports the neurosymbolized bodily needs from E2 to E3."),
	I1_3("Libidinous and aggressive drives represented by more or less complex associated thing presentations containing at least drive source, aim of drive, and drive object together with the tensions at the various drive sources are forwarded from E3 to E4."),
	I1_4("Pair of opposites in form of thing presentations and the tensions at the various drive source are transmitted from E4 to E5."),
	I1_5("Thing presentations and their quota of affects are transported from E38 to E6, E7, and E9."),
	I1_6("Transports (unchanged or adapted) drive contents which passed the defense mechanisms from E6 to the conversion module E8."),
	I1_7("This interface distributes the drive wishes produced by module E8 to the modules E22, E23, and E26."),
	I1_8("Connection of a bodily module to its neurosymbolization module. E39->E40"),
	I1_9("Neurosymbols representing libido are transmitted from E40 to E41."),
	I1_10("The total amount of libido tension as well as the pair of opposites are transmitted from E41 to E43."),
	I2_1("Connection of a bodily module to its neurosymbolization module. E10->E11"),
	I2_2("Neurosymbols derived from external sensors are transmitted from E11 to E14."),
	I2_3("Connection of a bodily module to its neurosymbolization module. E12->E13"),
	I2_4("Similar to I2.2, I2.4 transports neurosymbols to E14. This time, they originate from E12."),
	I2_5("Memory traces representing perceived environment and body information are forwarded to E46."),
	I2_8("From E35, thing presentations and quotas of affects are transported to E18 and E45."),
	I2_9("Thing presentations of the perception (enriched with data from memory and feed back thing presentations) and their attached quota of affects are forwarded from E18 to E7 and E19."),
	I2_10("Transports (unchanged or adapted) perceptions which passed the defense mechanisms from E19 to the conversion module E21."),
	I2_11("The perception contents consisting of word presentations, thing presentations, and affects are sent from E21 to E22 and E23 for further processing."),
	I2_12("The list - the package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importance - is forwarded by the interface I2.12 to E24 and E25."),
	I2_13("The results of the first reality check performed by module E24 is forwarded to E26."),
	I2_14("Thing presentations and their quota of affects are transported from E37 to E35."),
	I2_15("Self-preservation drives represented by thing presentations and their quota of affects are sent from E5 to E38."),
	I2_16("Quantified quotas of affect are transported from E45 to E18."),
	I2_17("The eight drives - the four partial sexual drives divided into libidinous and aggressive components - as well as the total amount of libido tension are transmitted from E43 to E42."),
	I2_18("Sexual drives in the form of thing presentations and their quota of affects is sent from E42 to E44."),
	I2_19("Thing presentations and their quota of affects are transported from E44 to E6, E7, and E9."),
	I2_20("Similarly to I2.5, thing presentations are transported from E46 to E37. If quota of affects were retrieved from memory, these values are transported too."),
	I3_1("Superego bans and rules are transported from E7 to E6."),
	I3_2("Superego bans and rules are transported from E7 to E19."),
	I3_3("Interface I3.3 transports the rules from E22 to E26."),
	I4_1("Repressed or blocked drive representations are not forwarded to the conversion to secondary processes. Instead they are moved to the repression handler. Thing presentations and their attached quota of affects are transferred from E6 to E36."),
	I4_2("Similarly to I4.1, perception contents in the form of thing presentations and their attached quota of affects are sent from E19 to E36."),
	I4_3("Repressed drive contents and repressed quota of affects are sent back to the defense mechansims. I4.3 connects E36 with E6."),
	I5_1("Transports quota of affects which have originally been attached to thing presentations representing drive contents from E6 to E20. The splitting apart is a result of the defense mechanisms."),
	I5_2("Analogous to I5.1, I5.2 transports quota of affects which have formerly been attached to thing presentations representing perceived contents. They are forwarded from E19 to E20.	"),
	I5_3("Drive wishes are transported from E8 to E20. The contents are in the form of word presentations, thing presentations, and affects."),
	I5_4("Analogous to I5.3, this interface transports the perceptions in the form of word presentations, thing presentations, and affects from E21 to E20."),
	I5_5("Affects and differentiated experienced moods are transported from E20 to E26 and E29."),
	I6_1("Transport contents of the semantic memory in the form of word and thing presentations. I6.1 connects E25 with E24"),
	I6_2("Contents of the ``episodic'' memory are transported from E28 to E27."),
	I6_3("Knowledge about the possibilities to satisfy drive demands is transported from E9 to E6."),
	I7_1("Word and thing presentations representing the result of module E26 are distributed to E27 and E28."),
	I7_3("The various imaginated action plans are distributed from E27 to E47, E29, E33, and E34."),
	I7_4("The final action plan is transported from E29 to E30."),
	I7_5("Transport contents of the semantic memory in the form of word and thing presentations. I7.5 connects E34 with E33"),
	I7_6("The result of the second reality check performed by module E33 is sent to E29."),
	I7_7("Word presentations originating in E27 are reduced to thing presentations in E47. These are forwarded together with their attached quota of affects to E46."),
	I8_1("Connects the last psychic module in the chain E30 to the neurodesymbolization of the actions E31.	"),
	I8_2("Connection of a neurosymbolization module with its body module. E31->E32");
	
	private String moDescription;
	
    private eInterfaces(String poDescription) {
    	this.moDescription = poDescription;
    }


	public static eInterfaces getEnum(String poName) throws java.lang.IllegalArgumentException {
		String[] temp = poName.split("_");
		poName = temp[0]+"_"+temp[1];
		return eInterfaces.valueOf(poName);
	}
	
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
	 * @author deutsch
	 * 15.04.2011, 14:28:15
	 * 
	 * @return the moDescription
	 */
	public String getDescription() {
		return moDescription;
	}
}
