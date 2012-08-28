package gmnk.boardgame.axisAndAllies.units;

import gmnk.boardgame.axisAndAllies.land.Territory;

public class UnitProfile {
	protected int cost;
	protected int attack;
	protected int defense;
	protected int move;
	protected int hitpoints = 1;
		

	public int getCost() {
		return cost;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getMove() {
		return move;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}
	

	public boolean canReachTerritoryToAttack(Territory Territory) {
		return false;
	}
	
	// Within Range
}
