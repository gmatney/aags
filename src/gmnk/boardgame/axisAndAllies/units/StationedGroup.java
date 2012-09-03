package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class StationedGroup {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(StationedGroup.class);
	private WorldPowerName worldPowerAllegence;
	private HashMap<UnitName,Integer> units = new HashMap<UnitName,Integer>();
	public StationedGroup(WorldPowerName worldPowerAllegence){
		this.worldPowerAllegence = worldPowerAllegence;
	}
	public void addUnit(UnitName name){
		if(units.containsKey(name)){
			units.put(name, units.get(name)+1);
		}
		else{
			units.put(name, 1);
		}
	}
	public void addUnit(UnitName name, int quantity){
		if(units.containsKey(name)){
			units.put(name, units.get(name)+quantity);
		}
		else{
			units.put(name, quantity);
		}
	}
	public void setUnitsStationed(HashMap<UnitName,Integer> units){
		if(units==null){
			log.warn("Trying to set unitsStationed to NULL, using empty instead");
			units = new HashMap<UnitName,Integer>();
		}
		this.units = units;
	}
	public WorldPowerName getWorldPowerAllegence(){
		return worldPowerAllegence;
	}
	public HashMap<UnitName,Integer> getStationedUnits(){
		return units;
	}
	public int getNumberOfTypesOfUnitsStationed(){
		return units.size();
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("StationedGroup[\npower='");
		sb.append(worldPowerAllegence);
		sb.append("\n'Units={");
		for(UnitName unit: units.keySet()){
			sb.append("\n\t");
			sb.append(unit);
			sb.append(":");
			sb.append(units.get(unit));
		}
		sb.append("\n}]");
		return sb.toString();
	}
	
}
