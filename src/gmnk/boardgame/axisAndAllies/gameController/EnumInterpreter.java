package gmnk.boardgame.axisAndAllies.gameController;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.air.Bomber;
import gmnk.boardgame.axisAndAllies.units.air.Fighter;
import gmnk.boardgame.axisAndAllies.units.land.*;
import gmnk.boardgame.axisAndAllies.units.sea.*;
import gmnk.boardgame.axisAndAllies.units.types.UnknownUnit;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;
import gmnk.boardgame.axisAndAllies.worldPowers.Faction;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

public class EnumInterpreter {
	public static final Infantry        UNIT_PROFILE_INFANTRY         = new Infantry();
	public static final Artillery       UNIT_PROFILE_ARTILLERY        = new Artillery();
	public static final Tank            UNIT_PROFILE_TANK             = new Tank();
	public static final Fighter         UNIT_PROFILE_FIGHTER          = new Fighter();
	public static final Bomber          UNIT_PROFILE_BOMBER           = new Bomber();
	public static final Submarine       UNIT_PROFILE_SUBMARINE        = new Submarine();
	public static final Destroyer       UNIT_PROFILE_DESTROYER        = new Destroyer();
	public static final Cruiser         UNIT_PROFILE_CRUISER          = new Cruiser();
	public static final AircraftCarrier UNIT_PROFILE_AIRCRAFT_CARRIER = new AircraftCarrier();
	public static final BattleShip      UNIT_PROFILE_BATTLESHIP       = new BattleShip();
	public static final UnknownUnit     UNIT_PROFILE_UNKNOWN          = new UnknownUnit(); 
	
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
		log.error("Faction String '"+s+"' not recognized, returning UNKNOWN");
		return Faction.UNKNOWN;
	}
	public static UnitName getUnitName(String s){
		if(s==null){
			log.error("NULL entered for UnitName String, returning UNKNOWN");
			return UnitName.UNKNOWN;
		}
		s=s.trim().toUpperCase();
		s=s.replaceAll(" ", "");
		
		switch(s){
			case "INFANTRY"            : return UnitName.INFANTRY;
			case "ARTILLERY"           : return UnitName.ARTILLERY;
			case "TANK"                : return UnitName.TANK;
			case "FIGHTER"             : return UnitName.FIGHTER;
			case "BOMBER"              : return UnitName.BOMBER;
			case "SUBMARINE"           : return UnitName.SUBMARINE;
			case "TRANSPORT"           : return UnitName.TRANSPORT;
			case "DESTROYER"           : return UnitName.DESTROYER;
			case "CRUISER"             : return UnitName.CRUISER;
			case "AIRCRAFT_CARRIER"    : return UnitName.AIRCRAFT_CARRIER;
			case "BATTLESHIP"          : return UnitName.BATTLESHIP;
			case "ANTIAIRCRAFTGUN"     : return UnitName.ANTIAIRCRAFTGUN;
			case "AAGUN"               : return UnitName.ANTIAIRCRAFTGUN;
			case "FACTORY"             : return UnitName.FACTORY;
			case "INDUSTRIAL_COMPLEX"  : return UnitName.FACTORY;
		}
		
		
		
		log.error("UnitName String '"+s+"' not recognized, returning UNKNOWN");
		return UnitName.UNKNOWN;
	}
	public static UnitProfile getUnitProfile(UnitName unit){
		switch(unit){
			case INFANTRY         : return UNIT_PROFILE_INFANTRY;
			case ARTILLERY        : return UNIT_PROFILE_ARTILLERY;
			case TANK             : return UNIT_PROFILE_TANK;
			case FIGHTER          : return UNIT_PROFILE_FIGHTER;
			case BOMBER           : return UNIT_PROFILE_BOMBER;
			case SUBMARINE        : return UNIT_PROFILE_SUBMARINE;
			case DESTROYER        : return UNIT_PROFILE_DESTROYER;
			case CRUISER          : return UNIT_PROFILE_CRUISER;
			case AIRCRAFT_CARRIER : return UNIT_PROFILE_AIRCRAFT_CARRIER;
			case BATTLESHIP       : return UNIT_PROFILE_BATTLESHIP;
		}
		return UNIT_PROFILE_UNKNOWN;
		
	}
}
