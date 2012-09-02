package gmnk.boardgame.axisAndAllies.worldPowers;

import java.util.Hashtable;

public class WorldPowers {
	Hashtable<String,WorldPower> powers;
	public WorldPowers(){
		powers = new Hashtable<String,WorldPower>();
	}
	public void addWorldPower(WorldPower wp){
		powers.put(wp.name, wp);	
	}
	public int getPowersSize(){
		return powers.size();
	}
}
