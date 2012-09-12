package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.sea.Transport;
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
	private ArrayList<UnitConcrete> units;
	private Territory targetTerritory;
	
	public BattleGroup(Territory targetTerritory){
		this.targetTerritory = targetTerritory;
		
	}
	public BattleGroup(Territory targetTerritory, ArrayList<UnitConcrete> units){
		this.targetTerritory = targetTerritory;
		for(UnitConcrete unit : units){
			if(unit.getProfile() instanceof LandUnit
					|| unit.getProfile() instanceof AirUnit
					|| unit.getProfile() instanceof SeaUnit){
				units.add(unit);
			}
			else{
				log.error("Ignoring this Unit '" + unit + "', not an air,sea, or land unit");
			}
		}
	}
	
	public ArrayList<UnitConcrete> getUnits() {
		return units;
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
	/**
	 * 
	 * Will return all SeaUnits except Transports, this is to prevent
	 * other attacking utils from using transports to take hits during
	 * the fight.  (even though all the transports will die if their
	 * side loses)
	 */
	public ArrayList<UnitConcrete> getSeaAttackUnits() {
		ArrayList<UnitConcrete> seaUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile() instanceof SeaUnit
				&& (!(unit.getProfile() instanceof Transport))
			) {
				seaUnits.add(unit);
			}
		}
		return seaUnits;
	}
	public Territory getTargetTerritory() {
		return targetTerritory;
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
	public ArrayList<UnitConcrete> getBombardUnits(int numBombardments) {
		ArrayList<UnitConcrete> bombardUnits = new ArrayList<UnitConcrete>();
		for(UnitConcrete unit : units) {
			if(unit.getProfile().getUnitName() == UnitName.BATTLESHIP && numBombardments > 0) {
				bombardUnits.add(unit);
				numBombardments--;
			}
		}
		for(UnitConcrete unit : units) {
			if(unit.getProfile().getUnitName() == UnitName.CRUISER && numBombardments > 0) {
				bombardUnits.add(unit);
				numBombardments--;
			}
		}
		return bombardUnits;
	}
	public int getNumTransportedUnits() {
		int total = 0;
		for(UnitConcrete unit : units) {
			if(unit.getProfile().getUnitName() == UnitName.TRANSPORT) {

			}
		}
		return total;
	}
	
}
