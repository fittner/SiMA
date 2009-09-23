/**
 * clsBWFastEntityAdapter.java: BW - sim
 * 
 * @author langr
 * 18.09.2009, 21:11:16
 */
package sim;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import statictools.clsGetARSPath;

import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 18.09.2009, 21:11:16
 * 
 */
public class clsBWFastEntityAdapter extends JDialog {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.09.2009, 10:34:02
	 */
	private static final long serialVersionUID = -8502114600901820076L;
	
	private clsBWProperties moPropOriginal;
	
	public clsBWFastEntityAdapter(JFrame poParent, String poTitle, clsBWProperties poProp) {
		
	    super(poParent, poTitle, true);
	    
	    if (poParent != null) {
	      Dimension parentSize = poParent.getSize(); 
	      Point p = poParent.getLocation(); 
	      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
	    }

	    moPropOriginal = poProp;
	    
		setSize(500, 600);
		setLayout(new GridLayout(0,1));
		int numGroups = poProp.getPropertyInt("entitygroups.numentitygroups");
		for (int i=0;i<numGroups;i++) {
			String oCountPath = "entitygroups."+i+".numentities";
			clsEntitySelector oEntitySelector = new clsEntitySelector(poProp.getPropertyInt(oCountPath), poProp.getProperty("entitygroups."+i+".entitygrouptype"), oCountPath, poProp );
			oEntitySelector.setSize(400, oEntitySelector.getSize().height);
			add( oEntitySelector);
		}
		
	    pack();
	    
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		//setVisible(true);
/*		
	    addKeyListener(new KeyAdapter() {
		    @Override
			public void keyPressed(KeyEvent e)
			    {
			    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			    	dispose();
			    }
		    }
	    });
*/	    
	    setVisible(true); 
	}

	class clsEntitySelector extends JPanel implements ActionListener {
		
		/**
		 * DOCUMENT (deutsch) - insert description 
		 * 
		 * @author deutsch
		 * 23.09.2009, 10:20:08
		 */
		private static final long serialVersionUID = -294815264176233253L;
		private int mnInitCount;
		private int mnCount;
		private clsBWProperties moProp;
		private JLabel moCountLabel;
		private String moCountPath;
	
		public clsEntitySelector(int pnCount, String poEntityName, String poCountPath, clsBWProperties oProp) {
			
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
}
