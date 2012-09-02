package gmnk.boardgame.axisAndAllies.worldPowers;

/** 
 * A player faction
 * @author garnett
 *
 */
public class WorldPower {
	protected Faction faction;
	protected String name;
	protected int startingIpcIncome;
	protected int currentIpcIncome;
	protected int ipcSavings;
	public WorldPower(String name, Faction faction,int startingIpcIncome){
		this.name = name;
		this.faction = faction;
		this.startingIpcIncome = startingIpcIncome;
		this.currentIpcIncome  = this.startingIpcIncome;
		this.ipcSavings        = this.startingIpcIncome;
	}
	public Faction getFaction() {
		return faction;
	}
	public String getName() {
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
	

}
