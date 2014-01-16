/**
 * CHANGELOG
 *
 * 04.07.2013 herret - File created
 *
 */
package mind.autocreated;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import config.personality_parameter.clsPersonalityParameter;
import config.personality_parameter.clsPersonalityParameterContainer;
import config.personality_parameter.clsPersonalityParameterModule;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 04.07.2013, 13:05:44
 * 
 */
public abstract class clsParameterInspector extends Inspector {


	private final int COUNT_COLUMNS = 2;
	protected static final long serialVersionUID = 3014683271944201987L;
	protected clsPersonalityParameterContainer moPPS;
	

	
	protected abstract void updateData();
	
	/* (non-Javadoc)
	 *
	 * @since 19.11.2012 10:22:11
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		updateData();
		drawParamters();
		
	}


	/**
	 * DOCUMENT (LuHe) - insert description
	 *
	 * @since 19.11.2012 10:22:42
	 *
	 */
	private void drawParamters() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JLabel header = new JLabel("Selected Personality Parameter File: "+ moPPS.getMoFilename());
		topPanel.add(header,BorderLayout.PAGE_START);
		JPanel oPanel = new JPanel();
		topPanel.add(oPanel,BorderLayout.CENTER);
		ArrayList<clsPersonalityParameterModule> oModules =moPPS.getAllModule();
		ArrayList<JPanel> oVerticalPanels = new ArrayList<JPanel>();
		oPanel.setLayout(new BoxLayout(oPanel,BoxLayout.X_AXIS));
		
		for (int i= 0;i<COUNT_COLUMNS;i++){
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			oVerticalPanels.add(panel);
			oPanel.add(panel);
		}
		int cnt=0;
		for (clsPersonalityParameterModule oModule :oModules){
			JPanel container = new JPanel();
			TitledBorder border = BorderFactory.createTitledBorder(oModule.getMoModuleNumber());
			border.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			container.setBorder(border);
			
			ArrayList<clsPersonalityParameter> parameters = oModule.getAllParamaters();
			container.setLayout(new GridLayout(parameters.size(),2));
			
			for(clsPersonalityParameter parameter :parameters){
				JLabel lblName = new JLabel(parameter.getName());
				lblName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
				lblName.setToolTipText(parameter.getMoDescription());
				
				JLabel lblValue = new JLabel("        "+parameter.getParameter());
				
				container.add(lblName);
				container.add(lblValue);
				
			}
			//oPanel.add(container);
			oVerticalPanels.get(cnt).add(container);
			if(cnt>=oVerticalPanels.size()-1)cnt=0;
			else cnt ++;
		}

		JScrollPane oScrollPane= new JScrollPane(topPanel);

		this.setLayout(new BorderLayout());
		this.add(oScrollPane, BorderLayout.CENTER);
	}

}
