package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.AirUnit;
import gmnk.boardgame.axisAndAllies.units.types.LandUnit;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * BattleGroup a group of units attacking a common Territory 
 */
public class BattleGroup {
	private static Logger log = Logger.getLogger(BattleGroup.class);
	private ArrayList<UnitGroup> landUnits;
	private ArrayList<UnitGroup> airUnits;
	private ArrayList<UnitGroup> seaUnits;
	private Territory targetTerritory;
	
	public BattleGroup(Territory targetTerritory){
		this.targetTerritory = targetTerritory;
		
	}
	public BattleGroup(Territory targetTerritory, ArrayList<UnitGroup> units){
		this.targetTerritory = targetTerritory;
		for(UnitGroup unit : units){
			if(unit.getProfile() instanceof LandUnit){
				landUnits.add(unit);
			}
			else if(unit.getProfile() instanceof AirUnit){
				airUnits.add(unit);
			}
			else if(unit.getProfile() instanceof SeaUnit){
				seaUnits.add(unit);
			}
			else{
				log.error("Ignoring this Unit '"+unit.getProfile()+"', not an air,sea, or land unit");
			}
		}
	}
	
	public ArrayList<UnitGroup> getLandUnits() {
		return landUnits;
	}
	public ArrayList<UnitGroup> getAirUnits() {
		return airUnits;
	}
	public ArrayList<UnitGroup> getSeaUnits() {
		return seaUnits;
	}
	public Territory getTargetTerritory() {
		return targetTerritory;
	}
	
}
