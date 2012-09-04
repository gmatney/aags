package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class StationedGroup {
	private static Logger log = Logger.getLogger(StationedGroup.class);
	private WorldPowerName worldPowerAllegence;
	private LinkedHashMap<UnitName,Integer> units     = new LinkedHashMap<UnitName,Integer>();
	private HashMap<UnitName,UnitGroup> landUnits     = new HashMap<UnitName,UnitGroup>();
	private HashMap<UnitName,UnitGroup> airUnits      = new HashMap<UnitName,UnitGroup>();
	private HashMap<UnitName,UnitGroup> seaUnits      = new HashMap<UnitName,UnitGroup>();
	private HashMap<UnitName,UnitGroup> antiAirGuns   = new HashMap<UnitName,UnitGroup>();
	private Territory station;
	
	
	
	public StationedGroup(Territory station, WorldPowerName worldPowerAllegence){
		this.worldPowerAllegence = worldPowerAllegence;
		this.station = station;
	}

	public void addUnit(UnitName name, int quantity){
		UnitProfile unit = EnumInterpreter.getUnitProfile(name);
		
		if(name.equals(UnitName.ANTIAIRCRAFTGUN)){
			if(antiAirGuns.containsKey(name)){
				UnitGroup old = antiAirGuns.remove(name);
				antiAirGuns.put(name,new UnitGroup(name, quantity+old.getRemainingQuantity(), station));
			}else{
				antiAirGuns.put(name,new UnitGroup(name, quantity, station));
			}
		}
		
		else if(unit instanceof LandUnit){
			if(landUnits.containsKey(name)){
				UnitGroup old = landUnits.remove(name);
				landUnits.put(name,new UnitGroup(name, quantity+old.getRemainingQuantity(), station));
			}else{
				landUnits.put(name,new UnitGroup(name, quantity, station));
			}
		}
		else if(unit instanceof AirUnit){
			if(airUnits.containsKey(name)){
				UnitGroup old = airUnits.remove(name);
				airUnits.put(name,new UnitGroup(name, quantity+old.getRemainingQuantity(), station));
			}else{
				airUnits.put(name,new UnitGroup(name, quantity, station));
			}
		}
		else if(unit instanceof SeaUnit){
			if(seaUnits.containsKey(name)){
				UnitGroup old = seaUnits.remove(name);
				seaUnits.put(name,new UnitGroup(name, quantity+old.getRemainingQuantity(), station));
			}else{
				seaUnits.put(name,new UnitGroup(name, quantity, station));
			}
		}
		else{
			//TODO AAGUN
			log.error("Ignoring this Unit '"+unit+"', not an air,sea, or land unit");
		}
		
		
		
		//TODO remove the units option
		if(units.containsKey(name)){
			units.put(name, units.get(name)+quantity);
		}
		else{
			units.put(name, quantity);
		}
	}
	
	
	
	public void setUnitsStationed(LinkedHashMap<UnitName,Integer> units){
		if(units==null){
			log.warn("Trying to set unitsStationed to NULL, using empty instead");
			units = new LinkedHashMap<UnitName,Integer>();
			landUnits   = new HashMap<UnitName,UnitGroup>();
			airUnits    = new HashMap<UnitName,UnitGroup>();
			seaUnits    = new HashMap<UnitName,UnitGroup>();
			antiAirGuns = new HashMap<UnitName,UnitGroup>();
			
		}
		else{
			for(UnitName n: units.keySet()){
				addUnit(n, units.get(n));
			}
		}
	}
	public WorldPowerName getWorldPowerAllegence(){
		return worldPowerAllegence;
	}
	public LinkedHashMap<UnitName,Integer> getStationedUnits(){
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
	
	public LinkedHashMap<UnitName, Integer> getUnits() {
		return units;
	}

	public HashMap<UnitName, UnitGroup> getLandUnits() {
		return landUnits;
	}

	public HashMap<UnitName, UnitGroup> getAirUnits() {
		return airUnits;
	}

	public HashMap<UnitName, UnitGroup> getSeaUnits() {
		return seaUnits;
	}

	public HashMap<UnitName, UnitGroup> getAntiAirGuns() {
		return antiAirGuns;
	}

	public Territory getStation() {
		return station;
	}
	
}
