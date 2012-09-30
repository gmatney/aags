package gmnk.boardgame.axisAndAllies.gui;

import gmnk.boardgame.axisAndAllies.CONSTANTS;
import gmnk.boardgame.axisAndAllies.gameController.GameController;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.territory.World;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitConcrete;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
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
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
	private boolean enableMapPositionDebugOverlay = false; 
	private boolean enableUnitCountToTerritories  = true;

	private Graphics2D g2d;
	private Timer time;
	private Camera cam;
	private Font font;
	private Font defaultFont;

	private BufferedImage mapImage;
	private BufferedImage territoryOverlay;

	Point mousePos;
	Territory mouseStartDrag;
	int activeTerritory;
	String gameMode = "Edit";
	GameController gameController;

	public GameBoardGUI() {
		KL newKeyListener = new KL();
		addKeyListener(newKeyListener);
		ML newMouseListener = new ML();
		addMouseListener(newMouseListener);
		addMouseMotionListener(newMouseListener);
		addMouseWheelListener(newMouseListener);
		setFocusable(true);
		gameController = new GameController();
		InputStream myStream;

		// Initialize fonts.
		defaultFont = new Font("serif", Font.PLAIN, 16);
		try {
			myStream = new BufferedInputStream(new FileInputStream(CONSTANTS.RESOURCE_PATH + "POTTERYB.TTF"));
			Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
			font = ttfBase.deriveFont(Font.PLAIN, 40);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Font not loaded.  Using standard serif font.");
			font = new Font("serif", Font.PLAIN, 40);
		}

		try {
			gameController.initializeGame();
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
	public Camera getCamera(){
		return cam;
	}


	// Prints the config to the console.
	public void saveTerritoryConfig() {
		for(int i = 1; i < 130; i++) {
			if(gameController.getWorld().getTerritoryById(i) != null)
				log(gameController.getWorld().getTerritoryById(i).toConfigString());
		}
	}

	public void actionPerformed(ActionEvent e){
		cam.update();
		updateActiveTerritory();

		if(gameMode.equals("Play"))
			gameController.update();

		repaint();
	}

	private void updateActiveTerritory() {
		int mouseX = (int) (cam.getImageRelativeMouseX() * territoryOverlay.getWidth(null));
		int mouseY = (int) (cam.getImageRelativeMouseY() * territoryOverlay.getHeight(null));
		//TODO fix this        	
		int color = territoryOverlay.getRGB(mouseX, mouseY);
		int  red   = (color & 0x00ff0000) >> 16;
		//int  green = (color & 0x0000ff00) >> 8;
		//int  blue  =  color & 0x000000ff;
		activeTerritory = red;

	}
	
	//TODO add a minimap (could go somewhere like bottom right and be collapsable
	public Image createMiniMapImage(BufferedImage image, int resizeFactor){
		return image.getScaledInstance(mapImage.getWidth() / resizeFactor
				, mapImage.getHeight() / resizeFactor
				, java.awt.Image.SCALE_SMOOTH);
	}
	
	public Territory getActiveTerritory(){
		if(activeTerritory == 0){
			return null;
		}
		return gameController.getWorld().getTerritoryById(activeTerritory);
	}


	public void paint(Graphics g){
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

		DrawUtils.drawImage(mapImage, new Point(0, 0));

		g2d.setColor(Color.red);
		// Territory centers
//		for(Territory t : world.getTerritories().values()) {
//			if(t.getCenter() != null)
//				DrawUtils.fillRect(t.getCenter().x, t.getCenter().y, 10, 10);
//		}

		// Lines connecting territories that are neighbors
		Territory t = getActiveTerritory();
		if(t != null) {
			for(Territory neighbor : t.getNeighbors()) {
				if(neighbor != null && neighbor.getCenter() != null) {
					drawDirectionalArrow(t.getCenter().x, t.getCenter().y, neighbor.getCenter().x, neighbor.getCenter().y);
				}
			}
		}
		
		if(enableUnitCountToTerritories){   addUnitCountToTerritories();}
		if(enableMapPositionDebugOverlay){  addMapPositionDebugOverlay();}

		if(gameMode.equals("Play")) {
			g2d.setFont(font);
			g2d.drawString(gameController.activePower.toString(), getWidth() / 2 - 50, 40);
			g2d.setFont(defaultFont);
			g2d.drawString(gameController.gamePhase.toString(), getWidth() / 2 - 50, 60);
			switch(gameController.gamePhase) {
				case PURCHASE_UNITS:
					showPurchaseUnitsScreen();
				case COMBAT_MOVEMENT:
				case COMBAT:
				case NONCOMBAT_MOVEMENT:
				case PLACE_UNITS:
			}
			addTerritoryInfoOverlay();
		}
	}
	
	private Line2D.Double getLineWithFixedOffset(Line2D.Double line, int offset) {
		double deltaY = line.getY2() - line.getY1();
		double deltaX = line.getX2() - line.getX1();
		double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		line = getPercentOfLine(line, (float)(length / (length + offset)));
		return line;
	}
	
	private Line2D.Double getPercentOfLine(Line2D.Double line, float percent) {
		double deltaY = line.getY2() - line.getY1();
		double deltaX = line.getX2() - line.getX1();
		double xDiff = deltaX * (1 - percent) / 2;
		double yDiff = deltaY * (1 - percent) / 2;
		line = new Line2D.Double(line.getX1() + xDiff, line.getY1() + yDiff, line.getX2() - xDiff, line.getY2() - yDiff);
		return line;
	}

	private void drawDirectionalArrow(int x1, int y1, int x2, int y2) {
		AffineTransform tx = new AffineTransform();
		Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
		line = getLineWithFixedOffset(line, 30);

		Polygon arrowHead = new Polygon();  
		arrowHead.addPoint( 0,5);
		arrowHead.addPoint( -5, -5);
		arrowHead.addPoint( 5,-5);

		tx.setToIdentity();
		double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
		Point arrowPoint = getCameraAdjustedPoint(new Point((int)line.x2, (int)line.y2)); 
		tx.translate(arrowPoint.x, arrowPoint.y);
		tx.rotate((angle-Math.PI/2d));  

		Graphics2D g = (Graphics2D) g2d.create();
		g.setTransform(tx);   
		DrawUtils.drawLine((int)line.x1, (int)line.y1, (int)line.x2, (int)line.y2);
		g.fill(arrowHead);
	}
	
	private Point getCameraAdjustedPoint(Point original) {
		int x = (int) (original.x * (1 + cam.getZoomFactor() / 10) - cam.getX());
		int y = (int) (original.y * (1 + cam.getZoomFactor() / 10) - cam.getY());
		return new Point(x, y);
	}
	
	private void showPurchaseUnitsScreen() {
		
	}

	public void enableUnitCountToTerritories(boolean enableUnitCountToTerritories){
		this.enableUnitCountToTerritories = enableUnitCountToTerritories;
	}


	public void addUnitCountToTerritories(){
		// Unit count in territories
		for(Territory territory : gameController.getWorld().getTerritories().values()) {
			HashMap<WorldPowerName, StationedGroup> units = territory.getUnitsStationed();
			int powerCounter = 0;
			for(WorldPowerName power : units.keySet()) {
				g2d.setColor(gameController.getWorldPowers().getPower(power).getColor());
				StationedGroup stationedGroup = units.get(power);
				int numRows = stationedGroup.getNumberOfTypesOfUnitsStationed() + 1;
				DrawUtils.fillRect(territory.getCenter().x + 130 * powerCounter, territory.getCenter().y, 130, 15 * numRows + 5);
				g2d.setColor(Color.black);
				String unitString = power.name() + "\n";
				LinkedHashMap<UnitName, Integer> groupUnits = stationedGroup.getUnitCount(); 
				for(UnitName unit : groupUnits.keySet()) {
					unitString += unit.name() + "  " + groupUnits.get(unit) + "\n";
				}
				DrawUtils.drawString(unitString, territory.getCenter().x + 5, territory.getCenter().y);
			}
		}
	}


	public void enableMapPositionDebugOverlay(boolean enableMapPositionDebugOverlay) {
		this.enableMapPositionDebugOverlay = enableMapPositionDebugOverlay;
	}


	private void addMapPositionDebugOverlay(){
		int textHorzPos = 5;
		int textVertPos = 5;
		int textMov = 15;

		// HUD overlay
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, 350, 340);
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

		Territory mouseTerritory = getActiveTerritory();
		if(mouseTerritory != null) {
			g2d.drawString("Active territory: " + activeTerritory + " (" 
					+ gameController.getWorld().getTerritoryById(activeTerritory).getName() + ")", textHorzPos, textVertPos+=textMov);
		}
		g2d.drawString("You are in " + gameMode + " mode", textHorzPos, (textVertPos+=textMov));
		g2d.drawString("  Press N to print a new config to the console", textHorzPos, (textVertPos+=textMov));
		g2d.drawString("  Click on a territory to define the center", textHorzPos, (textVertPos+=textMov));
		g2d.drawString("  Click and drag between two territories to toggle neighbors", textHorzPos, (textVertPos+=textMov));
	}
	
	private void addTerritoryInfoOverlay() {
		int textHorzPos = 5;
		int textVertPos = 5;
		int textMov = 15;
		
		Territory t = getActiveTerritory();
		if(t != null) {
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, 350, 340);
			g2d.setColor(Color.black);
			g2d.drawString("Territory: " + t.getName(), textHorzPos, (textVertPos+=textMov));
			g2d.drawString("IPC value: " + t.getIpcValue(), textHorzPos, (textVertPos+=textMov));
			g2d.drawString("Is Sea Zone: " + t.isSeaZone(), textHorzPos, (textVertPos+=textMov));
			g2d.drawString("Has Victory City: " + t.hasVictoryCity(), textHorzPos, (textVertPos+=textMov));
			StationedGroup group = t.getUnitsStationed(gameController.activePower);
			g2d.drawString("Active Power Units: ", textHorzPos, (textVertPos+=textMov));
			for(UnitConcrete unit : group.getUnits()) {
				g2d.drawString("  " + unit.getProfile().getUnitName(), textHorzPos, (textVertPos+=textMov));
				g2d.drawString("  Movement Points: " + unit.getMovementPoints(), textHorzPos, (textVertPos+=textMov));
			}
		}
	}


	private class KL implements KeyListener{
		public void keyPressed(KeyEvent e) {
			cam.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			cam.keyReleased(e);
			
			// Keybindings independent of gameMode:
			if(e.getKeyCode() == KeyEvent.VK_E)	{
				gameMode = "Edit";
			}
			else if(e.getKeyCode() == KeyEvent.VK_P) {
				gameMode = "Play";
				try {
					gameController.initializeGame();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			
			// Edit mode keybindings:
			if(gameMode.equals("Edit"))	{
				if(e.getKeyCode() == KeyEvent.VK_N)	{
					saveTerritoryConfig();
				}
			}
			// Play mode keybindings:
			else if(gameMode.equals("Play")) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					gameController.volunteerEndPhase();
				}
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
				Territory clickedTerritory = gameController.getWorld().getTerritoryById(activeTerritory);
				if(clickedTerritory != null) {
					clickedTerritory.setCenter(new Point(mousePos.x, mousePos.y));
				}
			}
		}
		public void mousePressed( MouseEvent e ) {
			mouseStartDrag = gameController.getWorld().getTerritoryById(activeTerritory);
		}
		public void mouseReleased( MouseEvent e ) { 
			Territory endTerritory = gameController.getWorld().getTerritoryById(activeTerritory); 
			if(endTerritory == mouseStartDrag) {
				mouseClicked(e);
				return;
			}
			if(gameMode.equals("Edit")) {
				if(endTerritory != null && mouseStartDrag != null) {
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
			/* TODO: most of this logic should probably be in gameController, the gui should send a 
			 * "request to move units" for example, and the gameController can return a response with 
			 * success/failure and reason.
			 */
			else if(gameMode.equals("Play")) {
				if(endTerritory != null && mouseStartDrag != null) {
					ArrayList<UnitConcrete> tempUnits = new ArrayList<UnitConcrete>();
					for(UnitConcrete unit : mouseStartDrag.getUnitsStationed(gameController.activePower).getUnits()) {
						tempUnits.add(unit);
					}
					for(UnitConcrete unit : tempUnits) {
						gameController.requestMoveUnit(unit, mouseStartDrag, endTerritory);
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
			cam.adjustZoomFactorBy( e.getPreciseWheelRotation());
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
