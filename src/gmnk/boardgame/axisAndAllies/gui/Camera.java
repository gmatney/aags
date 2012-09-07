package gmnk.boardgame.axisAndAllies.gui;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import sun.util.logging.resources.logging;


public class Camera {
	//TODO need to be able to get the current screen dimension
	private static Logger log = Logger.getLogger(Camera.class);
	enum ZOOM_MODE{
		 ON_MAP_CENTER
		,PUT_MOUSE_OVER_IN_CENTER
		,MOUSE_OVER_ADJUSTED
		,ZOOM_TOWARDS_MOUSE //TODO
	}
	
	private ZOOM_MODE zm = ZOOM_MODE.ZOOM_TOWARDS_MOUSE;
	
	private long lastZoomTime;
	private double lastFocalZoomRelativeX;
	private double lastFocalZoomRelativeY;
	
    private boolean up, down, left, right;
    private int x, y;
    private int xVel = 30;
    private int yVel = 30;
    public int getScreenMouseX() {
		return screenMouseX;
	}

	private int screenMouseX = 0;
    private int screenMouseY = 0;

    
    private final int realImageWidth;
    private final int realImageHeight;

	private int screenWidth;
    private int screenHeight;
    private double zoomFactor=0;
    private double zoomNegativeLimit=-7;

    public ZOOM_MODE getZoomMode(){
    	return zm;
    }

	public Camera(int imageWidth, int imageHeight){
        this.realImageHeight = imageHeight;
        this.realImageWidth  = imageWidth;
    }
	public double getImageRelativeX(){
		return x/ (getZoomWidth()+0.0);
	}
	public double getImageRelativeY(){
		return y/ (getZoomHeight()+0.0);
	}
	public double getImageRelativeMouseX(){
		return (x+screenMouseX)/ (getZoomWidth()+0.0);
	}
	public double getImageRelativeMouseY(){
		return (y+screenMouseY)/ (getZoomHeight()+0.0);
	}
	public double getCamCenterImageRelativeY(){
		return getCameraCenterY()/ (getZoomHeight()+0.0);
	}
	public double getCamCenterImageRelativeX(){
		return getCameraCenterX()/ (getZoomWidth()+0.0);
	}
    
    public void adjustZoomFactorBy(double adjustment){
    	if(zoomFactor -adjustment < zoomNegativeLimit){
    		//zoomFactor = zoomNegativeLimit -1;
    		//TODO force zoom factor to make 
    		//getZoomWidth return realImageWidth
    	}
    	
    	
    	
    	if(zm == ZOOM_MODE.ON_MAP_CENTER){
        	double oldImgrelX = getCamCenterImageRelativeX();
        	double oldImgrelY = getCamCenterImageRelativeY();    	
        	zoomFactor = zoomFactor - adjustment;
        	x = (int)((getZoomWidth()  * oldImgrelX) -getScreenHalfWidth());
        	y = (int)((getZoomHeight() * oldImgrelY) -getScreenHalfHeight());
        	updateX();
        	updateY();
    	}
    	else if(zm == ZOOM_MODE.PUT_MOUSE_OVER_IN_CENTER){
        	double oldImgrelX = getImageRelativeMouseX();
        	double oldImgrelY = getImageRelativeMouseY();
        	zoomFactor = zoomFactor - adjustment;
        	
        	double newZoomMousePosX = oldImgrelX * getZoomWidth();
        	double newZoomMousePosY = oldImgrelY * getZoomHeight();
        	
        	//This causes the map to center on the picture the mouse was on
        	x = ((int)newZoomMousePosX) - getScreenHalfWidth();
        	y = ((int)newZoomMousePosY) - getScreenHalfHeight() ;
        	
        	updateX();
        	updateY();	
    	}
    	else if(zm == ZOOM_MODE.MOUSE_OVER_ADJUSTED){

    		long diff = (System.currentTimeMillis()-lastZoomTime);
    		long acceptable = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
    		log.debug("Is "+acceptable+" < "+ diff+"?");
    		
        	if( //See if you zoomed in the last little bit
        			diff < acceptable
        	){
        		//DONT CHANGE THE LAST FOCAL ZOOM
        		log.warn("About to do something interesting");
        	}
        	else{
        		
            	lastFocalZoomRelativeX = getImageRelativeMouseX();
            	lastFocalZoomRelativeY = getImageRelativeMouseY();
        	}
        	zoomFactor = zoomFactor - adjustment;
        	double newZoomMousePosX = lastFocalZoomRelativeX * getZoomWidth();
        	double newZoomMousePosY = lastFocalZoomRelativeY * getZoomHeight();
        	
        	//This causes the map to center on the picture the mouse was on
        	//Though if you have been zooming recently it will be centered on what
        	//You first mapped over
        	x = ((int)newZoomMousePosX) - getScreenHalfWidth();
        	y = ((int)newZoomMousePosY) - getScreenHalfHeight() ;

        	
        	lastZoomTime=System.currentTimeMillis();
        	
        	updateX();
        	updateY();	
    	}
    	else if(zm == ZOOM_MODE.ZOOM_TOWARDS_MOUSE){
        	double oldImgrelX = getImageRelativeMouseX();
        	double oldImgrelY = getImageRelativeMouseY();
        	zoomFactor = zoomFactor - adjustment;
        	
        	double newZoomMousePosX = oldImgrelX * getZoomWidth();
        	double newZoomMousePosY = oldImgrelY * getZoomHeight();
        	
        	//This causes the map to center on the picture the mouse was on
        	x = ((int)newZoomMousePosX) - screenMouseX;
        	y = ((int)newZoomMousePosY) - screenMouseY;
        	
        	updateX();
        	updateY();	
    	}
    	else{
    		//Zoom off of 0,0
    	}
    	
    }
    public double getZoomFactor(){
    	return zoomFactor;
    }
    
    public int getScreenHalfWidth(){
    	return (screenWidth/2);
    }
    public int getScreenHalfHeight(){
    	return (screenHeight/2);
    }
    public int getCameraCenterX(){
    	return x+getScreenHalfWidth();
    }
    public int getCameraCenterY(){
    	return y+getScreenHalfHeight();
    }
    
    
    public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
    

    public int getX() {return x;}


    public int getY() {return y;}
    
    private void updateX(){
    	int maxWidth = getZoomWidth()-screenWidth;
    	if(x<0){x=0;}
    	if(x>maxWidth){x=maxWidth;}
    }
    private void updateY(){
    	int maxHeight=getZoomHeight()-screenHeight;
    	if(y<0){y=0;}
    	if(y>maxHeight){y=maxHeight;}
    }
    
    public void update(){
        if(up){
        	y = y - yVel; 
        	updateY();
        }
        if(down){
        	y = y + yVel;
        	updateY();
        }
        if(left){
        	x = x - xVel;
        	updateX();
        }
        if(right){
        	x = x + xVel;
        	updateX();
        }
    }
    public double getZoomMultiple(double factor){
    	return (1 + factor / 10);
    }
    
    public int getZoomWidth(){
    	return (int) (realImageWidth  * getZoomMultiple(zoomFactor));
    }
    public int getZoomHeight(){
    	return (int) (realImageHeight * getZoomMultiple(zoomFactor));
    }
    
    public void keyPressed(KeyEvent e) {    
        switch(e.getKeyCode()) {    
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN: down = true; break;
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP: up = true; break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT: left = true; break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT: right = true; break;    
        }    
    }    
    public void keyReleased(KeyEvent e) {   
        switch(e.getKeyCode()) {        
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN: down = false; break;
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP: up = false; break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT: left = false; break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT: right = false; break;    
        }   
    }
    
	public void setScreenMouseX(int screenMouseX) {
		this.screenMouseX = screenMouseX;
	}

	public int getScreenMouseY() {
		return screenMouseY;
	}

	public void setScreenMouseY(int screenMouseY) {
		this.screenMouseY = screenMouseY;
	}
	
	
    public int getCurrentZoomMouseX() {
		return screenMouseX+x;
	}

	public int getCurrentZoomMouseY() {
		return screenMouseY+y;
	}

}
