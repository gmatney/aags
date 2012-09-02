package gmnk.boardgame.axisAndAllies.gui;

import java.awt.event.KeyEvent;


public class Camera {
    private boolean up, down, left, right;
    private int x, y;
    private int xVel = 30;
    private int yVel = 30;
    private int maxWidth;
    private int maxHeight;
    
    public Camera(int maxWidth, int maxHeight){
        this.maxHeight = maxHeight;
        this.maxWidth  = maxWidth;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        	if(newY>maxHeight){
        		newY=maxHeight;
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
        	if(newX>maxWidth){
        		newX=maxWidth;
        	}
            x = newX;
        }
            
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
