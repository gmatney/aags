package gmnk.boardgame.axisAndAllies.territory;

import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitUtils;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerJsonDeserializer;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class Territory {
	private static Logger log = Logger.getLogger(Territory.class);
	private int id;
    private String name;
    private String standardizedName;
    private int ipcValue;
    private boolean hasVictoryCity;
    private HashSet<Territory> neighbors;
    private Point center;
    private HashMap<WorldPowerName,StationedGroup> unitsStationed;
    
    // TODO: I've added this here just to make it easier to save territories to config files, 
    // but eventually we may want to keep all references to owned territories within the "Power" class.
    public String startingOwnership;  

    public Territory() {
    	unitsStationed = new HashMap<WorldPowerName,StationedGroup>();
        neighbors = new HashSet<Territory>();
        center = new Point();
        log.debug("Territory Initialized: "+toConfigString());
    }    
    public Territory(int colorId, String name, int ipcValue, boolean hasVictoryCity, String owningPower, Point center) {
        this.id = colorId;
        this.name = name;
        this.ipcValue = ipcValue;
        this.hasVictoryCity = hasVictoryCity;
        this.startingOwnership = owningPower;
        this.center = center;
        neighbors = new HashSet<Territory>();
        unitsStationed = new HashMap<WorldPowerName,StationedGroup>();
        log.debug("Territory Initialized: "+toConfigString());
    }
    public String getStandardizedName(){
    	if(standardizedName ==null){
    		standardizedName = World.getStandardizedTerritoryKey(name);
    	}
    	return standardizedName;
    }
   

    public int getId() {
 		return id;
 	}

 	public String getName() {
 		return name;
 	}

 	public HashMap<WorldPowerName, StationedGroup> getUnitsStationed(){
 		return unitsStationed;
 	}
 	public boolean hasUnitsStationed(WorldPowerName wpn){
 		if(unitsStationed.containsKey(wpn)){
 			if(unitsStationed.get(wpn).getNumberOfTypesOfUnitsStationed() >0){
 				return true;
 			}
 			else{
 				return false;
 			}
 		}else{
 			return false;
 		}
 	}
 	
 	public StationedGroup getUnitsStationed(WorldPowerName wpn){
 		if(unitsStationed.containsKey(wpn)){
 			return unitsStationed.get(wpn);
 		}else{
 			return new StationedGroup(wpn);
 		}
 	}
 	
 	public void addUnitsStationed(StationedGroup units){
 		if(units==null){
 			log.warn("Trying to add NULL StationedGroup");
 			return;
 		}
 		log.debug("addUnitsStationed:\n"+units);
 		if(hasUnitsStationed(units.getWorldPowerAllegence())){
 			unitsStationed.put(units.getWorldPowerAllegence()
	 			, UnitUtils.mergeStationedGroups(
	 					unitsStationed.get(units.getWorldPowerAllegence()),
	 					units
	 			)
 			);
 		}else{
 			unitsStationed.put(units.getWorldPowerAllegence(), units);
 		}
 		
 		
 	}
 	
 	public void setUnitsStationed(HashMap<WorldPowerName, StationedGroup> unitsStationed){
 		if(unitsStationed==null){
 			log.warn("unitsStationed was NULL setting to empty HashMap instead");
 			unitsStationed = new HashMap<WorldPowerName,StationedGroup>();
 			return;
 		}
 		this.unitsStationed = unitsStationed;
 	} 	
 	
    
    public void addNeighbor(Territory neighbor) {
        neighbors.add(neighbor);
    }
    
    public void removeNeighbor(Territory neighbor) {
        neighbors.remove(neighbor);
    }
    public boolean isNeighborOf(int neighborId){
    	for(Territory n: neighbors){
    		if(n.getId() == neighborId){
    			return true;
    		}
    	}
    	return false;
    }
    public boolean isNeighborOf(String neighborName){
    	for(Territory n: neighbors){
    		if(n.getName().equals(neighborName)){
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public HashSet<Territory> getNeighbors() {
        return neighbors;
    }
    
    public boolean hasVictoryCity(){
    	return hasVictoryCity;
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
    
    public LinkedHashMap<String, Integer> getUnitTable() {
    	LinkedHashMap<String, Integer> units = new LinkedHashMap<String, Integer>();
    	units.put("I", 3);
    	units.put("A", 2);
    	units.put("T", 1);
    	units.put("F", 1);
    	units.put("B", 4);
    	units.put("AA", 1);
    	units.put("IC", 1);
    	return units;
    }
    
    public String toConfigString() {
        String neighborConfig = "";
        ArrayList<Integer> sortedNeighbors = new ArrayList<Integer>();
        for(Territory t : neighbors) {
            sortedNeighbors.add(t.id);
        }
        Collections.sort(sortedNeighbors);
        for(int i = 0; i < sortedNeighbors.size(); i++) {
            neighborConfig += sortedNeighbors.get(i) + ",";
        }
        char victoryCity = 'N';
        if(hasVictoryCity)
            victoryCity = 'Y';
        neighborConfig = neighborConfig.length() ==0? "": 
        		neighborConfig.substring(0, neighborConfig.lastIndexOf(','));
        return id + "|" + name + "|" + getStandardizedName() +"|"+ ipcValue + "|" + victoryCity + "|" + startingOwnership + "|" + neighborConfig + "|" + center.x + "," + center.y; 
    }
}
