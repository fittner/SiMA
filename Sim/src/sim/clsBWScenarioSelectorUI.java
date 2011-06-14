/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * clsBWScenarioSelectorUI.java
 *
 * Created on 08.06.2011, 12:27:43
 */
package sim;


import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import config.clsBWProperties;
import statictools.clsGetARSPath;

/**
 * This class can act as a Main function for Simulation. This adds the 
 * functionality to select from different scenarios and then calls
 * the clsBMMain class.
 * You don not need to use this. you can also call clsBWMainWithUI directly.
 * This is just a neat way to start the simulation!
 * @author muchitsch
 */
public class clsBWScenarioSelectorUI extends javax.swing.JFrame {

    /**
	 * 
	 * @author muchitsch
	 * 08.06.2011, 17:26:02
	 */
	private static final long serialVersionUID = -1592720371723582306L;
	/** Creates new form clsBWScenarioSelectorUI */
    public clsBWScenarioSelectorUI() {
        initComponents();
        FillScenarioList();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlScenarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstScenarioList = new javax.swing.JList();
        pnlDescription = new javax.swing.JPanel();
        lblScenatioTitle = new javax.swing.JLabel();
        scrLongDescription = new javax.swing.JScrollPane();
        txaLongDescription = new javax.swing.JTextArea();
        lblScreenshot = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaShortDescription = new javax.swing.JTextArea();
        lblFieldSize = new javax.swing.JLabel();
        lblFilename = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnStartScenario = new javax.swing.JButton();
        chkAutostart = new javax.swing.JCheckBox();
        btnStartWithAdaper = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlScenarios.setBorder(javax.swing.BorderFactory.createTitledBorder("Scenario"));

        lstScenarioList.setModel(new javax.swing.AbstractListModel() {

			private static final long serialVersionUID = -1008339432610914271L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            @Override
			public int getSize() { return strings.length; }
            @Override
			public Object getElementAt(int i) { return strings[i]; }
        });
        lstScenarioList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstScenarioList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstScenarioListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstScenarioList);

        javax.swing.GroupLayout pnlScenariosLayout = new javax.swing.GroupLayout(pnlScenarios);
        pnlScenarios.setLayout(pnlScenariosLayout);
        pnlScenariosLayout.setHorizontalGroup(
            pnlScenariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScenariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3, Short.MAX_VALUE))
        );
        pnlScenariosLayout.setVerticalGroup(
            pnlScenariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScenariosLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlDescription.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));

        lblScenatioTitle.setText("Scenario Title:");

        txaLongDescription.setColumns(20);
        txaLongDescription.setEditable(false);
        txaLongDescription.setLineWrap(true);
        txaLongDescription.setRows(5);
        txaLongDescription.setText("long description sadas sad sad asd as d asd as d asd asdasdas dsa d asd asd");
        scrLongDescription.setViewportView(txaLongDescription);

        lblScreenshot.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblScreenshot.setMaximumSize(new java.awt.Dimension(125, 125));

        txaShortDescription.setColumns(20);
        txaShortDescription.setLineWrap(true);
        txaShortDescription.setRows(5);
        txaShortDescription.setText("short description  asd asd asd as d asd as d assadasd   sad sadasdasdsad   asd asd as ddasdasd");
        jScrollPane2.setViewportView(txaShortDescription);

        lblFieldSize.setText("Field Size:");

        lblFilename.setText("Filename:");

        javax.swing.GroupLayout pnlDescriptionLayout = new javax.swing.GroupLayout(pnlDescription);
        pnlDescription.setLayout(pnlDescriptionLayout);
        pnlDescriptionLayout.setHorizontalGroup(
            pnlDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescriptionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblScenatioTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDescriptionLayout.createSequentialGroup()
                        .addComponent(lblScreenshot, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFilename)
                            .addComponent(lblFieldSize, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)))
                    .addComponent(scrLongDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlDescriptionLayout.setVerticalGroup(
            pnlDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDescriptionLayout.createSequentialGroup()
                .addComponent(lblScenatioTitle)
                .addGap(11, 11, 11)
                .addGroup(pnlDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDescriptionLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFieldSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblFilename))
                    .addComponent(lblScreenshot, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrLongDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblFilename.getAccessibleContext().setAccessibleName("Filename:");

        btnCancel.setText("Exit");
        btnCancel.setActionCommand("");
        btnCancel.setName(""); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnStartScenario.setText("Start Scenario");
        btnStartScenario.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartScenarioActionPerformed(evt);
            }
        });
        
        chkAutostart.setSelected(true);
        chkAutostart.setText("autostart");

        btnStartWithAdaper.setText("Start with Adaptor");
        btnStartWithAdaper.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartWithAdaperActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(chkAutostart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStartWithAdaper)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStartScenario, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlScenarios, javax.swing.GroupLayout.Alignment.LEADING, 0, 567, Short.MAX_VALUE)
                        .addComponent(pnlDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlScenarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartScenario, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStartWithAdaper, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(chkAutostart))
                .addGap(22, 22, 22))
        );

        btnCancel.getAccessibleContext().setAccessibleName("btnCancel");

        pack();
    }// </editor-fold>

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void btnStartScenarioActionPerformed(java.awt.event.ActionEvent evt) {
        
        int selectedIndex= lstScenarioList.getSelectedIndex();
        String[] args = new String[4];

        if(selectedIndex != -1)
        {
        	ScenarioEntry oSelectedScenarioEntry = (ScenarioEntry) lstScenarioList.getModel().getElementAt(selectedIndex);
        	
        	String val = oSelectedScenarioEntry.getFileName();
        	args[0] = "-config";
        	args[1] = val;
        	
        	if(chkAutostart.isSelected())
        	{
	        	args[2] = "-autostart";
	        	args[3] = "true";
        	}
        	else
        	{
        		args[2] = "-autostart";
	        	args[3] = "false";
        	}
        	
        	
    		clsBWMainWithUI.main(args);
    		this.dispose();
        }
               
    }
    
    private void btnStartWithAdaperActionPerformed(java.awt.event.ActionEvent evt) {
    	int selectedIndex= lstScenarioList.getSelectedIndex();

    	String[] args = new String[6];
    	
        if(selectedIndex != -1)
        {
        	ScenarioEntry oSelectedScenarioEntry = (ScenarioEntry) lstScenarioList.getModel().getElementAt(selectedIndex);

        	String val = oSelectedScenarioEntry.getFileName();

        	args[0] = "-config";
        	args[1] = val;
        	args[2] = "-adapter";
        	args[3] = "true";
        	
        	if(chkAutostart.isSelected()) {
	        	args[4] = "-autostart";
	        	args[5] = "true";
        	}
        	else {
        		args[4] = "-autostart";
	        	args[5] = "false";
        	}
        	
    		clsBWMainWithUI.main(args);
    		this.dispose();
        }
    }

    private void lstScenarioListValueChanged(javax.swing.event.ListSelectionEvent evt) {
        
        int selectedIndex= lstScenarioList.getSelectedIndex();

        if(selectedIndex != -1)
        {
    		ScenarioEntry oSelectedScenarioEntry = (ScenarioEntry) lstScenarioList.getModel().getElementAt(selectedIndex);

            lblScenatioTitle.setText("Title: " + oSelectedScenarioEntry.getName());
            txaShortDescription.setText(oSelectedScenarioEntry.getShortDescription());
            txaLongDescription.setText(oSelectedScenarioEntry.getLongDescription());
            lblFieldSize.setText("Field Size (WxH): " + oSelectedScenarioEntry.getFieldSize());
            lblFilename.setText("Filename: " + oSelectedScenarioEntry.getFileName());
            
            lblScreenshot.setText("");
            lblScreenshot.setIcon(oSelectedScenarioEntry.getScreenshotImage());
        }
   }


   private void FillScenarioList(){
       String oConfigFilePath = clsGetARSPath.getConfigPath();

       java.io.File oDir = new java.io.File(oConfigFilePath);
    
       java.io.FileFilter fileFilter = new java.io.FileFilter() {
           @Override
			public boolean accept(java.io.File file) {
        	  
               return file.isFile();
           }
       };
       
       java.io.File[] oFiles = oDir.listFiles(fileFilter);

       if (oFiles != null) {
       
           //java.util.ArrayList<String> oFilenames = new  java.util.ArrayList();
    	   
    	  ArrayList<String> oTmpFiles = new ArrayList<String>();
    	   
           for (int i=0; i<oFiles.length; i++) {
	           if(oFiles[i].getName().startsWith("pa.implementationstate.") ||
	              oFiles[i].getName().startsWith("system.")){
	           	//TODO: CM unwanted files in the config folder, could be donne in fileFilter
	           }
	           else{
	        	   oTmpFiles.add(oFiles[i].getName());
	           }
           }
           
           ScenarioEntry[] oScenarios= new ScenarioEntry[oTmpFiles.size()];
           
           for (int n=0; n<oTmpFiles.size(); n++) {
               oScenarios[n] = new ScenarioEntry(oTmpFiles.get(n));
           }
           
           lstScenarioList.setListData(oScenarios);
       }
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
			public void run() {
                new clsBWScenarioSelectorUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnStartScenario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFieldSize;
    private javax.swing.JLabel lblFilename;
    private javax.swing.JLabel lblScenatioTitle;
    private javax.swing.JLabel lblScreenshot;
    private javax.swing.JList lstScenarioList;
    private javax.swing.JPanel pnlDescription;
    private javax.swing.JPanel pnlScenarios;
    private javax.swing.JScrollPane scrLongDescription;
    private javax.swing.JTextArea txaLongDescription;
    private javax.swing.JTextArea txaShortDescription;
    private javax.swing.JButton btnStartWithAdaper;
    private javax.swing.JCheckBox chkAutostart;
    // End of variables declaration
    

}


//-------------------------------------------
class ScenarioEntry {
	  private final String moName;
	  private final String moFilename;
	  private final String moShortDescription;
	  private final String moLongDescription;
	  private final String moFieldWidth;
	  private final String moFieldHeight;
	  private ImageIcon moScreenshotImage;

	  public ScenarioEntry(String poFilename) {
	    this.moFilename = poFilename;
	    
	    String oPath = clsGetARSPath.getConfigPath();
        clsBWProperties oProp = clsBWProperties.readProperties(oPath, moFilename);
 	   
        moName = oProp.getPropertyString("title");
        moShortDescription = oProp.getPropertyString("short_description");
        moLongDescription = oProp.getPropertyString("description");
        moFieldWidth =  oProp.getPropertyString("field_width");
        moFieldHeight = oProp.getPropertyString("field_height");
              
        String oImagepath = clsGetARSPath.getConfigImagePath()+oProp.getPropertyString("image");
        
		File oFile = new File( oImagepath ); 
		if(!oFile.exists()){
			System.out.println("Image for scenario not found. Path: "+oImagepath);
		}
		
        moScreenshotImage = new ImageIcon( oImagepath , "scenario screenshot");
        Image oTMPimg = moScreenshotImage.getImage();  
        oTMPimg = oTMPimg.getScaledInstance(125, 125,  java.awt.Image.SCALE_SMOOTH);  
        moScreenshotImage = new ImageIcon(oTMPimg); 
	  }

	  public String getName() {
	    return moName;
	  }
	  
	  public String getFileName() {
		    return moFilename;
		  }
	  
	  public String getShortDescription() {
		    return moShortDescription;
		  }
	  
	  public String getLongDescription() {
		    return moLongDescription;
		  }
	  
	  public ImageIcon getScreenshotImage() {
		    return moScreenshotImage;
		  }
	  
	  public String getFieldSize() {
		    return moFieldWidth + " x " + moFieldHeight;
		  }

	  @Override
	public String toString() {
	    return moName;
	  }
	}

