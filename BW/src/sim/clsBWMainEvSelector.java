/**
 * @author andi
 * 16.07.2009, 11:17:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

package sim;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



/**
 * simple GUI to choose between different test-environments
 * new test-environments have to be manually added at clsBWMain::start()  
 * 
 * @author andi
 * 16.07.2009, 11:17:25
 * @deprecated
 */


public class clsBWMainEvSelector implements ActionListener {

	/**
	 * GUI - wrapper class
	 *
	 * @author andi
	 * 16.07.2009, 11:17:25
	 * 
	 */
	
	private JFrame 			frame;
	private JButton 		startBtn;
	private TextField 		txtSelectedEnv;

	
	/**
	 * 
	 * constructs the GUI for choosing an environment  
	 * 
	 * @author andi
	 * 21.07.2009, 08:43:48
	 *
	 */
	public clsBWMainEvSelector() {
		
/*		
 * 		pop-up version -> very restricted respectively to layout reasons

  		frame = new JFrame("select BW-Environment");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100, 300);

		
		startBtn = new JButton("start");
		startBtn.addActionListener(this);
		
		txtSelectedEnv = new TextField("1", 30);

		
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(txtSelectedEnv);
		panel.add(startBtn);		
		
		panel.setMinimumSize(new Dimension(400, 400));
		
		factory = PopupFactory.getSharedInstance();
		popup = factory.getPopup(frame, panel, 300, 100);
		popup.show();
		

		frame.setVisible(true);
	*/

		JLabel infoText1 = new JLabel("use number or string (eg. \"0\") to start your desired test-environment.");
		JLabel infoText2 = new JLabel("new test-environments can be added manually at clsBWMain::start()");
		
		startBtn = new JButton("start");
		startBtn.addActionListener(this);
		txtSelectedEnv = new TextField("0", 30);
		
		frame = new JFrame("BW Enviroment Selector");

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		contentPane.setMaximumSize(new Dimension(200, 200));
		contentPane.add(infoText1);
		contentPane.add(infoText2);
		contentPane.add(txtSelectedEnv);
		contentPane.add(startBtn);
		frame.setContentPane(contentPane);
		
		frame.setMaximumSize(new Dimension(200, 200));
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		// TODO (andi) - Auto-generated method stub
		clsBWMainEvSelector oUI = new clsBWMainEvSelector();
	}

	/**
	 * 
	 * starts the test-envrionment GUI and closes the selection-GUI (this class)
	 * 
	 */
	public void actionPerformed(ActionEvent event) {
		
		String val = txtSelectedEnv.getText();
		String[] args = {val};
		clsBWMainWithUI.main(args);
		frame.dispose();

	}

	   
}
