package gmnk.boardgame.axisAndAllies.gui;

import gmnk.boardgame.axisAndAllies.PurchaseOrder;
import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.gui.Camera.ZOOM_MODE;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

public class Game_Applet extends JApplet {
	private static Logger log = Logger.getLogger(Game_Applet.class);
    GameBoardGUI board;
    
    public void init() {
        board = new GameBoardGUI();        
        
        OptionsPanel options = new OptionsPanel(board);

        this.getContentPane().add(BorderLayout.EAST,options);
        this.getContentPane().add(BorderLayout.CENTER,board);
        
        //JOptionPane.showMessageDialog(null, purchasePanel, 
        //        "Please Enter your purchase order", JOptionPane.OK_CANCEL_OPTION);
        //this.add(purchasePanel);
       // this.getContentPane().add(BorderLayout.CENTER,purchasePanel);
        this.setSize(GameBoardGUI.WORLD_WIDTH,GameBoardGUI.WORLD_HEIGHT);
        
     }
    

    
    public class OptionsPanel extends JPanel implements ActionListener{
    	GameBoardGUI board;
 
    	//Listeners
    	ZoomStyleSelectorListener zoomStyleSelectorListener;
    	MapPositionDebugOverlayListener mapPositionDebugOverlayListener;
    	UnitCountInTerritoriesListener unitCountInTerritoriesListener;
    	
    	//Panel Components
    	JComboBox<ZOOM_MODE> zoomStyleOptions;
    	JCheckBox mapPositionDebugOverlayCheckbox;
    	JCheckBox unitCountInTerritoriesCheckbox;
    	
    	public OptionsPanel(GameBoardGUI board){
    		this.board = board;
    		this.setBackground(Color.gray);
    		//this.setLayout(new FlowLayout());
    		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); //Staggered weird
    		//this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS)); //Staggerd Weird
    		//this.setLayout(new BorderLayout()); //Only one box shows
    		this.setLayout(new GridLayout(25,1));
    		this.setFocusable(false);
    		
    		addRandomLargeButton();
    		addZoomStyleSelector();
    		addMapPositionDebugOverlayCheckbox();
    		addUnitCountInTerritoriesCheckbox();
    		addViewPurchaseOrderButton();
            this.setMaximumSize(new Dimension(250,99999));
            this.setVisible(true);
    	}
    	
    	public void addRandomLargeButton(){
    		JButton button = new JButton("BUTTON");
            Font big = new Font("SanSerif",Font.BOLD,20);
            button.setFocusable(false);
            this.add(button);
    	}
    	public void addViewPurchaseOrderButton(){
    		JButton button = new JButton("View Purchase Order");
    		button.addActionListener(this);
            button.setActionCommand("viewPurchaseOrder");
            button.setFocusable(false);
            this.add(button);
    	}
    	
    	
    	
    	
    	public class ZoomStyleSelectorListener implements ActionListener{
    		@Override
    		public void actionPerformed(ActionEvent arg0) {
				board.getCamera().setZoomMode(zoomStyleOptions.getItemAt(zoomStyleOptions.getSelectedIndex()));
			}
    	}
    	public class MapPositionDebugOverlayListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				board.enableMapPositionDebugOverlay(mapPositionDebugOverlayCheckbox.isSelected());
			}
    	}
    	public class UnitCountInTerritoriesListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.enableUnitCountToTerritories(unitCountInTerritoriesCheckbox.isSelected());
			}
    	}    	
    	
    	
		@Override
		public void actionPerformed(ActionEvent event) {
			log.debug(event);
			log.debug("ActionCommand="+event.getActionCommand());
			if(event.getActionCommand().equals("viewPurchaseOrder")) {
				String purchaseOrder = "No purchase order has been placed";
				if(board.gameController.getPurchaseOrder() != null)
					purchaseOrder = board.gameController.getPurchaseOrder().toString();
				JOptionPane.showMessageDialog(this, purchaseOrder);
			}
			board.actionPerformed(event);
		}		
	    public void addZoomStyleSelector(){
	    	zoomStyleOptions = new JComboBox<ZOOM_MODE>(ZOOM_MODE.values());
	    	zoomStyleOptions.setFocusable(false);
	    	zoomStyleOptions.setSelectedIndex(3);
	    	zoomStyleSelectorListener = new ZoomStyleSelectorListener();
	    	zoomStyleOptions.addActionListener(zoomStyleSelectorListener);
	    	zoomStyleOptions.setMaximumSize(new Dimension(250,20));
	    	this.add(zoomStyleOptions);
	    }
	    public void addMapPositionDebugOverlayCheckbox(){
	    	mapPositionDebugOverlayCheckbox = new JCheckBox("MapPositionDebugOverlay");
	    	mapPositionDebugOverlayCheckbox.setFocusable(false);
	    	mapPositionDebugOverlayListener = new MapPositionDebugOverlayListener();
	    	mapPositionDebugOverlayCheckbox.addActionListener(mapPositionDebugOverlayListener);
	    	mapPositionDebugOverlayCheckbox.setMaximumSize(new Dimension(250,20));
	    	this.add(mapPositionDebugOverlayCheckbox);
	    }
		public void addUnitCountInTerritoriesCheckbox(){
			unitCountInTerritoriesCheckbox = new JCheckBox("UnitCountInTerritories");
	    	unitCountInTerritoriesCheckbox.setFocusable(false);
	    	unitCountInTerritoriesListener = new UnitCountInTerritoriesListener();
	    	unitCountInTerritoriesCheckbox.addActionListener(unitCountInTerritoriesListener);
	    	unitCountInTerritoriesCheckbox.setMaximumSize(new Dimension(250,20));
	    	unitCountInTerritoriesCheckbox.setSelected(true);
	    	this.add(unitCountInTerritoriesCheckbox);
		}
    	
    }
    
}
