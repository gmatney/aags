package gmnk.boardgame.axisAndAllies.land;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Territory {

    public int colorId;
    public String name;
    private int ipcValue;
    public boolean hasVictoryCity;
    private HashSet<Territory> neighbors;
    private Point center;
    
    // TODO: I've added this here just to make it easier to save territories to config files, 
    // but eventually we may want to keep all references to owned territories within the "Power" class.
    public String owningPower;  

    public Territory() {
        neighbors = new HashSet<Territory>();
        center = new Point();
    }
    
    public Territory(int colorId, String name, int ipcValue, boolean hasVictoryCity, String owningPower, Point center) {
        this.colorId = colorId;
        this.name = name;
        this.ipcValue = ipcValue;
        this.hasVictoryCity = hasVictoryCity;
        this.owningPower = owningPower;
        this.center = center;
        neighbors = new HashSet<Territory>();
    }

    public void addNeighbor(Territory neighbor) {
        neighbors.add(neighbor);
    }
    
    public void removeNeighbor(Territory neighbor) {
        neighbors.remove(neighbor);
    }

    public HashSet<Territory> getNeighbors() {
        return neighbors;
    }

    public int getIpcValue() {
        return ipcValue;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center; 
    }
    
    public String toConfigString() {
        String neighborConfig = "";
        ArrayList<Integer> sortedNeighbors = new ArrayList<Integer>();
        for(Territory t : neighbors) {
            sortedNeighbors.add(t.colorId);
        }
        Collections.sort(sortedNeighbors);
        for(int i = 0; i < sortedNeighbors.size(); i++) {
            neighborConfig += sortedNeighbors.get(i) + ",";
        }
        char victoryCity = 'N';
        if(hasVictoryCity)
            victoryCity = 'Y';
        neighborConfig = neighborConfig.substring(0, neighborConfig.lastIndexOf(','));
        return colorId + "|" + name + "|" + ipcValue + "|" + victoryCity + "|" + owningPower + "|" + neighborConfig + "|" + center.x + "," + center.y; 
    }
}
