package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.units.UnitGroup;

public class CasualtyReport {

    private int attackerCasualties;
    private int defenderCasualties;
    
    public int getAttackerCasualties() {
        return attackerCasualties;
    }
    
    public void setAttackerCasualties(int numCasualties) {
        attackerCasualties = numCasualties;
    }
    
    public int getDefenderCasualties() {
        return defenderCasualties;
    }
    
    public void setDefenderCasualties(int numCasualties) {
        defenderCasualties = numCasualties;
    }
}
