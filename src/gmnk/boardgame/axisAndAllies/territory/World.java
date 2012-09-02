package gmnk.boardgame.axisAndAllies.territory;

import gmnk.boardgame.axisAndAllies.CONSTANTS;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;



public class World {
	Hashtable<String,Territory> territories;
	public World(){
		loadTerritoryConfig();
	}
	
	
	public boolean hasTerritoryByName(String name){
		if(name == null){return false;}
		name = getStandardizedTerritoryKey(name);
		if(territories.containsKey(name)){return true;}
		return false;
	}
	public boolean hasTerritoryById(int id){
		if(id<0){return false;}
		for(Territory t: territories.values()){
			if(t.getId() == id){
				return true;
			}
		}
		return false;
	}
	public Territory getTerritoryByName(String name){
		if(name == null){return null;}
		return territories.get(
				getStandardizedTerritoryKey(name));
		
	}
	
	
	
	private void loadTerritoryConfig() {
	    String configPath = CONSTANTS.RESOURCE_PATH + CONSTANTS.TERRITORY_CONFIG;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(configPath));
		    String line;
		    
		    // Load territories (ignore neighbors for now).
		    while ((line = in.readLine()) != null) {
		    	String[] lineParts = line.split("\\|");
		    	if(lineParts.length == 7 && !line.startsWith("#")) {
		    	    int id                 = Integer.parseInt(lineParts[0]);
		    	    String territoryName   = lineParts[1];
		    	    int ipcValue           = Integer.parseInt(lineParts[2]);
		    	    boolean hasVictoryCity = (lineParts[3].equalsIgnoreCase("Y"));
		    	    String owningPower     = lineParts[4];
		    	    String[] centerString  = lineParts[6].split(",");
		    	    Point center           = new Point(Integer.parseInt(centerString[0])
		    	                                     , Integer.parseInt(centerString[1]));
		    		Territory newTerritory = new Territory(id, territoryName, ipcValue
		    		                                      , hasVictoryCity, owningPower, center);
		        	territories.put(getStandardizedTerritoryKey(territoryName)
		        	                , newTerritory);
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
	/**
	 * Standardizes Territory Names
	 * 
	 * Example:  turns "  Yakut S.S.R.   " into YAKUT_SSR
	 * @param territoryName
	 * @return
	 */
	private String getStandardizedTerritoryKey(String territoryName){
		if(territoryName == null){
			return "NULL";
		}
		territoryName = territoryName.replaceAll("[\\.,:]", ""); //Delete punctuation
		territoryName = territoryName.toUpperCase();
		territoryName = territoryName.trim();
		while(territoryName.contains("  ")){
			territoryName = territoryName.replace("  ", " "); //Make all multiple spaces one space
		}
		territoryName = territoryName.replaceAll(" ", "_");
		return territoryName;
		
	}
	public static void main(String[] args) {
		World w = new World();
		System.out.println(w.getStandardizedTerritoryKey("  Yakut S.S.R.  "));
	}
	
	
}
