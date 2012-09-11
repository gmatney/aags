package gmnk.boardgame.axisAndAllies;

import gmnk.boardgame.axisAndAllies.gameController.EnumInterpreter;
import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.UnitProfile;

import java.util.EnumMap;

public class PurchaseOrder {
	
	public EnumMap<UnitName, Integer> units;
	
	public PurchaseOrder() {
		units = new EnumMap<UnitName, Integer>(UnitName.class);
	}
	
	public int getTotalCost() {
		int total = 0;
		for(UnitName unit : units.keySet()) {
			UnitProfile profile = EnumInterpreter.getUnitProfile(unit);
			if(profile != null && profile.getUnitName() != UnitName.UNKNOWN) {
				total += profile.getCost();
			}
		}
		return total;
	}
	
	public void addUnit(UnitName unit) {
		if(units.containsKey(unit)) {
			units.put(unit, units.get(unit) + 1);
		}
		else {
			units.put(unit, 1);
		}
	}
	
	public void addUnit(UnitName unit, int amount) {
		if(units.containsKey(unit)) {
			units.put(unit, units.get(unit) + amount);
		}
		else {
			units.put(unit, amount);
		}
	}
	
	public void removeUnit(UnitName unit) {
		units.remove(unit);
	}
	
	public void removeUnit(UnitName unit, int amount) {
		if(units.containsKey(unit)) {
			int currentAmount = units.get(unit);
			units.put(unit, Math.max(0, currentAmount - amount));
		}
	} 
}
