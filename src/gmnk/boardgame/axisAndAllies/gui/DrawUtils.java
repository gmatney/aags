package gmnk.boardgame.axisAndAllies.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Provides draw* methods to the GameBoardGUI, taking into account 
 * the camera position and zooming.
 */
public class DrawUtils {

	public static Graphics2D g2d;
	public static Camera cam;
	
	public static void drawImage(BufferedImage image, Point destination) {
		g2d.drawImage(image, destination.x - cam.getX(), destination.y - cam.getY(), (int)(image.getWidth() * (1 + cam.zoomFactor / 10)), (int)(image.getHeight() * (1 + cam.zoomFactor / 10)), null);
	}
	
	public static void fillRect(int x, int y, int width, int height) {
		g2d.fillRect((int)(x * (1 + cam.zoomFactor / 10) - cam.getX()), (int)(y * (1 + cam.zoomFactor / 10) - cam.getY()), width, height);
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2) {
		g2d.drawLine((int)(x1 * (1 + cam.zoomFactor / 10) - cam.getX()), (int)(y1 * (1 + cam.zoomFactor / 10) - cam.getY()), (int)(x2 * (1 + cam.zoomFactor / 10) - cam.getX()), (int)(y2 * (1 + cam.zoomFactor / 10) - cam.getY()));
	}
	
	public static void drawString(String string, int x, int y) {
	    String[] lines = string.split("\\n");
	    for(int i = 1; i <= lines.length; i++) {
	        g2d.drawString(lines[i - 1], (int)(x * (1 + cam.zoomFactor / 10) - cam.getX()), (int)(y * (1 + cam.zoomFactor / 10) - cam.getY() + i * 15));
	    }
	}
}
