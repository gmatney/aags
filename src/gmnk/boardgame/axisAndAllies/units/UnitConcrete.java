package gmnk.boardgame.axisAndAllies.units;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.land.Infantry;
import gmnk.boardgame.axisAndAllies.units.land.Tank;
import gmnk.boardgame.axisAndAllies.units.sea.Transport;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;


public class UnitConcrete {
	private static Logger log = Logger.getLogger(UnitConcrete.class);

	private UnitProfile profile;
	private Territory territorySource;
	private int remainingHitpoints;
	private int movementPoints;
	private ArrayList<UnitConcrete> cargo;

	// TODO: should units know where they are?
	public UnitConcrete(UnitName type, Territory territorySource){
		this.territorySource = territorySource;
		profile = EnumInterpreter.getUnitProfile(type);
		cargo = new ArrayList<UnitConcrete>();
	}
	public UnitConcrete(UnitName type){
		this.territorySource = null;
		profile = EnumInterpreter.getUnitProfile(type);
		cargo = new ArrayList<UnitConcrete>();
	}

	public UnitName getType() {
		return profile.getUnitName();
	}

	public UnitProfile getProfile() {
		return profile;
	}
	public Territory getTerritorySource(){
		return territorySource;
	}
	public void setTerritorySource(Territory territorySource) {
		this.territorySource = territorySource;
	}
	public int getRemainingHitpoints() {
		return remainingHitpoints;
	}
	public int getMovementPoints() {
		return movementPoints;
	}
	public void setMovementPoints(int movementPoints) {
		this.movementPoints = movementPoints;
	}
	public ArrayList<UnitConcrete> getCargo() {
		return cargo;
	}

	public boolean tryAddCargo(UnitConcrete cargoUnit) {
		if(profile instanceof Transport) {
			if(!(cargoUnit.profile instanceof Infantry || cargoUnit.profile instanceof Tank))
				return false;
			if(cargo.isEmpty()) {
				cargo.add(cargoUnit);
				return true;
			}
			else if(cargo.size() == 1) {
				for(UnitConcrete unit : cargo) {
					if(unit.profile instanceof Infantry) {
						cargo.add(cargoUnit);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
 	In current rules only will be used for battleship 
	 **/
	public void resetRemainingHitpoints() {
		if(isDead()){
			log.debug("no more health for the dead");
		}{
			remainingHitpoints = profile.getHitpoints();
		}
	}
	public boolean isDead(){
		return remainingHitpoints<=0;
	}




}
