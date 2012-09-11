package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class StationedGroup {
	private static Logger log = Logger.getLogger(StationedGroup.class);
	private WorldPowerName worldPowerAllegence;
	private LinkedHashMap<UnitName,Integer> units     = new LinkedHashMap<UnitName,Integer>();
	private ArrayList<UnitConcrete> landUnits     = new ArrayList<UnitConcrete>();
	private ArrayList<UnitConcrete> airUnits      = new ArrayList<UnitConcrete>();
	private ArrayList<UnitConcrete> seaUnits      = new ArrayList<UnitConcrete>();
	private ArrayList<UnitConcrete> antiAirGuns   = new ArrayList<UnitConcrete>();
	private Territory station;
	
	
	
	public StationedGroup(Territory station, WorldPowerName worldPowerAllegence){
		this.worldPowerAllegence = worldPowerAllegence;
		this.station = station;
	}

	public void addUnit(UnitName name, int quantity){
		UnitProfile unit = EnumInterpreter.getUnitProfile(name);
		
		if(name.equals(UnitName.ANTIAIRCRAFTGUN)){
			antiAirGuns.add(new UnitConcrete(name, station));
		}
		
		else if(unit instanceof LandUnit){
			landUnits.add(new UnitConcrete(name, station));
		}
		else if(unit instanceof AirUnit){
			airUnits.add(new UnitConcrete(name, station));
		}
		else if(unit instanceof SeaUnit){
			seaUnits.add(new UnitConcrete(name, station));
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
			landUnits   = new ArrayList<UnitConcrete>();
			airUnits    = new ArrayList<UnitConcrete>();
			seaUnits    = new ArrayList<UnitConcrete>();
			antiAirGuns = new ArrayList<UnitConcrete>();
			
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

	public ArrayList<UnitConcrete> getLandUnits() {
		return landUnits;
	}

	public ArrayList<UnitConcrete> getAirUnits() {
		return airUnits;
	}

	public ArrayList<UnitConcrete> getSeaUnits() {
		return seaUnits;
	}

	public ArrayList<UnitConcrete> getAntiAirGuns() {
		return antiAirGuns;
	}

	public Territory getStation() {
		return station;
	}
	
}
