package gmnk.boardgame.axisAndAllies.gui;

import gmnk.boardgame.axisAndAllies.land.Territory;

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
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameBoardGUI extends JPanel implements ActionListener {

    final int WORLD_WIDTH = 1000;
    final int WORLD_HEIGHT = 700;
    final String RESOURCE_PATH = "./gmnk/boardgame/axisAndAllies/gui/resources/";

    Graphics2D g2d;
    Timer time;
    Camera cam;
    
    BufferedImage mapImage;
    BufferedImage territoryOverlay;

	Point mousePos;
	Territory mouseStartDrag;
	int activeTerritory;
	String gameMode = "Edit";
	HashMap<Integer, Territory> territories;
	
	public GameBoardGUI() {
        KL newKeyListener = new KL();
        addKeyListener(newKeyListener);
        ML newMouseListener = new ML();
        addMouseListener(newMouseListener);
        addMouseMotionListener(newMouseListener);
        addMouseWheelListener(newMouseListener);
        
        setFocusable(true);
		initialize();
	}
	
	public void initialize() {
		try {
		    File dir = new File(RESOURCE_PATH);
		    log(dir.getCanonicalPath());
			mapImage = ImageIO.read(new File(RESOURCE_PATH + "AAmap_final.jpg"));
			territoryOverlay = ImageIO.read(new File(RESOURCE_PATH + "AAmap_overlay_final.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        cam = new Camera();
        cam.setX(WORLD_WIDTH / 2);
        cam.setY(WORLD_HEIGHT / 2);
        mousePos = new Point();
        
        territories = new HashMap<Integer, Territory>();
        loadTerritoryConfig();
        
        time = new Timer(5, this);
        time.start();
	}
	
	public void loadTerritoryConfig() {
	    String configPath = RESOURCE_PATH + "Territory_Config";
		try {
		    BufferedReader in = new BufferedReader(new FileReader(configPath));
		    String line;
		    
		    // Load territories (ignore neighbors for now).
		    while ((line = in.readLine()) != null) {
		    	String[] lineParts = line.split("\\|");
		    	if(lineParts.length == 7 && !line.startsWith("#")) {
		    	    int id = Integer.parseInt(lineParts[0]);
		    	    String territoryName = lineParts[1];
		    	    int ipcValue = Integer.parseInt(lineParts[2]);
		    	    boolean hasVictoryCity = (lineParts[3].equalsIgnoreCase("Y"));
		    	    String owningPower = lineParts[4];
		    	    String[] centerString = lineParts[6].split(",");
		    	    Point center = new Point(Integer.parseInt(centerString[0]), Integer.parseInt(centerString[1]));
		    	    
		    		Territory newTerritory = new Territory(id, territoryName, ipcValue, hasVictoryCity, owningPower, center);
		        	territories.put(id, newTerritory);
		    	}
		    }
		    
		    // Load neighbors (have to do this in separate loop so that all countries are loaded first).
		    in = new BufferedReader(new FileReader(configPath));
		    while ((line = in.readLine()) != null) {
		    	String[] lineParts = line.split("\\|");
		    	if(lineParts.length == 7 && !line.startsWith("#")) {
			    	int id = Integer.parseInt(lineParts[0]);
			    	String[] neighbors = lineParts[5].split(",");
			    	Territory t = territories.get(id);
			    	for(String neighborStr : neighbors) {
			    		Territory neighbor = territories.get(Integer.parseInt(neighborStr));
			    		t.addNeighbor(neighbor);
			    	}
		    	}
		    }
		    in.close();
		} catch (IOException e) {
		}
	}
	
	// Prints the config to the console.
	public void saveTerritoryConfig() {
	    for(int i = 0; i < 130; i++) {
	        if(territories.get(i) != null)
	            log(territories.get(i).toConfigString());
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
    
    public void paint(Graphics g)
    {
        super.paint(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        
        // Background map image
        g2d.drawImage(mapImage, 0 - cam.getX(), 0 - cam.getY(), null);
        
        // Territory centers
        g2d.setColor(Color.red);
        for(Territory t : territories.values()) {
        	if(t.getCenter() != null)
        		g2d.fillRect(t.getCenter().x - cam.getX(), t.getCenter().y - cam.getY(), 10, 10);
        }
        
        // Lines connecting territories that are neighbors
        Territory t = territories.get(activeTerritory);
        if(t != null) {
	    	for(Territory neighbor : t.getNeighbors()) {
	    		if(neighbor != null && neighbor.getCenter() != null) {
	        		g2d.drawLine(t.getCenter().x - cam.getX(), t.getCenter().y - cam.getY(), neighbor.getCenter().x - cam.getX(), neighbor.getCenter().y - cam.getY());
	    		}
	    	}
        }
        
        // HUD overlay
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 350, 140);
        g2d.setColor(Color.black);
        g2d.drawString("Camera position: " + cam.getX() + ", " + cam.getY(), 5, 20);
        Territory mouseTerritory = territories.get(activeTerritory);
        if(mouseTerritory != null) {
            g2d.drawString("Mouse position: " + mousePos.x + ", " + mousePos.y, 5, 35);
            g2d.drawString("Active territory: " + activeTerritory + " (" + territories.get(activeTerritory).name + ")", 5, 50);
        }
        g2d.drawString("You are in " + gameMode + " mode", 5, 80);
        //g2d.drawString("  Press E to enter Edit mode", 5, 95);
        //g2d.drawString("  Press P to enter Play mode (does nothing currently)", 5, 110);
        g2d.drawString("  Press N to print a new config to the console", 5, 95);
        g2d.drawString("  Click on a territory to define the center", 5, 110);
        g2d.drawString("  Click and drag between two territories to toggle neighbors", 5, 125);
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
        		Territory clickedTerritory = territories.get(activeTerritory);
        		if(clickedTerritory != null) {
        			clickedTerritory.setCenter(new Point(mouseX, mouseY));
        		}
        	}
        }
        public void mousePressed( MouseEvent e ) {
            if(gameMode.equals("Edit")) {
                mouseStartDrag = territories.get(activeTerritory);
            }
        }
        public void mouseReleased( MouseEvent e ) { 
            if(gameMode.equals("Edit")) {
                Territory endTerritory = territories.get(activeTerritory); 
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
        }
        public void mouseDragged( MouseEvent e ) {  
            mousePos.x = e.getX();
            mousePos.y = e.getY();
        }
        public void mouseWheelMoved(MouseWheelEvent e) {
        }
    }
    
    public void log(String s)
    {
        System.out.println(s);
    }
}
