/**
 * CHANGELOG
 *
 * 09.07.2011 hinterleitner - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;
import pa._v38.interfaces.modules.D2_1_receive;
import pa._v38.interfaces.modules.D2_1_send;
import pa._v38.interfaces.modules.D2_2_receive;
import pa._v38.interfaces.modules.D2_2_send;
import pa._v38.interfaces.modules.D2_3_receive;
import pa._v38.interfaces.modules.D2_3_send;
import pa._v38.interfaces.modules.D2_4_receive;
import pa._v38.interfaces.modules.D2_4_send;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.tools.clsPair;



/**
 * DOCUMENT (hinterleitner) - inserted storage module for delivering psychic energy to F55, F7, F21, F20, F8, F23 
 * 
 * @author hinterleitner
 * 09.07.2011, 16:39:44
 * 
 */
public class clsPsychicEnergyStorage implements D2_1_receive, D2_1_send,
		D2_2_receive, D2_2_send, D2_3_receive, D2_3_send, D2_4_receive,
		D2_4_send {
		
		
		/**
		 * Module F56 sends free psychic energy that is constituted out of the affect values and emotions from {I5.3}
			A special storage containing these psychic values contents is necessary in order to distribute them
			The stored data is drive meshes with attached quota (affect values)
			The modules are connected to this special type of storage with ({D2.2} and {D2.4}),({D2.1} and {D2.3}) interface
			The incoming interface into module {F56} is {I5.3}, where Affect values and Emotions are transfered
			The free psychic Energy is forwarded via interface {I5.4} to the next module
		 * 
		   @author: Isabella Hinterleitner 
		 * 28.06.2011, 20:22:42
		 */	
		
		public clsPsychicEnergyStorage() {
	    	// The storage consists of an ArrayList of clsPair, in each pair, the element A is the DataStructure and
	    	// the element B contains the AssociatedDataStructures from the PrimaryDataStructureContainer that has been blocked.
	    //	moBlockedContent = new ArrayList<clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>();
	    //	fillWithTestData();
	    }
		
		public <result> Object calculateDistributionKey(Object mrPsychicEnergy) 
		{
			double mnPsychicEnergy = 6.0;
			
			if (mnPsychicEnergy == 6.0) {
				mnPsychicEnergy = 6.0 / 6;
	    		
			}
				return mrPsychicEnergy;
		
		}


		
		public class clsPsychicEnergyStorage1 implements D2_1_receive, D2_1_send,
		D2_2_receive, D2_2_send, D2_3_receive, D2_3_send, D2_4_receive,
		D2_4_send {
	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:39:59
	 * 
	 * @see pa._v38.interfaces.modules.D2_4_send#send_D2_4()
	 */
	@Override
	public clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> send_D2_4() {
		// TODO (hinterleitner) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:39:59
	 * 
	 * @see pa._v38.interfaces.modules.D2_4_receive#receive_D2_4(pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer, java.util.ArrayList)
	 */
	@Override
	public void receive_D2_4(clsPrimaryDataStructureContainer poData,
			ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:00
	 * 
	 * @see pa._v38.interfaces.modules.D2_3_send#send_D2_3(java.util.ArrayList)
	 */
	@Override
	public void send_D2_3(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:00
	 * 
	 * @see pa._v38.interfaces.modules.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_3(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:01
	 * 
	 * @see pa._v38.interfaces.modules.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public void send_D2_2(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:02
	 * 
	 * @see pa._v38.interfaces.modules.D2_2_receive#receive_D2_2(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_2(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:02
	 * 
	 * @see pa._v38.interfaces.modules.D2_1_send#send_D2_1(java.util.ArrayList)
	 */
	@Override
	public void send_D2_1(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 09.07.2011 16:40:02
	 * 
	 * @see pa._v38.interfaces.modules.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:48
 * 
 * @see pa._v38.interfaces.modules.D2_3_receive#receive_D2_3(java.util.ArrayList)
 */
@Override
public void receive_D2_3(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:49
 * 
 * @see pa._v38.interfaces.modules.D2_4_receive#receive_D2_4(pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer, java.util.ArrayList)
 */
@Override
public void receive_D2_4(clsPrimaryDataStructureContainer poData,
		ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:49
 * 
 * @see pa._v38.interfaces.modules.D2_4_send#send_D2_4()
 */
@Override
public clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> send_D2_4() {
	// TODO (hinterleitner) - Auto-generated method stub
	return null;
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:49
 * 
 * @see pa._v38.interfaces.modules.D2_2_send#send_D2_2(java.util.ArrayList)
 */
@Override
public void send_D2_2(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:50
 * 
 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
 */
public String getDescription() {
	return "Module {F56} sends free psychic energy that is constituted out of the affect values and emotions from {I5.3}." +
			"A special storage containing these psychic values contents is necessary in order to distribute them. " +
			"The stored data is drive meshes with attached quota (affect values). " +
			"The modules are connected to this special type of storage with ({D2.2} and {D2.4}),({D2.1} and {D2.3}) interface. " +
			"The incoming interface into module {F56} is {I5.3}, where Affect values and Emotions are transfered.  " +
			"The free psychic Energy is forwarded via interface {I5.4} to the next module.";
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:01:52
 * 
 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
 */
public String stateToTEXT() {
	// TODO (hinterleitner) - Auto-generated method stub
	String text = "";	
	
	//text += toText.valueToTEXT.("mrPsychicEnergy", mrPsychicEnergy);
	return text;

}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:12:54
 * 
 * @see pa._v38.interfaces.modules.D2_3_send#send_D2_3(java.util.ArrayList)
 */
@Override
public void send_D2_3(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:12:55
 * 
 * @see pa._v38.interfaces.modules.D2_2_receive#receive_D2_2(java.util.ArrayList)
 */
@Override
public void receive_D2_2(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:12:55
 * 
 * @see pa._v38.interfaces.modules.D2_1_send#send_D2_1(java.util.ArrayList)
 */
@Override
public void send_D2_1(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

/* (non-Javadoc)
 *
 * @since 09.07.2011 17:12:55
 * 
 * @see pa._v38.interfaces.modules.D2_1_receive#receive_D2_1(java.util.ArrayList)
 */
@Override
public void receive_D2_1(ArrayList<Object> poData) {
	// TODO (hinterleitner) - Auto-generated method stub
	
}

}