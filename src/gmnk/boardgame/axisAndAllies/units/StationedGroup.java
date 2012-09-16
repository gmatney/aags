package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.land.AntiaircraftArtillery;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class StationedGroup {
	private static Logger log = Logger.getLogger(StationedGroup.class);
	private WorldPowerName worldPowerAllegence;
	private ArrayList<UnitConcrete> units         = new ArrayList<UnitConcrete>();
	private Territory station;
	
	public StationedGroup(Territory station, WorldPowerName worldPowerAllegence){
		this.worldPowerAllegence = worldPowerAllegence;
		this.station = station;
	}
	
	public void addUnit(UnitConcrete unit) {
		units.add(unit);
	}	
	public void addUnit(UnitName name, int quantity) {
		for(int i = 0; i < quantity; i++) {
			units.add(new UnitConcrete(name));
		}
	}
	
	public void setUnitsStationed(ArrayList<UnitConcrete> units){
		if(units==null){
			log.warn("Trying to set unitsStationed to NULL, using empty instead");
			units = new ArrayList<UnitConcrete>();			
		}
		else{
			for(UnitConcrete unit : units){
				addUnit(unit);
			}
		}
	}
	public WorldPowerName getWorldPowerAllegence(){
		return worldPowerAllegence;
	}
	public ArrayList<UnitConcrete> getStationedUnits(){
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
		ArrayList<UnitName> listedUnits = new ArrayList<UnitName>();
		for(UnitConcrete unit : units) {
			UnitName name = unit.getProfile().getUnitName();
			if(!listedUnits.contains(name)) {
				sb.append("\n\t");
				sb.append(name);
				sb.append(":");
				sb.append(getNumUnit(name));
				listedUnits.add(name);
			}
		}
		sb.append("\n}]");
		return sb.toString();
	}
	
	public ArrayList<UnitConcrete> getUnits() {
		return units;
	}
	public LinkedHashMap<UnitName, Integer> getUnitCount() {
		LinkedHashMap<UnitName, Integer> map = new LinkedHashMap<UnitName, Integer>();
		for(UnitConcrete unit : units) {
			UnitName name = unit.getProfile().getUnitName();
			if(!map.keySet().contains(name)) {
				map.put(name, getNumUnit(name));
			}
		}
		return map;
	}

	public ArrayList<UnitConcrete> getLandUnits() {
		ArrayList<UnitConcrete> landUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile() instanceof LandUnit) {
				landUnits.add(unit);
			}
		}
		return landUnits;
	}
	public ArrayList<UnitConcrete> getAirUnits() {
		ArrayList<UnitConcrete> airUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile() instanceof AirUnit) {
				airUnits.add(unit);
			}
		}
		return airUnits;
	}
	public ArrayList<UnitConcrete> getSeaUnits() {
		ArrayList<UnitConcrete> seaUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile() instanceof SeaUnit) {
				seaUnits.add(unit);
			}
		}
		return seaUnits;
	}
	public ArrayList<UnitConcrete> getAntiAirGuns() {
		ArrayList<UnitConcrete> antiAirUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile() instanceof AntiaircraftArtillery) {
				antiAirUnits.add(unit);
			}
		}
		return antiAirUnits;
	}
	
	public int getNumUnit(UnitName unitName) {
		int counter = 0;
		for(UnitConcrete unit : units) {
			if(unit.getProfile().getUnitName() == unitName) {
				counter++;
			}
		}
		return counter;
	}

	public Territory getStation() {
		return station;
	}
	
}
