package gmnk.boardgame.axisAndAllies.worldPowers;

import gmnk.boardgame.axisAndAllies.territory.Territory;

import java.awt.Color;
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
	private ArrayList<Territory> controlledTerritories;
	private Territory capitalTerritory;
	private Color guiColor;
	
	public WorldPower(WorldPowerName name, Faction faction,int startingIpcIncome, Territory capitalTerritory, Color guiColor){
		this.name = name;
		this.faction = faction;
		this.startingIpcIncome = startingIpcIncome;
		this.capitalTerritory  = capitalTerritory;
		this.currentIpcIncome  = this.startingIpcIncome;
		this.ipcSavings        = this.startingIpcIncome;
		controlledTerritories  = new ArrayList<Territory>();
		this.guiColor          = guiColor;
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
	public void addTerritory(Territory t){
		controlledTerritories.add(t);
	}
	public String removeTerritory(String t){
		controlledTerritories.remove(t);
		return t;
	}
	public ArrayList<Territory> getControlledTerritories(){
		return controlledTerritories;
	}
	public int getUnitPlacementCount() {
		int total = 0;
		for(Territory t : controlledTerritories) {
			total += t.getUnitPlacementCount(name);
		}
		return total;
	}
	public Territory getCapitalTerritory(){
		return capitalTerritory;
	}
	public Color getColor() {
	    return guiColor;
	}
	public void setColor(Color guiColor) {
        this.guiColor = guiColor;
    }
}
