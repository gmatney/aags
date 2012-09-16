package gmnk.boardgame.axisAndAllies.units;


import java.util.ArrayList;
import java.util.HashMap;

import gmnk.boardgame.axisAndAllies.territory.Territory;
import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import org.apache.log4j.Logger;

public class UnitUtils {
	private static Logger log = Logger.getLogger(UnitUtils.class);
	public static StationedGroup mergeStationedGroups(StationedGroup a, StationedGroup b, Territory t){
		if(!a.getWorldPowerAllegence().equals(b.getWorldPowerAllegence())){
			//Should prevent this beforehand (could use enums to enforce
			log.error("Forces from different groups trying to merge, they all die. Returning empty WorldPower");
			return new StationedGroup(t, WorldPowerName.UNKNOWN);
		}
		ArrayList<UnitConcrete> au = a.getStationedUnits();
		ArrayList<UnitConcrete> bu = b.getStationedUnits();
		StationedGroup n = new StationedGroup(t,a.getWorldPowerAllegence()); 
		for(UnitConcrete unit : au){
			n.addUnit(unit);
		}
		for(UnitConcrete unit : bu){
			n.addUnit(unit);
		}		
		return n;
		
	}
}
