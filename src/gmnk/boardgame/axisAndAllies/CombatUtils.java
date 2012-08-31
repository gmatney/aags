package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.units.UnitGroup;
import gmnk.boardgame.axisAndAllies.units.UnitProfile;
import java.util.LinkedList;
import java.util.Random;

/**
 * A class for resolving combat. 
 */
public class CombatUtils {
    
    Random randGenerator;

    public CasualtyReport runCombatRound(UnitGroup attackingUnits, UnitGroup defendingUnits) {
        
        //TODO: Special combat (submarines, bombardments, etc)
        
        // Phase 2: general combat.
        int defendingCasualties = 0;
        int attackingCasualties = 0;
        for(UnitProfile unit : attackingUnits.units) {
            if(rollDie() <= unit.getAttack())
                defendingCasualties++;
        }
        for(UnitProfile unit : defendingUnits.units) {
            if(rollDie() <= unit.getDefense())
                attackingCasualties++;
        }
        
        CasualtyReport casualties = new CasualtyReport();
        casualties.setAttackerCasualties(attackingCasualties);
        casualties.setDefenderCasualties(defendingCasualties);
        
        return casualties;
    }
    
    // Rolls a standard six-sided die and returns the result.
    public int rollDie() {
        return randGenerator.nextInt(6) + 1;
    }
}
