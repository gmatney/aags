package gmnk.boardgame.axisAndAllies.gui;

import java.awt.event.KeyEvent;


public class Camera {
    private boolean up, down, left, right;
    private int x, y;
    private float xVel = 5.0f;
    private float yVel = 5.0f;
    
    public Camera()
    {
        
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
        if(down)
            y += yVel;
        if(up)
            y -= yVel;
        if(left)
            x -= xVel;
        if(right)
            x += xVel;
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
