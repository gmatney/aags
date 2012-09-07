package gmnk.boardgame.axisAndAllies.gui;

import gmnk.boardgame.axisAndAllies.CONSTANTS;
import gmnk.boardgame.axisAndAllies.gameController.GameController;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.UnitUtils;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;


public class GameBoardGUI extends JPanel implements ActionListener {
	private static Logger log = Logger.getLogger(GameBoardGUI.class);
    public static final int WORLD_WIDTH = 1000;
    public static final int WORLD_HEIGHT = 700;

    private Graphics2D g2d;
    private Timer time;
    private Camera cam;
    
    private BufferedImage mapImage;
    private BufferedImage territoryOverlay;

	Point mousePos;
	Territory mouseStartDrag;
	int activeTerritory;
	String gameMode = "Edit";
	GameController gameController;
	World world;
	
	public GameBoardGUI() {
        KL newKeyListener = new KL();
        addKeyListener(newKeyListener);
        ML newMouseListener = new ML();
        addMouseListener(newMouseListener);
        addMouseMotionListener(newMouseListener);
        addMouseWheelListener(newMouseListener);
        
        setFocusable(true);
        gameController = new GameController();
        try {
            gameController.initializeGame();
            world = gameController.getWorld();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		initialize();
	}
	
	public void initialize() {
		try {
		    File dir = new File(CONSTANTS.RESOURCE_PATH);
		    log(dir.getCanonicalPath());
		    String path = CONSTANTS.RESOURCE_PATH + "AAmap_final.jpg";
		    log.info("Path = "+path);
			mapImage = ImageIO.read(new File(path));
			territoryOverlay = ImageIO.read(new File(CONSTANTS.RESOURCE_PATH + "AAmap_overlay_final.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        cam = new Camera(getMapWidth()-this.getWidth(),getMapHeight()-this.getHeight());
        mousePos = new Point();
        DrawUtils.cam = cam;
        
        time = new Timer(5, this);
        time.start();
	}
	
	// Prints the config to the console.
	public void saveTerritoryConfig() {
	    for(int i = 0; i < 130; i++) {
	        if(world.getTerritoryById(i) != null)
	            log(world.getTerritoryById(i).toConfigString());
	    }
	}
	
    public void actionPerformed(ActionEvent e)
    {
        cam.update();
        updateActiveTerritory();
        repaint();
    }
    
    private void updateActiveTerritory() {
        int mouseX = mousePos.x + cam.getX();
        int mouseY = mousePos.y + cam.getY();
        if(mouseX > 0 && mouseX < territoryOverlay.getWidth(null) && mouseY > 0 && mouseY < territoryOverlay.getHeight(null)) {
            int color = territoryOverlay.getRGB(mouseX, mouseY);
            int  red   = (color & 0x00ff0000) >> 16;
            //int  green = (color & 0x0000ff00) >> 8;
            //int  blue  =  color & 0x000000ff;
            activeTerritory = red;
        }
    }
    //TODO add a minimap (could go somewhere like bottom right and be collapsable
    public Image createMiniMapImage(BufferedImage image, int resizeFactor){
    	return image.getScaledInstance(mapImage.getWidth() / resizeFactor
    			, mapImage.getHeight() / resizeFactor
    			, java.awt.Image.SCALE_SMOOTH);
    	
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setColor(Color.black);
        
        cam.setScreenWidth(g.getClipBounds().width);
        cam.setScreenHeight(g.getClipBounds().height);
        
        DrawUtils.g2d = g2d;
        
        // Background map image
        //DrawUtils.drawImage(mapImage, new Point(cam.getScreenHalfWidth()+cam.getX(), cam.getScreenHalfHeight()+cam.getY()));
        DrawUtils.drawImage(mapImage, new Point(0, 0));
        
        
        // Territory centers
        g2d.setColor(Color.red);
        for(Territory t : world.getTerritories().values()) {
        	if(t.getCenter() != null)
        		DrawUtils.fillRect(t.getCenter().x, t.getCenter().y, 10, 10);
        }
        
        // Lines connecting territories that are neighbors
        Territory t = world.getTerritoryById(activeTerritory);
        if(t != null) {
	    	for(Territory neighbor : t.getNeighbors()) {
	    		if(neighbor != null && neighbor.getCenter() != null) {
	        		DrawUtils.drawLine(t.getCenter().x, t.getCenter().y, neighbor.getCenter().x, neighbor.getCenter().y);
	    		}
	    	}
        }
        
        // Unit count in territories
        for(Territory territory : world.getTerritories().values()) {
        	HashMap<WorldPowerName, StationedGroup> units = territory.getUnitsStationed();
        	int powerCounter = 0;
        	for(WorldPowerName power : units.keySet()) {
        	    g2d.setColor(gameController.getWorldPowers().getPower(power).getColor());
        	    StationedGroup stationedGroup = units.get(power);
            	int numRows = stationedGroup.getNumberOfTypesOfUnitsStationed() + 1;
            	DrawUtils.fillRect(territory.getCenter().x + 130 * powerCounter, territory.getCenter().y, 130, 15 * numRows + 5);
            	g2d.setColor(Color.black);
            	String unitString = power.name() + "\n";
            	LinkedHashMap<UnitName, Integer> groupUnits = stationedGroup.getStationedUnits(); 
            	for(UnitName unit : groupUnits.keySet()) {
            	    unitString += unit.name() + "  " + groupUnits.get(unit) + "\n";
            	}
            	DrawUtils.drawString(unitString, territory.getCenter().x + 5, territory.getCenter().y);
        	}
        }
        int textHorzPos = 5;
        int textVertPos = 5;
        int textMov = 15;
        
        // HUD overlay
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 350, 240);
        g2d.setColor(Color.black);
        g2d.drawString("Camera position: " + cam.getX() + ", " + cam.getY(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Camera Center:   " + cam.getCameraCenterX() + ", " + cam.getCameraCenterY(), textHorzPos, (textVertPos+=textMov));
        
        g2d.drawString("Cam-Pos Relative:" + String.format("%1$,.6f",cam.getImageRelativeX())
        		+ ", " + String.format("%1$,.6f",cam.getImageRelativeY()), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Cam-Center-Rel:  " + String.format("%1$,.6f",cam.getCamCenterImageRelativeX())
        		+ ", " + String.format("%1$,.6f",cam.getCamCenterImageRelativeY()), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Cam-Mouse-Rel:  " + String.format("%1$,.6f",cam.getImageRelativeMouseX())
        		+ ", " + String.format("%1$,.6f",cam.getImageRelativeMouseY()), textHorzPos, (textVertPos+=textMov));        
        g2d.drawString("Mouse position:  " + mousePos.x + ", " + mousePos.y, textHorzPos, (textVertPos+=textMov));
        g2d.drawString("CamScrnMousePos:  " + cam.getScreenMouseX() + ", " + cam.getScreenMouseY(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("CamZoomMousePos:  " + cam.getCurrentZoomMouseX() + ", " + cam.getCurrentZoomMouseY(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Zoom Mode: "      + cam.getZoomMode(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Zoom Factor: "      + cam.getZoomFactor(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("MaxZoomOutFactor: "      + cam.getMaxZoomFactor(), textHorzPos, (textVertPos+=textMov));
        g2d.drawString("Map Dimensions:  " + getMapWidth() + ", " + getMapHeight(),textHorzPos,(textVertPos+=textMov));
        g2d.drawString("Zoom Dimensions: " + cam.getZoomWidth() + ", " + cam.getZoomHeight(),textHorzPos,(textVertPos+=textMov));
        g2d.drawString("Zoom Multiple: " + cam.getZoomMultiple(cam.getZoomFactor()),textHorzPos,(textVertPos+=textMov));
        g2d.drawString("Screen Size:     " + cam.getScreenWidth() + ", " + cam.getScreenHeight(),textHorzPos,(textVertPos+=textMov));
        
        Territory mouseTerritory = world.getTerritoryById(activeTerritory);
        if(mouseTerritory != null) {
            
            g2d.drawString("Active territory: " + activeTerritory + " (" 
            + world.getTerritoryById(activeTerritory).getName() + ")", textHorzPos, textVertPos+=textMov);
            
            
        }
        g2d.drawString("You are in " + gameMode + " mode", textHorzPos, (textVertPos+=textMov));
        //g2d.drawString("  Press E to enter Edit mode", textHorzPos, 9textHorzPos);
        //g2d.drawString("  Press P to enter Play mode (does nothing currently)", textHorzPos, 110);
        g2d.drawString("  Press N to print a new config to the console", textHorzPos, (textVertPos+=textMov));
        g2d.drawString("  Click on a territory to define the center", textHorzPos, (textVertPos+=textMov));
        g2d.drawString("  Click and drag between two territories to toggle neighbors", textHorzPos, (textVertPos+=textMov));
    }
    
    private class KL implements KeyListener
    {
        public void keyPressed(KeyEvent e) {
            cam.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
        	cam.keyReleased(e);
        	if(e.getKeyCode() == KeyEvent.VK_E)
            {
                gameMode = "Edit";
            }
        	else if(e.getKeyCode() == KeyEvent.VK_P)
            {
                gameMode = "Play";
            }
        	else if(e.getKeyCode() == KeyEvent.VK_N)
        	{
        	    saveTerritoryConfig();
        	}
        }
        public void keyTyped(KeyEvent e) {  
        }
    }
    
    private class ML implements MouseListener, MouseMotionListener, MouseWheelListener 
    {
        public void mouseClicked(MouseEvent e) {
        	int mouseX = e.getX() + cam.getX();
        	int mouseY = e.getY() + cam.getY();
        	if(gameMode.equals("Edit")) {
        		Territory clickedTerritory = world.getTerritoryById(activeTerritory);
        		if(clickedTerritory != null) {
        			clickedTerritory.setCenter(new Point(mouseX, mouseY));
        		}
        	}
        }
        public void mousePressed( MouseEvent e ) {
            if(gameMode.equals("Edit")) {
                mouseStartDrag = world.getTerritoryById(activeTerritory);
            }
        }
        public void mouseReleased( MouseEvent e ) { 
            if(gameMode.equals("Edit")) {
                Territory endTerritory = world.getTerritoryById(activeTerritory); 
                if(endTerritory == mouseStartDrag)
                    mouseClicked(e);
                else if(endTerritory != null && mouseStartDrag != null) {
                    if(endTerritory.getNeighbors().contains(mouseStartDrag)) {
                        endTerritory.removeNeighbor(mouseStartDrag);
                        mouseStartDrag.removeNeighbor(endTerritory);
                    }
                    else {
                        endTerritory.addNeighbor(mouseStartDrag);
                        mouseStartDrag.addNeighbor(endTerritory);
                    }
                }
            }
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
        public void mouseMoved( MouseEvent e ) {
        	mousePos.x = e.getX();
        	mousePos.y = e.getY();
        	cam.setScreenMouseX(mousePos.x);
        	cam.setScreenMouseY(mousePos.y);
        }
        public void mouseDragged( MouseEvent e ) {  
            mousePos.x = e.getX();
            mousePos.y = e.getY();
        	cam.setScreenMouseX(mousePos.x);
        	cam.setScreenMouseY(mousePos.y);
        }
        public void mouseWheelMoved(MouseWheelEvent e) {
        	//TODO allow zooming in and out on the map.
        	//TODO not sure if the scaling would take too much processing
        	//TODO or if we would need to create image maps for a set of scaling levels
        	//  EXAMPLE 10 different levels one can zoom in, each with their pair of images so no scaling was needed
        	cam.adjustZoomFactorBy( e.getPreciseWheelRotation());
        	
        	//cam.setX((int)(cam.getX() * (0.75f + e.getPreciseWheelRotation() / 20)));
        	//cam.setY((int)(cam.getY() * (0.75f + e.getPreciseWheelRotation() / 20)));
        }
    }
    public int getMapWidth(){
    	if(mapImage==null){
    		return 0;
    	}
    	return mapImage.getWidth();
    }
    public int getMapHeight(){
    	if(mapImage==null){
    		return 0;
    	}
    	return mapImage.getHeight();    	
    }
    
    public void log(String s)
    {
        System.out.println(s);
    }
}
