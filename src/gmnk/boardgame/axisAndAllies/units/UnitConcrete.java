package gmnk.boardgame.axisAndAllies.units;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;


public class UnitConcrete {
	private static Logger log = Logger.getLogger(UnitConcrete.class);

	private UnitProfile profile;
	private Territory territorySource;
	private int remainingHitpoints;
	private int movementPoints;

	// TODO: should units know where they are?
	public UnitConcrete(UnitName type, Territory territorySource){
		this.territorySource = territorySource;
		profile = EnumInterpreter.getUnitProfile(type);
	}
	public UnitConcrete(UnitName type){
		this.territorySource = null;
		profile = EnumInterpreter.getUnitProfile(type);
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
