package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.territory.SeaTerritory;
import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.units.BattleGroup;
import gmnk.boardgame.axisAndAllies.units.StationedGroup;
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
        int defendingCasualties = 0;
        int attackingCasualties = 0;
        
        //TODO: Special combat (submarines, bombardments, etc)
        // Phase 2: general combat.
    	
    	
    	Territory t = attackingUnits.getTargetTerritory();
    	if(t instanceof SeaTerritory){
    		//NAVAL WARFARE
    	}
    	else{ //LAND WARFARE
    		//TODO add Bombardments 

    		//TODO add AAGUN support 
    		
    		//ATTACKER
    		defendingCasualties =+ getHitsCausedByStandardAttackingGroup(attackingUnits.getLandUnits());
    		defendingCasualties =+ getHitsCausedByStandardAttackingGroup(attackingUnits.getAirUnits());
            
            //DEFENDER
    		attackingCasualties =+ getHitsCausedByStandardDefendingGroup(defendingUnits.getLandUnits());
    		attackingCasualties =+ getHitsCausedByStandardDefendingGroup(defendingUnits.getAirUnits());
    	}
        CasualtyReport casualties = new CasualtyReport(attackingCasualties,defendingCasualties);
        return casualties;
    }
    public int getHitsCausedByStandardAttackingGroup(ArrayList<UnitGroup>  attackers){
        int hits = 0;
    	for(UnitGroup unit : attackers) {
    		//TODO add check to make sure all units are standard
        	for(int i=0; i< unit.getRemainingQuantity(); i++){
        		if(rollDie() <= unit.getProfile().getAttack()){
        			hits++;
        		}
        	}
        }
    	return hits;
    }
    
    public int getHitsCausedByStandardDefendingGroup(HashMap<UnitName, UnitGroup> defenders){
    	int hits = 0;
    	for(UnitGroup unit : defenders.values()) {
    		//TODO add check to make sure all units are standard
        	for(int i=0; i< unit.getRemainingQuantity(); i++){
        		if(rollDie() <= unit.getProfile().getAttack()){
        			hits++;
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
