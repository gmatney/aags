package gmnk.boardgame.axisAndAllies.units;

import org.apache.log4j.Logger;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;


public class UnitGroup {
	private static Logger log = Logger.getLogger(UnitGroup.class);

	private UnitName type;
	private int startingQuantity;
	private int remainingQuantity;
	private int totalHitPointsOfGroup;
	private int damageTakenToGroup=0;
	private UnitProfile profile;
	private Territory territorySource;
	public UnitGroup(UnitName type, int startingQuantity, Territory territorySource){
		this.startingQuantity = startingQuantity;
		this.type=type;
		this.remainingQuantity = startingQuantity;
		this.territorySource = territorySource;
		profile = EnumInterpreter.getUnitProfile(type);
		totalHitPointsOfGroup = startingQuantity * profile.getHitpoints();
	}

	public void takeHits(int hits){
		if(hits>totalHitPointsOfGroup){
			log.warn("More hits ("+hits+") assigned to group than remaining hitpoints "
					+totalHitPointsOfGroup+" left");
			totalHitPointsOfGroup=0;
			damageTakenToGroup = startingQuantity * profile.getHitpoints(); 
		}else{
			totalHitPointsOfGroup = totalHitPointsOfGroup - hits;
			damageTakenToGroup = damageTakenToGroup + hits;
			if(totalHitPointsOfGroup<remainingQuantity){
				remainingQuantity = totalHitPointsOfGroup;
			}
		}
	}
	
	public UnitName getType() {
		return type;
	}

	public int getStartingQuantity() {
		return startingQuantity;
	}

	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	public int getTotalHitPointsOfGroup() {
		return totalHitPointsOfGroup;
	}

	public int getDamageTakenToGroup() {
		return damageTakenToGroup;
	}

	public UnitProfile getProfile() {
		return profile;
	}
	public Territory getTerritorySource(){
		return territorySource;
	}
	
	
	
}
