package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.units.UnitGroup;

public class CasualtyReport {
	//TODO add the outcome of the battle
    private int attackerGeneralCasualties;
    private int attackerAirCasualties;
    private int attackerSeaCasualties;
    private int defenderGeneralCasualties;
    private int defenderAirCasualties;
    private int defenderSeaCasualties;
	public CasualtyReport(int attackerGeneralCasualties, int defenderGeneralCasualties,
    		int attackerAirCasualties, int defenderAirCasualties, int attackerSeaCasualties, int defenderSeaCasualties){
		this.attackerGeneralCasualties = attackerGeneralCasualties;
		this.attackerAirCasualties = attackerAirCasualties;
		this.attackerSeaCasualties = attackerSeaCasualties;
		this.defenderGeneralCasualties = defenderGeneralCasualties;
		this.defenderAirCasualties = defenderAirCasualties;
		this.defenderSeaCasualties = defenderSeaCasualties;
	}
	
	public int getAttackerTotalCasualties() {
		return attackerGeneralCasualties + attackerAirCasualties + attackerSeaCasualties;
	}
    public int getAttackerGeneralCasualties() {
        return attackerGeneralCasualties;
    }
    public int getAttackerAirCasualties() {
        return attackerAirCasualties;
    }
    public int getAttackerSeaCasualties() {
        return attackerSeaCasualties;
    }
    
    public int getDefenderTotalCasualties() {
		return defenderGeneralCasualties + defenderAirCasualties + defenderSeaCasualties;
	}
    public int getDefenderGeneralCasualties() {
        return defenderGeneralCasualties;
    }
    public int getDefenderAirCasualties() {
        return defenderAirCasualties;
    }
    public int getDefenderSeaCasualties() {
        return defenderSeaCasualties;
    }
    

}
