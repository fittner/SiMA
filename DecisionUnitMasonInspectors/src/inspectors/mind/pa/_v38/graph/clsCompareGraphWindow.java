/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;



import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.demo.JGraphLayoutMorphingManager;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;



import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

import sim.portrayal.Inspector;
import statictools.clsGetARSPath;


/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 11, 2012, 4:21:44 PM
 * 
 */
public abstract class clsCompareGraphWindow extends Inspector {

	/** DOCUMENT (herret) - insert description; @since Sep 12, 2012 11:32:26 AM */
	private static final long serialVersionUID = 5137462354912020126L;
	
	
	//class members...
	protected clsCompareGraph moGraphInput;
	protected clsCompareGraph moGraphOutput;
	private JTaskPane moTaskPane = new JTaskPane();
	private JLabel moLabelStatusBar = new JLabel("Status...");


	private boolean moAutoUpdate = false;
	private int moStepCounter = 0; //counter for the automatic interval updating
	protected static int mnAutomaticUpdateInterval = 20;
	
	private JScrollPane oGraphInputScrollPane;
	private JScrollPane oGraphOutputScrollPane;
	
	private JSplitPane oSplitPaneGraph;
		
	//colors for all datatypes, used in clsMeshBase:
	protected static final Color moColorTP = new Color(0xff99FF33); //light green
	protected static final Color moColorNULL = new Color(0xff222222); // dark dark grey
	protected static final Color moColorString = Color.WHITE;
	protected static final Color moColorDouble = Color.WHITE;
	protected static final Color moColorWP = new Color(0xff6699FF); //light blue
	protected static final Color moColorDD = new Color(0xffCC66CC);  //Drive Demand, lavendel
	protected static final Color moColorACT = new Color(0xffFF9933); //brown
	protected static final Color moColorPairRoot = new Color(0xffFFCC00); //light orange
	protected static final Color moColorPair = new Color(0xffFFCC00);
	protected static final Color moColorTrippleRoot = new Color(0xffffFF99); //light yellow
	protected static final Color moColorTripple = new Color(0xffffFF99);
	protected static final Color moColorRoot = Color.GRAY;
	protected static final Color moColorPrimaryDataStructureContainer = new Color(0xff99CC33); //dark green
	protected static final Color moColorSecondaryDataStructureContainer = new Color(0xff3366CC);
	protected static final Color moColorDMRoot = new Color(0xffff0066); //pinkish red
	protected static final Color moColorTPMRoot = new Color(0xff99CC33); //dark green
	protected static final Color moColorTI = new Color(0xffFF9933); //brown
	protected static final Color moColorWPMRoot = new Color(0xff1874CD); //dark blue


    /**
     * Constructor of the class. Creates the panel, buttons etc. 
     * 
     * @author muchitsch
     * 04.08.2010, 16:56:58
     *
     * @param originalInspector
     * @param wrapper
     * @param guiState
     * @param poModuleContainer eg moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives
     * @param poModuleMemoryMemberName 
     */
    public clsCompareGraphWindow()  {
    	initializePanel();	//put all components on the panel	
		
    }
    
 

    private void initializePanel()
    {
    	setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		moGraphInput  = new clsCompareGraph();
		moGraphOutput  = new clsCompareGraph();
		ComponentListener compList = new ComponentListener() {
			@Override
			public void componentMoved(ComponentEvent arg0) {/* do nothing */}
			@Override
			public void componentShown(ComponentEvent arg0) {/* do nothing */}
			@Override
			public void componentHidden(ComponentEvent e) {/* do nothing */}
			@Override
			public void componentResized(ComponentEvent arg0) {
				oSplitPaneGraph.setDividerLocation((getSize().width-150)/2);
				
			}
	      };
		addComponentListener(compList);
		
		// === LAYOUT ===
		// ADD TaskPaneGroup for Layout
		JTaskPaneGroup oTaskGroupLayout = new JTaskPaneGroup();
		moTaskPane.add(addTaskPaneLayout(oTaskGroupLayout));
		
		// === Displayed Data ===
		// ADD TaskPaneGroup for displayed Data
		JTaskPaneGroup oTaskGroupDisplayedData = new JTaskPaneGroup();
		moTaskPane.add(addTaskPaneDisplayedData(oTaskGroupDisplayedData));
		
		// === COMMAND ===
		// ADD TaskPaneGroup for Commands
		JTaskPaneGroup oTaskGroupCommands = new JTaskPaneGroup();
		moTaskPane.add( addTaskPaneCommands(oTaskGroupCommands) );
		
		// === FILTER ===
		// ADD TaskPaneGroup for Filters
		JTaskPaneGroup oTaskGroupFilter = new JTaskPaneGroup();
		moTaskPane.add( addTaskPaneFilter(oTaskGroupFilter) );
		
		// === SEARCH ===
		// ADD TaskPaneGroup for Search
		JTaskPaneGroup oTaskGroupSearch = new JTaskPaneGroup();
		moTaskPane.add( addTaskPaneSearch(oTaskGroupSearch ));
		
		// === LEGEND ===
		// ADD TaskPaneGroup for Legend
		JTaskPaneGroup oTaskGroupLegend = new JTaskPaneGroup();
		moTaskPane.add( addTaskPaneLegend(oTaskGroupLegend) );
		
		//create the SplitPane and add the two windows
		JScrollPane oMenuScrollPane = new JScrollPane(moTaskPane);
		oGraphInputScrollPane = new JScrollPane(moGraphInput);
		oGraphOutputScrollPane = new JScrollPane(moGraphOutput);
		
		//InputScrollPane Listener
		AdjustmentListener adjustmentListenerISP = new AdjustmentListener() {
	      	@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				if(moGraphInput.linked()){
					oGraphOutputScrollPane.getVerticalScrollBar().setValue(arg0.getValue());
				}
				
			}
	      };
		
		oGraphInputScrollPane.getVerticalScrollBar().addAdjustmentListener(adjustmentListenerISP);
		
		//OutputScrollPane Listener
		AdjustmentListener adjustmentListenerOSP = new AdjustmentListener() {
	      	@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				if(moGraphOutput.linked()){
					oGraphInputScrollPane.getVerticalScrollBar().setValue(arg0.getValue());
				}
				
			}
	      };
		
		oGraphOutputScrollPane.getVerticalScrollBar().addAdjustmentListener(adjustmentListenerOSP);
		
		
        moGraphInput.setUseSimpleView(true);
        moGraphOutput.setUseSimpleView(true);
		
		
		// Adds the split pane
		oSplitPaneGraph = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,  oGraphInputScrollPane, oGraphOutputScrollPane);
		
		
		//JScrollPane oGraphScrollPane = new JScrollPane(oSplitPaneGraph);
		
		oSplitPaneGraph.setDividerLocation((getSize().width-150)/2);
		JSplitPane oSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, oMenuScrollPane, oSplitPaneGraph);
		oSplitPane.setDividerLocation(150);
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		oMenuScrollPane.setMinimumSize(minimumSize);
		oSplitPaneGraph.setMinimumSize(minimumSize);
		
		//add the SplitPane to the Inspector (final magic)
    	this.add(oSplitPane, BorderLayout.CENTER);
    	
    	// Adds the status bar at bottom (final final)
		moLabelStatusBar.setText(JGraphLayout.VERSION  +  " - Graph Memory Inspector");
		moLabelStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		moLabelStatusBar.setFont(moLabelStatusBar.getFont().deriveFont(Font.PLAIN));
		this.add(moLabelStatusBar, BorderLayout.SOUTH);
		
		moGraphInput.setLinkedPartner(moGraphOutput);
		moGraphOutput.setLinkedPartner(moGraphInput);
		
    }
    
    protected abstract void updateInspectorData();
  
    
    
    /**
     * Adds a Color&Icon legend to the TaskPane. This needs to be altered when new datatypes are added.
     *
     * @author muchitsch
     * 07.06.2011, 14:41:43
     *
     * @param poTaskGroup
     * @return poTaskGroup
     */
    private JTaskPaneGroup addTaskPaneLegend(JTaskPaneGroup poTaskGroup){
    	poTaskGroup.setTitle("Legend");
    	poTaskGroup.setExpanded(false);
 
    	poTaskGroup.add(addLegendItem( "TP", moColorTP, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "NULL", moColorNULL, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "String", moColorString, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "Double", moColorDouble, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "WP", moColorWP, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "WPM", moColorWPMRoot, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "DD", moColorDD, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "ACT", moColorACT, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "Pair", moColorPair, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "Tripple", moColorTripple, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "PDSC", moColorPrimaryDataStructureContainer, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "SDSC", moColorSecondaryDataStructureContainer, "/World/src/resources/images/view.png") );
    	poTaskGroup.add(addLegendItem( "DM root", moColorDMRoot, "/World/src/resources/images/view.png") );
     	
    	return poTaskGroup;
    }
    
    /**
     * adds a single legend item in the form of a JLabel and a Icon is called by @see #method addTaskPaneLegend
     *
     * @author muchitsch
     * 07.06.2011, 14:43:31
     *
     * @param poText
     * @param poColor
     * @param poImagePath
     * @return JLabel item
     */
    private JLabel addLegendItem(String poText, Color poColor, String poImagePath){
     	String oFullImagePath = clsGetARSPath.getArsPath()+poImagePath;
    	ImageIcon oIcon = new ImageIcon(oFullImagePath, poText);

     	JLabel oLegendLabel = new JLabel(poText, oIcon, JLabel.LEFT);
     	oLegendLabel.setBackground(poColor);
     	oLegendLabel.setOpaque(true);
     	oLegendLabel.setForeground(Color.BLACK);
    	return oLegendLabel;
    }
    
    private JTaskPaneGroup addTaskPaneSearch(JTaskPaneGroup poTaskGroup){
    	poTaskGroup.setTitle("Search");
    	poTaskGroup.setExpanded(false);
    	//fill me with wisdom
    	return poTaskGroup;
    }
    
    private JTaskPaneGroup addTaskPaneFilter(JTaskPaneGroup poTaskGroup){
    	poTaskGroup.setTitle("Filter");
    	poTaskGroup.setExpanded(false);
    	//fill me with wisdom
    	return poTaskGroup;
    }
   
    private JTaskPaneGroup addTaskPaneLayout(JTaskPaneGroup poTaskGroup){
		  poTaskGroup.setTitle("Graph Layout");
		 
		  poTaskGroup.add(new AbstractAction("Tree") {
	
			private static final long serialVersionUID = 1504821733263092999L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphTreeLayout layout = new JGraphTreeLayout();
				layout.setOrientation(SwingConstants.WEST);
				
				moGraphInput.performGraphLayoutChange(layout);
				moGraphOutput.performGraphLayoutChange(layout);
			}
		});
		  
		  poTaskGroup.add(new AbstractAction("Compact Tree") {
				
				private static final long serialVersionUID = -2811903925630396473L;
		
				@Override
				public void actionPerformed(ActionEvent e) {
					JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
					layout.setOrientation(SwingConstants.WEST);
					moGraphInput.performGraphLayoutChange(layout);
					moGraphOutput.performGraphLayoutChange(layout);
				}
			});
		  
		  
		  poTaskGroup.add(new AbstractAction("Hierarchical") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphHierarchicalLayout layout = new JGraphHierarchicalLayout();
				layout.setOrientation(SwingConstants.WEST);
				moGraphInput.performGraphLayoutChange(layout);
				moGraphOutput.performGraphLayoutChange(layout);
			}
		});
		  poTaskGroup.add(new AbstractAction("Fast Organic") {
	
			private static final long serialVersionUID = 6447974553914052766L;
	
			@Override
			public void actionPerformed(ActionEvent e) {	
				moGraphInput.performGraphLayoutChange( new JGraphFastOrganicLayout());
				moGraphOutput.performGraphLayoutChange( new JGraphFastOrganicLayout());
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Simple Circle") {
	
			private static final long serialVersionUID = -1444623169928884258L;
	
			@Override
			public void actionPerformed(ActionEvent e) {		
				moGraphInput.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
				moGraphOutput.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Simple Tilt") {
	
			private static final long serialVersionUID = -6513607636960953397L;
	
			@Override
			public void actionPerformed(ActionEvent e) {			
				moGraphInput.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_TILT));
				moGraphOutput.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_TILT));
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Radialtree") {
	
			private static final long serialVersionUID = -7537382541569261175L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphRadialTreeLayout layout = new JGraphRadialTreeLayout();
				moGraphInput.performGraphLayoutChange(layout);
				moGraphOutput.performGraphLayoutChange(layout);
			}
		});

		  
		  
  return poTaskGroup;
}
    
    
    private JTaskPaneGroup addTaskPaneDisplayedData(JTaskPaneGroup poTaskGroup){
		  poTaskGroup.setTitle("Displayed Data");
		  
		  		//Simple View CheckBox
			javax.swing.JCheckBox oSimpleViewCB = new javax.swing.JCheckBox("Simple View");
			oSimpleViewCB.setSelected(true);
		    ActionListener actionListener = new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          moGraphInput.setUseSimpleView(abstractButton.getModel().isSelected());
		          moGraphOutput.setUseSimpleView(abstractButton.getModel().isSelected());
		          updateGraphes();
		        }
		      };
		      
		      oSimpleViewCB.addActionListener(actionListener);
		      poTaskGroup.add(oSimpleViewCB);
			      
			    //Define Checkboxes to use in oIntern
				final javax.swing.JCheckBox oExternAndIntern1 = new  javax.swing.JCheckBox("Interne 1st Level");
				final javax.swing.JCheckBox oExternAndInternBest = new  javax.swing.JCheckBox("Interne Best");
				final javax.swing.JCheckBox oExtern = new  javax.swing.JCheckBox("Externe Associations");
			   
				
				
				//Checkbox Buttons intern Assoziations
			   final javax.swing.JCheckBox oIntern = new  javax.swing.JCheckBox("Interne Associations");

			   
			   oIntern.setSelected(true);
			   
			    ActionListener actionListenerCBI= new ActionListener() {
			        @Override
					public void actionPerformed(ActionEvent actionEvent) {
			          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();			          	
			          if(abstractButton.getModel().isSelected()){
				          moGraphInput.showInternAssoc(true);
				          moGraphOutput.showInternAssoc(true);
				          oExternAndIntern1.setEnabled(false);
				          oExternAndInternBest.setEnabled(false);
			          }
			          else{
				          moGraphInput.showInternAssoc(false);
				          moGraphOutput.showInternAssoc(false);
				          if(oExtern.isSelected()){
				        	  oExternAndIntern1.setEnabled(true);
				        	  oExternAndInternBest.setEnabled(true);
				          }else{
				        	  oExternAndIntern1.setEnabled(false);
				        	  oExternAndInternBest.setEnabled(false);
				          }
				         
			          }
			          updateGraphes();
			        }
			      };
			      oIntern.addActionListener(actionListenerCBI);
			      poTaskGroup.add(oIntern);
			      
				   //Checkbox Buttons externe Assoziations			      
				   
				   
				   oExtern.setSelected(false);
				   
				    ActionListener actionListenerCBE= new ActionListener() {
				        @Override
						public void actionPerformed(ActionEvent actionEvent) {
				          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();			          	
				          
				          if(abstractButton.getModel().isSelected()){
					          moGraphInput.showExternAssoc(true);
					          moGraphOutput.showExternAssoc(true);
					          oExternAndIntern1.setEnabled(true);
					          oExternAndInternBest.setEnabled(true);
				          }
				          else{
					          moGraphInput.showExternAssoc(false);
					          moGraphOutput.showExternAssoc(false);
					          oExternAndIntern1.setEnabled(false);
					          oExternAndInternBest.setEnabled(false);
				          }
				          updateGraphes();
				        }
				      };
				      oExtern.addActionListener(actionListenerCBE);
				      poTaskGroup.add(oExtern);
				      
				      
					   //Checkbox  oExternAndIntern1	      
					  
					   oExternAndIntern1.setSelected(false);
					   oExternAndIntern1.setEnabled(false);
					   
					    ActionListener actionListenerCBEAI1= new ActionListener() {
					        @Override
							public void actionPerformed(ActionEvent actionEvent) {
					          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();			          	
					          
					          if(abstractButton.getModel().isSelected()){
						          moGraphInput.showInternAssocFirstLevel(true);
						          moGraphOutput.showInternAssocFirstLevel(true);
					          }
					          else{
						          moGraphInput.showInternAssocFirstLevel(false);
						          moGraphOutput.showInternAssocFirstLevel(false);
					        	  
					          }
					          updateGraphes();
					        }
					      };
					      oExternAndIntern1.addActionListener(actionListenerCBEAI1);
					      poTaskGroup.add(oExternAndIntern1);
				      
				      
						   //Checkbox  oExternAndInternBest	      
						  
					      oExternAndInternBest.setSelected(false);
					      oExternAndInternBest.setEnabled(false);
						   
						    ActionListener actionListenerCBEAIB= new ActionListener() {
						        @Override
								public void actionPerformed(ActionEvent actionEvent) {
						          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();			          	
						          
						          if(abstractButton.getModel().isSelected()){
							          moGraphInput.showInternAssocBest(true);
							          moGraphOutput.showInternAssocBest(true);
						          }
						          else{
							          moGraphInput.showInternAssocBest(false);
							          moGraphOutput.showInternAssocBest(false);						        	  
						          }
						          updateGraphes();
						        }
						      };
						      oExternAndInternBest.addActionListener(actionListenerCBEAIB);
						      poTaskGroup.add(oExternAndInternBest);
			   
				      poTaskGroup.add(new AbstractAction("Expand Intern") {
				    							
						private static final long serialVersionUID = 8703769545272229866L;

							@Override
							public void actionPerformed(ActionEvent e) {
								Object cellsInput[] = moGraphInput.getSelectionCells();
								Object cellsOutput[] = moGraphOutput.getSelectionCells();
								
								for(int i =0; i<cellsOutput.length;i++){
									moGraphOutput.expandGraphCellInternAssoc((clsGraphCell) cellsOutput[i]);
								}
								if(cellsOutput.length>0) moGraphOutput.redraw();
								
								for(int i =0; i<cellsInput.length;i++){
									moGraphInput.expandGraphCellInternAssoc((clsGraphCell) cellsInput[i]);
								}
								if(cellsInput.length>0) moGraphInput.redraw();
								
							}
						});
				      
				      poTaskGroup.add(new AbstractAction("Expand Extern") {
							
						private static final long serialVersionUID = 8703769545272229866L;

							@Override
							public void actionPerformed(ActionEvent e) {
								Object cellsInput[] = moGraphInput.getSelectionCells();
								Object cellsOutput[] = moGraphOutput.getSelectionCells();
								
								for(int i =0; i<cellsOutput.length;i++){
									moGraphOutput.expandGraphCellExternAssoc((clsGraphCell) cellsOutput[i]);
								}
								if(cellsOutput.length>0) moGraphOutput.redraw();
								
								for(int i =0; i<cellsInput.length;i++){
									moGraphInput.expandGraphCellExternAssoc((clsGraphCell) cellsInput[i]);
								}
								if(cellsInput.length>0) moGraphInput.redraw();
								
							}
						});
				      
	      
	  return poTaskGroup;
}
    
    private JTaskPaneGroup addTaskPaneCommands(JTaskPaneGroup poTaskGroup){
      	poTaskGroup.setTitle("Commands");
		
    	poTaskGroup.add(new AbstractAction("Updata Data") {
			private static final long serialVersionUID = -7683571637499420675L;
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGraphes();
				//moGraphInput.updateControl();
				//moGraphOutput.updateControl();
			}
		});
    	// TODO: Auto Update doesn't work correctly
    	/*		
		javax.swing.JCheckBox oAutoUpdateCB = new javax.swing.JCheckBox("Auto Update");
		
		    ActionListener actionListener = new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          moAutoUpdate = abstractButton.getModel().isSelected();
		        }
		      };
		      oAutoUpdateCB.addActionListener(actionListener);
		      poTaskGroup.add(oAutoUpdateCB);
*/			
		      	//link Checkbox
				javax.swing.JCheckBox oLinkCB = new javax.swing.JCheckBox("Link Input and Output Graphes");
				oLinkCB.setSelected(true);
				
			    ActionListener actionListenerLCB = new ActionListener() {
			        @Override
					public void actionPerformed(ActionEvent actionEvent) {
			          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();			          	
			          moGraphInput.setLinked(abstractButton.getModel().isSelected());
			          moGraphOutput.setLinked(abstractButton.getModel().isSelected());
			      		if(abstractButton.getModel().isSelected()){
			      			moGraphOutput.setScale(moGraphInput.getScale());
			      			updateGraphes();
			      		}

			        }
			      };
			      oLinkCB.addActionListener(actionListenerLCB);
			      poTaskGroup.add(oLinkCB);
		      
		      
		      
		      poTaskGroup.add(new AbstractAction("Actual Size") {
			private static final long serialVersionUID = -7683571637499420675L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraphInput.setScale(1);
				moGraphOutput.setScale(1);
			}
		});
		       
		      poTaskGroup.add(new AbstractAction("Fit Window") {
			private static final long serialVersionUID = -4236402393050941924L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphLayoutMorphingManager.fitViewport(moGraphInput);
				JGraphLayoutMorphingManager.fitViewport(moGraphOutput);
			}
		});
		      poTaskGroup.add(new AbstractAction("Zoom In") {
			private static final long serialVersionUID = 4963188240381232166L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraphInput.setScale(1.5 * moGraphInput.getScale());
				moGraphOutput.setScale(1.5 * moGraphOutput.getScale());
			}
		});
		      poTaskGroup.add(new AbstractAction("Zoom Out") {
			private static final long serialVersionUID = 6518488789619229086L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraphInput.setScale(moGraphOutput.getScale() / 1.5);
				moGraphOutput.setScale(moGraphOutput.getScale() / 1.5);
			}
		});
		      poTaskGroup.add(new AbstractAction("Reset") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				moGraphInput.reset();
				moGraphOutput.reset();
			}
		});
		      poTaskGroup.add(new AbstractAction("Group") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				//groupCells(moGraph.getSelectionCells());
			}
		});
		      poTaskGroup.add(new AbstractAction("UnGroup") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				//ungroupCells(moGraph.getSelectionCells());
			}
		});
		
    	return poTaskGroup;
    }


	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 4:24:00 PM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		if(moAutoUpdate && moStepCounter > mnAutomaticUpdateInterval)
		{
			moStepCounter = 0;
			updateGraphes();
		}
		else
		{
			moStepCounter++;
		}
	}
	
	protected void updateGraphes(){
		updateInspectorData();
		moGraphInput.updateControl();
		moGraphOutput.updateControl();
		//moGraphInput.performGraphLayoutChange(actualLayout);
		//moGraphOutput.performGraphLayoutChange(actualLayout);
	}

}
