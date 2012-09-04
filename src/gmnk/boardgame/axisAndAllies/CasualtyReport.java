package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.units.UnitGroup;

public class CasualtyReport {
	//TODO add the outcome of the battle
    private int attackerCasualties;
    private int defenderCasualties;
	public CasualtyReport(int attackerCasualties, int defenderCasualties){
		this.attackerCasualties = attackerCasualties;
		this.defenderCasualties = defenderCasualties;
	}

    public int getAttackerCasualties() {
        return attackerCasualties;
    }
    
    public int getDefenderCasualties() {
        return defenderCasualties;
    }
    

}
