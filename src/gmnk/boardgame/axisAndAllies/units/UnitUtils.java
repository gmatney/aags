package gmnk.boardgame.axisAndAllies.units;


import java.util.HashMap;

import gmnk.boardgame.axisAndAllies.worldPowers.WorldPowerName;

import org.apache.log4j.Logger;

public class UnitUtils {
	private static Logger log = Logger.getLogger(UnitUtils.class);
	public static StationedGroup mergeStationedGroups(StationedGroup a, StationedGroup b){
		if(!a.getWorldPowerAllegence().equals(b.getWorldPowerAllegence())){
			//Should prevent this beforehand (could use enums to enforce
			log.error("Forces from different groups trying to merge, they all die. Returning empty WorldPower");
			return new StationedGroup(WorldPowerName.UNKNOWN);
		}
		HashMap<UnitName,Integer> au = a.getStationedUnits();
		HashMap<UnitName,Integer> bu = a.getStationedUnits();
		StationedGroup n = new StationedGroup(a.getWorldPowerAllegence()); 
		for(UnitName key: au.keySet()){
			n.addUnit(key, au.get(key));
		}
		for(UnitName key: bu.keySet()){
			n.addUnit(key, bu.get(key));
		}		
		return n;
		
	}
}
