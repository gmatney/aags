package gmnk.boardgame.axisAndAllies.units;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;


public class UnitConcrete {
	private static Logger log = Logger.getLogger(UnitConcrete.class);

	private UnitProfile profile;
	private Territory territorySource;
	
	public UnitConcrete(UnitName type, Territory territorySource){
		this.territorySource = territorySource;
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
}
