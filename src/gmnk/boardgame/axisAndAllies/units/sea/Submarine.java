package gmnk.boardgame.axisAndAllies.units.sea;

import gmnk.boardgame.axisAndAllies.units.UnitName;
import gmnk.boardgame.axisAndAllies.units.types.SeaUnit;

public class Submarine extends SeaUnit{
	public Submarine(){
		cost	= 6;
		attack 	= 2;
		defense = 1;
		move 	= 2;
	}
	@Override
	public UnitName getUnitName() {
		return UnitName.SUBMARINE;
	}
}
