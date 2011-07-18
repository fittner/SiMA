/**
 * CHANGELOG
 *
 * 14.07.2011 deutsch - refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.eInterfaces;




/**
 * Storage module for delivering psychic energy to F55, F7, F21, F20, F8, F23.
 * Module F56 sends free psychic energy that is constituted out of the affect values and emotions from {I5.3}. A special storage 
 * containing these psychic values contents is necessary in order to distribute them. The stored data is drive meshes with attached 
 * quota (affect values). The modules are connected to this special type of storage with ({D2.2} and {D2.4}),({D2.1} and {D2.3}) 
 * interface. The incoming interface into module {F56} is {I5.3}, where Affect values and Emotions are transfered. The free psychic 
 * Energy is forwarded via interface {I5.4} to the next module 
 * 
 * @author hinterleitner
 * 09.07.2011, 16:39:44
 * 
 */
public class clsPsychicEnergyStorage implements itfInspectorInternalState, itfInterfaceDescription{
		/**
		 * DOCUMENT (hinterleitner) - insert description 
		 *
		 * @since 14.07.2011 16:40:17
		 *
		 */
		public clsPsychicEnergyStorage() {

	    }
		
		

		/* (non-Javadoc)
		 *
		 * @since 14.07.2011 16:38:05
		 * 
		 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
		 */
		@Override
		public String getDescription() {
			return  "Module {F56} sends free psychic energy that is constituted out of the affect values and emotions from {I5.3}." +
			   "A special storage containing these psychic values contents is necessary in order to distribute them. " +
			   "The stored data is drive meshes with attached quota (affect values). " +
			   "The modules are connected to this special type of storage with ({D2.2} and {D2.4}),({D2.1} and {D2.3}) interface. " +
			   "The incoming interface into module {F56} is {I5.3}, where Affect values and Emotions are transfered.  " +
			   "The free psychic Energy is forwarded via interface {I5.4} to the next module.";
		}

		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_2_send#send_D3_2(java.util.ArrayList)
		 */
		public void D3_2_send(int ReducedPsychicEnergy) {
			
			
		}
		
		
		public void D3_2_receive(int ReducedPsychicEnergy) {
			
			
		}
		
		/* (non-Javadoc)
		 *
		 * @since 14.07.2011 16:38:05
		 * 
		 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
		 */
		@Override
		public ArrayList<eInterfaces> getInterfacesRecv() {
			// TODO (hinterleitner) - Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 *
		 * @since 14.07.2011 16:38:05
		 * 
		 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
		 */
		@Override
		public ArrayList<eInterfaces> getInterfacesSend() {
			// TODO (hinterleitner) - Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 *
		 * @since 14.07.2011 16:38:05
		 * 
		 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
		 */
		@Override
		public String stateToTEXT() {
			// TODO (hinterleitner) - Auto-generated method stub
			return null;
		}

}