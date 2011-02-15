/**
 * clsBWMainWithUIEx.java: Sim - sim
 * 
 * @author muchitsch
 * 12.07.2010, 12:50:50
 */
package sim;

import java.awt.Container;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import statictools.clsGetARSPath;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 12.07.2010, 12:50:50
 * 
 */
public class clsBWMainWithUIEx {
	
	private String moStartupArguments = "";
	
	public clsBWMainWithUIEx() { 
		}
	
	public static void main(String[] args){
		String oPath = clsBWMain.argumentForKey("-path", args, 0);
		if (oPath == null) {oPath = clsGetARSPath.getConfigPath();	}
		
		
		//create dialog
		JDialog oOptionsDialog = new JDialog();
		oOptionsDialog.setSize(800, 600);
		oOptionsDialog.setName("Extended Options Dialog");
		
		oOptionsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		oOptionsDialog.setModal(true);
		oOptionsDialog.setVisible(true);
		
		addComponentsToDialog(oOptionsDialog.getContentPane());

		
		//add scenarios
		clsScenarioSelector oScenarioSelector = (new clsBWMainWithUIEx()).new clsScenarioSelector();
		oScenarioSelector.setSize(600, 400);
		oOptionsDialog.add( oScenarioSelector);
		
		
		
		
	
		//clsBWMainWithUI oBWmain = new clsBWMainWithUI(moStartupArguments);
		//oBWmain.main(moStartupArguments);
	}
	
	/**
	 * @author muchitsch
	 * 13.07.2010, 15:07:09
	 * 
	 * @param moStartupArguments the moStartupArguments to set
	 */
	public void setMoStartupArguments(String moStartupArguments) {
		this.moStartupArguments = moStartupArguments;
	}

	/**
	 * @author muchitsch
	 * 13.07.2010, 15:07:09
	 * 
	 * @return the moStartupArguments
	 */
	public String getMoStartupArguments() {
		return moStartupArguments;
	}
	
	 public static void addComponentsToDialog(Container pPane) {
		 
		 pPane.setLayout(new GridLayout(2,2));
		
		 //pPane.add(comp, )

		 
	 }



	class clsScenarioSelector extends JPanel implements ActionListener {
		/**
		 * DOCUMENT (muchitsch) - insert description 
		 * 
		 * @author muchitsch
		 * 12.07.2010, 13:57:25
		 */
		private static final long serialVersionUID = 3273247177544475953L;
		
		
		public clsScenarioSelector()
		{
						
			String moScenatrioTitle = "Default Title";
			String moScenarioDescription = "<html><P ALIGN='JUSTIFY'><b>"+moScenatrioTitle+"</b><br><font color=#000099>description of<br>the scenario xyz<br>this is a cool scenario<br>for showing xyz</font>";
			
			String oButtonPath = clsGetARSPath.getIconPath()+"ScenarioX.png";
		    ImageIcon oEntityIcon = new ImageIcon(oButtonPath);
		    //JButton buttonEntity = new JButton("<html>SCENARIO 1<b><u>T</u>wo</b><br>description of<br>the scenario xyz</html>", oEntityIcon);
		    JButton buttonEntity = new JButton(moScenarioDescription, oEntityIcon);
		    buttonEntity.setVerticalTextPosition(AbstractButton.BOTTOM);
		    buttonEntity.setHorizontalTextPosition(AbstractButton.CENTER);
		    buttonEntity.setActionCommand("scenario1");
		    buttonEntity.addActionListener(this);
		    add(buttonEntity);
		    
		    
		    ImageIcon oEntityIcon2 = new ImageIcon(oButtonPath);
		    JButton buttonEntity2 = new JButton("test2", oEntityIcon2);
		    buttonEntity2.setVerticalAlignment(SwingConstants.BOTTOM );
		    buttonEntity2.setActionCommand("reset");
		    buttonEntity2.addActionListener(this);
		    add(buttonEntity2);
		    
		    ImageIcon oEntityIcon3 = new ImageIcon(oButtonPath);
		    JButton buttonEntity3 = new JButton("test3", oEntityIcon3);
		    
		    buttonEntity3.setActionCommand("reset");
		    buttonEntity3.addActionListener(this);
		    add(buttonEntity3);
		    
			JPanel oPanelSel = new JPanel();
			oPanelSel.setLayout(new GridLayout(0,2));
		
		}


		/* (non-Javadoc)
		 *
		 * @author muchitsch
		 * 12.07.2010, 13:56:20
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			if ("scenario1".equals(e.getActionCommand())) {
				setMoStartupArguments("xyz");
			}
			else if ("scenario2".equals(e.getActionCommand())) {
				//todo
			}
			else {
				//noop
			}
		
			this.repaint();
		}
	}
}
