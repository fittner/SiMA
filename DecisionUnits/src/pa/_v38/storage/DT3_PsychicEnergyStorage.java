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
public class DT3_PsychicEnergyStorage implements itfInspectorInternalState, itfInterfaceDescription{
		/**
		 * DOCUMENT (hinterleitner) - insert description 
		 *
		 * @since 14.07.2011 16:40:17
		 *
		 */
	
		public DT3_PsychicEnergyStorage() {

	    }
		public int PsychicEnergy_IN;
		public int PsychicEnergy_OUT;
		

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
			   "The modules are connected to this special type of storage with ({D2.1} and {D2.2}),({D2.3}, {D2.4}),{D2.5}), {D2.6})interface. " +
			   "The incoming interface into module {F56} is {I5.3}, where Affect values and Emotions are transfered.  ";
		}

		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_1_send#send_D3_1(java.util.ArrayList)
		 */
		public int D3_1_send(int ReducedPsychicEnergy) {
			//FixMe
			return ReducedPsychicEnergy;
			
		}
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_1_receive#receive_D3_1(java.util.ArrayList)
		 */
		public int D3_1_receive(int ReducedPsychicEnergy) {
		
			PsychicEnergy_IN = ReducedPsychicEnergy; 
			
			return ReducedPsychicEnergy;
			
		}
		
	
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_2_send#send_D3_2(java.util.ArrayList)
		 */
		public int D3_2_send(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_2_receive#receive_D3_2(java.util.ArrayList)
		 */
		public int D3_2_receive(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		

		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_3_send#send_D3_3(java.util.ArrayList)
		 */
		public int D3_3_send(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_3_receive#receive_D3_3(java.util.ArrayList)
		 */
		public int D3_3_receive(int ReducedPsychicEnergy) {
		
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_4_send#send_D3_4(java.util.ArrayList)
		 */
		public int D3_4_send(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_4_receive#receive_D3_4(java.util.ArrayList)
		 */
		public int D3_4_receive(int ReducedPsychicEnergy) {
		
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_5_send#send_D3_5(java.util.ArrayList)
		 */
		public int D3_5_send(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_5_receive#receive_D3_5(java.util.ArrayList)
		 */
		public int D3_5_receive(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
	

		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:15:13
		 * 
		 * @see pa.interfaces.send._v38.D3_5_send#send_D3_5(java.util.ArrayList)
		 */
		public int D3_6_send(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
		}
		
		
		/* (non-Javadoc)
		 *
		 * @author hinterleitner
		 * 09.03.2011, 17:18:20
		 * 
		 * @see pa.interfaces.send._v38.D3_6_receive#receive_D3_6(java.util.ArrayList)
		 */
		public int D3_6_receive(int ReducedPsychicEnergy) {
			
			return ReducedPsychicEnergy;
			
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