package gmnk.boardgame.axisAndAllies.worldPowers;

import java.util.ArrayList;

/** 
 * A player faction
 * @author garnett
 *
 */
public class WorldPower {
	private Faction faction;
	private WorldPowerName name;
	private int startingIpcIncome;
	private int currentIpcIncome;
	private int ipcSavings;
	private ArrayList<String> controlledTerritories;
	private String capitalTerritory;
	
	public WorldPower(WorldPowerName name, Faction faction,int startingIpcIncome, String capitalTerritory){
		this.name = name;
		this.faction = faction;
		this.startingIpcIncome = startingIpcIncome;
		this.capitalTerritory  = capitalTerritory;
		this.currentIpcIncome  = this.startingIpcIncome;
		this.ipcSavings        = this.startingIpcIncome;
		controlledTerritories  = new ArrayList<String>();
	}
	public Faction getFaction() {
		return faction;
	}
	public WorldPowerName getName() {
		return name;
	}
	public int getStartingIpcIncome() {
		return startingIpcIncome;
	}
	public int getCurrentIpcIncome() {
		return currentIpcIncome;
	}
	public void setCurrentIpcIncome(int currentIpcIncome) {
		this.currentIpcIncome = currentIpcIncome;
	}
	public int getIpcSavings() {
		return ipcSavings;
	}
	public void setIpcSavings(int ipcSavings) {
		this.ipcSavings = ipcSavings;
	}
	public void addTerritory(String t){
		controlledTerritories.add(t);
	}
	public String removeTerritory(String t){
		controlledTerritories.remove(t);
		return t;
	}
	public String getCapitalTerritory(){
		return capitalTerritory;
	}

}
