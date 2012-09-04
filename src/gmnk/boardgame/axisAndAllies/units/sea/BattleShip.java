package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class BattleShip extends SeaUnit{
	public BattleShip(){
		cost	  = 20;
		attack 	  = 4;
		defense   = 4;
		move 	  = 2;
		hitpoints = 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.BATTLESHIP;
	}
}
