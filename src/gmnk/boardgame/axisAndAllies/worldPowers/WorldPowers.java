package gmnk.boardgame.axisAndAllies.worldPowers;

import java.util.Hashtable;

public class WorldPowers {
	Hashtable<WorldPowerName,WorldPower> powers;
	public WorldPowers(){
		powers = new Hashtable<WorldPowerName,WorldPower>();
	}
	public void addWorldPower(WorldPower wp){
		powers.put(wp.getName(), wp);	
	}
	public int getPowersSize(){
		return powers.size();
	}
	public WorldPower getPower(WorldPowerName powerName) {
	    return powers.get(powerName);
	}
}
