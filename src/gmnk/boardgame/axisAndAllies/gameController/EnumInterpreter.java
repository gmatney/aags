package gmnk.boardgame.axisAndAllies.gameController;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.worldPowers.Faction;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

public class EnumInterpreter {
	private static Logger log = Logger.getLogger(EnumInterpreter.class);
	public static WorldPowerName getWorldPowerName(String s){
		if(s==null){
			log.error("NULL entered for WorldPowerName, returning UNKNOWN");
			return WorldPowerName.UNKNOWN;
		}
		s=s.trim().toUpperCase();
		s=s.replaceAll(" ", "");
		if(s.equals("SOVIETUNION")){
			return WorldPowerName.SOVIET_UNION;
		}
		if(s.equals("GERMANY")){
			return WorldPowerName.GERMANY;
		}
		if(s.equals("BRITAIN")){
			return WorldPowerName.UNITED_KINGDOM;
		}
		if(s.equals("UNITEDKINGDOM")){
			return WorldPowerName.UNITED_KINGDOM;
		}
		if(s.equals("JAPAN")){
			return WorldPowerName.JAPAN;
		}
		if(s.equalsIgnoreCase("UNITEDSTATES")){
			return WorldPowerName.UNITED_STATES;
		}
		log.error("No Suitable WorldPowerName found for '"+s+"', returning UNKNOWN");
		return WorldPowerName.UNKNOWN;
	}
	public static Faction getFaction(String s){
		if(s==null){
			log.error("NULL entered for Faction String, returning UNKNOWN");
			return Faction.UNKNOWN;
		}
		s=s.trim().toUpperCase();
		if(s.equals("AXIS")){
			return Faction.Axis; 
		}
		if(s.equals("ALLIES")){
			return Faction.Alliance;
		}
		if(s.equals("ALLIANCE")){
			return Faction.Alliance;
		}
		log.error("Faction String '"+s+"' not recognized, returning UKNOWN");
		return Faction.UNKNOWN;
	}
	public static UnitName getUnitName(String s){
		if(s==null){
			log.error("NULL entered for UnitName String, returning UNKNOWN");
			return UnitName.UNKOWN;
		}
		s=s.trim().toUpperCase();
		s=s.replaceAll(" ", "");
		if(s.equals("INFANTRY")){
			return UnitName.INFANTRY;
		}
		if(s.equals("ARTILLERY")){
			return UnitName.ARTILLERY;
		}
		if(s.equals("TANK")){
			return UnitName.TANK;
		}
		if(s.equals("FIGHTER")){
			return UnitName.FIGHTER;
		}
		if(s.equals("BOMBER")){
			return UnitName.BOMBER;
		}
		if(s.equals("SUBMARINE")){
			return UnitName.SUBMARINE;
		}
		if(s.equals("TRANSPORT")){
			return UnitName.TRANSPORT;
		}
		if(s.equals("DESTROYER")){
			return UnitName.DESTROYER;
		}
		if(s.equals("CRUISER")){
			return UnitName.CRUISER;
		}
		if(s.equals("AIRCRAFT_CARRIER")){
			return UnitName.AIRCRAFT_CARRIER;
		}
		if(s.equals("BATTLESHIP")){
			return UnitName.BATTLESHIP;
		}
		if(s.equals("ANTIAIRCRAFTGUN")){
			return UnitName.ANTIAIRCRAFTGUN;
		}
		if(s.equals("AAGUN")){
			return UnitName.ANTIAIRCRAFTGUN;
		}
		if(s.equals("FACTORY")){
			return UnitName.FACTORY;
		}
		if(s.equals("INDUSTRIAL_COMPLEX")){
			return UnitName.FACTORY;
		}
		log.error("UnitName String '"+s+"' not recognized, returning UKNOWN");
		return UnitName.UNKOWN;
	}
}
