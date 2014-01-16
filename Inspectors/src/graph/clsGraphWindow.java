/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
 *
 */
package graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
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
import utils.clsGetARSPath;


/**
 * Abstract window to hold one ore more graphs. Including control elements and triggers the graph update
 * 
 * @author herret
 * Sep 11, 2012, 4:21:44 PM
 * 
 */
public abstract class clsGraphWindow extends Inspector {

	/** DOCUMENT (herret) - insert description; @since Sep 12, 2012 11:32:26 AM */
	private static final long serialVersionUID = 5137462354912020126L;
	
	
	//class members...
	
	protected ArrayList<clsGraph> moGraphes;
	protected ArrayList<JSplitPane> moSplitPanes;
	protected ArrayList<JScrollPane> moScrollPanes;
	
	private JTaskPane moTaskPane = new JTaskPane();
	private JLabel moLabelStatusBar = new JLabel("Status...");
	
	protected final boolean mbOrientationVertical;


	private boolean moAutoUpdate = false;
	private int moStepCounter = 0; //counter for the automatic interval updating
	protected static int mnAutomaticUpdateInterval = 100;
	
		


    /**
     * Constructor of the class. Creates the panel
     * 
     * @author herret
     * 20.09.2012, 16:56:58
     *
     */
    public clsGraphWindow(boolean poOrtientationVertical)  {
    	mbOrientationVertical=poOrtientationVertical;
    	initializePanel();	//put all components on the panel		
    }
    
    protected abstract void createGraphes();

    private void initializePanel()
    {
    	setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		createGraphes();
		
		ComponentListener compList = new ComponentListener() {
			@Override
			public void componentMoved(ComponentEvent arg0) {/* do nothing */}
			@Override
			public void componentShown(ComponentEvent arg0) {/* do nothing */}
			@Override
			public void componentHidden(ComponentEvent e) {/* do nothing */}
			@Override
			public void componentResized(ComponentEvent arg0) {
				int countGraphes = moGraphes.size();
				if(mbOrientationVertical){
					for (JSplitPane oPane : moSplitPanes){
						oPane.setDividerLocation((getSize().width-150)/countGraphes);
					}
				}
				else {
					for (JSplitPane oPane : moSplitPanes){
						oPane.setDividerLocation((getSize().height)/countGraphes);
					}
				}
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
		
		//create ScrollPane for each Graph
		moScrollPanes = new ArrayList<JScrollPane>();
		for(int i =0; i<moGraphes.size();i++){
			JScrollPane oPane = new JScrollPane(moGraphes.get(i));
			//ScrollPane Listener
			AdjustmentListener adjustmentListenerISP = new AdjustmentListener(){
				
		      	@Override
				public void adjustmentValueChanged(AdjustmentEvent arg0) {
		      		//get adjusted Graph
		      		//get graph
		      		JScrollBar oBar = (JScrollBar) arg0.getAdjustable();
		      		JScrollPane oScrollPane=null;
		      		for(JScrollPane oPane :moScrollPanes){
		      			if(oPane.getVerticalScrollBar().equals(oBar)){
		      				oScrollPane=oPane;
		      				break;
		      			}
		      		}
					clsGraph oGraph = (clsGraph) ((Container)oScrollPane.getComponent(0)).getComponent(0);
		      		if(oGraph.linked()){
						//set value of all other graphes
						for(JScrollPane oOtherPane: moScrollPanes){
							if(!oOtherPane.equals(oScrollPane)){
								oOtherPane.getVerticalScrollBar().setValue(arg0.getValue());
							}
							
						}
		      		}
				}
		      };
			
		      oPane.getVerticalScrollBar().addAdjustmentListener(adjustmentListenerISP);

		      moScrollPanes.add(oPane);
		}
		
		//set all graphes to simpleView
		for(clsGraph oGraph: moGraphes){
			oGraph.setUseSimpleView(true);
		}
		JSplitPane oSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		oSplitPane.setTopComponent(oMenuScrollPane);
		oSplitPane.setDividerLocation(150);
		
		
		//create and set splitPanes
		int countGraphes= moScrollPanes.size();
		int i;
		if(countGraphes ==0){
			//should not be
		}
		else if (countGraphes==1){
			oSplitPane.setBottomComponent(moScrollPanes.get(0));
		}
		else if(countGraphes>1){
			for(i = 0; i< countGraphes-1;i++){
				JSplitPane oSPane = new JSplitPane(mbOrientationVertical?JSplitPane.HORIZONTAL_SPLIT:JSplitPane.VERTICAL_SPLIT);
				if(i==0){
					oSPane.setTopComponent(moScrollPanes.get(0));
				}

				else{
					moSplitPanes.get(moSplitPanes.size()-1).setBottomComponent(oSPane);
					oSPane.setTopComponent(moScrollPanes.get(i));
						
				}
				moSplitPanes.add(oSPane);
							
			}
			
			moSplitPanes.get(moSplitPanes.size()-1).setBottomComponent(moScrollPanes.get(moScrollPanes.size()-1));
			oSplitPane.setBottomComponent(moSplitPanes.get(0));
		}

		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		oMenuScrollPane.setMinimumSize(minimumSize);
		
		//add the SplitPane to the Inspector (final magic)
    	this.add(oSplitPane, BorderLayout.CENTER);
    	
    	// Adds the status bar at bottom (final final)
		moLabelStatusBar.setText(JGraphLayout.VERSION  +  " - Graph Memory Inspector");
		moLabelStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		moLabelStatusBar.setFont(moLabelStatusBar.getFont().deriveFont(Font.PLAIN));
		this.add(moLabelStatusBar, BorderLayout.SOUTH);
		
		for(clsGraph oGraph: moGraphes){
			@SuppressWarnings("unchecked")
			ArrayList<clsGraph> partners = (ArrayList<clsGraph>) moGraphes.clone();
			partners.remove(oGraph);
			oGraph.setLinkedPartner(partners);
		}
		
    }
    
    protected abstract void updateInspectorData();
  
    
    
    /**
     * Adds a Color&Icon legend to the TaskPane. This needs to be altered when new datatypes are added.
     *
     * @author herret
     * 20.09.2012, 14:41:43
     *
     * @param poTaskGroup
     * @return poTaskGroup
     */
    private JTaskPaneGroup addTaskPaneLegend(JTaskPaneGroup poTaskGroup){
    	poTaskGroup.setTitle("Legend");
    	poTaskGroup.setExpanded(false);
 
    	poTaskGroup.add(addLegendItem( "TP", clsGraph.moColorTP, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "NULL", clsGraph.moColorNULL, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "String", clsGraph.moColorString, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Double", clsGraph.moColorDouble, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "WP", clsGraph.moColorWP, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "WPM", clsGraph.moColorWPMRoot, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "DD", clsGraph.moColorDD, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "ACT", clsGraph.moColorACT, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Pair", clsGraph.moColorPair, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Tripple", clsGraph.moColorTripple, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "PDSC", clsGraph.moColorPrimaryDataStructureContainer, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "SDSC", clsGraph.moColorSecondaryDataStructureContainer, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "DM root", clsGraph.moColorDMRoot, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Emotion", clsGraph.moColorEmotion, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Symbol Vision", clsGraph.moColorSymbolVision, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Vision Entry", clsGraph.moColorVisionEntry, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Sensor Intern", clsGraph.moColorSensorIntern, clsGetARSPath.getRelativImagePath() + "view.png") );
    	poTaskGroup.add(addLegendItem( "Sensor Extern", clsGraph.moColorSensorExtern, clsGetARSPath.getRelativImagePath() + "view.png") );

    	return poTaskGroup;
    }
    
    /**
     * adds a single legend item in the form of a JLabel and a Icon is called by @see #method addTaskPaneLegend
     *
     * @author herret
     * 20.09.2012, 14:43:31
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
				layout.setOrientation(mbOrientationVertical?SwingConstants.WEST:SwingConstants.NORTH);
								
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange(layout);
				}
			}
		});
		  
		  poTaskGroup.add(new AbstractAction("Compact Tree") {
				
				private static final long serialVersionUID = -2811903925630396473L;
		
				@Override
				public void actionPerformed(ActionEvent e) {
					JGraphCompactTreeLayout layout = new JGraphCompactTreeLayout();
					layout.setOrientation(mbOrientationVertical?SwingConstants.WEST:SwingConstants.NORTH);
					for(clsGraph oGraph: moGraphes){
						oGraph.performGraphLayoutChange();
					}
				}
			});
		  
		  
		  poTaskGroup.add(new AbstractAction("Hierarchical") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphHierarchicalLayout layout = new JGraphHierarchicalLayout();
				layout.setOrientation(mbOrientationVertical?SwingConstants.WEST:SwingConstants.NORTH);
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange(layout);
				}
			}
		});
		  poTaskGroup.add(new AbstractAction("Fast Organic") {
	
			private static final long serialVersionUID = 6447974553914052766L;
	
			@Override
			public void actionPerformed(ActionEvent e) {					
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange( new JGraphFastOrganicLayout());
				}
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Simple Circle") {
	
			private static final long serialVersionUID = -1444623169928884258L;
	
			@Override
			public void actionPerformed(ActionEvent e) {		
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE));
				}
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Simple Tilt") {
	
			private static final long serialVersionUID = -6513607636960953397L;
	
			@Override
			public void actionPerformed(ActionEvent e) {			
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange(new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_TILT));
				}
				
			}
		});
		
		  poTaskGroup.add(new AbstractAction("Radialtree") {
	
			private static final long serialVersionUID = -7537382541569261175L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				JGraphRadialTreeLayout layout = new JGraphRadialTreeLayout();
				for(clsGraph oGraph: moGraphes){
					oGraph.performGraphLayoutChange(layout);
				}
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
					for(clsGraph oGraph: moGraphes){
						oGraph.setUseSimpleView(abstractButton.getModel().isSelected());
					}
		          
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
							for(clsGraph oGraph: moGraphes){
								oGraph.showInternAssoc(true);
							}
				          oExternAndIntern1.setEnabled(false);
				          oExternAndInternBest.setEnabled(false);
			          }
			          else{
							for(clsGraph oGraph: moGraphes){
								oGraph.showInternAssoc(false);
							}
				          
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
								for(clsGraph oGraph: moGraphes){
									oGraph.showExternAssoc(true);
								}
					          oExternAndIntern1.setEnabled(true);
					          oExternAndInternBest.setEnabled(true);
				          }
				          else{
								for(clsGraph oGraph: moGraphes){
									oGraph.showExternAssoc(false);
								}
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
									for(clsGraph oGraph: moGraphes){
										oGraph.showInternAssocFirstLevel(true);
									}
					          }
					          else{
									for(clsGraph oGraph: moGraphes){
										oGraph.showInternAssocFirstLevel(false);
									}
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
										for(clsGraph oGraph: moGraphes){
											oGraph.showInternAssocBest(true);
										}
						          }
						          else{
										for(clsGraph oGraph: moGraphes){
											oGraph.showInternAssocBest(false);
										}					        	  
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

								for(clsGraph oGraph: moGraphes){
									Object cells[] = oGraph.getSelectionCells();
									for(int i =0; i<cells.length;i++){
										oGraph.expandGraphCellInternAssoc((clsGraphCell) cells[i]);
									}
									if(cells.length>0) oGraph.redraw();
								}
								
								
							}
						});
				      
				      poTaskGroup.add(new AbstractAction("Expand Extern") {
							
						private static final long serialVersionUID = 8703769545272229866L;

							@Override
							public void actionPerformed(ActionEvent e) {
								for(clsGraph oGraph: moGraphes){
									Object cells[] = oGraph.getSelectionCells();
									for(int i =0; i<cells.length;i++){
										oGraph.expandGraphCellExternAssoc((clsGraphCell) cells[i]);
									}
									if(cells.length>0) oGraph.redraw();
								}
								
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
			}
		});
    			
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
			
		      	//link Checkbox
				javax.swing.JCheckBox oLinkCB = new javax.swing.JCheckBox("Link Input and Output Graphes");
				oLinkCB.setSelected(true);
				
			    ActionListener actionListenerLCB = new ActionListener() {
			        @Override
					public void actionPerformed(ActionEvent actionEvent) {
			          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();		
						for(clsGraph oGraph: moGraphes){
							oGraph.setLinked(abstractButton.getModel().isSelected());
						}
			        }
			      };
			      oLinkCB.addActionListener(actionListenerLCB);
			      poTaskGroup.add(oLinkCB);
		      
		      
		      
		      poTaskGroup.add(new AbstractAction("Actual Size") {
			private static final long serialVersionUID = -7683571637499420675L;
			@Override
			public void actionPerformed(ActionEvent e) {
				for(clsGraph oGraph: moGraphes){
					oGraph.setScale(1);
				}
			}
		});
		       
		      poTaskGroup.add(new AbstractAction("Fit Window") {
			private static final long serialVersionUID = -4236402393050941924L;
			@Override
			public void actionPerformed(ActionEvent e) {
				for(clsGraph oGraph: moGraphes){
					JGraphLayoutMorphingManager.fitViewport(oGraph);
				}
			}
		});
		      poTaskGroup.add(new AbstractAction("Zoom In") {
			private static final long serialVersionUID = 4963188240381232166L;
			@Override
			public void actionPerformed(ActionEvent e) {
				for(clsGraph oGraph: moGraphes){
					oGraph.setScale(1.5 * oGraph.getScale());
				}
			}
		});
		      poTaskGroup.add(new AbstractAction("Zoom Out") {
			private static final long serialVersionUID = 6518488789619229086L;
			@Override
			public void actionPerformed(ActionEvent e) {
				for(clsGraph oGraph: moGraphes){
					oGraph.setScale(oGraph.getScale()/1.5);
				}
			}
		});
		      poTaskGroup.add(new AbstractAction("Reset") {
			private static final long serialVersionUID = 4769006307236101696L;
			@Override
			public void actionPerformed(ActionEvent e) {
				for(clsGraph oGraph: moGraphes){
					oGraph.reset();
				}
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
		for(clsGraph oGraph: moGraphes){
			oGraph.updateControl();
		}
	}

}
