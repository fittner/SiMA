package sim;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class clsSimStartMainGUI extends javax.swing.JFrame {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 21.04.2011, 01:21:54
	 */
	private static final long serialVersionUID = -4471548653304423566L;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JButton cancelButton;
	private JButton okayButton;
	private JButton oButtonEntitySelector;
	private JPanel jPanel1;
	private AbstractAction abstractAction1;
	private JButton oButtonScenario02;
	private JButton oButtonScenario01;
	private JPanel oJPanelScenario;
	private JLabel oScenarioDescription;
	private JLabel oScenarioImage;
	private JLabel oScenarioTitle;
//	private AbstractAction actionCancel;
	private JMenuItem oMenuItemLoad;
	private JMenu jMenu1;
	private JMenuBar oMenuBar;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				clsSimStartMainGUI inst = new clsSimStartMainGUI();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public clsSimStartMainGUI() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout(getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				oMenuBar = new JMenuBar();
				setJMenuBar(oMenuBar);
				{
					jMenu1 = new JMenu();
					oMenuBar.add(jMenu1);
					jMenu1.setText("File");
					{
						oMenuItemLoad = new JMenuItem();
						jMenu1.add(oMenuItemLoad);
						oMenuItemLoad.setText("Load");
					}
				}
			}
			{
				cancelButton = new JButton();
				cancelButton.setText("Cancel");

			}
			{
				okayButton = new JButton();
				okayButton.setText("Start Scenario");
				okayButton.setFont(new java.awt.Font("Segoe UI",1,12));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(getJPanel1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
				    .addComponent(getJPanel1x(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(cancelButton, GroupLayout.Alignment.BASELINE, 0, 51, Short.MAX_VALUE)
				    .addComponent(getOButtonEntitySelector(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
				    .addComponent(okayButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
				.addGap(6));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(getJPanel1x(), GroupLayout.PREFERRED_SIZE, 470, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 6, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(0, 360, Short.MAX_VALUE)
				        .addComponent(getOButtonEntitySelector(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(getJPanel1(), 0, 181, Short.MAX_VALUE)
				        .addGap(10))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(okayButton, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, GroupLayout.PREFERRED_SIZE)
				        .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
				.addGap(6));
			pack();
			this.setSize(701, 448);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private AbstractAction getAbstractAction1() {
		if(abstractAction1 == null) {
			abstractAction1 = new AbstractAction("abstractAction1", null) {
				/**
				 * DOCUMENT (deutsch) - insert description 
				 * 
				 * @author deutsch
				 * 21.04.2011, 01:22:04
				 */
				private static final long serialVersionUID = -3931139416049510458L;

				@Override
				public void actionPerformed(ActionEvent evt) {
					
				}
			};
		}
		return abstractAction1;
	}
	
	private JLabel getOScenarioTitle() {
		if(oScenarioTitle == null) {
			oScenarioTitle = new JLabel();
			oScenarioTitle.setText("Title");
		}
		return oScenarioTitle;
	}
	
	private JLabel getOScenarioImage() {
		if(oScenarioImage == null) {
			oScenarioImage = new JLabel();
			oScenarioImage.setText("Image");
			oScenarioImage.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
		}
		return oScenarioImage;
	}
	
	private JLabel getOScenarioDescription() {
		if(oScenarioDescription == null) {
			oScenarioDescription = new JLabel();
			oScenarioDescription.setText("Description");
			oScenarioDescription.setBackground(new java.awt.Color(255,255,128));
		}
		return oScenarioDescription;
	}
	
	private JPanel getJPanel1() {
		if(oJPanelScenario == null) {
			oJPanelScenario = new JPanel();
			GroupLayout jPanel1Layout = new GroupLayout(oJPanelScenario);
			oJPanelScenario.setLayout(jPanel1Layout);
			oJPanelScenario.setBorder(BorderFactory.createTitledBorder("info"));
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				    .addComponent(getOScenarioTitle(), GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				    .addComponent(getOScenarioImage(), 0, 90, Short.MAX_VALUE))
				.addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				    .addGap(7)
				    .addComponent(getOScenarioDescription(), 0, 147, Short.MAX_VALUE)
				    .addContainerGap()));
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
				        .addComponent(getOScenarioTitle(), GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
				        .addGap(16))
				    .addComponent(getOScenarioImage(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
				.addComponent(getOScenarioDescription(), 0, 186, Short.MAX_VALUE)
				.addContainerGap());
		}
		return oJPanelScenario;
	}
	
	private JButton getOButtonScenario01() {
		if(oButtonScenario01 == null) {
			oButtonScenario01 = new JButton();
			oButtonScenario01.setText("Scenario01");
		}
		return oButtonScenario01;
	}
	
	private JButton getJButton1() {
		if(oButtonScenario02 == null) {
			oButtonScenario02 = new JButton();
			oButtonScenario02.setText("Scenario02");
		}
		return oButtonScenario02;
	}
	
	private JPanel getJPanel1x() {
		if(jPanel1 == null) {
			jPanel1 = new JPanel();
			GroupLayout jPanel1Layout1 = new GroupLayout(jPanel1);
			//GroupLayout jPanel1Layout1 = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout1);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Scenarios", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
			jPanel1Layout1.setHorizontalGroup(jPanel1Layout1.createSequentialGroup()
				.addComponent(getOButtonScenario01(), GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(getJButton1(), GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(249, Short.MAX_VALUE));
			jPanel1Layout1.setVerticalGroup(jPanel1Layout1.createSequentialGroup()
				.addGroup(jPanel1Layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(getOButtonScenario01(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
				    .addComponent(getJButton1(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(219, 219));
		}
		return jPanel1;
	}
	
	private JButton getOButtonEntitySelector() {
		if(oButtonEntitySelector == null) {
			oButtonEntitySelector = new JButton();
			oButtonEntitySelector.setText("Start Entity Selector");
		}
		return oButtonEntitySelector;
	}

}
