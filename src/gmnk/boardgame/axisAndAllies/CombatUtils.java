package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.territory.SeaTerritory;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.BattleGroup;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitConcrete;
import gmnk.boardgame.axisAndAllies.units.UnitGroup;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * A class for resolving combat. 
 */
public class CombatUtils {
    
    Random randGenerator;

    //TODO write event handlers to send for instructions (death after
    public CasualtyReport runCombat(BattleGroup attackingUnits, StationedGroup defendingUnits) {
        int defendingGeneralCasualties = 0;
        int defendingAirCasualties = 0;
        int defendingSeaCasualties = 0;
        int attackingGeneralCasualties = 0;
        int attackingAirCasualties = 0;
        int attackingSeaCasualties = 0;
        
        //TODO: Special combat (submarines, bombardments, etc)
        // Phase 2: general combat.
    	
    	
    	Territory t = attackingUnits.getTargetTerritory();
    	if(t instanceof SeaTerritory){
    		//NAVAL WARFARE
    	}
    	else{ //LAND WARFARE
    		// TODO: detect if naval battle has occurred, if so then no bombardments are possible.
    		int numBombardments = attackingUnits.getNumTransportedUnits();
    		ArrayList<UnitConcrete> bombardUnits = attackingUnits.getBombardUnits(numBombardments);
    		for(int i = 0; i < numBombardments; i++) {
    			if(rollDie() <= bombardUnits.get(i).getProfile().getAttack()) {
    				defendingGeneralCasualties++;
    			}
    		}

    		//TODO add AAGUN support 
    		
    		//ATTACKER
	    	defendingGeneralCasualties =+ getHitsCausedByStandardAttackingGroup(attackingUnits.getLandUnits());
	    	defendingGeneralCasualties =+ getHitsCausedByStandardAttackingGroup(attackingUnits.getAirUnits());
	            
	        //DEFENDER
	    	attackingGeneralCasualties =+ getHitsCausedByStandardDefendingGroup(defendingUnits.getLandUnits());
	    	attackingGeneralCasualties =+ getHitsCausedByStandardDefendingGroup(defendingUnits.getAirUnits());
    	}
        CasualtyReport casualties = new CasualtyReport(attackingGeneralCasualties, defendingGeneralCasualties,
        		attackingAirCasualties, defendingAirCasualties, attackingSeaCasualties, defendingSeaCasualties);
        return casualties;
    }
    public int getHitsCausedByStandardAttackingGroup(ArrayList<UnitConcrete> attackers){
        int hits = 0;
    	for(UnitConcrete unit : attackers) {
    		//TODO add check to make sure all units are standard
        	if(rollDie() <= unit.getProfile().getAttack()){
        		hits++;
        	}
        }
    	return hits;
    }
    
    public int getHitsCausedByStandardDefendingGroup(ArrayList<UnitConcrete> defenders){
    	int hits = 0;
    	for(UnitConcrete unit : defenders) {
    		//TODO add check to make sure all units are standard
        	if(rollDie() <= unit.getProfile().getAttack()){
        		hits++;
        	}
        }    	
    	return hits;
    }
    
    
    
    // Rolls a standard six-sided die and returns the result.
    public int rollDie() {
        return randGenerator.nextInt(6) + 1;
    }
}
