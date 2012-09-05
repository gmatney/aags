package gmnk.boardgame.axisAndAllies.gui;

import java.awt.event.KeyEvent;


public class Camera {
	//TODO need to be able to get the current screen dimension
	enum ZOOM_MODE{
		OnMapCenter,
		OnMouseCenter,
	}
	private ZOOM_MODE zm = ZOOM_MODE.OnMapCenter;
    private boolean up, down, left, right;
    private int x, y;
    private int xVel = 30;
    private int yVel = 30;
    private int mouseX = 0;
    private int mouseY = 0;
    private int realImageWidth;
    private int realImageHeight;

	private int screenWidth;
    private int screenHeight;
    private double zoomFactor=0;

    public ZOOM_MODE getZoomMode(){
    	return zm;
    }
    
    public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}


	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}


	public Camera(int imageWidth, int imageHeight){
        this.realImageHeight = imageHeight;
        this.realImageWidth  = imageWidth;
    }
    
    
    public void adjustZoomFactorBy(double adjustment){
    	int oldZoomWidth = getZoomWidth();
    	int oldZoomHeigth = getZoomHeight();
    	zoomFactor = zoomFactor - adjustment;
    	if(zm == ZOOM_MODE.OnMapCenter){
        	x = x + ((getZoomWidth() - oldZoomWidth) /4);
        	y = y + ((getZoomHeight() - oldZoomHeigth) /4);
    	}
    	else if(zm == ZOOM_MODE.OnMouseCenter){
    		//Keep working on
    		//Need MAP position on map, not only screen
    		x = x+mouseX + ((getZoomWidth() - oldZoomWidth) /4);
        	y = y+mouseY + ((getZoomHeight() - oldZoomHeigth) /4);
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
    

    public int getX() {
    	return x;
    }


    public int getY() {
    	return y;
    }

    
    public void update()
    {
        if(up){
        	int newY = y - yVel; 
        	if(newY<0){
        		newY=0;
        	}
            y = newY;
        }
        if(down){
        	int newY = y + yVel; 
        	if(newY>getZoomHeight()-screenHeight){
        		newY=getZoomHeight()-screenHeight;
        	}
            y = newY;
        }
        if(left){
        	int newX= x - xVel; 
        	if(newX<0){
        		newX=0;
        	}
            x = newX;
        }
        if(right){
        	int newX= x + xVel; 
        	if(newX>getZoomWidth()-screenWidth){
        		newX=getZoomWidth()-screenWidth;
        	}
            x = newX;
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
}
