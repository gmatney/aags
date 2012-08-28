package gmnk.boardgame.axisAndAllies.land;

import java.util.LinkedList;

public class Territory {
	
	public String name;
	public int ipcValue;
	public boolean hasVictoryCity;
	public LinkedList<Territory> neighbors;
}
