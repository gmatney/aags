package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.territory.SeaTerritory;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.BattleGroup;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
import gmnk.boardgame.axisAndAllies.units.UnitConcrete;
import gmnk.boardgame.axisAndAllies.units.UnitGroup;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.sea.Submarine;
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
        int defendingSubCasualties = 0;
        int attackingGeneralCasualties = 0;
        int attackingAirCasualties = 0;
        int attackingSeaCasualties = 0;
        int attackingSubCasualties = 0;

        
        //TODO: Special combat (submarines, bombardments, etc)
        // Phase 2: general combat.
    	
    	
        //Need to get the attack path. And whether defending units are in attack path
    	Territory t = attackingUnits.getTargetTerritory();
    	if(t instanceof SeaTerritory){
    		ArrayList<UnitConcrete> seaUnits = attackingUnits.getSeaAttackUnits();
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
	    	defendingGeneralCasualties =+ getHitsCausedByStandardGroup(attackingUnits.getUnits(), true);
	    	defendingSubCasualties =+ getHitsCausedBySubs(attackingUnits.getUnits(), true);
	            
	        //DEFENDER
	    	attackingGeneralCasualties =+ getHitsCausedByStandardGroup(defendingUnits.getUnits(), false);
	    	attackingSubCasualties =+ getHitsCausedBySubs(defendingUnits.getUnits(), false);
    	}
        CasualtyReport casualties = new CasualtyReport(attackingGeneralCasualties, defendingGeneralCasualties,
        		attackingAirCasualties, defendingAirCasualties, attackingSeaCasualties, defendingSeaCasualties);
        return casualties;
    }
    
    public int getHitsCausedByStandardGroup(ArrayList<UnitConcrete> group, boolean attack){
        int hits = 0;
    	for(UnitConcrete unit : group) {
    		//TODO add check to make sure all units are standard
    		if(!(unit.getProfile() instanceof Submarine)) {
    			if(attack) {
		        	if(rollDie() <= unit.getProfile().getAttack()){
		        		hits++;
		        	}
    			}
    			else {
    				if(rollDie() <= unit.getProfile().getDefense()){
    	        		hits++;
    	        	}
    			}
    		}
        }
    	return hits;
    }    
    
    public int getHitsCausedBySubs(ArrayList<UnitConcrete> group, boolean attack) {
        int hits = 0;
    	for(UnitConcrete unit : group) {
    		//TODO add check to make sure all units are standard
    		if(unit.getProfile() instanceof Submarine) {
    			if(attack) {
		        	if(rollDie() <= unit.getProfile().getAttack()){
		        		hits++;
		        	}
    			}
    			else {
    				if(rollDie() <= unit.getProfile().getDefense()){
    	        		hits++;
    	        	}
    			}
    		}
        }
    	return hits;
    }
    
    // Rolls a standard six-sided die and returns the result.
    public int rollDie() {
        return randGenerator.nextInt(6) + 1;
    }
}
