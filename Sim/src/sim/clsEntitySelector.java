/**
 * 2011/06/15 TD - extracted class from clsBWFastEntityAdapter.
 */
package sim;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import statictools.clsGetARSPath;
import config.clsProperties;

/**
 * Entry for the clsBWFastEntityAdapter. Extracts the information from the provided properties. 
 * 
 * @see sim.clsFastEntityAdapter
 * @author langr
 * 15.06.2011, 16:26:46
 * 
 */
public class clsEntitySelector extends JPanel implements ActionListener {
	private static final long serialVersionUID = -294815264176233253L;
	private int mnInitCount;
	private int mnCount;
	private clsProperties moProp;
	private JLabel moCountLabel;
	private String moCountPath;

	/**
	 * Creates an entry of type JPanel that display the type of the entity group, the name of the group, the number of elements in the group, and an up and a down button to change the numbers.
	 *
	 * @param pnCount number of entities
	 * @param poEntityName name of the type of the entities in the group
	 * @param poCountPath pointer to the entry in the properties where the number is defined
	 * @param oProp the properties to be adapted
	 */
	public clsEntitySelector(int pnCount, String poEntityName, String poCountPath, clsProperties oProp) {
		
		mnInitCount = mnCount = pnCount;
		moProp = oProp;
		moCountPath = poCountPath;
		
		//create the entity icon
		String oButtonPath = clsGetARSPath.getIconPath()+poEntityName+".png";
	    ImageIcon oEntityIcon = new ImageIcon(oButtonPath);
	    JButton buttonEntity = new JButton(poEntityName, oEntityIcon);
	    buttonEntity.setSize(500, buttonEntity.getSize().height);
	    buttonEntity.setActionCommand("reset");
	    buttonEntity.addActionListener(this);
	    add(buttonEntity);
	    
	    //create the second row
		JPanel oPanelSel = new JPanel();
		oPanelSel.setLayout(new GridLayout(0,2));
	    
	    //create the up/down-selector 
		JPanel oPanel = new JPanel();
		oPanel.setLayout(new GridLayout(0,1));
		
		String oPath = clsGetARSPath.getIconPath()+"arrow_up.png";
	    ImageIcon oBtnUpImg = new ImageIcon(oPath);
	    JButton oBtnUp = new JButton(oBtnUpImg);
	    oBtnUp.setActionCommand("up");
	    oBtnUp.addActionListener(this);
		
		oPath = clsGetARSPath.getIconPath()+"arrow_down.png";
	    ImageIcon oBtnDownImg = new ImageIcon(oPath);
	    JButton oBtnDown = new JButton(oBtnDownImg);
	    oBtnDown.setActionCommand("down");
	    oBtnDown.addActionListener(this);

	    oPanel.add(oBtnUp);
	    oPanel.add(oBtnDown);
	    oPanelSel.add(oPanel);
		
		//create the text
	    moCountLabel = new JLabel("   "+Integer.toString(mnCount));
	    moCountLabel.setFont(new Font("Arial", Font.BOLD, 40));
	    moCountLabel.setHorizontalTextPosition(JLabel.CENTER);
		oPanelSel.add(moCountLabel);
		
		add(oPanelSel);

	}

	/* (non-Javadoc)
	 *
	 * reactes to the various actions that can be performed with the JPAnel. in this case up and down. everything else has no influence. 
	 *
	 * @author deutsch
	 * 15.06.2011, 16:43:04
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if ("up".equals(arg0.getActionCommand())) {
			mnCount++;
		}
		else if ("down".equals(arg0.getActionCommand())) {
			if(mnCount>0) { 
				mnCount--;
			}
			else {
				mnCount=0;
			}
		}
		else {
			mnCount=mnInitCount;
		}
		moProp.setProperty(moCountPath, mnCount);
		moCountLabel.setText("   "+Integer.toString(mnCount));
		this.repaint();
	}
	
}
